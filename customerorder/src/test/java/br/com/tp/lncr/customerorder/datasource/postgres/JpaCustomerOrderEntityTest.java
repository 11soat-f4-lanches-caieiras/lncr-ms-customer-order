package br.com.tp.lncr.customerorder.datasource.postgres;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class JpaCustomerOrderEntityTest {

    @Test
    void shouldCreateEntityWithAllParameters() {
        Integer id = 1;
        Double totalCost = 50.0;
        Integer statusId = 2;
        Integer customerId = 10;
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now();
        String customerName = "João Silva";

        JpaCustomerOrderEntity entity = new JpaCustomerOrderEntity(id, totalCost, statusId, customerId, created, updated, customerName);

        assertEquals(id, entity.getId());
        assertEquals(totalCost, entity.getTotalCost());
        assertEquals(statusId, entity.getStatusId());
        assertEquals(customerId, entity.getCustomerId());
        assertEquals(created, entity.getCreated());
        assertEquals(updated, entity.getUpdated());
        assertEquals(customerName, entity.getCustomerName());
    }

    @Test
    void shouldCreateEmptyEntityWithDefaultValues() {
        JpaCustomerOrderEntity entity = new JpaCustomerOrderEntity();

        assertNull(entity.getId());
        assertEquals(0.0, entity.getTotalCost());
        assertNull(entity.getStatusId());
        assertNull(entity.getCustomerId());
        assertNull(entity.getCreated());
        assertNull(entity.getUpdated());
        assertNull(entity.getCustomerName());
    }

    @Test
    void shouldSetAndGetAllProperties() {
        JpaCustomerOrderEntity entity = new JpaCustomerOrderEntity();
        Integer id = 1;
        Double totalCost = 75.5;
        Integer statusId = 3;
        Integer customerId = 20;
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now();
        String customerName = "Maria Santos";

        entity.setId(id);
        entity.setTotalCost(totalCost);
        entity.setStatusId(statusId);
        entity.setCustomerId(customerId);
        entity.setCreated(created);
        entity.setUpdated(updated);
        entity.setCustomerName(customerName);

        assertEquals(id, entity.getId());
        assertEquals(totalCost, entity.getTotalCost());
        assertEquals(statusId, entity.getStatusId());
        assertEquals(customerId, entity.getCustomerId());
        assertEquals(created, entity.getCreated());
        assertEquals(updated, entity.getUpdated());
        assertEquals(customerName, entity.getCustomerName());
    }

    @Test
    void shouldHandleNullCustomerId() {
        JpaCustomerOrderEntity entity = new JpaCustomerOrderEntity();

        entity.setCustomerId(null);

        assertNull(entity.getCustomerId());
    }

    @Test
    void shouldHandleNullCustomerName() {
        JpaCustomerOrderEntity entity = new JpaCustomerOrderEntity();

        entity.setCustomerName(null);

        assertNull(entity.getCustomerName());
    }

    @Test
    void shouldHandleZeroTotalCost() {
        JpaCustomerOrderEntity entity = new JpaCustomerOrderEntity();

        entity.setTotalCost(0.0);

        assertEquals(0.0, entity.getTotalCost());
    }

    @Test
    void shouldHandleNegativeTotalCost() {
        JpaCustomerOrderEntity entity = new JpaCustomerOrderEntity();

        entity.setTotalCost(-10.0);

        assertEquals(-10.0, entity.getTotalCost());
    }

    @Test
    void shouldSetCreatedTimeOnPrePersist() {
        JpaCustomerOrderEntity entity = new JpaCustomerOrderEntity();
        LocalDateTime beforePrePersist = LocalDateTime.now();

        entity.prePersist();

        assertNotNull(entity.getCreated());
        assertTrue(entity.getCreated().isAfter(beforePrePersist) || entity.getCreated().isEqual(beforePrePersist));
    }

    @Test
    void shouldSetUpdatedTimeOnPreUpdate() {
        JpaCustomerOrderEntity entity = new JpaCustomerOrderEntity();
        LocalDateTime beforePreUpdate = LocalDateTime.now();

        entity.preUpdate();

        assertNotNull(entity.getUpdated());
        assertTrue(entity.getUpdated().isAfter(beforePreUpdate) || entity.getUpdated().isEqual(beforePreUpdate));
    }

    @Test
    void shouldOverwriteCreatedTimeOnMultiplePrePersistCalls() {
        JpaCustomerOrderEntity entity = new JpaCustomerOrderEntity();
        LocalDateTime manuallySetTime = LocalDateTime.of(2020, 1, 1, 0, 0);

        // Set a manual timestamp
        entity.setCreated(manuallySetTime);
        assertEquals(manuallySetTime, entity.getCreated());

        // prePersist should overwrite with current time
        entity.prePersist();
        LocalDateTime createdAfterPrePersist = entity.getCreated();

        assertNotEquals(manuallySetTime, createdAfterPrePersist);
        assertTrue(createdAfterPrePersist.isAfter(manuallySetTime));
    }

    @Test
    void shouldOverwriteUpdatedTimeOnMultiplePreUpdateCalls() {
        JpaCustomerOrderEntity entity = new JpaCustomerOrderEntity();
        LocalDateTime manuallySetTime = LocalDateTime.of(2020, 1, 1, 0, 0);

        // Set a manual timestamp
        entity.setUpdated(manuallySetTime);
        assertEquals(manuallySetTime, entity.getUpdated());

        // preUpdate should overwrite with current time
        entity.preUpdate();
        LocalDateTime updatedAfterPreUpdate = entity.getUpdated();

        assertNotEquals(manuallySetTime, updatedAfterPreUpdate);
        assertTrue(updatedAfterPreUpdate.isAfter(manuallySetTime));
    }
}
