package PropertyManager.Services;

import PropertyManager.Entities.Apartment;
import PropertyManager.Entities.Payment;
import PropertyManager.Exception.EntityIdNotFound;
import PropertyManager.Repositories.ApartmentRepository;
import PropertyManager.Repositories.PaymentRepository;
import PropertyManager.ServiceInterfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    ApartmentRepository apartmentRepository;

    public Iterable<Payment> getAll(){
        return paymentRepository.findAll();
    }

    public void addNew(long buildingId, long apartmentId, int paymentAmount, int month){
        List<Apartment> apt = apartmentRepository.findApartmentByBuildingAndApartmentId(buildingId, apartmentId);
        paymentRepository.save(new Payment(apt.get(0).getApartmentId(), paymentAmount, month));
    }

    public List<Payment> getAllPaymentsByApartment (Long apartmentId){
        return paymentRepository.findPaymentsByApartmentId(apartmentId);
    }

    public void delete(long paymentId){
        paymentRepository.deleteById(paymentId);
    }

    //TODO expected exception not being thrown when findById should be failing.
    public Optional<Payment>  getById(long paymentId){
        Optional<Payment> paymentOpt = paymentRepository.findById(paymentId);
        if (paymentOpt.isEmpty()){
            throw new EntityIdNotFound(paymentId, "payment");
        }
        return paymentOpt;
    }

    public void update(Payment payment){
        paymentRepository.save(payment);
    }

}
