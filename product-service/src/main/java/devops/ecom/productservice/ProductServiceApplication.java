package devops.ecom.productservice;

import devops.ecom.productservice.dao.entities.Product;
import devops.ecom.productservice.dao.repos.ProductRepository;
import devops.ecom.productservice.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
@EnableFeignClients
public class ProductServiceApplication {
    private ProductRepository productRepository;
    private RepositoryRestConfiguration restConfiguration;
    private ProductService productService;

    public ProductServiceApplication(ProductRepository productRepository, RepositoryRestConfiguration restConfiguration, ProductService productService) {
        this.productRepository = productRepository;
        this.restConfiguration = restConfiguration;
        this.productService = productService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            restConfiguration.exposeIdsFor(Product.class);
//            this.productService.initProduct();
        };
    }

}
