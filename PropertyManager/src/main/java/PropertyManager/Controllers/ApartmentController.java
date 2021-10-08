package PropertyManager.Controllers;

import PropertyManager.Repositories.ApartmentRepository;
import PropertyManager.Models.Apartment;
import PropertyManager.ServiceInterfaces.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/apartments")
public class ApartmentController {

    @Autowired
    private ApartmentRepository repository;

    @Autowired
    private ApartmentService apartmentService;

    @GetMapping("/all")
    @ResponseBody
    public Iterable<Apartment> getAllApartments(){
        return apartmentService.getAll();
    }

    @GetMapping("/byNumber")
    @ResponseBody
    public List<Apartment> getByNumber(@RequestParam String number){
        return apartmentService.getByNumber(number);
    }

    @GetMapping("/byBuilding")
    @ResponseBody
    public List<Apartment> getByBuilding(@RequestParam Long building){
        return apartmentService.getByBuilding(building);
    }


    @PostMapping("/addNew")
    public String addNewApartment(@RequestParam Long buildingId,
                                @RequestParam String number){
        apartmentService.addNewApartment(buildingId, number);
        return "redirect:all";
    }

    @GetMapping("/delinquent")
    @ResponseBody
    public List<Apartment> getDelinquent(@RequestParam int month){
        return apartmentService.getDelinquent(month);
    }

}
