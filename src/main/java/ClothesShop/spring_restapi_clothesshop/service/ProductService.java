package ClothesShop.spring_restapi_clothesshop.service;

import ClothesShop.spring_restapi_clothesshop.dto.product.ProductCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.product.ProductFilterRequest;
import ClothesShop.spring_restapi_clothesshop.dto.product.ProductResponse;
import ClothesShop.spring_restapi_clothesshop.dto.product.ProductUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductCreateRequest request);

    ProductResponse getProductById(Long id);

    ProductResponse getProductByName(String name);

    Page<ProductResponse> getAllProducts(Pageable pageable);

    Page<ProductResponse> getProductsByCategory(String categoryName, Pageable pageable);

    Page<ProductResponse> filterProducts(ProductFilterRequest filterRequest, Pageable pageable);

    ProductResponse updateProduct(Long id, ProductUpdateRequest request);

    ProductResponse uploadProductImages(Long id, List<MultipartFile> files);

    void deleteProduct(Long id);

    boolean existsByName(String name);
}