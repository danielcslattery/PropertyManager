package PropertyManager.service;

import PropertyManager.model.Building;
import PropertyManager.repository.BuildingRepository;
import PropertyManager.exception.BuildingAddressNotFound;
import PropertyManager.exception.EntityIdNotFound;
import PropertyManager.exception.EmptyReturnFromQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    public List<Building> getAll(){
        List<Building> buildings = buildingRepository.findAll();

        if (buildings.size() == 0){
            throw new EmptyReturnFromQuery("building", "getAll");
        }

        return buildings;
    }

    public List<Building> getByAddress(String address){
        List<Building> buildings = buildingRepository.findByAddress(address);

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
    public Building delete(Building building){
        buildingRepository.delete(building);
        return building;
    }

    public Building update(Building building){
        return buildingRepository.save(building);
    }

    public void recalculateNumberOfApartments(Building building){
        int numberOfApartments = building.getApartments().size();
        building.setNumberApartments(numberOfApartments);
        buildingRepository.save(building);
    }
}
