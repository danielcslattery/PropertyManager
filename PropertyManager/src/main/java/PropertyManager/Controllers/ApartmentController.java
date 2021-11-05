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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // Add new apartment from a byBuilding page and redirect to that same page
    // rather than redirect to /apartments/all.  Redirect Attributes allows this dynamically.
    @PostMapping("/byBuilding/{buildingId}/add")
    public String addNewToBuilding(@ModelAttribute Apartment apartment,
                                   @PathVariable Long buildingId,
                                   RedirectAttributes redirectAttributes){
        apartmentService.addNewApartment(buildingId, apartment.getApartmentNumber());
        redirectAttributes.addAttribute("buildingId", buildingId);
        return "redirect:/apartments/byBuilding/{buildingId}";
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


    @GetMapping("/byBuilding/{buildingId}/delete/{apartmentId}")
    public String byBuildingDeleteApartment(@PathVariable("buildingId") String buildingId,
                                            @PathVariable("apartmentId") Long apartmentId,
                                            Model model,
                                            RedirectAttributes redirectAttributes){
        apartmentService.delete(apartmentId);
        redirectAttributes.addAttribute("buildingId", buildingId);
        return "redirect:/apartments/byBuilding/{buildingId}";
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

