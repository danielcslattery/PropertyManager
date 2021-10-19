package PropertyManager.Services;


import PropertyManager.Entities.Building;
import PropertyManager.Exception.BuildingNotFound;
import PropertyManager.Repositories.BuildingRepository;
import PropertyManager.ServiceInterfaces.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    public Iterable<Building> getAll(){
        return buildingRepository.findAll();
    }

    public List<Building> getByAddress(String address){
        List<Building> buildings = buildingRepository.findBuildingByAddress(address);
        if (buildings.size() == 0){
            throw new BuildingNotFound(address);
        }
        return buildings;
    }

    public void addNew(String address){
        buildingRepository.save(new Building(address));
    }

    public Optional<Building> getById(Long id){
        return buildingRepository.findById(id);
    }


}
