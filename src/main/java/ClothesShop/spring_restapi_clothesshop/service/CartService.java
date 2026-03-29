package ClothesShop.spring_restapi_clothesshop.service;

import ClothesShop.spring_restapi_clothesshop.dto.cart.CartCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.cart.CartResponse;
import ClothesShop.spring_restapi_clothesshop.dto.cart.CartUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.cart.CartUserResponse;
import ClothesShop.spring_restapi_clothesshop.dto.cartDetail.CartItemRequest;
import ClothesShop.spring_restapi_clothesshop.dto.cartDetail.CartItemUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartService {

    // ========== ADMIN ==========

    CartResponse createCart(CartCreateRequest request);

    CartResponse getCartById(Long id);

    CartResponse getCartByUserId(Long userId);

    Page<CartResponse> getAllCarts(Pageable pageable);

    CartResponse updateCart(Long id, CartUpdateRequest request);

    void deleteCart(Long id);

    // ========== USER (me) ==========

    CartUserResponse getMyCart(Long userId);

    CartUserResponse addItemToMyCart(Long userId, CartItemRequest request);

    CartUserResponse updateMyCartItem(Long userId, Long itemId, CartItemUpdateRequest request);

    CartUserResponse deleteMyCartItem(Long userId, Long itemId);

    CartUserResponse clearMyCart(Long userId);
}
