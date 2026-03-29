package ClothesShop.spring_restapi_clothesshop.dto.product;

import java.math.BigDecimal;
import java.util.List;

public class ProductFilterRequest {

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private List<String> categoryNames;

    private List<String> sizes;

    public ProductFilterRequest() {
    }

    public ProductFilterRequest(BigDecimal minPrice, BigDecimal maxPrice,
            List<String> categoryNames, List<String> sizes) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.categoryNames = categoryNames;
        this.sizes = sizes;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public List<String> getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(List<String> categoryNames) {
        this.categoryNames = categoryNames;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }
}
