package PropertyManager.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Transactional
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    @Size(min = 4)
    private String address;

    private int numberApartments;

    @OneToMany(mappedBy = "building", fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apartment> apartments = new ArrayList<Apartment>();

    public List<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }

    public Building(){};

    public Building(String address) {
        this.address = address;
        System.out.println(this.toString());
        this.numberApartments = 0;
    }

    public void addApartment(Apartment apartment){
        this.apartments.add(apartment);
    }

    @Override
    public String toString(){
        return String.format("Building[buildingId = '%d', buildingAddress= '%s', numberApartments = '%d']",
                id, address, numberApartments);
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) { this.address = address; }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public int getNumberApartments() {
        return numberApartments;
    }

    public void setNumberApartments(int numberApartments) {
        this.numberApartments = numberApartments;
    }
}
