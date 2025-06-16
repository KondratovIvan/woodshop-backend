package devops.ecom.productservice.web;

import devops.ecom.productservice.dao.entities.*;
import devops.ecom.productservice.dao.enums.PageEventType;
import devops.ecom.productservice.dao.enums.ProductCategory;
import devops.ecom.productservice.service.ProductService;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/products")
@CrossOrigin(origins = "*")
public class ProductRestController {
    private final ProductService productService;
    private final StreamBridge streamBridge;

    public ProductRestController(ProductService productService, StreamBridge streamBridge) {
        this.productService = productService;
        this.streamBridge = streamBridge;
    }

    @GetMapping("{size}")
    public PageInfo getProductsPageInfo(@PathVariable int size) {
        return this.productService.getProductPageInfo(size);
    }

    @GetMapping("find/{productId}")
    public Product getProductById(@PathVariable String productId) {
        return this.productService.getProductById(productId);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return this.productService.createProduct(product);
    }

    @PutMapping("{productId}")
    public Product update(@PathVariable String productId, @RequestBody Product product) {
        return this.productService.updateProduct(productId, product);
    }

    @GetMapping("event/click/{productName:.+}/{customerId}")
    public void catchEventType(@PathVariable String productName, @PathVariable String customerId) {
        PageEventClickProduct event = PageEventClickProduct.builder()
                .productName(productName)
                .type(PageEventType.CLICK_PRODUCT)
                .date(new Date())
                .userId(customerId)
                .build();
        this.streamBridge.send("R1", event);
    }

    @GetMapping("event/category/{category}/{customerId}")
    public void catchSearchByCategoryEvent(@PathVariable ProductCategory category, @PathVariable String customerId) {
        PageEventSearchByCategory event = PageEventSearchByCategory.builder()
                .category(category)
                .type(PageEventType.SEARCH_BY_CATEGORY)
                .date(new Date())
                .userId(customerId)
                .build();
        this.streamBridge.send("R1", event);
    }

    @GetMapping("event/keyword/{keyword}/{customerId}")
    public void catchSearchByKeywordEvent(@PathVariable String keyword, @PathVariable String customerId) {
        PageEventSearchByKeyword event = PageEventSearchByKeyword.builder()
                .keyword(keyword)
                .type(PageEventType.SEARCH_BY_KEYWORD)
                .date(new Date())
                .userId(customerId)
                .build();
        this.streamBridge.send("R1", event);
    }
}
