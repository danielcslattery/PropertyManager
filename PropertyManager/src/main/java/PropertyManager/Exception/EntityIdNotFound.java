package PropertyManager.exception;

public class EntityIdNotFound extends RuntimeException {

    private Long id;
    private String entityType;

    public EntityIdNotFound(Long id) {
        this.id = id;
    }

    public EntityIdNotFound(Long id, String entityType) {
        this.id = id;
        this.entityType = entityType;
    }

    public Long getId(){
        return this.id;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }
}
