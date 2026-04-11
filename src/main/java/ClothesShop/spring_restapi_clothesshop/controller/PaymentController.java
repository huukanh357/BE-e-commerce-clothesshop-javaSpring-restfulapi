package ClothesShop.spring_restapi_clothesshop.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ClothesShop.spring_restapi_clothesshop.dto.ApiResponse;
import ClothesShop.spring_restapi_clothesshop.dto.ResultPaginationDTO;
import ClothesShop.spring_restapi_clothesshop.dto.payment.PaymentCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.payment.PaymentResponse;
import ClothesShop.spring_restapi_clothesshop.dto.payment.PaymentUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PaymentStatusEnum;
import ClothesShop.spring_restapi_clothesshop.service.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentController {

    PaymentService paymentService;
@GetMapping("/api/payments")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getPayments(
            @RequestParam(required = false) PaymentStatusEnum status,
            Pageable pageable) {
        Page<PaymentResponse> page = (status == null)
                ? paymentService.getAllPayments(pageable)
                : paymentService.getPaymentsByStatus(status, pageable);
        ResultPaginationDTO result = ResultPaginationDTO.fromPage(page);
        return ResponseEntity.ok(ApiResponse.success("Get payments successfully", result));
    }

    @GetMapping("/api/payments/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id) {
        PaymentResponse payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(ApiResponse.success("Get payment successfully", payment));
    }

    @GetMapping("/api/payments/order/{orderId}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentByOrderId(
            @PathVariable @Positive(message = "Order id phải lớn hơn 0") Long orderId) {
        PaymentResponse payment = paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity.ok(ApiResponse.success("Get payment by order successfully", payment));
    }

    @GetMapping("/api/payments/user/{userId}")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getPaymentsByUserId(
            @PathVariable @Positive(message = "User id phải lớn hơn 0") Long userId,
            Pageable pageable) {
        Page<PaymentResponse> page = paymentService.getPaymentsByUserId(userId, pageable);
        ResultPaginationDTO result = ResultPaginationDTO.fromPage(page);
        return ResponseEntity.ok(ApiResponse.success("Get payments by user successfully", result));
    }

    @PostMapping("/api/payments")
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(
            @RequestBody @Valid PaymentCreateRequest request) {
        PaymentResponse payment = paymentService.createPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Payment created successfully", payment));
    }

    @PutMapping("/api/payments/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> updatePayment(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id,
            @RequestBody @Valid PaymentUpdateRequest request) {
        PaymentResponse payment = paymentService.updatePayment(id, request);
        return ResponseEntity.ok(ApiResponse.success("Payment updated successfully", payment));
    }

    @DeleteMapping("/api/payments/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable @Positive(message = "Id phải lớn hơn 0") Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
