package ClothesShop.spring_restapi_clothesshop.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ShippingInfoRequest {

    @NotBlank(message = "Receiver name không được để trống")
    @Size(max = 100, message = "Receiver name tối đa 100 ký tự")
    private String receiverName;

    @NotBlank(message = "Receiver phone không được để trống")
    @Size(max = 20, message = "Receiver phone tối đa 20 ký tự")
    private String receiverPhone;

    @NotBlank(message = "Shipping address không được để trống")
    @Size(max = 255, message = "Shipping address tối đa 255 ký tự")
    private String shippingAddress;

    @NotBlank(message = "Shipping city không được để trống")
    @Size(max = 100, message = "Shipping city tối đa 100 ký tự")
    private String shippingCity;

    @Size(max = 500, message = "Shipping note tối đa 500 ký tự")
    private String shippingNote;

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public String getShippingNote() {
        return shippingNote;
    }

    public void setShippingNote(String shippingNote) {
        this.shippingNote = shippingNote;
    }
}
