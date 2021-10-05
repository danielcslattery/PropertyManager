package PropertyManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
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

    //TODO: Change parameters to include buildingId only for simplification
    @PostMapping("/addNew")
    @ResponseBody
    public String addNewApartment(@RequestParam Long buildingId,
                                @RequestParam String number){
        repository.save(new Apartment(buildingId, number));
        return "Added new apartment.";
    }

    @GetMapping("/delinquent")
    @ResponseBody
    public List<Apartment> getDelinquent(@RequestParam int month){
        return repository.findDelinquentApartments(month);
    }

}
