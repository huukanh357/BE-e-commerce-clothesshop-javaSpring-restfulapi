package ClothesShop.spring_restapi_clothesshop.service.serviceImpl;

import ClothesShop.spring_restapi_clothesshop.exception.AppException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ClothesShop.spring_restapi_clothesshop.dto.productDetail.ProductDetailCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.productDetail.ProductDetailResponse;
import ClothesShop.spring_restapi_clothesshop.dto.productDetail.ProductDetailUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.model.Product;
import ClothesShop.spring_restapi_clothesshop.model.ProductDetail;
import ClothesShop.spring_restapi_clothesshop.repository.ProductDetailRepository;
import ClothesShop.spring_restapi_clothesshop.repository.ProductRepository;
import ClothesShop.spring_restapi_clothesshop.service.ProductDetailService;
import ClothesShop.spring_restapi_clothesshop.mapper.ProductDetailMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductDetailServiceImpl implements ProductDetailService {

    ProductDetailRepository productDetailRepository;
    ProductRepository productRepository;
    ProductDetailMapper productDetailMapper;

    @Override
    public ProductDetailResponse createProductDetail(ProductDetailCreateRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> AppException.resourceNotFound("Product", "id", request.getProductId()));

        if (productDetailRepository.existsByProduct_IdAndSizeAndColor(
                request.getProductId(), request.getSize(), request.getColor())) {
            throw AppException.duplicateResource(
                    "ProductDetail", "productId-size-color",
                    request.getProductId() + "-" + request.getSize() + "-" + request.getColor());
        }

        ProductDetail detail = productDetailMapper.toEntity(request);
        detail.setProduct(product);

        ProductDetail savedDetail = productDetailRepository.save(detail);
        return productDetailMapper.toResponse(savedDetail);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDetailResponse getProductDetailById(Long id) {
        return productDetailMapper.toResponse(findProductDetailByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDetailResponse> getAllProductDetails(Pageable pageable) {
        return productDetailRepository.findAll(pageable).map(productDetailMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDetailResponse> getProductDetailsByProductId(Long productId, Pageable pageable) {
        if (!productRepository.existsById(productId)) {
            throw AppException.resourceNotFound("Product", "id", productId);
        }
        return productDetailRepository.findByProduct_Id(productId, pageable).map(productDetailMapper::toResponse);
    }

    @Override
    public ProductDetailResponse updateProductDetail(Long id, ProductDetailUpdateRequest request) {
        ProductDetail detail = findProductDetailByIdOrThrow(id);

        String newSize = request.getSize() != null ? request.getSize() : detail.getSize();
        String newColor = request.getColor() != null ? request.getColor() : detail.getColor();

        productDetailRepository.findByProduct_IdAndSizeAndColor(detail.getProduct().getId(), newSize, newColor)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(detail.getId())) {
                        throw AppException.duplicateResource(
                                "ProductDetail", "productId-size-color",
                                detail.getProduct().getId() + "-" + newSize + "-" + newColor);
                    }
                });

        productDetailMapper.updateFromRequest(request, detail);

        ProductDetail updatedDetail = productDetailRepository.save(detail);
        return productDetailMapper.toResponse(updatedDetail);
    }

    @Override
    public void deleteProductDetail(Long id) {
        findProductDetailByIdOrThrow(id);
        productDetailRepository.deleteById(id);
    }

    private ProductDetail findProductDetailByIdOrThrow(Long id) {
        return productDetailRepository.findById(id)
                .orElseThrow(() -> AppException.resourceNotFound("ProductDetail", "id", id));
    }
}