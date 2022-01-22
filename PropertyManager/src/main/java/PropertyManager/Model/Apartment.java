package PropertyManager.Model;

import PropertyManager.Repositories.PaymentRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private Long buildingId;

    @NotBlank
    private String number;

    Apartment() {}

    public Apartment(Long buildingId, String apartmentNumber) {
        this.buildingId = buildingId;
        this.number = apartmentNumber;
    }

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

    public void addPayment(PaymentRepository repository, int paymentAmount, int month){
        repository.save(new Payment(Id, paymentAmount, month));
    }


}
