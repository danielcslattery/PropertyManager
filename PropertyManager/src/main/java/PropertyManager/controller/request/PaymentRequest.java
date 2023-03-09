package PropertyManager.controller.request;

public final class PaymentRequest {
    
    private long id;
    private long apartmentId;
    private int amount;
    private int month;
    
    public long getId() {
        return id;
    }
    public long getApartmentId() {
        return apartmentId;
    }
    public int getAmount() {
        return amount;
    }
    public int getMonth() {
        return month;
    }

}
