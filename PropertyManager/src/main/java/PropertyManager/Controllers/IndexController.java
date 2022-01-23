package PropertyManager.Controllers;

import PropertyManager.Services.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class IndexController {

    @Autowired
    private BuildingService buildingService;

    @GetMapping
    public String getAllBuildings(Model model){
        model.addAttribute("buildings", buildingService.getAll());
        return "index";
    }

}