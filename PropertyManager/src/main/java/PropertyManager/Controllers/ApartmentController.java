package PropertyManager.Controllers;

import PropertyManager.Repositories.ApartmentRepository;
import PropertyManager.Model.Apartment;
import PropertyManager.ServiceInterfaces.ApartmentService;
import PropertyManager.ServiceInterfaces.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/apartments")
@CrossOrigin(origins = "http://localhost:4200")
public class ApartmentController {

    @Autowired
    private ApartmentRepository repository;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private BuildingService buildingService;

    @GetMapping
    public String getAllApartments(Model model){
        model.addAttribute("apartments", apartmentService.getAll());
        return "/Apartments/all";
    }

    @GetMapping("/allRest")
    @ResponseBody
    public List<Apartment> getAllApartmentsRest(){
        List<Apartment> apartments = apartmentService.getAll();
        return apartments;
    }

    @GetMapping("/byBuilding/{buildingId}")
    public String getByBuilding(Model model, @PathVariable Long buildingId){
        model.addAttribute("apartments", apartmentService.getByBuildingId(buildingId));
        model.addAttribute("building", buildingService.getById(buildingId).get());
        return "/Apartments/ApartmentsByBuilding";
    }

    @GetMapping("/byBuildingRest/{buildingId}")
    @ResponseBody
    public List<Apartment> getByBuildingRest(@PathVariable Long buildingId){
        List<Apartment> apartments = apartmentService.getByBuildingId(buildingId);
        return apartments;
    }


    // Adds new apartment to database without returning a new view.  Used with AJAX requests.
    @PostMapping
    public void addNewApartment(@Valid @RequestBody Apartment apartment){
        apartmentService.add(apartment.getBuildingId(), apartment.getApartmentNumber());
    }


    // Returns apartments where the rents have not been paid for inputted month
    @GetMapping("/latePayments")
    public String getLatePayments(Model model, @RequestParam int month){
        model.addAttribute("apartments", apartmentService.getLatePayments(month));
        model.addAttribute("month", month);

        return "/Apartments/LatePayments";
    }

    // Other commands are redirected to a landing page for single buildings.
    @GetMapping("/{apartmentId}")
    public String getApartment(@PathVariable Long apartmentId, Model model){
        Optional<Apartment> apartment = apartmentService.getById(apartmentId);
        model.addAttribute("apartment", apartment.get());
        return "/Apartments/landing";
    }

    // Deletes apartment from database without returning a new view.  Used with AJAX requests.
    @DeleteMapping("/{apartmentId}")
    public void deleteApartment(@PathVariable Long apartmentId, Model model){
        apartmentService.delete(apartmentId);
    }

    //
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

    //TODO figure out how to pass the building id properly
    @GetMapping("/byBuilding/edit/{apartmentId}")
    public String byBuildingShowUpdateForm(@PathVariable("apartmentId") Long apartmentId, Model model){
        Optional<Apartment> apartment = apartmentService.getById(apartmentId);
        model.addAttribute("apartment", apartment.get());
        System.out.println("edit" + model.getAttribute("apartment").toString());
        return "Apartments/update";
    }

    @PostMapping("/byBuilding/update/{apartmentId}")
    public String byBuildingUpdate(@PathVariable Long apartmentId,
                                   Apartment apartment,
                                   Model model,
                                   RedirectAttributes redirectAttributes){
        // Retrieve buildingId from the database and add it to the apartment object.
        long buildingId = apartmentService.getById(apartmentId).get().getBuildingId();
        apartment.setBuildingId(buildingId);

        System.out.println("update model" + apartment.toString());
        apartmentService.update(apartment);
        redirectAttributes.addAttribute("buildingId", buildingId);
        return "redirect:../{buildingId}";
    }


}

