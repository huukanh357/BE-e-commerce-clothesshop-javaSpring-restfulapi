package ClothesShop.spring_restapi_clothesshop.service.serviceImpl;

import ClothesShop.spring_restapi_clothesshop.dto.payment.PaymentCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.payment.PaymentResponse;
import ClothesShop.spring_restapi_clothesshop.dto.payment.PaymentUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.payment.PaymentUserCreateRequest;
import ClothesShop.spring_restapi_clothesshop.exception.DuplicateResourceException;
import ClothesShop.spring_restapi_clothesshop.exception.ResourceNotFoundException;
import ClothesShop.spring_restapi_clothesshop.model.Order;
import ClothesShop.spring_restapi_clothesshop.model.Payment;
import ClothesShop.spring_restapi_clothesshop.model.User;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PaymentStatusEnum;
import ClothesShop.spring_restapi_clothesshop.repository.OrderRepository;
import ClothesShop.spring_restapi_clothesshop.repository.PaymentRepository;
import ClothesShop.spring_restapi_clothesshop.repository.UserRepository;
import ClothesShop.spring_restapi_clothesshop.service.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository,
            UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PaymentResponse createPayment(PaymentCreateRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", request.getOrderId()));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getUserId()));

        if (paymentRepository.existsByOrder_Id(request.getOrderId())) {
            throw new DuplicateResourceException("Payment", "orderId", request.getOrderId());
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setUser(user);
        payment.setAmount(request.getAmount());
        payment.setMethod(request.getMethod());
        payment.setStatus(request.getStatus() == null ? PaymentStatusEnum.PENDING : request.getStatus());

        Payment savedPayment = paymentRepository.save(payment);
        return PaymentResponse.fromEntity(savedPayment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(Long id) {
        return PaymentResponse.fromEntity(findPaymentByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrder_Id(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "orderId", orderId));
        return PaymentResponse.fromEntity(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getPaymentsByUserId(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
        return paymentRepository.findByUser_Id(userId, pageable).map(PaymentResponse::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable).map(PaymentResponse::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getPaymentsByStatus(PaymentStatusEnum status, Pageable pageable) {
        return paymentRepository.findByStatus(status, pageable).map(PaymentResponse::fromEntity);
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
        return PaymentResponse.fromEntity(updatedPayment);
    }

    @Override
    public void deletePayment(Long id) {
        findPaymentByIdOrThrow(id);
        paymentRepository.deleteById(id);
    }

    // USER (me)
    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getMyPayments(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
        return paymentRepository.findByUser_Id(userId, pageable).map(PaymentResponse::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getMyPaymentById(Long userId, Long paymentId) {
        Payment payment = findPaymentByIdOrThrow(paymentId);
        if (!payment.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Payment", "id", paymentId);
        }
        return PaymentResponse.fromEntity(payment);
    }

    @Override
    public PaymentResponse createMyPayment(Long userId, PaymentUserCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", request.getOrderId()));

        if (!order.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Order", "id", request.getOrderId());
        }

        if (paymentRepository.existsByOrder_Id(request.getOrderId())) {
            throw new DuplicateResourceException("Payment", "orderId", request.getOrderId());
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setUser(user);
        payment.setAmount(request.getAmount());
        payment.setMethod(request.getMethod());
        payment.setStatus(PaymentStatusEnum.PENDING);

        Payment savedPayment = paymentRepository.save(payment);
        return PaymentResponse.fromEntity(savedPayment);
    }

    private Payment findPaymentByIdOrThrow(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));
    }

}
