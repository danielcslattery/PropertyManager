package PropertyManager.service.mapper;

import PropertyManager.model.Apartment;
import PropertyManager.model.Building;
import PropertyManager.repository.ApartmentRepository;
import PropertyManager.repository.BuildingRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import PropertyManager.controller.request.ApartmentRequest;
import PropertyManager.controller.request.BuildingRequest;

@Component
public class RequestMapper {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;
    
    public Building toModel(BuildingRequest request){
        Optional<Building> buildingOpt = buildingRepository.findById(request.getId());
        Building building;

        if (buildingOpt.isPresent()) {
            building = buildingOpt.get();
        } else {
            building = new Building();
        }

        building.setAddress(request.getAddress());
        return building;
    } 

    public Apartment toModel(ApartmentRequest request){
        Optional<Apartment> apartmentOpt = apartmentRepository.findById(request.getId());
        Apartment apartment;

        if (apartmentOpt.isPresent()){
            apartment = apartmentOpt.get();
        } else {
            apartment = new Apartment();
        }

        apartment.setNumber(request.getNumber());
        apartment.setBuildingId(request.getBuildingId());

        return apartment;
    }
}
