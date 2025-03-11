package devops.ecom.customerservice.service;

import devops.ecom.customerservice.web.model.MonobankInvoiceRequest;
import devops.ecom.customerservice.web.model.MonobankInvoiceResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MonobankService {
    private static final String MONOBANK_API_URL = "https://api.monobank.ua/api/merchant/invoice/create";
    private static final String MONOBANK_API_TOKEN = "uHuevGSB4NpYxstom5D6gvwwzdNLG_PCJFQzwttCiXR8"; // замените на свой

    private final RestTemplate restTemplate;

    public MonobankService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MonobankInvoiceResponse createInvoice(MonobankInvoiceRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Token", MONOBANK_API_TOKEN);

        HttpEntity<MonobankInvoiceRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<MonobankInvoiceResponse> response = restTemplate.exchange(
                MONOBANK_API_URL, HttpMethod.POST, entity, MonobankInvoiceResponse.class);

        return response.getBody();
    }
}

