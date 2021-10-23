package PropertyManager.Controllers;

import PropertyManager.Entities.Apartment;
import PropertyManager.Entities.Building;
import PropertyManager.Entities.Payment;
import PropertyManager.ServiceInterfaces.ApartmentService;
import PropertyManager.ServiceInterfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ApartmentService apartmentService;

    @GetMapping("/all")
    public String getAllPayments(Model model){
        model.addAttribute("payments", paymentService.getAll());
        return "Payments/all";
    }

    //TODO: Change parameters to apartmentId to simplify function.
    @PostMapping("/addNew")
    public String addNewPayment(@RequestParam long buildingId,
                                @RequestParam long apartmentId,
                                @RequestParam int paymentAmount,
                                @RequestParam int month){
        paymentService.addNew(buildingId, apartmentId, paymentAmount, month);
        return "redirect:all";
    }

    @GetMapping("/byApartment/{apartmentId}")
    public String getAllPaymentsByApartment (@PathVariable Long apartmentId, Model model){
        model.addAttribute("payments", paymentService.getAllPaymentsByApartment(apartmentId));
        model.addAttribute("apartment", apartmentService.getById(apartmentId).get());

        return "/Payments/paymentsByApartment";
    }

    @GetMapping("/{paymentId}")
    public String getPayment(@PathVariable Long paymentId, Model model){
        Optional<Payment> payment = paymentService.getById(paymentId);
        System.out.println("Here!");
        model.addAttribute("payment", payment.get());
        return "/Payments/landing";
    }


    @GetMapping("/delete/{paymentId}")
    public String deletePayment(@PathVariable Long paymentId, Model model){
        paymentService.delete(paymentId);
        return "redirect:../all";
    }


    @GetMapping("/edit/{paymentId}")
    public String showUpdateForm(@PathVariable Long paymentId, Model model){
        Optional<Payment> payment = paymentService.getById(paymentId);
        model.addAttribute("payment", payment.get());
        System.out.println("edit" + model.getAttribute("payment").toString());
        return "payments/update";
    }

    @PostMapping("/update/{paymentId}")
    public String updatepayment(@PathVariable Long paymentId, Payment payment, Model model){
        System.out.println("update" + model.getAttribute("payment").toString());
        paymentService.update(payment);
        return "redirect:../all";
    }

}