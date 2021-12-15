package PropertyManager.Controllers;

import PropertyManager.Repositories.BuildingRepository;
import PropertyManager.ServiceInterfaces.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class IndexController {

    @Autowired
    private BuildingRepository repository;

    @Autowired
    private BuildingService buildingService;

    @GetMapping
    public String getAllBuildings(Model model){
        model.addAttribute("buildings", buildingService.getAll());
        return "index";
    }

}