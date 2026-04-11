package ClothesShop.spring_restapi_clothesshop.service.serviceImpl;

import ClothesShop.spring_restapi_clothesshop.exception.AppException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ClothesShop.spring_restapi_clothesshop.dto.cart.CartCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.cart.CartResponse;
import ClothesShop.spring_restapi_clothesshop.dto.cart.CartUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.cart.CartUserResponse;
import ClothesShop.spring_restapi_clothesshop.dto.cartDetail.CartItemRequest;
import ClothesShop.spring_restapi_clothesshop.dto.cartDetail.CartItemUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.model.Cart;
import ClothesShop.spring_restapi_clothesshop.model.CartDetail;
import ClothesShop.spring_restapi_clothesshop.model.ProductDetail;
import ClothesShop.spring_restapi_clothesshop.model.User;
import ClothesShop.spring_restapi_clothesshop.repository.CartDetailRepository;
import ClothesShop.spring_restapi_clothesshop.repository.CartRepository;
import ClothesShop.spring_restapi_clothesshop.repository.ProductDetailRepository;
import ClothesShop.spring_restapi_clothesshop.repository.UserRepository;
import ClothesShop.spring_restapi_clothesshop.service.CartService;
import ClothesShop.spring_restapi_clothesshop.mapper.CartMapper;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;
    CartDetailRepository cartDetailRepository;
    UserRepository userRepository;
    ProductDetailRepository productDetailRepository;
    CartMapper cartMapper;

    @Override
    public CartResponse createCart(CartCreateRequest request) {
        User user = findUserByIdOrThrow(request.getUserId());

        if (cartRepository.existsByUser_Id(request.getUserId())) {
            throw AppException.duplicateResource("Cart", "userId", request.getUserId());
        }

        Cart cart = new Cart();
        cart.setUser(user);

        return cartMapper.toResponse(cartRepository.save(cart));
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCartById(Long id) {
        return cartMapper.toResponse(findCartByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> AppException.resourceNotFound("Cart", "userId", userId));
        return cartMapper.toResponse(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CartResponse> getAllCarts(Pageable pageable) {
        return cartRepository.findAll(pageable).map(cartMapper::toResponse);
    }

    @Override
    public CartResponse updateCart(Long id, CartUpdateRequest request) {
        Cart cart = findCartByIdOrThrow(id);

        if (request.getUserId() != null && !request.getUserId().equals(cart.getUser().getId())) {
            User user = findUserByIdOrThrow(request.getUserId());

            if (cartRepository.existsByUser_Id(request.getUserId())) {
                throw AppException.duplicateResource("Cart", "userId", request.getUserId());
            }

            cart.setUser(user);
        }

        return cartMapper.toResponse(cartRepository.save(cart));
    }

    @Override
    public void deleteCart(Long id) {
        findCartByIdOrThrow(id);
        cartRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public CartUserResponse getMyCart(Long userId) {
        return cartRepository.findByUser_Id(userId)
                .map(cart -> CartUserResponse.from(cart, cartDetailRepository.findByCart_Id(cart.getId())))
                .orElse(CartUserResponse.empty(userId));
    }

    @Override
    public CartUserResponse addItemToMyCart(Long userId, CartItemRequest request) {
        User user = findUserByIdOrThrow(userId);

        Cart cart = cartRepository.findByUser_Id(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        ProductDetail pd = productDetailRepository.findById(request.getProductDetailId())
                .orElseThrow(() -> AppException.resourceNotFound("ProductDetail", "id", request.getProductDetailId()));

        if (pd.getStockQuantity() == 0) {
            throw AppException.insufficientStock(
                    String.format("Sản phẩm '%s' (size: %s, màu: %s) đã hết hàng",
                            pd.getProduct().getName(), pd.getSize(), pd.getColor()));
        }

        Optional<CartDetail> existingItem = cartDetailRepository
                .findByCart_IdAndProductDetail_Id(cart.getId(), pd.getId());

        CartDetail cartDetail;
        int newQuantity;

        if (existingItem.isPresent()) {
            cartDetail = existingItem.get();
            newQuantity = cartDetail.getQuantity() + request.getQuantity();
        } else {
            cartDetail = new CartDetail();
            cartDetail.setCart(cart);
            cartDetail.setProductDetail(pd);
            newQuantity = request.getQuantity();
        }

        if (newQuantity > pd.getStockQuantity()) {
            throw AppException.insufficientStock(
                    String.format("Số lượng vượt quá tồn kho. Tồn kho hiện tại: %d", pd.getStockQuantity()));
        }

        cartDetail.setQuantity(newQuantity);
        cartDetailRepository.save(cartDetail);

        List<CartDetail> items = cartDetailRepository.findByCart_Id(cart.getId());
        return CartUserResponse.from(cart, items);
    }

    @Override
    public CartUserResponse updateMyCartItem(Long userId, Long itemId, CartItemUpdateRequest request) {
        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> AppException.resourceNotFound("Cart", "userId", userId));

        CartDetail cartDetail = cartDetailRepository.findById(itemId)
                .orElseThrow(() -> AppException.resourceNotFound("CartDetail", "id", itemId));

        if (!cartDetail.getCart().getId().equals(cart.getId())) {
            throw AppException.resourceNotFound("CartDetail", "id", itemId);
        }

        if (request.getQuantity() == 0) {
            cartDetailRepository.delete(cartDetail);
        } else {
            ProductDetail pd = cartDetail.getProductDetail();
            if (request.getQuantity() > pd.getStockQuantity()) {
                throw AppException.insufficientStock(
                        String.format("Số lượng vượt quá tồn kho. Tồn kho hiện tại: %d", pd.getStockQuantity()));
            }
            cartDetail.setQuantity(request.getQuantity());
            cartDetailRepository.save(cartDetail);
        }

        List<CartDetail> items = cartDetailRepository.findByCart_Id(cart.getId());
        return CartUserResponse.from(cart, items);
    }

    @Override
    public CartUserResponse deleteMyCartItem(Long userId, Long itemId) {
        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> AppException.resourceNotFound("Cart", "userId", userId));

        CartDetail cartDetail = cartDetailRepository.findById(itemId)
                .orElseThrow(() -> AppException.resourceNotFound("CartDetail", "id", itemId));

        if (!cartDetail.getCart().getId().equals(cart.getId())) {
            throw AppException.resourceNotFound("CartDetail", "id", itemId);
        }

        cartDetailRepository.delete(cartDetail);

        List<CartDetail> items = cartDetailRepository.findByCart_Id(cart.getId());
        return CartUserResponse.from(cart, items);
    }

    @Override
    public CartUserResponse clearMyCart(Long userId) {
        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> AppException.resourceNotFound("Cart", "userId", userId));

        List<CartDetail> items = cartDetailRepository.findByCart_Id(cart.getId());
        cartDetailRepository.deleteAll(items);

        return CartUserResponse.from(cart, List.of());
    }

    private Cart findCartByIdOrThrow(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> AppException.resourceNotFound("Cart", "id", id));
    }

    private User findUserByIdOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> AppException.resourceNotFound("User", "id", userId));
    }
}