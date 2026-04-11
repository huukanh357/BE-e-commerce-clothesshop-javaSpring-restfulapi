package ClothesShop.spring_restapi_clothesshop.service.serviceImpl;

import ClothesShop.spring_restapi_clothesshop.exception.AppException;
import ClothesShop.spring_restapi_clothesshop.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ClothesShop.spring_restapi_clothesshop.dto.orderItem.OrderItemCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.orderItem.OrderItemResponse;
import ClothesShop.spring_restapi_clothesshop.dto.orderItem.OrderItemUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.model.Order;
import ClothesShop.spring_restapi_clothesshop.model.OrderItem;
import ClothesShop.spring_restapi_clothesshop.model.ProductDetail;
import ClothesShop.spring_restapi_clothesshop.repository.OrderItemRepository;
import ClothesShop.spring_restapi_clothesshop.repository.OrderRepository;
import ClothesShop.spring_restapi_clothesshop.repository.ProductDetailRepository;
import ClothesShop.spring_restapi_clothesshop.service.OrderItemService;
import ClothesShop.spring_restapi_clothesshop.mapper.OrderItemMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderItemServiceImpl implements OrderItemService {

    OrderItemRepository orderItemRepository;
    OrderRepository orderRepository;
    ProductDetailRepository productDetailRepository;
    OrderItemMapper orderItemMapper;

    @Override
    public OrderItemResponse createOrderItem(OrderItemCreateRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Order", "id", request.getOrderId()));
        ProductDetail productDetail = productDetailRepository.findById(request.getProductDetailId())
                .orElseThrow(() -> new AppException(
                        ErrorCode.RESOURCE_NOT_FOUND,
                        "ProductDetail",
                        "id",
                        request.getProductDetailId()));

        OrderItem orderItem = orderItemMapper.toEntity(request);
        orderItem.setOrder(order);
        orderItem.setProductDetail(productDetail);

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return orderItemMapper.toResponse(savedOrderItem);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderItemResponse getOrderItemById(Long id) {
        return orderItemMapper.toResponse(findOrderItemByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderItemResponse> getAllOrderItems(Pageable pageable) {
        return orderItemRepository.findAll(pageable).map(orderItemMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderItemResponse> getOrderItemsByOrderId(Long orderId, Pageable pageable) {
        if (!orderRepository.existsById(orderId)) {
            throw new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Order", "id", orderId);
        }
        return orderItemRepository.findByOrder_Id(orderId, pageable).map(orderItemMapper::toResponse);
    }

    @Override
    public OrderItemResponse updateOrderItem(Long id, OrderItemUpdateRequest request) {
        OrderItem orderItem = findOrderItemByIdOrThrow(id);

        orderItemMapper.updateFromRequest(request, orderItem);

        OrderItem updatedOrderItem = orderItemRepository.save(orderItem);
        return orderItemMapper.toResponse(updatedOrderItem);
    }

    @Override
    public void deleteOrderItem(Long id) {
        findOrderItemByIdOrThrow(id);
        orderItemRepository.deleteById(id);
    }

    private OrderItem findOrderItemByIdOrThrow(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "OrderItem", "id", id));
    }

}