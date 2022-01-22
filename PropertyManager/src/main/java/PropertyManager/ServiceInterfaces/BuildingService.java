package PropertyManager.ServiceInterfaces;


import PropertyManager.Model.Building;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface BuildingService {

    List<Building> getAll();

    List<Building> getByAddress(String address);

    Building add(Building building);

    Building getById(Long id);

    Building delete(Long id);

    Building update(Building building);

}
