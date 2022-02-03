package PropertyManager.Services;

import PropertyManager.Model.Apartment;
import PropertyManager.Exception.ApartmentNumberNotFound;
import PropertyManager.Exception.EmptyReturnFromQuery;
import PropertyManager.Exception.EntityIdNotFound;
import PropertyManager.Model.Building;
import PropertyManager.Model.Payment;
import PropertyManager.Repositories.ApartmentRepository;
import PropertyManager.Repositories.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApartmentService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private PaymentService paymentService;

    public List<Apartment> getAll(){
        List<Apartment> apartments = (List<Apartment>) apartmentRepository.findAll();

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
        Building building = buildingService.getById(apartment.getBuildingId());
        apartment.setBuildingId(building.getId());
        return apartmentRepository.save(apartment);
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

        for(Payment payment : paymentService.getByApartment(apartment)){
            paymentService.delete(payment);
        }

        apartmentRepository.delete(apartment);
        return apartment;
    }

    public Apartment update(Apartment apartment){
        return apartmentRepository.save(apartment);
    }


}
