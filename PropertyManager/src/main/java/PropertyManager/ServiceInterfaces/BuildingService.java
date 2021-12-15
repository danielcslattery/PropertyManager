package PropertyManager.ServiceInterfaces;


import PropertyManager.Model.Building;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface BuildingService {

    List<Building> getAll();

    List<Building> getByAddress(String address);

    void addNew(String address);

    Optional<Building> getById(Long id);

    void delete(Long id);

    void update(Building building);

}
