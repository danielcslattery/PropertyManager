package PropertyManager.Exception;

public class BuildingIdNotFound extends RuntimeException {

    private Long id;

    public BuildingIdNotFound(Long id) {
        this.id = id;
    }

    public Long getId(){
        return this.id;
    }

}
