package fr.amu.services;


import fr.amu.models.Food;
import fr.amu.models.FoodDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FoodService {

    private static final Logger logger = LoggerFactory.getLogger(FoodService.class);

    @Autowired
    private FoodDAO foodDAO;

    public FoodService() {
    }

    public Integer addFood(Food food){
        if(food == null) return -1;
        return foodDAO.add(food);
    }

    public void removeFood(int id){
        foodDAO.delete(id);
    }

    public void updateFood(Food food){
        foodDAO.update(food);
    }

    public Food getFood(int id){
        return foodDAO.findById(id);
    }

    public List<Food> getFoods() {
        return foodDAO.findAll();
    }

    public List<Food> findByTag(String type){
        return foodDAO.findByTag(type);
    }
    public List<Food> findByDone(boolean bool){
        return foodDAO.findByDone(bool);
    }
    public List<Food> findByTagAndDone(String tag, boolean done){
        return foodDAO.findByTagAndDone(tag, done);
    }

    public Map<String, Integer> getProgressCounts(){
        Map<String, Integer> stringIntegerMap = new HashMap<>();
        stringIntegerMap.put("TODO", foodDAO.findByDone(false).size());
        stringIntegerMap.put("DONE", foodDAO.findByDone(true).size());
        return stringIntegerMap ;
    }

    public void visualizeFood(){
        logger.info("VISUALIZE FOOD");
    }
}