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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @PostMapping("/add")
    public void addNewPayment(@RequestParam long buildingId,
                                @RequestParam long apartmentId,
                                @RequestParam int paymentAmount,
                                @RequestParam int month){
        paymentService.addNew(buildingId, apartmentId, paymentAmount, month);
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
    @ResponseBody
    public void deletePayment(@PathVariable Long paymentId, Model model){
        paymentService.delete(paymentId);
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


    @GetMapping("/byApartment/{apartmentId}/delete/{paymentId}")
    public String byApartmentDeleteApartment(@PathVariable("apartmentId") String apartmentId,
                                            @PathVariable("paymentId") Long paymentId,
                                            Model model,
                                            RedirectAttributes redirectAttributes){
        //TODO change this to deleteById
        paymentService.delete(paymentId);
        redirectAttributes.addAttribute("apartmentId", apartmentId);
        return "redirect:/payments/byApartment/{apartmentId}";
    }

    //TODO figure out how to pass the apartment id properly
    @GetMapping("/byApartment/edit/{paymentId}")
    public String byApartmentShowUpdateForm(@PathVariable("paymentId") Long paymentId, Model model){
        Optional<Payment> payment = paymentService.getById(paymentId);
        model.addAttribute("payment", payment.get());
        System.out.println("edit" + model.getAttribute("payment").toString());
        return "Payments/update";
    }

    @PostMapping("/byApartment/update/{paymentId}")
    public String byApartmentUpdate(@PathVariable Long paymentId,
                                   Payment payment,
                                   Model model,
                                   RedirectAttributes redirectAttributes){
        // Retrieve apartmentId from the database and add it to the apartment object.
        long apartmentId = paymentService.getById(paymentId).get().getApartmentId();
        payment.setApartmentId(apartmentId);

        System.out.println("update model" + payment.toString());
        paymentService.update(payment);
        redirectAttributes.addAttribute("apartmentId", apartmentId);
        return "redirect:../{apartmentId}";
    }

}