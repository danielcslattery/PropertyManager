package PropertyManager.Entities;

import PropertyManager.Repositories.PaymentRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long apartmentId;
    private Long buildingId;
    private String apartmentNumber;

    Apartment() {}

    public Apartment(Long buildingId, String apartmentNumber) {
        this.buildingId = buildingId;
        this.apartmentNumber = apartmentNumber;
    }

    @Override
    public String toString() {
        return String.format("Apartment[apartmentId = '%d', buildingId = '%s', apartmentNumber = '%s']",
                apartmentId, buildingId, apartmentNumber);
    }



    public Long getApartmentId() {
        return apartmentId;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void addPayment(PaymentRepository repository, int paymentAmount, int month){
        repository.save(new Payment(apartmentId, paymentAmount, month));
    }

}
