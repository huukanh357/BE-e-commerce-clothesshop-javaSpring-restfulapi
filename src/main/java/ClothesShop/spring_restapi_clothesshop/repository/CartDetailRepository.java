package ClothesShop.spring_restapi_clothesshop.repository;

import ClothesShop.spring_restapi_clothesshop.model.CartDetail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    List<CartDetail> findByCart_Id(Long cartId);

    Page<CartDetail> findByCart_Id(Long cartId, Pageable pageable);

    Optional<CartDetail> findByCart_IdAndProductDetail_Id(Long cartId, Long productDetailId);

    boolean existsByCart_IdAndProductDetail_Id(Long cartId, Long productDetailId);
}