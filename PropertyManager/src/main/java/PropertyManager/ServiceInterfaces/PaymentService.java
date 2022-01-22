package PropertyManager.ServiceInterfaces;

import PropertyManager.Model.Payment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface PaymentService {

    List<Payment> getAll();

    Payment addNew(long buildingId, long apartmentId, int paymentAmount, int month);

    Payment addNew(Payment payment);

    Payment delete(long paymentId);

    List<Payment> getAllPaymentsByApartment (Long apartmentId);

    Optional<Payment> getById(long paymentId);

    Payment update(Payment payment);

}
