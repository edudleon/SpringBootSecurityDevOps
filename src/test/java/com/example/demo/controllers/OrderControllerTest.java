package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private OrderController orderController;
    private UserRepository userRepository = mock(UserRepository.class);
    private OrderRepository orderRepository = mock(OrderRepository.class);

    //Inject mock objects as attributes to orderController
    @BeforeEach
    void setUp() {
        orderController = new OrderController();
        TestUtils.injectObjetcs(orderController, "userRepository", userRepository);
        TestUtils.injectObjetcs(orderController, "orderRepository", orderRepository);
    }

    @Test
    public void test_submit_user_not_found(){
        when(userRepository.findByUsername("test")).thenReturn(null);
        ResponseEntity<UserOrder> response = orderController.submit("test");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void test_submit_user_success(){
        when(userRepository.findByUsername("edeleon")).thenReturn(createUser("edeleon"));
        ResponseEntity<UserOrder> response = orderController.submit("edeleon");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    private User createUser(String username){
        User user = new User();
        user.setId(1);
        user.setPassword("testPassword");
        user.setUsername(username);
        user.setCart(createCart());
        return user;
    }

    private Cart createCart(){
        Cart cart = new Cart();
        List<Item> items = new ArrayList<Item>();
        items.add(createItem());
        cart.setItems(items);
        cart.setId(1L);
        return cart;
    }

    private Item createItem(){
        Item item = new Item();
        item.setId(1L);
        item.setDescription("description");
        item.setName("name");
        item.setPrice(BigDecimal.valueOf(10));
        return item;
    }

}
