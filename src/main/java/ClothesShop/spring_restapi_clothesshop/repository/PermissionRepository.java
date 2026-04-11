package ClothesShop.spring_restapi_clothesshop.repository;

import ClothesShop.spring_restapi_clothesshop.model.Permission;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PermissionMethodENUM;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByName(String name);

    boolean existsByName(String name);

    Page<Permission> findByMethodAndModule(PermissionMethodENUM method, String module, Pageable pageable);

    Page<Permission> findByMethod(PermissionMethodENUM method, Pageable pageable);

    Page<Permission> findByModule(String module, Pageable pageable);
}