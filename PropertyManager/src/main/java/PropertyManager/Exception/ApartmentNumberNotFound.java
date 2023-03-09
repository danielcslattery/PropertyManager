package PropertyManager.exception;

public class ApartmentNumberNotFound extends RuntimeException{

    private String apartmentNumber;

    public ApartmentNumberNotFound(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getNumber(){
        return this.apartmentNumber;
    }

}
