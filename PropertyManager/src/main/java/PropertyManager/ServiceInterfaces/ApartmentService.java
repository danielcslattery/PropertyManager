package PropertyManager.ServiceInterfaces;

import PropertyManager.Entities.Apartment;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public interface ApartmentService {

    Iterable<Apartment> getAll();

    List<Apartment> getByNumber(String number);

    List<Apartment> getByBuilding(Long building);

    void addNewApartment(Long buildingId, String number);

    List<Apartment> getDelinquent(int month);

}
