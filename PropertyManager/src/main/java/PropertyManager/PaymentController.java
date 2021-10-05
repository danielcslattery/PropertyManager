package PropertyManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;

    @GetMapping("/all")
    @ResponseBody
    public Iterable<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }

    //TODO: Change parameters to apartmentId to simplify function.
    @PostMapping("/addNew")
    public String addNewPayment(@RequestParam long buildingId,
                                @RequestParam long apartmentId,
                                @RequestParam int paymentAmount,
                                @RequestParam int month){
        List<Apartment> apt = apartmentRepository.findApartmentByBuildingAndApartmentId(buildingId, apartmentId);
        paymentRepository.save(new Payment(apt.get(0).getApartmentId(), paymentAmount, month));
        return "redirect:all";
    }

    //TODO: Investigate query vs @pathvariable https://spring.io/guides/tutorials/rest/
    @GetMapping("/allByApartment")
    @ResponseBody
    public List<Payment> getAllPaymentsByApartment (@RequestParam Long apartmentId){
        return paymentRepository.findPaymentsByApartmentId(apartmentId);
    }


}