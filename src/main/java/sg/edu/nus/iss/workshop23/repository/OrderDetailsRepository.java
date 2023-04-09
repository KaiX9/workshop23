package sg.edu.nus.iss.workshop23.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.workshop23.model.OrderDetails;

import static sg.edu.nus.iss.workshop23.repository.DBQueries.*;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDetailsRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public OrderDetails getOrderDetailsWithDiscount(String orderId) {

        List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(ORDER_DETAILS_WITH_DISCOUNT_QUERY, orderId);
        
        while (rs.next()) {
            // Check value of order_id taken from db
            System.out.println(rs.getInt("order_id"));
            if (rs.getInt("order_id") == 0) {
                return null;
            }
            orderDetailsList.add(OrderDetails.create(rs));
        }
        
        return orderDetailsList.get(0);
    }

    public List<OrderDetails> getOrderDetailsWithName(String name) {
        
        String correctName = "%" + name + "%";
        List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(ORDER_DETAILS_BY_NAME, correctName);

        if (!rs.next()) {
            orderDetailsList = null;
        } else {
            do {
            System.out.println(rs.getString("customer_name"));
            orderDetailsList.add(OrderDetails.createByName(rs));
            } while (rs.next());
        }
        return orderDetailsList;
    }
}
