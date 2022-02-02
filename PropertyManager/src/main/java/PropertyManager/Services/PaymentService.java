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
        Optional<Apartment> apartmentOptional = apartmentRepository.findById(payment.getApartmentId());

        if (apartmentOptional.isEmpty()){
            throw new EntityIdNotFound(payment.getApartmentId(), "payment");
        }

        payment.setApartmentId(apartmentOptional.get().getId());

        return paymentRepository.save(payment);
    }

    public List<Payment> getByApartment(Long apartmentId){
        return paymentRepository.findPaymentsByApartmentId(apartmentId);
    }

    public Payment delete(Payment payment){
//        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
//        if (paymentOptional.isEmpty()){
//            throw new EntityIdNotFound(paymentId, "payment");
//        }
        paymentRepository.delete(payment);
        return payment;
    }

    //TODO expected exception not being thrown when findById should be failing.
    public Payment  getById(long paymentId){
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
