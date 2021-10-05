package PropertyManager.Models;

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
        return String.format("Payment[buildingId = '%d', buildingAddress= '%s']",
                buildingId, buildingAddress);
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

}
