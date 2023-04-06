package PropertyManager.controller.dto;

public class BuildingDTO {
    private long id;
    private String address;
    private int numberApartments;
    
    public int getNumberApartments() {
        return numberApartments;
    }
    public void setNumberApartments(int numberApartments) {
        this.numberApartments = numberApartments;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
