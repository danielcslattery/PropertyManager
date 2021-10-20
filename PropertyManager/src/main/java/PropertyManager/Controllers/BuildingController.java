package PropertyManager.Controllers;

import PropertyManager.Repositories.BuildingRepository;
import PropertyManager.Entities.Building;
import PropertyManager.ServiceInterfaces.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/buildings")
public class BuildingController {

    @Autowired
    private BuildingRepository repository;

    @Autowired
    private BuildingService buildingService;

    @GetMapping("/all")
    public String getAllBuildings(Model model){
        model.addAttribute("buildings", buildingService.getAll());
        return "/Buildings/all";
    }

    @GetMapping("/byAddress")
    public String getByAddress(@RequestParam String address){
        Building building = buildingService.getByAddress(address).get(0);
        return "redirect:" + building.getBuildingId();
    }

    // Currently, Model Attribute is not deeply used
    @PostMapping("/addNew")
    public String addNewBuilding(@ModelAttribute Building building, Model model){
        buildingService.addNew(building.getAddress());
        model.addAttribute("building", building);
        return "redirect:all";
    }

    // Other commands are redirected to a landing page for single buildings.
    @GetMapping("/{buildingId}")
    public String getBuilding(@PathVariable Long buildingId, Model model){
        Optional<Building> building = buildingService.getById(buildingId);
        model.addAttribute("building", building.get());
        return "/Buildings/landing";
    }

    @GetMapping("/delete/{buildingId}")
    public String deleteBuilding(@PathVariable Long buildingId, Model model){
        buildingService.delete(buildingId);
        return "redirect:../all";
    }

}
