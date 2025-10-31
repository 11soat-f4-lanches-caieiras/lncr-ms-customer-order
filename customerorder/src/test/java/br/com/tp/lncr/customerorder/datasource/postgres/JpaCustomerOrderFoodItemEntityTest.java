package br.com.tp.lncr.customerorder.datasource.postgres;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class JpaCustomerOrderFoodItemEntityTest {

    @Test
    void shouldCreateEntityWithAllParameters() {
        Integer id = 1;
        Integer orderId = 10;
        Integer foodItemId = 5;
        Double price = 25.50;
        String notes = "Sem cebola";
        String name = "Hambúrguer";
        String description = "Hambúrguer artesanal";

        JpaCustomerOrderFoodItemEntity entity = new JpaCustomerOrderFoodItemEntity(id, orderId, foodItemId, price, notes, name, description);

        assertEquals(id, entity.getId());
        assertEquals(orderId, entity.getOrderId());
        assertEquals(foodItemId, entity.getFoodItemId());
        assertEquals(price, entity.getPrice());
        assertEquals(notes, entity.getNotes());
        assertEquals(name, entity.getName());
        assertEquals(description, entity.getDescription());
    }

    @Test
    void shouldCreateEmptyEntityWithNullValues() {
        JpaCustomerOrderFoodItemEntity entity = new JpaCustomerOrderFoodItemEntity();

        assertNull(entity.getId());
        assertNull(entity.getOrderId());
        assertNull(entity.getFoodItemId());
        assertNull(entity.getPrice());
        assertNull(entity.getNotes());
        assertNull(entity.getName());
        assertNull(entity.getDescription());
    }

    @Test
    void shouldSetAndGetAllProperties() {
        JpaCustomerOrderFoodItemEntity entity = new JpaCustomerOrderFoodItemEntity();
        Integer id = 2;
        Integer orderId = 15;
        Integer foodItemId = 8;
        Double price = 18.75;
        String notes = "Bem passado";
        String name = "X-Bacon";
        String description = "Hambúrguer com bacon crocante";

        entity.setId(id);
        entity.setOrderId(orderId);
        entity.setFoodItemId(foodItemId);
        entity.setPrice(price);
        entity.setNotes(notes);
        entity.setName(name);
        entity.setDescription(description);

        assertEquals(id, entity.getId());
        assertEquals(orderId, entity.getOrderId());
        assertEquals(foodItemId, entity.getFoodItemId());
        assertEquals(price, entity.getPrice());
        assertEquals(notes, entity.getNotes());
        assertEquals(name, entity.getName());
        assertEquals(description, entity.getDescription());
    }

    @Test
    void shouldHandleNullNotes() {
        JpaCustomerOrderFoodItemEntity entity = new JpaCustomerOrderFoodItemEntity();

        entity.setNotes(null);

        assertNull(entity.getNotes());
    }

    @Test
    void shouldHandleEmptyNotes() {
        JpaCustomerOrderFoodItemEntity entity = new JpaCustomerOrderFoodItemEntity();

        entity.setNotes("");

        assertEquals("", entity.getNotes());
    }

    @Test
    void shouldHandleNullName() {
        JpaCustomerOrderFoodItemEntity entity = new JpaCustomerOrderFoodItemEntity();

        entity.setName(null);

        assertNull(entity.getName());
    }

    @Test
    void shouldHandleEmptyName() {
        JpaCustomerOrderFoodItemEntity entity = new JpaCustomerOrderFoodItemEntity();

        entity.setName("");

        assertEquals("", entity.getName());
    }

    @Test
    void shouldHandleNullDescription() {
        JpaCustomerOrderFoodItemEntity entity = new JpaCustomerOrderFoodItemEntity();

        entity.setDescription(null);

        assertNull(entity.getDescription());
    }

    @Test
    void shouldHandleEmptyDescription() {
        JpaCustomerOrderFoodItemEntity entity = new JpaCustomerOrderFoodItemEntity();

        entity.setDescription("");

        assertEquals("", entity.getDescription());
    }

    @Test
    void shouldHandleZeroPrice() {
        JpaCustomerOrderFoodItemEntity entity = new JpaCustomerOrderFoodItemEntity();

        entity.setPrice(0.0);

        assertEquals(0.0, entity.getPrice());
    }

    @Test
    void shouldHandleNegativePrice() {
        JpaCustomerOrderFoodItemEntity entity = new JpaCustomerOrderFoodItemEntity();

        entity.setPrice(-5.0);

        assertEquals(-5.0, entity.getPrice());
    }

    @Test
    void shouldHandleLargePrice() {
        JpaCustomerOrderFoodItemEntity entity = new JpaCustomerOrderFoodItemEntity();
        Double largePrice = 999999.99;

        entity.setPrice(largePrice);

        assertEquals(largePrice, entity.getPrice());
    }

    @Test
    void shouldHandleLongNotes() {
        JpaCustomerOrderFoodItemEntity entity = new JpaCustomerOrderFoodItemEntity();
        String longNotes = "A".repeat(1000);

        entity.setNotes(longNotes);

        assertEquals(longNotes, entity.getNotes());
    }

    @Test
    void shouldHandleSpecialCharactersInNotes() {
        JpaCustomerOrderFoodItemEntity entity = new JpaCustomerOrderFoodItemEntity();
        String specialNotes = "Sem açúcar, ñ, café com ☕";

        entity.setNotes(specialNotes);

        assertEquals(specialNotes, entity.getNotes());
    }

    @Test
    void shouldHandleSpecialCharactersInName() {
        JpaCustomerOrderFoodItemEntity entity = new JpaCustomerOrderFoodItemEntity();
        String specialName = "Açaí & Granola";

        entity.setName(specialName);

        assertEquals(specialName, entity.getName());
    }

    @Test
    void shouldHandleSpecialCharactersInDescription() {
        JpaCustomerOrderFoodItemEntity entity = new JpaCustomerOrderFoodItemEntity();
        String specialDescription = "Açaí natural com granola & mel 100%";

        entity.setDescription(specialDescription);

        assertEquals(specialDescription, entity.getDescription());
    }
}
