package PropertyManager.Controllers;

import PropertyManager.Entities.Building;
import PropertyManager.Repositories.ApartmentRepository;
import PropertyManager.Entities.Apartment;
import PropertyManager.ServiceInterfaces.ApartmentService;
import PropertyManager.ServiceInterfaces.BuildingService;
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

    @Autowired
    private BuildingService buildingService;

    @GetMapping("/all")
    public String getAllApartments(Model model){
        model.addAttribute("apartments", apartmentService.getAll());
        return "/Apartments/all";
    }

    @GetMapping("/byBuilding/{buildingId}")
    public String getByBuilding(Model model, @PathVariable Long buildingId){
        model.addAttribute("apartments", apartmentService.getByBuildingId(buildingId));
        model.addAttribute("building", buildingService.getById(buildingId).get());
        return "/Apartments/ApartmentsByBuilding";
    }


    @PostMapping("/add")
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

    @GetMapping("/delete/{apartmentId}")
    public String deleteApartment(@PathVariable Long apartmentId, Model model){
        apartmentService.delete(apartmentId);
        return "redirect:../all";
    }


    @GetMapping("/edit/{apartmentId}")
    public String showUpdateForm(@PathVariable Long apartmentId, Model model){
        Optional<Apartment> apartment = apartmentService.getById(apartmentId);
        model.addAttribute("apartment", apartment.get());
        System.out.println("edit" + model.getAttribute("apartment").toString());
        return "Apartments/update";
    }

    @PostMapping("/update/{apartmentId}")
    public String updateApartment(@PathVariable Long apartmentId, Apartment apartment, Model model){
        System.out.println("update" + model.getAttribute("apartment").toString());
        apartmentService.update(apartment);
        return "redirect:../all";
    }

}

