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

@Controller
@RequestMapping("/buildings")
@CrossOrigin(origins = "http://localhost:4200")
public class BuildingController {

    @Autowired
    private BuildingRepository repository;

    @Autowired
    private BuildingService buildingService;

    @GetMapping("/all")
    @ResponseBody
    public List<Building> getAll(){
        return buildingService.getAll();
    }

    // Allows search by address
    @GetMapping("/byAddress")
    public String getByAddress(@RequestParam String address){
        Building building = buildingService.getByAddress(address).get(0);
        return "redirect:" + building.getId();
    }

    // Adds new building to database without returning a new view.  Used with AJAX requests.
    @PostMapping
    public ResponseEntity<Building> addNew(@Valid @RequestBody Building building){
        Building buildingAdded = buildingService.add(building);
        return new ResponseEntity<>(buildingAdded, HttpStatus.CREATED);
    }

    // Other commands are redirected to a landing page for single buildings.
    @GetMapping("/{buildingId}")
    public String getBuilding(@PathVariable Long buildingId, Model model){
        Building building = buildingService.getById(buildingId);
        model.addAttribute("building", building);
        return "/Buildings/landing";
    }

    // Adds new apartment to database without returning a new view.  Used with AJAX requests.
    @DeleteMapping("/{buildingId}")
    public ResponseEntity<Building> delete(@PathVariable Long buildingId){
        Building buildingDeleted = buildingService.delete(buildingId);
        return new ResponseEntity<>(buildingDeleted, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Building> update(@Valid @RequestBody Building building){
        Building buildingUpdated = buildingService.update(building);
        return new ResponseEntity<>(buildingUpdated, HttpStatus.OK);
    }

}
