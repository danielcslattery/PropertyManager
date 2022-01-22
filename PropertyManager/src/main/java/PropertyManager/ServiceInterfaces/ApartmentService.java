package PropertyManager.ServiceInterfaces;

import PropertyManager.Model.Apartment;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;

@Component
public interface ApartmentService {

    List<Apartment> getAll();

    List<Apartment> getByBuilding(Long building);

    Apartment add(Apartment apartment);

    List<Apartment> getLatePayments(int month);

    Apartment getById(long id);

    Apartment delete(Long id);

    Apartment update(Apartment apartment);

}
