package ClothesShop.spring_restapi_clothesshop.service.serviceImpl;

import ClothesShop.spring_restapi_clothesshop.exception.AppException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ClothesShop.spring_restapi_clothesshop.dto.cartDetail.CartDetailCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.cartDetail.CartDetailResponse;
import ClothesShop.spring_restapi_clothesshop.dto.cartDetail.CartDetailUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.model.Cart;
import ClothesShop.spring_restapi_clothesshop.model.CartDetail;
import ClothesShop.spring_restapi_clothesshop.model.ProductDetail;
import ClothesShop.spring_restapi_clothesshop.repository.CartDetailRepository;
import ClothesShop.spring_restapi_clothesshop.repository.CartRepository;
import ClothesShop.spring_restapi_clothesshop.repository.ProductDetailRepository;
import ClothesShop.spring_restapi_clothesshop.service.CartDetailService;
import ClothesShop.spring_restapi_clothesshop.mapper.CartDetailMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CartDetailServiceImpl implements CartDetailService {

    CartDetailRepository cartDetailRepository;
    CartRepository cartRepository;
    ProductDetailRepository productDetailRepository;
    CartDetailMapper cartDetailMapper;

    @Override
    public CartDetailResponse createCartDetail(CartDetailCreateRequest request) {
        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> AppException.resourceNotFound("Cart", "id", request.getCartId()));
        ProductDetail productDetail = productDetailRepository.findById(request.getProductDetailId())
                .orElseThrow(() -> AppException.resourceNotFound("ProductDetail", "id", request.getProductDetailId()));

        if (cartDetailRepository.existsByCart_IdAndProductDetail_Id(request.getCartId(),
                request.getProductDetailId())) {
            throw AppException.duplicateResource(
                    "CartDetail",
                    "cartId-productDetailId",
                    request.getCartId() + "-" + request.getProductDetailId());
        }

        CartDetail cartDetail = cartDetailMapper.toEntity(request);
        cartDetail.setCart(cart);
        cartDetail.setProductDetail(productDetail);

        CartDetail savedCartDetail = cartDetailRepository.save(cartDetail);
        return cartDetailMapper.toResponse(savedCartDetail);
    }

    @Override
    @Transactional(readOnly = true)
    public CartDetailResponse getCartDetailById(Long id) {
        return cartDetailMapper.toResponse(findCartDetailByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CartDetailResponse> getAllCartDetails(Pageable pageable) {
        return cartDetailRepository.findAll(pageable).map(cartDetailMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CartDetailResponse> getCartDetailsByCartId(Long cartId, Pageable pageable) {
        if (!cartRepository.existsById(cartId)) {
            throw AppException.resourceNotFound("Cart", "id", cartId);
        }
        return cartDetailRepository.findByCart_Id(cartId, pageable).map(cartDetailMapper::toResponse);
    }

    @Override
    public CartDetailResponse updateCartDetail(Long id, CartDetailUpdateRequest request) {
        CartDetail cartDetail = findCartDetailByIdOrThrow(id);

        if (request.getQuantity() != null) {
            cartDetail.setQuantity(request.getQuantity());
        }

        CartDetail updatedCartDetail = cartDetailRepository.save(cartDetail);
        return cartDetailMapper.toResponse(updatedCartDetail);
    }

    @Override
    public void deleteCartDetail(Long id) {
        findCartDetailByIdOrThrow(id);
        cartDetailRepository.deleteById(id);
    }

    private CartDetail findCartDetailByIdOrThrow(Long id) {
        return cartDetailRepository.findById(id)
                .orElseThrow(() -> AppException.resourceNotFound("CartDetail", "id", id));
    }

}