package PropertyManager.Entities;

import javax.persistence.*;

@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long buildingId;
    private String buildingAddress;

    public Building(){};

    public Building(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }

    @Override
    public String toString(){
        return String.format("Building[buildingId = '%d', buildingAddress= '%s']",
                buildingId, buildingAddress);
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) { this.buildingAddress = buildingAddress; }

    public void setBuildingId(long buildingId) {
        this.buildingId = buildingId;
    }

    public long getBuildingId() {
        return buildingId;
    }
}
