package PropertyManager.controller;

import PropertyManager.controller.dto.BuildingDTO;
import PropertyManager.controller.request.BuildingRequest;
import PropertyManager.model.Building;
import PropertyManager.service.BuildingService;
import PropertyManager.service.mapper.RequestMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buildings")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private RequestMapper mapper;

    @GetMapping
    public List<BuildingDTO> getAll(){

        List<Building> buildings = buildingService.getAll();
        List<BuildingDTO> dtos = buildings.stream()
            .map(building -> mapper.toDTO(building))
            .collect(Collectors.toList());

        return dtos;
    }

    // Adds new building to database without returning a new view.  Used with AJAX requests.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BuildingDTO addNew(@Valid @RequestBody BuildingRequest request){
        Building building = mapper.toModel(request);

        building = buildingService.add(building);

        return mapper.toDTO(building);
    }

    // Other commands are redirected to a landing page for single buildings.
    @GetMapping("/{buildingId}")
    public BuildingDTO get(@PathVariable Long buildingId){
        Building building = buildingService.getById(buildingId);

        return mapper.toDTO(building);
    }

    // Adds new apartment to database without returning a new view.  Used with AJAX requests.
    @DeleteMapping("/{buildingId}")
    public BuildingDTO delete(@PathVariable Long buildingId){
        Building building = buildingService.getById(buildingId);

        building = buildingService.delete(building);
        return mapper.toDTO(building);
    }

    @PutMapping("/{buildingId}")
    public BuildingDTO update(@PathVariable Long buildingId, @Valid @RequestBody BuildingRequest request){
        Building building = mapper.toModel(request);

        building = buildingService.update(building);
        return mapper.toDTO(building);
    }
}
