package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    //Inject mock objects as attributes to itemController
    @BeforeEach
    void setUp() {
        itemController = new ItemController();
        TestUtils.injectObjetcs(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void test_get_items_by_name_success(){
        when(itemRepository.findByName("success")).thenReturn(getItems());
        ResponseEntity<List<Item>> response = itemController.getItemsByName("success");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void test_get_items_by_name_not_found(){
        when(itemRepository.findByName("test")).thenReturn(null);
        ResponseEntity<List<Item>> response = itemController.getItemsByName("test");
        Assertions.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void test_get_item_by_id_success(){
        when(itemRepository.findById(1L)).thenReturn(Optional.of(createItem()));
        ResponseEntity<Item> response = itemController.getItemById(1l);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertEquals(1l, response.getBody().getId());
    }

    private List<Item> getItems(){
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setId(1L);
        item.setName("success");
        item.setPrice(BigDecimal.valueOf(10));
        item.setDescription("description");
        items.add(item);
        return items;
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
