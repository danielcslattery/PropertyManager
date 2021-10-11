package PropertyManager.Controllers;

import PropertyManager.Repositories.BuildingRepository;
import PropertyManager.Entities.Building;
import PropertyManager.ServiceInterfaces.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/buildings")
public class BuildingController {

    @Autowired
    private BuildingRepository repository;

    @Autowired
    private BuildingService buildingService;

    @GetMapping("/all")
    @ResponseBody
    public Iterable<Building> getAllBuildings(){
        return buildingService.getAll();
    }

    @GetMapping("/byAddress")
    @ResponseBody
    public List<Building> getByAddress(@RequestParam String address){
        return buildingService.getByAddress(address);
    }


    @PostMapping("/addNew")
    public String addNewBuilding(@ModelAttribute Building building, Model model){
        model.addAttribute("building", building);
        buildingService.addNew(building.getBuildingAddress());
        return "redirect:all";
    }

    @GetMapping("/getById")
    @ResponseBody
    public Optional<Building> getBuilding(@RequestParam long buildingId, Model model){
        Optional<Building> building = buildingService.getById(buildingId);
        model.addAttribute("building", building);
        return building;
    }

//    @GetMapping("/getById")
//    @ResponseBody
//    public Optional<Building> getById(@RequestParam long id){
//        return buildingService.getById(id);
//    }

}
