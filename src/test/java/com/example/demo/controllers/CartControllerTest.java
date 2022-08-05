package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private CartController cartController;
    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);
    //Inject mock objects as attributes to cartController
    @BeforeEach
    void setUp() {
        cartController = new CartController();
        TestUtils.injectObjetcs(cartController, "userRepository", userRepository);
        TestUtils.injectObjetcs(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjetcs(cartController, "itemRepository", itemRepository);
        userController = new UserController();
        TestUtils.injectObjetcs(userController, "userRepository", userRepository);
        TestUtils.injectObjetcs(userController, "cartRepository", cartRepository);
        TestUtils.injectObjetcs(userController, "bCryptPasswordEncoder", encoder);
    }

    @Test
    public void test_add_cart_success(){
        when(userRepository.findByUsername("edeleon")).thenReturn(createUser("edeleon", false));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(createItem()));
        User newUser = createUser("edeleon", false);
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(newUser.getUsername());
        request.setItemId(1);
        request.setQuantity(1);
        ResponseEntity<Cart>  response = cartController.addTocart(request);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }


    @Test
    public void test_add_cart_null_user(){
        when(userRepository.findByUsername("edeleon")).thenReturn(null);
        User newUser = createUser("edeleon", false);
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(newUser.getUsername());
        request.setItemId(1);
        request.setQuantity(1);
        ResponseEntity<Cart>  response = cartController.addTocart(request);
        Assertions.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void test_remove_cart_success(){
        when(userRepository.findByUsername("edeleon")).thenReturn(createUser("edeleon", true));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(createItem()));
        User newUser = createUser("edeleon", false);
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(newUser.getUsername());
        request.setItemId(1);
        request.setQuantity(1);
        ResponseEntity<Cart>  response = cartController.removeFromcart(request);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    private User createUser(String username, Boolean withItem){
        User user = new User();
        user.setId(1);
        user.setPassword("testPassword");
        user.setUsername(username);
        user.setCart(createCart(withItem));
        return user;
    }

    private Cart createCart(boolean withItem){
        Cart cart = new Cart();
        if(withItem){
            List<Item> items = new ArrayList<Item>();
            items.add(createItem());
            cart.setItems(items);
        }
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
