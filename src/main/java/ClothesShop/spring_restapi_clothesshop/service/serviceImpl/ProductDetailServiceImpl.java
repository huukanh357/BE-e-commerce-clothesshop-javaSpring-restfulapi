package ClothesShop.spring_restapi_clothesshop.service.serviceImpl;

import ClothesShop.spring_restapi_clothesshop.dto.productDetail.ProductDetailCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.productDetail.ProductDetailResponse;
import ClothesShop.spring_restapi_clothesshop.dto.productDetail.ProductDetailUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.exception.DuplicateResourceException;
import ClothesShop.spring_restapi_clothesshop.exception.ResourceNotFoundException;
import ClothesShop.spring_restapi_clothesshop.model.Product;
import ClothesShop.spring_restapi_clothesshop.model.ProductDetail;
import ClothesShop.spring_restapi_clothesshop.repository.ProductDetailRepository;
import ClothesShop.spring_restapi_clothesshop.repository.ProductRepository;
import ClothesShop.spring_restapi_clothesshop.service.ProductDetailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailRepository productDetailRepository;
    private final ProductRepository productRepository;

    public ProductDetailServiceImpl(ProductDetailRepository productDetailRepository,
            ProductRepository productRepository) {
        this.productDetailRepository = productDetailRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ProductDetailResponse createProductDetail(ProductDetailCreateRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", request.getProductId()));

        if (productDetailRepository.existsByProduct_IdAndSizeAndColor(
                request.getProductId(), request.getSize(), request.getColor())) {
            throw new DuplicateResourceException(
                    "ProductDetail", "productId-size-color",
                    request.getProductId() + "-" + request.getSize() + "-" + request.getColor());
        }

        ProductDetail detail = new ProductDetail();
        detail.setProduct(product);
        detail.setSize(request.getSize());
        detail.setColor(request.getColor());
        detail.setStockQuantity(request.getStockQuantity());

        ProductDetail savedDetail = productDetailRepository.save(detail);
        return ProductDetailResponse.fromEntity(savedDetail);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDetailResponse getProductDetailById(Long id) {
        return ProductDetailResponse.fromEntity(findProductDetailByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDetailResponse> getAllProductDetails(Pageable pageable) {
        return productDetailRepository.findAll(pageable).map(ProductDetailResponse::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDetailResponse> getProductDetailsByProductId(Long productId, Pageable pageable) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product", "id", productId);
        }
        return productDetailRepository.findByProduct_Id(productId, pageable).map(ProductDetailResponse::fromEntity);
    }

    @Override
    public ProductDetailResponse updateProductDetail(Long id, ProductDetailUpdateRequest request) {
        ProductDetail detail = findProductDetailByIdOrThrow(id);

        String newSize = request.getSize() != null ? request.getSize() : detail.getSize();
        String newColor = request.getColor() != null ? request.getColor() : detail.getColor();

        productDetailRepository.findByProduct_IdAndSizeAndColor(detail.getProduct().getId(), newSize, newColor)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(detail.getId())) {
                        throw new DuplicateResourceException(
                                "ProductDetail", "productId-size-color",
                                detail.getProduct().getId() + "-" + newSize + "-" + newColor);
                    }
                });

        if (request.getSize() != null) {
            detail.setSize(request.getSize());
        }
        if (request.getColor() != null) {
            detail.setColor(request.getColor());
        }
        if (request.getStockQuantity() != null) {
            detail.setStockQuantity(request.getStockQuantity());
        }

        ProductDetail updatedDetail = productDetailRepository.save(detail);
        return ProductDetailResponse.fromEntity(updatedDetail);
    }

    @Override
    public void deleteProductDetail(Long id) {
        findProductDetailByIdOrThrow(id);
        productDetailRepository.deleteById(id);
    }

    private ProductDetail findProductDetailByIdOrThrow(Long id) {
        return productDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductDetail", "id", id));
    }
}
