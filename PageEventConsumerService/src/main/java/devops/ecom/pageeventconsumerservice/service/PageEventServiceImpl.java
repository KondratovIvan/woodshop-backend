package devops.ecom.pageeventconsumerservice.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import devops.ecom.pageeventconsumerservice.entities.PageEventClickProduct;
import devops.ecom.pageeventconsumerservice.entities.PageEventSearchByCategory;
import devops.ecom.pageeventconsumerservice.entities.PageEventSearchByKeyword;
import devops.ecom.pageeventconsumerservice.repos.PageEventClickProductRepo;
import devops.ecom.pageeventconsumerservice.repos.PageEventSearchByCategoryRepo;
import devops.ecom.pageeventconsumerservice.repos.PageEventSearchByKeywordRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.function.Consumer;

@Service
@Slf4j
public class PageEventServiceImpl implements PageEventService {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final PageEventClickProductRepo pageEventClickProductRepo;
    private final PageEventSearchByCategoryRepo pageEventSearchByCategoryRepo;
    private final PageEventSearchByKeywordRepo pageEventSearchByKeywordRepo;

    public PageEventServiceImpl(
            PageEventClickProductRepo pageEventClickProductRepo,
            PageEventSearchByCategoryRepo pageEventSearchByCategoryRepo,
            PageEventSearchByKeywordRepo pageEventSearchByKeywordRepo) {
        this.pageEventClickProductRepo = pageEventClickProductRepo;
        this.pageEventSearchByCategoryRepo = pageEventSearchByCategoryRepo;
        this.pageEventSearchByKeywordRepo = pageEventSearchByKeywordRepo;
    }

    @Bean
    public Consumer<String> pageEventConsumer() {
        return (input) -> {
            log.info("Received page event: {}", input);
            String pageEventJson = decodeBase64String(input);

            try {
                // First parse as JsonNode to determine event type
                JsonNode eventNode = objectMapper.readTree(pageEventJson);
                String eventType = eventNode.has("type") ? eventNode.get("type").asText() : "";

                switch (eventType) {
                    case "CLICK_PRODUCT":
                        PageEventClickProduct clickEvent = parseJsonToObject(pageEventJson, PageEventClickProduct.class);
                        pageEventClickProductRepo.insert(clickEvent);
                        log.info("Saved CLICK_PRODUCT event: {}", clickEvent);
                        break;

                    case "SEARCH_BY_CATEGORY":
                        PageEventSearchByCategory categoryEvent = parseJsonToObject(pageEventJson, PageEventSearchByCategory.class);
                        pageEventSearchByCategoryRepo.insert(categoryEvent);
                        log.info("Saved SEARCH_BY_CATEGORY event: {}", categoryEvent);
                        break;

                    case "SEARCH_BY_KEYWORD":
                        PageEventSearchByKeyword keywordEvent = parseJsonToObject(pageEventJson, PageEventSearchByKeyword.class);
                        pageEventSearchByKeywordRepo.insert(keywordEvent);
                        log.info("Saved SEARCH_BY_KEYWORD event: {}", keywordEvent);
                        break;

                    default:
                        log.warn("Unknown event type: {}", eventType);
                }
            } catch (Exception e) {
                log.error("Error processing page event: {}", e.getMessage(), e);
                throw new RuntimeException("Error processing page event", e);
            }
        };
    }

    public static String decodeBase64String(String base64String) {
        // Check if the decoded string starts and ends with double quotes
        if (base64String.startsWith("\"") && base64String.endsWith("\"")) {
            // Remove the double quotes
            base64String = base64String.substring(1, base64String.length() - 1);
        }
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        return new String(decodedBytes);
    }

    public static <T> T parseJsonToObject(String jsonString, Class<T> valueType) throws Exception {
        return objectMapper.readValue(jsonString, valueType);
    }
}