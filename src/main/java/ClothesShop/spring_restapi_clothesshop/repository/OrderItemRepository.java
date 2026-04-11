package ClothesShop.spring_restapi_clothesshop.repository;

import ClothesShop.spring_restapi_clothesshop.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Page<OrderItem> findByOrder_Id(Long orderId, Pageable pageable);
}