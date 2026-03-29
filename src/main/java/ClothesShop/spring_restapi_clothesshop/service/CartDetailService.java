package ClothesShop.spring_restapi_clothesshop.service;

import ClothesShop.spring_restapi_clothesshop.dto.cartDetail.CartDetailCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.cartDetail.CartDetailResponse;
import ClothesShop.spring_restapi_clothesshop.dto.cartDetail.CartDetailUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartDetailService {

    CartDetailResponse createCartDetail(CartDetailCreateRequest request);

    CartDetailResponse getCartDetailById(Long id);

    Page<CartDetailResponse> getAllCartDetails(Pageable pageable);

    Page<CartDetailResponse> getCartDetailsByCartId(Long cartId, Pageable pageable);

    CartDetailResponse updateCartDetail(Long id, CartDetailUpdateRequest request);

    void deleteCartDetail(Long id);
}
