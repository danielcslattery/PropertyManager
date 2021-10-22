package PropertyManager.Services;

import PropertyManager.Entities.Apartment;
import PropertyManager.Entities.Building;
import PropertyManager.Exception.ApartmentNumberNotFound;
import PropertyManager.Exception.BuildingIdNotFound;
import PropertyManager.Repositories.ApartmentRepository;
import PropertyManager.ServiceInterfaces.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    public Iterable<Apartment> getAll(){
        return apartmentRepository.findAll();
    }

    public List<Apartment> getByNumber(String number){
        List<Apartment> apartmentList = apartmentRepository.findApartmentByNumber(number);
        if(apartmentList.size() == 0){
            throw new ApartmentNumberNotFound(number);
        }

        return apartmentList;
    }

    public List<Apartment> getByBuildingId(Long building){
        return apartmentRepository.findApartmentByBuilding(building);
    }

    public void addNewApartment(Long buildingId, String number){
        apartmentRepository.save(new Apartment(buildingId, number));
    }

    public List<Apartment> getDelinquent(int month){
        return apartmentRepository.findDelinquentApartments(month);
    }

    public Optional<Apartment> getById(long id){

        Optional<Apartment> apartmentOpt = apartmentRepository.findById(id);

//        if (apartmentOpt.isEmpty()){
//            throw new ApartmentIdNotFound(id);
//        }

        return apartmentOpt;
    }

    public void delete(Long id){
        Optional<Apartment> apartmentOpt = apartmentRepository.findById(id);
        apartmentRepository.delete(apartmentOpt.get());
    }

    public void update(Apartment apartment){
        apartmentRepository.save(apartment);
    }


}
