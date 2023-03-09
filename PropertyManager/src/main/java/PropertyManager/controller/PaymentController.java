package PropertyManager.controller;

import PropertyManager.controller.request.PaymentRequest;
import PropertyManager.model.Payment;
import PropertyManager.service.ApartmentService;
import PropertyManager.service.PaymentService;
import PropertyManager.service.mapper.RequestMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private RequestMapper mapper;

    @GetMapping
    public List<Payment> getAll(){
        return paymentService.getAll();
    }

    // Adds new payment to database without returning a new view.  Used with AJAX requests.
    //TODO: Change parameters to apartmentId to simplify function.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment add( @Valid @RequestBody PaymentRequest request){
        Payment payment = mapper.toModel(request);

        return paymentService.add(payment);
    }

    @GetMapping("/byApartment/{apartmentId}")
    public List<Payment> getByApartment (@PathVariable Long apartmentId){
        return paymentService.getByApartment(apartmentService.getById(apartmentId));
    }

    @GetMapping("/{paymentId}")
    public Payment get(@PathVariable Long paymentId){
        return paymentService.getById(paymentId);
    }

    @DeleteMapping
    public Payment delete(@Valid @RequestBody PaymentRequest request){
        Payment payment = mapper.toModel(request);

        return paymentService.delete(payment);
    }

    @PutMapping
    public Payment update(@Valid @RequestBody PaymentRequest request){
        Payment payment = mapper.toModel(request);

        return paymentService.update(payment);
    }
}