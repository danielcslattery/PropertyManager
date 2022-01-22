package PropertyManager.Controllers;

import PropertyManager.Model.Apartment;
import PropertyManager.Model.Payment;
import PropertyManager.ServiceInterfaces.ApartmentService;
import PropertyManager.ServiceInterfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/payments")
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ApartmentService apartmentService;

    @GetMapping("/allRest")
    @ResponseBody
    public List<Payment> getAllRest(){
        List<Payment> payments = paymentService.getAll();
        return payments;
    }

    // Adds new payment to database without returning a new view.  Used with AJAX requests.
    //TODO: Change parameters to apartmentId to simplify function.
    @PostMapping
    public ResponseEntity addNew( @Valid @RequestBody Payment payment){
//        paymentService.addNew(buildingId, apartmentId, paymentAmount, month);
        Payment paymentAdded = paymentService.addNew(payment);
        ResponseEntity responseEntity = new ResponseEntity(paymentAdded, HttpStatus.CREATED);
        return responseEntity;
    }

    @GetMapping("/byApartmentRest/{apartmentId}")
    @ResponseBody
    public List<Payment> getAllPaymentsByApartmentRent (@PathVariable Long apartmentId, Model model){
        List<Payment> payments = paymentService.getAllPaymentsByApartment(apartmentId);
        return payments;
    }

    @GetMapping("/{paymentId}")
    public String getPayment(@PathVariable Long paymentId, Model model){
        Optional<Payment> payment = paymentService.getById(paymentId);
        System.out.println("Here!");
        model.addAttribute("payment", payment.get());
        return "/Payments/landing";
    }


    // Delete payment from database without returning a new view.  Used with AJAX requests.
    @DeleteMapping("/{paymentId}")
    public ResponseEntity deletePayment(@PathVariable Long paymentId){
        Payment paymentDeleted = paymentService.delete(paymentId);
        ResponseEntity responseEntity = new ResponseEntity(paymentDeleted, HttpStatus.OK);
        return responseEntity;
    }

    @PutMapping
    public ResponseEntity updatePaymentDirect(@Valid @RequestBody Payment payment){
        Payment paymentUpdated = paymentService.update(payment);

        ResponseEntity responseEntity = new ResponseEntity(paymentUpdated, HttpStatus.OK);
        System.out.println(responseEntity);
        return responseEntity;
    }
}