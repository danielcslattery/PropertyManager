package PropertyManager.Exception;

public class ApartmentIdNotFound extends RuntimeException {

    private Long id;

    public ApartmentIdNotFound(Long id) {
        this.id = id;
    }

    public Long getId(){
        return this.id;
    }

}
