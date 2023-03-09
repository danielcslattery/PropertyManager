package PropertyManager.controller;

import PropertyManager.controller.request.BuildingRequest;
import PropertyManager.model.Building;
import PropertyManager.service.BuildingService;
import PropertyManager.service.mapper.RequestMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/buildings")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private RequestMapper mapper;

    @GetMapping
    public List<Building> getAll(){
        return buildingService.getAll();
    }

    // Allows search by address.  Not implemented
    @GetMapping("/byAddress")
    public String getByAddress(@RequestParam String address){
        Building building = buildingService.getByAddress(address).get(0);
        return "redirect:" + building.getId();
    }

    // Adds new building to database without returning a new view.  Used with AJAX requests.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Building addNew(@Valid @RequestBody BuildingRequest request){
        Building building = mapper.toModel(request);

        Building buildingAdded = buildingService.add(building);
        return buildingAdded;
    }

    // Other commands are redirected to a landing page for single buildings.
    @GetMapping("/{buildingId}")
    public ResponseEntity<Building> getBuilding(@PathVariable Long buildingId){
        return new ResponseEntity<>(buildingService.getById(buildingId), HttpStatus.OK);
    }

    // Adds new apartment to database without returning a new view.  Used with AJAX requests.
    @DeleteMapping
    public ResponseEntity<Building> delete(@Valid @RequestBody BuildingRequest request){
        Building building = mapper.toModel(request);

        Building buildingDeleted = buildingService.delete(building);
        return new ResponseEntity<>(buildingDeleted, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Building> update(@Valid @RequestBody BuildingRequest request){
        Building building = mapper.toModel(request);

        Building buildingUpdated = buildingService.update(building);
        return new ResponseEntity<>(buildingUpdated, HttpStatus.OK);
    }
}
