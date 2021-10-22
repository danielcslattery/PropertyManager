package PropertyManager.ServiceInterfaces;

import PropertyManager.Entities.Apartment;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;

@Component
public interface ApartmentService {

    Iterable<Apartment> getAll();

    List<Apartment> getByNumber(String number);

    List<Apartment> getByBuildingId(Long building);

    void addNewApartment(Long buildingId, String number);

    List<Apartment> getDelinquent(int month);

    Optional<Apartment> getById(long id);

    void delete(Long id);

    void update(Apartment apartment);

}
