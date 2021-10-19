package PropertyManager.Services;


import PropertyManager.Entities.Building;
import PropertyManager.Exception.AddressNotFound;
import PropertyManager.Exception.BuildingIdNotFound;
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
            throw new AddressNotFound(address);
        }
        return buildings;
    }

    public void addNew(String address){
        buildingRepository.save(new Building(address));
    }

    public Optional<Building> getById(Long id){

        Optional<Building> buildingOpt = buildingRepository.findById(id);

        if (buildingOpt.isEmpty()){
            throw new BuildingIdNotFound(id);
        }

        return buildingOpt;


    }


}
