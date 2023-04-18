package PropertyManager.controller;

import PropertyManager.controller.dto.PaymentDTO;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buildings/{buildingId}/apartments/{apartmentId}/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private RequestMapper mapper;

    // Adds new payment to database without returning a new view.  Used with AJAX requests.
    //TODO: Change parameters to apartmentId to simplify function.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentDTO add(@Valid @RequestBody PaymentRequest request){
        Payment payment = mapper.toModel(request);

        payment = paymentService.add(payment);
        return mapper.toDTO(payment);
    }

    @GetMapping
    public List<PaymentDTO> getByApartment(@PathVariable Long apartmentId){
        List<Payment> payments = paymentService.getByApartment(apartmentService.getById(apartmentId));
        List<PaymentDTO> dtos = payments.stream()
            .map(payment -> mapper.toDTO(payment))
            .collect(Collectors.toList());

        return dtos;
    }

    @GetMapping("/{paymentId}")
    public PaymentDTO get(@PathVariable Long paymentId){
        Payment payment = paymentService.getById(paymentId);
        return mapper.toDTO(payment);
    }

    @DeleteMapping("/{paymentId}")
    public PaymentDTO delete(@PathVariable Long paymentId){
        Payment payment = paymentService.getById(paymentId);
        paymentService.delete(payment);

        return mapper.toDTO(payment);
    }

    @PutMapping("/{paymentId}")
    public PaymentDTO update(@PathVariable Long paymentId, @Valid @RequestBody PaymentRequest request){
        Payment payment = mapper.toModel(request);
        paymentService.update(payment);
        
        return mapper.toDTO(payment);
    }
}