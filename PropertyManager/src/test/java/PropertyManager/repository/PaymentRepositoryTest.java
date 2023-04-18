package PropertyManager.repository;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import PropertyManager.model.Apartment;
import PropertyManager.model.Building;
import PropertyManager.model.Payment;

@SpringBootTest
@Transactional
public class PaymentRepositoryTest {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void PaymentRepository_Save_AddsToRepository() {
        Building building = new Building("1234 Sesame Street");
        buildingRepository.save(building);
        Apartment apartment = new Apartment(building, "1W");
        apartmentRepository.save(apartment);
        Payment payment = new Payment(apartment, 1200, 1);
        paymentRepository.save(payment);

        Assertions.assertThat(paymentRepository.findById(payment.getId())).isPresent();
    }

    @Test
    @Transactional
    public void PaymentRepository_FindAll_ReturnsList(){
        Building building = new Building("1234 Sesame Street");
        buildingRepository.save(building);

        Assertions.assertThat(apartmentRepository.findAll().size()).isEqualTo(8);

        Apartment apartment = new Apartment(building, "1W");
        apartmentRepository.save(apartment);

        Payment payment1 = new Payment(apartment, 1200, 1);
        Payment payment2 = new Payment(apartment, 1200, 1);
        paymentRepository.saveAll(Arrays.asList(payment1, payment2));

        List<Payment> payments = paymentRepository.findAll();

        Assertions.assertThat(payments).isNotNull();
        Assertions.assertThat(payments.size()).isEqualTo(14);
    }
}