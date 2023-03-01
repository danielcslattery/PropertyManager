package PropertyManager.Controllers;

import PropertyManager.Model.Building;
import PropertyManager.Services.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/buildings")
@CrossOrigin(origins = "http://localhost:4200")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @GetMapping
    public ResponseEntity<List<Building>> getAll(){
        return new ResponseEntity<>(buildingService.getAll(), HttpStatus.OK);
    }

    // Allows search by address.  Not implemented
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
    public ResponseEntity<Building> getBuilding(@PathVariable Long buildingId){
        return new ResponseEntity<>(buildingService.getById(buildingId), HttpStatus.OK);
    }

    // Adds new apartment to database without returning a new view.  Used with AJAX requests.
    @DeleteMapping
    public ResponseEntity<Building> delete(@Valid @RequestBody Building building){
        Building buildingDeleted = buildingService.delete(building);
        return new ResponseEntity<>(buildingDeleted, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Building> update(@Valid @RequestBody Building building){
        Building buildingUpdated = buildingService.update(building);
        return new ResponseEntity<>(buildingUpdated, HttpStatus.OK);
    }

}
