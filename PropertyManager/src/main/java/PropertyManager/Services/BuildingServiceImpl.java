package PropertyManager.Services;


import PropertyManager.Entities.Building;
import PropertyManager.Exception.BuildingAddressNotFound;
import PropertyManager.Exception.EntityIdNotFound;
import PropertyManager.Exception.NoBuildingsInDatabase;
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

    public List<Building> getAll(){
        List<Building> buildingIter = (List<Building>) buildingRepository.findAll();

        if (buildingIter.size() == 0){
            throw new NoBuildingsInDatabase();
        }

        return buildingIter;
    }

    public List<Building> getByAddress(String address){
        List<Building> buildings = buildingRepository.findBuildingByAddress(address);
        if (buildings.size() == 0){
            throw new BuildingAddressNotFound(address);
        }
        return buildings;
    }

    public void addNew(String address){
        buildingRepository.save(new Building(address));
    }

    public Optional<Building> getById(Long id){

        Optional<Building> buildingOpt = buildingRepository.findById(id);

        if (buildingOpt.isEmpty()){
            throw new EntityIdNotFound(id, "building");
        }

        return buildingOpt;
    }

    public void delete(Long id){
        Optional<Building> buildingOpt = buildingRepository.findById(id);
        buildingRepository.delete(buildingOpt.get());
    }

    public void update(Building building){
        buildingRepository.save(building);
    }

}
