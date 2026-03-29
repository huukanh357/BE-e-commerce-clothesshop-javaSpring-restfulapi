package ClothesShop.spring_restapi_clothesshop.service;

import ClothesShop.spring_restapi_clothesshop.dto.payment.PaymentCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.payment.PaymentResponse;
import ClothesShop.spring_restapi_clothesshop.dto.payment.PaymentUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.payment.PaymentUserCreateRequest;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PaymentStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {

    PaymentResponse createPayment(PaymentCreateRequest request);

    PaymentResponse getPaymentById(Long id);

    PaymentResponse getPaymentByOrderId(Long orderId);

    Page<PaymentResponse> getPaymentsByUserId(Long userId, Pageable pageable);

    Page<PaymentResponse> getAllPayments(Pageable pageable);

    Page<PaymentResponse> getPaymentsByStatus(PaymentStatusEnum status, Pageable pageable);

    PaymentResponse updatePayment(Long id, PaymentUpdateRequest request);

    void deletePayment(Long id);

    // USER (me)
    Page<PaymentResponse> getMyPayments(Long userId, Pageable pageable);

    PaymentResponse getMyPaymentById(Long userId, Long paymentId);

    PaymentResponse createMyPayment(Long userId, PaymentUserCreateRequest request);
}
