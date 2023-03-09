package PropertyManager.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @ManyToOne
    private Building building;

    @NotBlank
    private String number;

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    public Apartment() {};

    public Apartment(Building building, String apartmentNumber) {
        this.building = building;
        this.number = apartmentNumber;
    };

    @Override
    public String toString() {
        return String.format("Apartment[apartmentId = '%d', buildingId = '%s', apartmentNumber = '%s']",
                Id, building, number);
    }

    public Long getId() {
        return Id;
    }

    public String getNumber() {
        return number;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setId(Long id){this.Id = id;}

    public void setNumber(String number) {
        this.number = number;
    }
}
