package PropertyManager.Services;

import PropertyManager.Model.Apartment;
import PropertyManager.Model.Payment;
import PropertyManager.Exception.EmptyReturnFromQuery;
import PropertyManager.Exception.EntityIdNotFound;
import PropertyManager.Repositories.ApartmentRepository;
import PropertyManager.Repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    ApartmentRepository apartmentRepository;

    public List<Payment> getAll(){
        List<Payment> payments = (List<Payment>) paymentRepository.findAll();

        if (payments.size() == 0){
            throw new EmptyReturnFromQuery("payment", "findAll");
        }

        return payments;
    }

    public Payment add(Payment payment){
        return paymentRepository.save(payment);
    }

    public List<Payment> getByApartment(Apartment apartment){
        return paymentRepository.findPaymentsByApartmentId(apartment.getId());
    }

    public Payment delete(Payment payment){
        paymentRepository.delete(payment);
        return payment;
    }

    //TODO expected exception not being thrown when findById should be failing.
    public Payment getById(long paymentId){
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
        if (paymentOptional.isEmpty()){
            throw new EntityIdNotFound(paymentId, "payment");
        }
        return paymentOptional.get();
    }

    public Payment update(Payment payment){
        return paymentRepository.save(payment);
    }

}
