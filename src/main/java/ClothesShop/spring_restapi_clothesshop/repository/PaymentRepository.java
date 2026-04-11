package ClothesShop.spring_restapi_clothesshop.repository;

import ClothesShop.spring_restapi_clothesshop.model.Payment;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PaymentStatusEnum;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrder_Id(Long orderId);

    Page<Payment> findByUser_Id(Long userId, Pageable pageable);

    Page<Payment> findByStatus(PaymentStatusEnum status, Pageable pageable);

    boolean existsByOrder_Id(Long orderId);
}