package PropertyManager.service;

import PropertyManager.model.Apartment;
import PropertyManager.exception.ApartmentNumberNotFound;
import PropertyManager.exception.EmptyReturnFromQuery;
import PropertyManager.exception.EntityIdNotFound;
import PropertyManager.model.Building;
import PropertyManager.repository.ApartmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApartmentService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private BuildingService buildingService;

    public List<Apartment> getAll(){
        List<Apartment> apartments = apartmentRepository.findAll();

        if (apartments.size() == 0){
            throw new EmptyReturnFromQuery("apartment", "getAll");
        }

        return apartments;
    }

    public List<Apartment> getByNumber(String number){
        List<Apartment> apartmentList = apartmentRepository.findApartmentByNumber(number);
        if(apartmentList.size() == 0){
            throw new ApartmentNumberNotFound(number);
        }

        return apartmentList;
    }

    public List<Apartment> getByBuilding(Building building){
        return apartmentRepository.findApartmentByBuilding(building.getId());
    }

    public Apartment add(Apartment apartment){
        apartmentRepository.save(apartment);

        Building building = apartment.getBuilding();
        buildingService.recalculateNumberOfApartments(building);

        return apartment;
    }

    public List<Apartment> getLatePayments(int month){
        return apartmentRepository.findLatePayments(month);
    }

    public Apartment getById(long id){

        Optional<Apartment> apartmentOpt = apartmentRepository.findById(id);

        if (apartmentOpt.isEmpty()){
            throw new EntityIdNotFound(id, "apartment");
        }

        return apartmentOpt.get();
    }

    public Apartment delete(Apartment apartment){
        apartment.getBuilding().getApartments().remove(apartment);
        apartmentRepository.delete(apartment);
        return apartment;
    }

    public Apartment update(Apartment apartment){
        return apartmentRepository.save(apartment);
    }

}
