package PropertyManager.Services;

import PropertyManager.Model.Apartment;
import PropertyManager.Model.Payment;
import PropertyManager.Exception.EmptyReturnFromQuery;
import PropertyManager.Exception.EntityIdNotFound;
import PropertyManager.Repositories.ApartmentRepository;
import PropertyManager.Repositories.PaymentRepository;
import PropertyManager.ServiceInterfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

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

    public Payment addNew(long buildingId, long apartmentId, int paymentAmount, int month){
        List<Apartment> apt = apartmentRepository.findApartmentByBuildingAndApartmentId(buildingId, apartmentId);
        return paymentRepository.save(new Payment(apt.get(0).getApartmentId(), paymentAmount, month));
    }

    public Payment addNew(Payment payment){
        System.out.print(payment);
        Optional<Apartment> apt = apartmentRepository.findById(payment.getApartmentId());
//        List<Apartment> apt = apartmentRepository.findApartmentByBuildingAndApartmentId(buildingId, apartmentId);
        return paymentRepository.save(new Payment(apt.get().getApartmentId(),
                                        payment.getPaymentAmount(),
                                        payment.getMonth()));
    }

    public List<Payment> getAllPaymentsByApartment (Long apartmentId){
        return paymentRepository.findPaymentsByApartmentId(apartmentId);
    }

    public Payment delete(long paymentId){
        Optional<Payment> paymentDeleted = paymentRepository.findById(paymentId);
        paymentRepository.deleteById(paymentId);
        return paymentDeleted.get();
    }

    //TODO expected exception not being thrown when findById should be failing.
    public Optional<Payment>  getById(long paymentId){
        Optional<Payment> paymentOpt = paymentRepository.findById(paymentId);
        if (paymentOpt.isEmpty()){
            throw new EntityIdNotFound(paymentId, "payment");
        }
        return paymentOpt;
    }

    public Payment update(Payment payment){
        return paymentRepository.save(payment);
    }

}
