package PropertyManager.ServiceInterfaces;

import PropertyManager.Models.Apartment;
import PropertyManager.Models.Payment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PaymentService {

    public Iterable<Payment> getAll();

    void addNewPayment(long buildingId, long apartmentId, int paymentAmount, int month);

    public List<Payment> getAllPaymentsByApartment (Long apartmentId);

}
