package PropertyManager.ServiceInterfaces;

import PropertyManager.Entities.Payment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PaymentService {

    Iterable<Payment> getAll();

    void addNew(long buildingId, long apartmentId, int paymentAmount, int month);

    void delete(long paymentId);

    List<Payment> getAllPaymentsByApartment (Long apartmentId);

    Payment getById(long paymentId);

}