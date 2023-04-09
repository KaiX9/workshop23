package sg.edu.nus.iss.workshop23.model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class OrderDetails {
    
    private Integer id;
    private String order_id;
    private DateTime orderDate;
    private Integer customerId;
    private Double totalDiscountedPrice;
    private Double totalCostPrice;
    private String customerName;

    public OrderDetails() {

    }
    
    public OrderDetails(Integer id, DateTime orderDate, Integer customerId, Double totalDiscountedPrice,
            Double totalCostPrice, String customerName) {
        this.id = id;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.totalDiscountedPrice = totalDiscountedPrice;
        this.totalCostPrice = totalCostPrice;
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public DateTime getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(DateTime orderDate) {
        this.orderDate = orderDate;
    }
    public Integer getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    public Double getTotalDiscountedPrice() {
        return totalDiscountedPrice;
    }
    public void setTotalDiscountedPrice(Double totalDiscountedPrice) {
        this.totalDiscountedPrice = totalDiscountedPrice;
    }
    public Double getTotalCostPrice() {
        return totalCostPrice;
    }
    public void setTotalCostPrice(Double totalCostPrice) {
        this.totalCostPrice = totalCostPrice;
    }
    
    @Override
    public String toString() {
        return "OrderDetails [id=" + id + ", customerName=" + customerName + ", orderDate=" + orderDate + ", customerId=" + customerId
                + ", totalDiscountedPrice=" + totalDiscountedPrice + ", totalCostPrice=" + totalCostPrice + "]";
    }

    public static OrderDetails create(SqlRowSet rs) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setId(rs.getInt("order_id"));
        orderDetails.setOrderDate(new DateTime(DateTimeFormat.forPattern("dd/MM/yyyy")
        .parseDateTime(rs.getString("order_date"))));
        orderDetails.setCustomerId(rs.getInt("customerId"));
        orderDetails.setTotalDiscountedPrice(rs.getDouble("discounted_price"));
        orderDetails.setTotalCostPrice(rs.getDouble("cost_price"));

        return orderDetails;
    }

    public static OrderDetails createByName(SqlRowSet rs) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setId(rs.getInt("order_id"));
        orderDetails.setCustomerName(rs.getString("customer_name"));
        orderDetails.setOrderDate(new DateTime(DateTimeFormat.forPattern("dd/MM/yyyy")
        .parseDateTime(rs.getString("order_date"))));
        orderDetails.setCustomerId(rs.getInt("customerId"));
        orderDetails.setTotalDiscountedPrice(rs.getDouble("discounted_price"));
        orderDetails.setTotalCostPrice(rs.getDouble("cost_price"));

        return orderDetails;
    }

    public JsonObject toJSON() {

        return Json.createObjectBuilder()
                .add("order_id", getId())
                .add("order_date", getOrderDate().toString(DateTimeFormat.forPattern("dd/MM/yyyy")))
                .add("customerId", getCustomerId())
                .add("discounted_price", getTotalDiscountedPrice())
                .add("cost_price", getTotalCostPrice())
                .build();
    }

    public JsonObject toJSONByName() {

        return Json.createObjectBuilder()
                .add("order_id", getId())
                .add("customer_name", getCustomerName())
                .add("order_date", getOrderDate().toString(DateTimeFormat.forPattern("dd/MM/yyyy")))
                .add("customerId", getCustomerId())
                .add("discounted_price", getTotalDiscountedPrice())
                .add("cost_price", getTotalCostPrice())
                .build();
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

}
