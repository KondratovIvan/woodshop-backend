package devops.ecom.productservice.service;

import devops.ecom.productservice.dao.entities.PageInfo;
import devops.ecom.productservice.dao.entities.Price;
import devops.ecom.productservice.dao.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(String productId, Product product);
    void deleteProduct(String productId);
    Product getProductById(String productId) ;
    List<Product> getProductsByCategory() ;
    List<Product> getProductsByName(String name) ;
    List<Product> getAllProduct();
    List<Product> getProductPage(int page , int size);
    void initProduct() ;
    PageInfo getProductPageInfo(int size);
}
