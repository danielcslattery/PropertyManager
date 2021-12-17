package PropertyManager.ServiceInterfaces;

import PropertyManager.Model.Apartment;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;

@Component
public interface ApartmentService {

    List<Apartment> getAll();

    List<Apartment> getByBuildingId(Long building);

    void add(Long buildingId, String number);

    List<Apartment> getLatePayments(int month);

    Optional<Apartment> getById(long id);

    void delete(Long id);

    void update(Apartment apartment);

}
