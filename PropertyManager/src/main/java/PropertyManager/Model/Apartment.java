package PropertyManager.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Transactional
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @ManyToOne
    private Building building;

    @NotBlank
    private String number;

    @OneToMany(mappedBy = "apartment", fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<Payment>();
    
    public Apartment() {};
    
    public Apartment(Building building, String apartmentNumber) {
        this.building = building;
        this.number = apartmentNumber;

        building.addApartment(this);
    };

    @Transactional
    public void addPayment(Payment payment){
        this.payments.add(payment);
    }

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

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
