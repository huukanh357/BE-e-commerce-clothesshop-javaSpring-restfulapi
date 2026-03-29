package ClothesShop.spring_restapi_clothesshop.repository;

import ClothesShop.spring_restapi_clothesshop.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> filterByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) {
                return null;
            }

            if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
        };
    }

    public static Specification<Product> filterByCategories(List<String> categoryNames) {
        return (root, query, criteriaBuilder) -> {
            if (categoryNames == null || categoryNames.isEmpty()) {
                return null;
            }

            var categoryJoin = root.join("categories");
            query.distinct(true);
            return categoryJoin.get("name").in(categoryNames);
        };
    }

    public static Specification<Product> filterBySizes(List<String> sizes) {
        return (root, query, criteriaBuilder) -> {
            if (sizes == null || sizes.isEmpty()) {
                return null;
            }

            var detailsJoin = root.join("productDetails");
            query.distinct(true);
            return detailsJoin.get("size").in(sizes);
        };
    }

    public static Specification<Product> filter(BigDecimal minPrice, BigDecimal maxPrice,
            List<String> categoryNames, List<String> sizes) {
        return Specification.where(filterByPrice(minPrice, maxPrice))
                .and(filterByCategories(categoryNames))
                .and(filterBySizes(sizes));
    }
}
