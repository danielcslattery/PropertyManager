package PropertyManager.Controllers;

import PropertyManager.Repositories.BuildingRepository;
import PropertyManager.Model.Building;
import PropertyManager.ServiceInterfaces.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/buildings")
@CrossOrigin(origins = "http://localhost:4200")
public class BuildingController {

    @Autowired
    private BuildingRepository repository;

    @Autowired
    private BuildingService buildingService;

    @GetMapping
    public String getAll(Model model){
        model.addAttribute("buildings", buildingService.getAll());
        return "/Buildings/all";
    }

    @GetMapping("/allRest")
    @ResponseBody
    public List<Building> getAllRest(Model model){
        List<Building> buildings = buildingService.getAll();
        return buildings;
    }

    // Allows search by address
    @GetMapping("/byAddress")
    public String getByAddress(@RequestParam String address){
        Building building = buildingService.getByAddress(address).get(0);
        return "redirect:" + building.getBuildingId();
    }

    // Adds new building to database without returning a new view.  Used with AJAX requests.
    @PostMapping
    public ResponseEntity addNew(@Valid @RequestBody Building building){
        Building buildingAdded = buildingService.addNew(building.getAddress());
        ResponseEntity responseEntity = new ResponseEntity(buildingAdded, HttpStatus.CREATED);
        System.out.println(responseEntity);
        return responseEntity;
    }

    // Other commands are redirected to a landing page for single buildings.
    @GetMapping("/{buildingId}")
    public String getBuilding(@PathVariable Long buildingId, Model model){
        Optional<Building> building = buildingService.getById(buildingId);
        model.addAttribute("building", building.get());
        System.out.println("Getting");
        return "/Buildings/landing";
    }

    // Adds new apartment to database without returning a new view.  Used with AJAX requests.
    @DeleteMapping("/{buildingId}")
    public ResponseEntity deleteBuilding(@PathVariable Long buildingId){
        Building buildingDeleted = buildingService.delete(buildingId);
        ResponseEntity responseEntity = new ResponseEntity(buildingDeleted, HttpStatus.OK);
        System.out.println(responseEntity);
        return responseEntity;
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

    @PutMapping
    public ResponseEntity updateBuildingDirect(@Valid @RequestBody Building building){
        System.out.println("update" + building.toString());
        Building buildingUpdated = buildingService.update(building);

        ResponseEntity responseEntity = new ResponseEntity(buildingUpdated, HttpStatus.OK);
        System.out.println(responseEntity);
        return responseEntity;
    }

}
