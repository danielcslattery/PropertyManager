package PropertyManager.Exception;

public class PaymentIdNotFound extends RuntimeException{

    private Long id;

    public PaymentIdNotFound(Long id) {
        this.id = id;
    }

    public Long getId(){
        return this.id;
    }

}
