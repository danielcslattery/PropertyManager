package PropertyManager.Controllers;

import PropertyManager.Repositories.ApartmentRepository;
import PropertyManager.Models.Apartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/apartments")
public class ApartmentController {

    @Autowired
    private ApartmentRepository repository;

    @GetMapping("/all")
    @ResponseBody
    public Iterable<Apartment> getAllApartments(){
        return repository.findAll();
    }

    @GetMapping("/byNumber")
    @ResponseBody
    public List<Apartment> getByNumber(@RequestParam String number){
        //model.addAttribute("number", number);
        return repository.findApartmentByNumber(number);
    }

    @GetMapping("/byBuilding")
    @ResponseBody
    public List<Apartment> getByBuilding(@RequestParam Long building){
        return repository.findApartmentByBuilding(building);
    }


    @PostMapping("/addNew")
    public String addNewApartment(@RequestParam Long buildingId,
                                @RequestParam String number){
        repository.save(new Apartment(buildingId, number));
        return "redirect:all";
    }

    @GetMapping("/delinquent")
    @ResponseBody
    public List<Apartment> getDelinquent(@RequestParam int month){
        return repository.findDelinquentApartments(month);
    }

}
