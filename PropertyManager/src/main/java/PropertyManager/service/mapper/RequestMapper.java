package PropertyManager.service.mapper;

import PropertyManager.model.Building;
import PropertyManager.repository.BuildingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import PropertyManager.controller.request.BuildingRequest;

@Component
public class RequestMapper {

    @Autowired
    private BuildingRepository buildingRepository;
    
    public Building toModel(BuildingRequest request){
        Building building;

        // TODO use Optional<> intead of checking request.
        if (request.getId() != 0) {
            building = buildingRepository.findById(request.getId()).get();
        } else {
            building = new Building();
        }

        building.setAddress(request.getAddress());
        return building;
    } 
}
