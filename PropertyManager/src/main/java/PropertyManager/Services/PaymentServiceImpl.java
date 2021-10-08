package PropertyManager.Services;

import PropertyManager.Models.Apartment;
import PropertyManager.Models.Payment;
import PropertyManager.Repositories.ApartmentRepository;
import PropertyManager.Repositories.PaymentRepository;
import PropertyManager.ServiceInterfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


}
