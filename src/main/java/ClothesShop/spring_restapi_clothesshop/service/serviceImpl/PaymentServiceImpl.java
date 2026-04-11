package ClothesShop.spring_restapi_clothesshop.service.serviceImpl;

import ClothesShop.spring_restapi_clothesshop.exception.AppException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ClothesShop.spring_restapi_clothesshop.dto.payment.PaymentCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.payment.PaymentResponse;
import ClothesShop.spring_restapi_clothesshop.dto.payment.PaymentUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.payment.PaymentUserCreateRequest;
import ClothesShop.spring_restapi_clothesshop.model.Order;
import ClothesShop.spring_restapi_clothesshop.model.Payment;
import ClothesShop.spring_restapi_clothesshop.model.User;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PaymentStatusEnum;
import ClothesShop.spring_restapi_clothesshop.repository.OrderRepository;
import ClothesShop.spring_restapi_clothesshop.repository.PaymentRepository;
import ClothesShop.spring_restapi_clothesshop.repository.UserRepository;
import ClothesShop.spring_restapi_clothesshop.service.PaymentService;
import ClothesShop.spring_restapi_clothesshop.mapper.PaymentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    PaymentRepository paymentRepository;
    OrderRepository orderRepository;
    UserRepository userRepository;
    PaymentMapper paymentMapper;

    @Override
    public PaymentResponse createPayment(PaymentCreateRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> AppException.resourceNotFound("Order", "id", request.getOrderId()));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> AppException.resourceNotFound("User", "id", request.getUserId()));

        if (paymentRepository.existsByOrder_Id(request.getOrderId())) {
            throw AppException.duplicateResource("Payment", "orderId", request.getOrderId());
        }

        Payment payment = paymentMapper.toEntity(request);
        payment.setOrder(order);
        payment.setUser(user);
        if (payment.getStatus() == null) {
            payment.setStatus(PaymentStatusEnum.PENDING);
        }

        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toResponse(savedPayment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(Long id) {
        return paymentMapper.toResponse(findPaymentByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrder_Id(orderId)
                .orElseThrow(() -> AppException.resourceNotFound("Payment", "orderId", orderId));
        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getPaymentsByUserId(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw AppException.resourceNotFound("User", "id", userId);
        }
        return paymentRepository.findByUser_Id(userId, pageable).map(paymentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable).map(paymentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getPaymentsByStatus(PaymentStatusEnum status, Pageable pageable) {
        return paymentRepository.findByStatus(status, pageable).map(paymentMapper::toResponse);
    }

    @Override
    public PaymentResponse updatePayment(Long id, PaymentUpdateRequest request) {
        Payment payment = findPaymentByIdOrThrow(id);

        if (request.getAmount() != null) {
            payment.setAmount(request.getAmount());
        }
        if (request.getMethod() != null) {
            payment.setMethod(request.getMethod());
        }
        if (request.getStatus() != null) {
            payment.setStatus(request.getStatus());
        }

        Payment updatedPayment = paymentRepository.save(payment);
        return paymentMapper.toResponse(updatedPayment);
    }

    @Override
    public void deletePayment(Long id) {
        findPaymentByIdOrThrow(id);
        paymentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getMyPayments(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw AppException.resourceNotFound("User", "id", userId);
        }
        return paymentRepository.findByUser_Id(userId, pageable).map(paymentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getMyPaymentById(Long userId, Long paymentId) {
        Payment payment = findPaymentByIdOrThrow(paymentId);
        if (!payment.getUser().getId().equals(userId)) {
            throw AppException.resourceNotFound("Payment", "id", paymentId);
        }
        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponse createMyPayment(Long userId, PaymentUserCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> AppException.resourceNotFound("User", "id", userId));
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> AppException.resourceNotFound("Order", "id", request.getOrderId()));

        if (!order.getUser().getId().equals(userId)) {
            throw AppException.resourceNotFound("Order", "id", request.getOrderId());
        }

        if (paymentRepository.existsByOrder_Id(request.getOrderId())) {
            throw AppException.duplicateResource("Payment", "orderId", request.getOrderId());
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setUser(user);
        payment.setAmount(request.getAmount());
        payment.setMethod(request.getMethod());
        payment.setStatus(PaymentStatusEnum.PENDING);

        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toResponse(savedPayment);
    }

    private Payment findPaymentByIdOrThrow(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> AppException.resourceNotFound("Payment", "id", id));
    }

}