package ClothesShop.spring_restapi_clothesshop.repository;

import ClothesShop.spring_restapi_clothesshop.model.ProductDetail;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

    Page<ProductDetail> findByProduct_Id(Long productId, Pageable pageable);

    Optional<ProductDetail> findByProduct_IdAndSizeAndColor(Long productId, String size, String color);

    boolean existsByProduct_IdAndSizeAndColor(Long productId, String size, String color);
}