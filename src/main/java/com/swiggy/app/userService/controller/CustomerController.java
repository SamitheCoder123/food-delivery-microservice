package com.swiggy.app.userService.controller;

import com.swiggy.app.userService.model.Customer;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {

    private List<Customer> customers = new ArrayList<>(
            List.of(
                    new Customer(1, "Sami", 1),
                    new Customer(2, "Deepa", 2)
            ));

    @GetMapping("/customersList")
    public List<Customer> getCustomers() {
        return customers;
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @PostMapping("/customer")
    public Customer addCustomer(@RequestBody Customer customer) {
        customers.add(customer);
        return customer;
    }

}