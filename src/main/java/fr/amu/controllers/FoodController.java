package fr.amu.controllers;

import javax.servlet.ServletContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.amu.models.Food;
import fr.amu.services.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value="/food")
public class FoodController {

	private static final Logger log = LoggerFactory.getLogger(FoodController.class);
	
	@Autowired
	ServletContext context;

	@Autowired
	FoodService foodService;
	
	final ObjectMapper mapper = new ObjectMapper(); // initialize un mapper qui tranforme un objet Java en JSON pour le graphique de la vue
//	mapper.writeValueAsString( Map<String, Integer> ) //
	
	// TODO : completer avec les methodes adequates

	@PostMapping("/add")
	public String add(@ModelAttribute("Food") Food food, ModelMap model) throws JsonProcessingException {

	    food.setTag("NONE");
		int pkFood = foodService.addFood(food);

		model.put("foods", foodService.getFoods());

        model.put("progresscounts", mapper.writeValueAsString(foodService.getProgressCounts()));


        List<Food.TAG> tags = Arrays.asList(Food.TAG.values());
        model.put("tags", tags);

		return "index";
	}

	@PostMapping("/remove")
	//@DeleteMapping("/remove")
	public String remove(ModelMap model, String id) throws JsonProcessingException {

		log.info("[REMOVE] id object -- " + id);

		int foodId = Integer.parseInt(id) ;

		foodService.removeFood(foodId);

		model.put("foods", foodService.getFoods() );
        model.put("progresscounts", mapper.writeValueAsString(foodService.getProgressCounts()));


        List<Food.TAG> tags = Arrays.asList(Food.TAG.values());
        model.put("tags", tags);

		return "index";
	}

    @PostMapping("/filter")
    public String filter(boolean todo, boolean done , ModelMap model) throws JsonProcessingException {

        log.info("[FILTER] -- " + todo + " --- " + done);

        //NONE, EXCELLENT, GOOD, MEDIOCRE, BAD

        //model.put("products", ps.getCategoryProducts(type));

        if(todo && done)
            model.put("foods", foodService.getFoods());
        else
            model.put("foods", (todo) ? foodService.findByDone(false) : (done) ? foodService.findByDone(true) : foodService.getFoods());

        List<Food.TAG> tags = Arrays.asList(Food.TAG.values());
        model.put("tags", tags);

        model.put("progresscounts", mapper.writeValueAsString(foodService.getProgressCounts()));

        return "index";
    }

    @PostMapping("/set")
    public String setTag(ModelMap model, String id, String tag) throws JsonProcessingException {

        int foodId = Integer.parseInt(id) ;

        log.info("[SET TAG] -- " + id + "-- " + tag);

        Food food = foodService.getFood(foodId) ;


        boolean old = food.isDone();
        food.setDone(!old);
        food.setTag(tag);
        foodService.updateFood(food);


        model.put("progresscounts", mapper.writeValueAsString(foodService.getProgressCounts()));
        model.put("foods", foodService.getFoods() );

        List<Food.TAG> tags = Arrays.asList(Food.TAG.values());
        model.put("tags", tags);

        return "index";
    }

    @PostMapping("/unset")
    public String unsetTag(ModelMap model, String id) throws JsonProcessingException {

        int foodId = Integer.parseInt(id) ;

        log.info("[UNSET TAG] -- " + id);

        Food food = foodService.getFood(foodId) ;

        if(!food.getTag().equals(Food.TAG.NONE)){
            boolean old = food.isDone();
            food.setDone(!old);
            foodService.updateFood(food);
        }

        model.put("foods", foodService.getFoods() );
        model.put("progresscounts", mapper.writeValueAsString(foodService.getProgressCounts()));

        List<Food.TAG> tags = Arrays.asList(Food.TAG.values());
        model.put("tags", tags);

        return "index";
    }
}
