package fr.amu.controllers;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.amu.models.Food;
import fr.amu.services.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Controller
public class IndexController {
	
	private static final Logger log = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private HttpSession httpSession;

	@Autowired
	ServletContext context;

	@Autowired
	FoodService foodService;
	
	
	final ObjectMapper mapper = new ObjectMapper(); // initialize un mapper qui tranforme un objet Java en JSON pour le graphique de la vue
//	mapper.writeValueAsString( Map<String, Integer> ) //
	
	// TODO : completer avec les methodes adequates

	@GetMapping("/") // raccourci pour @RequestMapping( value = "/", method = RequestMethod.GET)
	public String index(Map<String, Object> model) throws JsonProcessingException {
		String sessionUser = (String) httpSession.getAttribute("user");

		model.put("foods", foodService.getFoods());

		List<Food.TAG> tags = Arrays.asList(Food.TAG.values());
		model.put("tags", tags);

        model.put("progresscounts", mapper.writeValueAsString(foodService.getProgressCounts()));


        System.out.println("session user = " + sessionUser);
		return "index";
	}
    
    @RequestMapping(value = "/shutdown", method = RequestMethod.GET)
    public void shutdown() {
    	System.exit(0);
    }
    
}