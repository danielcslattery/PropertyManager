package PropertyManager.Services;


import PropertyManager.Model.Building;
import PropertyManager.Exception.BuildingAddressNotFound;
import PropertyManager.Exception.EntityIdNotFound;
import PropertyManager.Exception.EmptyReturnFromQuery;
import PropertyManager.Repositories.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    public List<Building> getAll(){
        List<Building> buildings = (List<Building>) buildingRepository.findAll();

        if (buildings.size() == 0){
            throw new EmptyReturnFromQuery("building", "getAll");
        }

        return buildings;
    }

    public List<Building> getByAddress(String address){
        List<Building> buildings = buildingRepository.findBuildingByAddress(address);
        if (buildings.size() == 0){
            throw new BuildingAddressNotFound(address);
        }
        return buildings;
    }

    public Building add(Building building){
        return buildingRepository.save(building);
    }

    public Building getById(Long id){
        Optional<Building> buildingOptional = buildingRepository.findById(id);

        if (buildingOptional.isEmpty()){
            throw new EntityIdNotFound(id, "building");
        }

        return buildingOptional.get();
    }

    // Returns the deleted building so the front end can delete it from the list.
    public Building delete(Long id){
        Optional<Building> buildingOptional = buildingRepository.findById(id);

        if (buildingOptional.isEmpty()){
            throw new EntityIdNotFound(id, "building");
        }

        buildingRepository.delete(buildingOptional.get());
        return buildingOptional.get();
    }

    public Building update(Building building){
        return buildingRepository.save(building);
    }

}
