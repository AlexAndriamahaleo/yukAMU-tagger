package fr.amu.controllers;// TODO : progress controller


import fr.amu.models.Food;
import fr.amu.services.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletContext;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class ProgressController {
    private static final Logger log = LoggerFactory.getLogger(ProgressController.class);

    @Autowired
    ServletContext context;

    @Autowired
    FoodService foodService;

    @GetMapping("/progress")
    public String progress(Map<String, Object> model){
        model.put("dones", foodService.findByDone(true));

        model.put("todos", foodService.findByDone(false));

        List<Food.TAG> tags = Arrays.asList(Food.TAG.values());
        model.put("tags", tags);

        return "progresspage";
    }

    @PostMapping("/progress/filter")
    public String progressFilter(ModelMap modelMap, String tag){

        //modelMap.put("foods")

        log.info(tag);

        modelMap.put("dones", foodService.findByTagAndDone(tag, true));
        modelMap.put("todos", foodService.findByTagAndDone(tag, false));

        List<Food.TAG> tags = Arrays.asList(Food.TAG.values());
        modelMap.put("tags", tags);

        return "progresspage";
    }
}