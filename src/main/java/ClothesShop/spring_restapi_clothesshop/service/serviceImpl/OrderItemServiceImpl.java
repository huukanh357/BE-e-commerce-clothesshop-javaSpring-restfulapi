package ClothesShop.spring_restapi_clothesshop.service.serviceImpl;

import ClothesShop.spring_restapi_clothesshop.dto.orderItem.OrderItemCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.orderItem.OrderItemResponse;
import ClothesShop.spring_restapi_clothesshop.dto.orderItem.OrderItemUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.exception.ResourceNotFoundException;
import ClothesShop.spring_restapi_clothesshop.model.Order;
import ClothesShop.spring_restapi_clothesshop.model.OrderItem;
import ClothesShop.spring_restapi_clothesshop.model.ProductDetail;
import ClothesShop.spring_restapi_clothesshop.repository.OrderItemRepository;
import ClothesShop.spring_restapi_clothesshop.repository.OrderRepository;
import ClothesShop.spring_restapi_clothesshop.repository.ProductDetailRepository;
import ClothesShop.spring_restapi_clothesshop.service.OrderItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductDetailRepository productDetailRepository;

    public OrderItemServiceImpl(
            OrderItemRepository orderItemRepository,
            OrderRepository orderRepository,
            ProductDetailRepository productDetailRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productDetailRepository = productDetailRepository;
    }

    @Override
    public OrderItemResponse createOrderItem(OrderItemCreateRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", request.getOrderId()));
        ProductDetail productDetail = productDetailRepository.findById(request.getProductDetailId())
                .orElseThrow(() -> new ResourceNotFoundException("ProductDetail", "id", request.getProductDetailId()));

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProductDetail(productDetail);
        orderItem.setQuantity(request.getQuantity());
        orderItem.setPrice(request.getPrice());

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return OrderItemResponse.fromEntity(savedOrderItem);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderItemResponse getOrderItemById(Long id) {
        return OrderItemResponse.fromEntity(findOrderItemByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderItemResponse> getAllOrderItems(Pageable pageable) {
        return orderItemRepository.findAll(pageable).map(OrderItemResponse::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderItemResponse> getOrderItemsByOrderId(Long orderId, Pageable pageable) {
        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("Order", "id", orderId);
        }
        return orderItemRepository.findByOrder_Id(orderId, pageable).map(OrderItemResponse::fromEntity);
    }

    @Override
    public OrderItemResponse updateOrderItem(Long id, OrderItemUpdateRequest request) {
        OrderItem orderItem = findOrderItemByIdOrThrow(id);

        if (request.getQuantity() != null) {
            orderItem.setQuantity(request.getQuantity());
        }
        if (request.getPrice() != null) {
            orderItem.setPrice(request.getPrice());
        }

        OrderItem updatedOrderItem = orderItemRepository.save(orderItem);
        return OrderItemResponse.fromEntity(updatedOrderItem);
    }

    @Override
    public void deleteOrderItem(Long id) {
        findOrderItemByIdOrThrow(id);
        orderItemRepository.deleteById(id);
    }

    private OrderItem findOrderItemByIdOrThrow(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem", "id", id));
    }

}
