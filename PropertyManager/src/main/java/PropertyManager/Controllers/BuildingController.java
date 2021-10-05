package PropertyManager.Controllers;

import PropertyManager.Repositories.BuildingRepository;
import PropertyManager.Models.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/buildings")
public class BuildingController {

    @Autowired
    private BuildingRepository repository;

    @GetMapping("/all")
    @ResponseBody
    public Iterable<Building> getAllBuildings(){
        return repository.findAll();
    }

    @GetMapping("/byAddress")
    @ResponseBody
    public List<Building> getByNumber(@RequestParam String address){
        return repository.findBuildingByAddress(address);
    }


    @PostMapping("/addNew")
    public String addNewBuilding(@RequestParam String address){
        repository.save(new Building(address));
        return "redirect:all";
    }

}
