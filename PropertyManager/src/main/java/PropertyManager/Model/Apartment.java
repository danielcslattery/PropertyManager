package PropertyManager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import PropertyManager.repository.PaymentRepository;

@Entity
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private Long buildingId;

    @NotBlank
    private String number;

    public Apartment() {};

    public Apartment(Long buildingId, String apartmentNumber) {
        this.buildingId = buildingId;
        this.number = apartmentNumber;
    };

    @Override
    public String toString() {
        return String.format("Apartment[apartmentId = '%d', buildingId = '%s', apartmentNumber = '%s']",
                Id, buildingId, number);
    }

    public Long getId() {
        return Id;
    }

    public String getNumber() {
        return number;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public void setId(Long id){this.Id = id;}

    // public void addPayment(PaymentRepository repository, int paymentAmount, int month){
    //     Apartment apartment = 

    //     repository.save(new Payment(Id, paymentAmount, month));
    // }

    public void setNumber(String number) {
        this.number = number;
    }


}
