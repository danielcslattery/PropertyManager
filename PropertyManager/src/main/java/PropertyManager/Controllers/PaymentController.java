package PropertyManager.Controllers;

import PropertyManager.Model.Payment;
import PropertyManager.Services.ApartmentService;
import PropertyManager.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/payments")
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ApartmentService apartmentService;

    @GetMapping
    public ResponseEntity<List<Payment>> getAll(){
        return new ResponseEntity<>(paymentService.getAll(), HttpStatus.OK);
    }

    // Adds new payment to database without returning a new view.  Used with AJAX requests.
    //TODO: Change parameters to apartmentId to simplify function.
    @PostMapping
    public ResponseEntity<Payment> add( @Valid @RequestBody Payment payment){
        Payment paymentAdded = paymentService.add(payment);
        return new ResponseEntity<>(paymentAdded, HttpStatus.CREATED);
    }

    @GetMapping("/byApartment/{apartmentId}")
    public ResponseEntity<List<Payment>> getByApartment (@PathVariable Long apartmentId){
        return new ResponseEntity<>(paymentService.getByApartment(apartmentId), HttpStatus.OK);
    }

    @GetMapping("/{paymentId}")
    public String get(@PathVariable Long paymentId, Model model){
        Payment payment = paymentService.getById(paymentId);
        model.addAttribute("payment", payment);
        return "/Payments/landing";
    }

    @DeleteMapping
    public ResponseEntity<Payment> delete(@Valid @RequestBody Payment payment){
        Payment paymentDeleted = paymentService.delete(payment);
        return new ResponseEntity<>(paymentDeleted, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Payment> update(@Valid @RequestBody Payment payment){
        Payment paymentUpdated = paymentService.update(payment);
        return new ResponseEntity<>(paymentUpdated, HttpStatus.OK);
    }
}