package PropertyManager.ServiceInterfaces;


import PropertyManager.Entities.Building;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface BuildingService {

    public Iterable<Building> getAll();

    public List<Building> getByAddress(String address);

    public void addNew(String address);

    public Optional<Building> getById(Long id);

    public void delete(Long id);

}
