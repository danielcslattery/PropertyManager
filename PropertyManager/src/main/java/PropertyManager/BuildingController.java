package PropertyManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BuildingController {

    @Autowired
    private BuildingRepository repository;

    @GetMapping("/allBuildings")
    @ResponseBody
    public Iterable<Building> getAllBuildings(){
        return repository.findAll();
    }

    @GetMapping("/buildingsQuery")
    @ResponseBody
    public List<Building> getByNumber(@RequestParam String address){
        return repository.findBuildingByAddress(address);
    }


    @PostMapping("/addNewBuilding")
    @ResponseBody
    public void addNewBuilding(@RequestParam String address){
        repository.save(new Building(address));
    }

}
