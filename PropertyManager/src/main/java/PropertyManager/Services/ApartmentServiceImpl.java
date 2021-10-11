package PropertyManager.Services;

import PropertyManager.Entities.Apartment;
import PropertyManager.Repositories.ApartmentRepository;
import PropertyManager.ServiceInterfaces.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    public Iterable<Apartment> getAll(){
        return apartmentRepository.findAll();
    }

    public List<Apartment> getByNumber(String number){
        return apartmentRepository.findApartmentByNumber(number);
    }

    public List<Apartment> getByBuilding(Long building){
        return apartmentRepository.findApartmentByBuilding(building);
    }

    public void addNewApartment(Long buildingId, String number){
        apartmentRepository.save(new Apartment(buildingId, number));
    }

    public List<Apartment> getDelinquent(int month){
        return apartmentRepository.findDelinquentApartments(month);
    }

}
