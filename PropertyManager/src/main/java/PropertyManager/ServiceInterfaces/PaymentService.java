package PropertyManager.ServiceInterfaces;

import PropertyManager.Entities.Apartment;
import PropertyManager.Entities.Payment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface PaymentService {

    Iterable<Payment> getAll();

    void addNew(long buildingId, long apartmentId, int paymentAmount, int month);

    void delete(long paymentId);

    List<Payment> getAllPaymentsByApartment (Long apartmentId);

    Optional<Payment> getById(long paymentId);

    void update(Payment payment);

}
