package PropertyManager.Exception;

public class BuildingAddressNotFound extends RuntimeException {

    private String address;

    public BuildingAddressNotFound(String address) {
        this.address = address;
    }

    public String getAddress(){
        return this.address;
    }

}
