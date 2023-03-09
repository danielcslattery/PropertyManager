package PropertyManager.controller.request;

public final class ApartmentRequest {
    
    private Long id;
    private Long buildingId;
    private String number;

    
    public Long getId() {
        // TODO Refactor so conversion from null isn't necessary.
        if (id == null) {
            return 0L;
        }

        return id;
    }
    public Long getBuildingId() {
        return buildingId;
    }
    public String getNumber() {
        return number;
    }

}
