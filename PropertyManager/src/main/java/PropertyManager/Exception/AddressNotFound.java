package PropertyManager.Exception;

public class AddressNotFound extends RuntimeException {

    private String address;

    public AddressNotFound(String address) {
        this.address = address;
    }

    public String getAddress(){
        return this.address;
    }

}
