package sg.edu.nus.iss.workshop23.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.workshop23.model.OrderDetails;
import sg.edu.nus.iss.workshop23.repository.OrderDetailsRepository;

@Controller
@RequestMapping
public class OrderDetailsController {

    @Autowired
    private OrderDetailsRepository odRepo;

    // Get method to show first page of html form to get order_id as input
    @GetMapping(path="/order")
    public String getOrderDetailsInfoForm(Model m, @ModelAttribute OrderDetails od) {

        m.addAttribute("order_details", od);
        return "order";
    }

    // Post method via path variable to display order details in html form
    @GetMapping(path="/display")
    public String displayOrderDetails(Model m, @ModelAttribute OrderDetails od
        , @RequestParam String order_id) {

        // OrderDetails orderDetails = odRepo.getOrderDetailsWithDiscount(od.getOrder_id());
        // System.out.println(orderDetails);
        // od = orderDetails;
        
        // if (orderDetails == null) {
        //     m.addAttribute("display", false);
        //     m.addAttribute("error", true);
        //     return "redirect:/display/" + order_id;
        // }

        m.addAttribute("display", true);
        m.addAttribute("error", false);
        m.addAttribute("order_details", od);
        return "redirect:/display/" + order_id;
    }

    @GetMapping(path="/display/{order_id}")
    public String getDisplayOrderDetails(Model m, @ModelAttribute OrderDetails od
        , @PathVariable String order_id) {

        OrderDetails orderDetails = odRepo.getOrderDetailsWithDiscount(od.getOrder_id());
        System.out.println(orderDetails);
        od = orderDetails;
        
        if (orderDetails == null) {
            m.addAttribute("display", false);
            m.addAttribute("error", true);
            return "orderdetails";
        }

        m.addAttribute("display", true);
        m.addAttribute("error", false);
        m.addAttribute("order_details", od);
        return "orderdetails";
    }


    @GetMapping(path="/name")
    public String getOrderDetailsByName(Model m, @ModelAttribute OrderDetails od) {
        
        m.addAttribute("od", od);
        return "name";
    }

    @GetMapping(path="/result")
    public String getOrderDetailsByNameResult(Model m, @ModelAttribute OrderDetails od
        , @RequestParam String customerName) {
        
        List<OrderDetails> orderDetails = odRepo.getOrderDetailsWithName(customerName);
        System.out.println(orderDetails);
        if (orderDetails == null) {
            m.addAttribute("display", false);
            m.addAttribute("error", true);
        } else {
        m.addAttribute("display", true);
        m.addAttribute("error", false);
        m.addAttribute("order_details", orderDetails);
        }
        
        return "name";
    }

}
