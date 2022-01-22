package PropertyManager.ServiceInterfaces;

import PropertyManager.Model.Payment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface PaymentService {

    List<Payment> getAll();

    Payment add(Payment payment);

    Payment delete(long paymentId);

    List<Payment> getByApartment(Long apartmentId);

    Payment getById(long paymentId);

    Payment update(Payment payment);

}
