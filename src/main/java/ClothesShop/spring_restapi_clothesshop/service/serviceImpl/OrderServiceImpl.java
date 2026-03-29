package ClothesShop.spring_restapi_clothesshop.service.serviceImpl;

import ClothesShop.spring_restapi_clothesshop.dto.order.OrderCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.order.OrderResponse;
import ClothesShop.spring_restapi_clothesshop.dto.order.OrderUserCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.order.OrderUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.order.ShippingInfoRequest;
import ClothesShop.spring_restapi_clothesshop.exception.ResourceNotFoundException;
import ClothesShop.spring_restapi_clothesshop.model.Cart;
import ClothesShop.spring_restapi_clothesshop.model.Order;
import ClothesShop.spring_restapi_clothesshop.model.ShippingInfo;
import ClothesShop.spring_restapi_clothesshop.model.User;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.OrderStatusEnum;
import ClothesShop.spring_restapi_clothesshop.repository.CartRepository;
import ClothesShop.spring_restapi_clothesshop.repository.OrderRepository;
import ClothesShop.spring_restapi_clothesshop.repository.UserRepository;
import ClothesShop.spring_restapi_clothesshop.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
            CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public OrderResponse createOrder(OrderCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getUserId()));
        Cart cart = null;
        if (request.getCartId() != null) {
            cart = cartRepository.findById(request.getCartId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", request.getCartId()));
        }

        Order order = new Order();
        order.setUser(user);
        order.setCart(cart);
        order.setTotalAmount(request.getTotalAmount());
        order.setStatus(request.getStatus() == null ? OrderStatusEnum.PENDING : request.getStatus());
        order.setShippingInfo(buildShippingInfo(request.getShippingInfo()));

        Order savedOrder = orderRepository.save(order);
        return OrderResponse.fromEntity(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {
        return OrderResponse.fromEntity(findOrderByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(OrderResponse::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> getOrdersByUserId(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
        return orderRepository.findByUser_Id(userId, pageable).map(OrderResponse::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> getOrdersByStatus(OrderStatusEnum status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable).map(OrderResponse::fromEntity);
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderUpdateRequest request) {
        Order order = findOrderByIdOrThrow(id);

        if (request.getCartId() != null) {
            Cart cart = cartRepository.findById(request.getCartId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", request.getCartId()));
            order.setCart(cart);
        }

        if (request.getTotalAmount() != null) {
            order.setTotalAmount(request.getTotalAmount());
        }

        if (request.getStatus() != null) {
            order.setStatus(request.getStatus());
        }

        Order updatedOrder = orderRepository.save(order);
        return OrderResponse.fromEntity(updatedOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        findOrderByIdOrThrow(id);
        orderRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> getMyOrders(Long userId, Pageable pageable) {
        return orderRepository.findByUser_Id(userId, pageable).map(OrderResponse::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getMyOrderById(Long userId, Long orderId) {
        Order order = findOrderByIdOrThrow(orderId);
        if (!order.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Order", "id", orderId);
        }
        return OrderResponse.fromEntity(order);
    }

    @Override
    public OrderResponse createMyOrder(Long userId, OrderUserCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Cart cart = null;
        if (request.getCartId() != null) {
            cart = cartRepository.findById(request.getCartId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", request.getCartId()));
            if (!cart.getUser().getId().equals(userId)) {
                throw new ResourceNotFoundException("Cart", "id", request.getCartId());
            }
        }

        Order order = new Order();
        order.setUser(user);
        order.setCart(cart);
        order.setTotalAmount(request.getTotalAmount());
        order.setStatus(OrderStatusEnum.PENDING);
        order.setShippingInfo(buildShippingInfo(request.getShippingInfo()));

        return OrderResponse.fromEntity(orderRepository.save(order));
    }

    private Order findOrderByIdOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
    }

    private ShippingInfo buildShippingInfo(ShippingInfoRequest request) {
        ShippingInfo shippingInfo = new ShippingInfo();
        shippingInfo.setReceiverName(request.getReceiverName());
        shippingInfo.setReceiverPhone(request.getReceiverPhone());
        shippingInfo.setShippingAddress(request.getShippingAddress());
        shippingInfo.setShippingCity(request.getShippingCity());
        shippingInfo.setShippingNote(request.getShippingNote());
        return shippingInfo;
    }

}
