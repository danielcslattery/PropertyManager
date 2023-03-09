package PropertyManager.service.mapper;

import PropertyManager.model.Apartment;
import PropertyManager.model.Building;
import PropertyManager.repository.ApartmentRepository;
import PropertyManager.repository.BuildingRepository;

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

    public Apartment toModel(ApartmentRequest request){
        System.out.println(request);
        Apartment apartment;

        if (request.getId() != 0){
            System.out.println("Object doesn't appear to lack id.");
            apartment = apartmentRepository.findById(request.getId()).get();
        } else {
            apartment = new Apartment();
        }

        apartment.setNumber(request.getNumber());
        apartment.setBuildingId(request.getBuildingId());

        System.out.println(apartment);

        return apartment;
    }
}
