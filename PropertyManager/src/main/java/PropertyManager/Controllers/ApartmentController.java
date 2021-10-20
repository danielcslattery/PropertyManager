package PropertyManager.Controllers;

import PropertyManager.Repositories.ApartmentRepository;
import PropertyManager.Entities.Apartment;
import PropertyManager.ServiceInterfaces.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/apartments")
public class ApartmentController {

    @Autowired
    private ApartmentRepository repository;

    @Autowired
    private ApartmentService apartmentService;

    @GetMapping("/all")
    public String getAllApartments(Model model){
        model.addAttribute("apartments", apartmentService.getAll());
        return "/Apartments/all";
    }

    // TODO I don't see a reason to keep this.
    @GetMapping("/byNumber")
    @ResponseBody
    public List<Apartment> getByNumber(@RequestParam String number){
        return apartmentService.getByNumber(number);
    }

    @GetMapping("/byBuilding")
    public String getByBuilding(Model model, @RequestParam Long buildingId){
        model.addAttribute("apartments", apartmentService.getByBuildingId(buildingId));
        model.addAttribute("buildingId", buildingId);
        return "/Apartments/ApartmentsByBuilding";
    }


    @PostMapping("/addNew")
    public String addNewApartment(@ModelAttribute Apartment apartment,
                                  Model model){
        apartmentService.addNewApartment(apartment.getBuildingId(), apartment.getApartmentNumber());
        return "redirect:all";
    }

    @GetMapping("/delinquent")
    public String getDelinquent(Model model, @RequestParam int month){
        model.addAttribute("apartments", apartmentService.getDelinquent(month));
        model.addAttribute("month", month);

        return "/Apartments/Delinquent";
    }

    // Other commands are redirected to a landing page for single buildings.
    @GetMapping("/{apartmentId}")
    public String getApartment(@PathVariable Long apartmentId, Model model){
        Optional<Apartment> apartment = apartmentService.getById(apartmentId);
        model.addAttribute("apartment", apartment.get());
        return "/Apartments/landing";
    }




}
