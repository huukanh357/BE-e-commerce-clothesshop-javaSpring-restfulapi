package ClothesShop.spring_restapi_clothesshop.service;

import ClothesShop.spring_restapi_clothesshop.dto.productDetail.ProductDetailCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.productDetail.ProductDetailResponse;
import ClothesShop.spring_restapi_clothesshop.dto.productDetail.ProductDetailUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDetailService {

    ProductDetailResponse createProductDetail(ProductDetailCreateRequest request);

    ProductDetailResponse getProductDetailById(Long id);

    Page<ProductDetailResponse> getAllProductDetails(Pageable pageable);

    Page<ProductDetailResponse> getProductDetailsByProductId(Long productId, Pageable pageable);

    ProductDetailResponse updateProductDetail(Long id, ProductDetailUpdateRequest request);

    void deleteProductDetail(Long id);
}
