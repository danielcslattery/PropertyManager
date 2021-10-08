package PropertyManager.Controllers;

import PropertyManager.Repositories.ApartmentRepository;
import PropertyManager.Models.Apartment;
import PropertyManager.Models.Payment;
import PropertyManager.Repositories.PaymentRepository;
import PropertyManager.ServiceInterfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    @GetMapping("/all")
    @ResponseBody
    public Iterable<Payment> getAllPayments(){
        return paymentService.getAll();
    }

    //TODO: Change parameters to apartmentId to simplify function.
    @PostMapping("/addNew")
    public String addNewPayment(@RequestParam long buildingId,
                                @RequestParam long apartmentId,
                                @RequestParam int paymentAmount,
                                @RequestParam int month){
        paymentService.addNewPayment(buildingId, apartmentId, paymentAmount, month);
        return "redirect:all";
    }

    //TODO: Investigate query vs @pathvariable https://spring.io/guides/tutorials/rest/
    @GetMapping("/allByApartment")
    @ResponseBody
    public List<Payment> getAllPaymentsByApartment (@RequestParam Long apartmentId){
        return paymentService.getAllPaymentsByApartment(apartmentId);
    }


}