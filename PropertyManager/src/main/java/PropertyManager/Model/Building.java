package PropertyManager.Model;

import javax.persistence.*;

@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long buildingId;
    private String address;

    public Building(){};

    public Building(String address) {
        this.address = address;
        System.out.println(this.toString());
    }

    @Override
    public String toString(){
        return String.format("Building[buildingId = '%d', buildingAddress= '%s']",
                buildingId, address);
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) { this.address = address; }

    public void setBuildingId(long buildingId) {
        this.buildingId = buildingId;
    }

    public long getBuildingId() {
        return this.buildingId;
    }
}
