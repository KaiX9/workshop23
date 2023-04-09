package sg.edu.nus.iss.workshop23.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import sg.edu.nus.iss.workshop23.model.OrderDetails;
import sg.edu.nus.iss.workshop23.repository.OrderDetailsRepository;

@RestController
@RequestMapping(path="/order/total", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderDetailsRestController {
    
    @Autowired
    private OrderDetailsRepository odRepo;

    // Get method to get order details through jquery & js
    // @GetMapping(path="{order_id}")
    // public ResponseEntity<String> getOrderDetailsInfo(@PathVariable Integer order_id) {

    //     OrderDetails orderDetails = odRepo.getOrderDetailsWithDiscount(order_id);

    //     return ResponseEntity
    //             .status(HttpStatus.OK)
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .body(orderDetails.toJSON().toString());
    // }

    // Post method to display order details in json string through html form 
    @PostMapping(path="/form/{order_id}")
    public ResponseEntity<String> getOrderDetailsInfoForm(@ModelAttribute OrderDetails od
        , @PathVariable String order_id) {

        OrderDetails orderDetails = odRepo.getOrderDetailsWithDiscount(od.getOrder_id());
        System.out.println(orderDetails);
        
        if (orderDetails == null) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{'error message' : Order ID >>> " + od.getOrder_id() + " not found}");
        }
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(orderDetails.toJSON().toString());
    }

    // Get method to obtain order details by parts of name, displaying json strings
    @GetMapping(path="/name")
    public ResponseEntity<String> getOrderDetailsByName(@RequestParam String name) {

        List<OrderDetails> orderDetails = odRepo.getOrderDetailsWithName(name);
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        for (OrderDetails o : orderDetails) {
            arrBuilder.add(o.toJSONByName());
        }

        JsonArray result = arrBuilder.build();

        if (orderDetails.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{'message' : " + "'" + name + "'" + " not found}");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

}
