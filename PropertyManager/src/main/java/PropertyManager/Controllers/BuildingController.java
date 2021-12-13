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
    public String getAll(Model model){
        model.addAttribute("buildings", buildingService.getAll());
        return "/Buildings/all";
    }

    // Allows search by address
    @GetMapping("/byAddress")
    public String getByAddress(@RequestParam String address){
        Building building = buildingService.getByAddress(address).get(0);
        return "redirect:" + building.getBuildingId();
    }

    // Adds new building to database without returning a new view.  Used with AJAX requests.
    @PostMapping("/addNew")
    public void addNew(@ModelAttribute Building building){
        buildingService.addNew(building.getAddress());
    }

    // Other commands are redirected to a landing page for single buildings.
    @GetMapping("/{buildingId}")
    public String getBuilding(@PathVariable Long buildingId, Model model){
        Optional<Building> building = buildingService.getById(buildingId);
        model.addAttribute("building", building.get());
        return "/Buildings/landing";
    }

    // Adds new apartment to database without returning a new view.  Used with AJAX requests.
    @GetMapping("/delete/{buildingId}")
    public void deleteBuilding(@PathVariable Long buildingId){
        buildingService.delete(buildingId);
    }

    @GetMapping("/edit/{buildingId}")
    public String showUpdateForm(@PathVariable Long buildingId, Model model){
        Optional<Building> building = buildingService.getById(buildingId);
        model.addAttribute("building", building.get());
        System.out.println("edit" + model.getAttribute("building").toString());
        return "Buildings/update";
    }

    @PostMapping("/update/{buildingId}")
    public String updateBuilding(@PathVariable Long buildingId, Building building, Model model){
        System.out.println("update" + model.getAttribute("building").toString());
        buildingService.update(building);
        return "redirect:../all";
    }

}
