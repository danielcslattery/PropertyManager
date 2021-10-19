package PropertyManager.Exception;

public class BuildingNotFound extends RuntimeException {

    private String address;

    public BuildingNotFound(String address) {
        this.address = address;
    }

    public String getAddress(){
        return this.address;
    }

}
