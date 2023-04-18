package PropertyManager.controller.dto;

public class PaymentDTO{

    private long id;
    private long apartmentId;
    private int amount;
    private int month;
    private long buildingId;

    public long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(long buildingId) {
        this.buildingId = buildingId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setApartmentId(long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setMonth(int month) {
        this.month = month;
    }

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
