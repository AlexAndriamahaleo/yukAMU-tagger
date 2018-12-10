package fr.amu.models;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Transactional
@Repository
public class FoodDAOImpl implements FoodDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class RowMapperFood implements RowMapper<Food> {

        @Override
        public Food mapRow(ResultSet resultSet, int i) throws SQLException {
            Food food = new Food();

            food.setId(resultSet.getInt("id"));
            food.setName(resultSet.getString("name"));
            food.setImgurl(resultSet.getString("imgurl"));
            food.setTag(resultSet.getString("tag"));
            food.setDone(resultSet.getBoolean("done"));
            return food ;
        }
    }


    @Override
    public Integer add(Food food) {
        String rSQL = "INSERT INTO food(name,imgurl,tag,done) values(?, ?, ?, ?);\n";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        String id_column = "ID" ;

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(rSQL,
                    new String[]{id_column});

            preparedStatement.setString(1, food.getName());
            preparedStatement.setString(2, food.getImgurl());
            preparedStatement.setString(3, String.valueOf(food.getTag()));
            preparedStatement.setBoolean(4, food.isDone());

            return preparedStatement ;
        }, keyHolder);

        Integer id = (Integer) keyHolder.getKeys().get(id_column);

        return id.intValue() ;
    }

    @Override
    public void update(Food food) {
        String rSQL = "UPDATE food SET name = ?, imgurl = ?, tag = ?, done = ? WHERE id = ?";

        jdbcTemplate.update(rSQL,
                food.getName(),
                food.getImgurl(),
                String.valueOf(food.getTag()),
                food.isDone(),
                food.getId());
    }

    @Override
    public void delete(Integer id) {
        String rSQL = "DELETE FROM food WHERE id = ?";

        jdbcTemplate.update(rSQL, id);
    }

    @Override
    public List<Food> findAll() {
        String rSQL = "SELECT * FROM food" ;

        List<Food> foodList = jdbcTemplate.query(rSQL, new RowMapperFood());

        return foodList ;
    }

    @Override
    public Food findById(Integer id) {
        String rSQL = "SELECT * FROM food WHERE id = ?";

        return jdbcTemplate.queryForObject(rSQL, new RowMapperFood(), id);
    }

    @Override
    public List<Food> findByTag(String type) {
        String rSQL = "SELECT * FROM food WHERE type = ?";

        return jdbcTemplate.query(rSQL, new RowMapperFood(), type);
    }

    @Override
    public List<Food> findByDone(boolean bool) {
        String rSQL = "SELECT * FROM food WHERE done = ?";

        return jdbcTemplate.query(rSQL, new RowMapperFood(), (bool) ? 1 : 0);
    }

    @Override
    public List<Food> findByTagAndDone(String tag, boolean done) {
        String rSQL = "SELECT * FROM food WHERE tag = ? AND done = ?";

        return jdbcTemplate.query(rSQL, new RowMapperFood(), tag, (done) ? 1 : 0);
    }

}
