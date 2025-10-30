package br.com.tp.lncr.customerorder.datasource.postgres;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_order_food_item",
        schema = "public",
        indexes = {
                @Index(name = "customer_order_food_item_id_idx", columnList = "id"),
                @Index(name = "customer_order_food_item_order_id_idx", columnList = "orderId")
        })
public class JpaCustomerOrderFoodItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_order_food_item_id_seq")
    @SequenceGenerator(name = "customer_order_food_item_id_seq", sequenceName = "customer_order_food_item_id_seq", allocationSize = 1)
    private Integer id;
    private Integer orderId;
    private Integer foodItemId;
    private Double price;
    private String notes;
    @Transient
    private String name;
    @Transient
    private String description;

    public JpaCustomerOrderFoodItemEntity(Integer id, Integer orderId, Integer foodItemId, Double price, String notes, String name, String description) {
        this.id = id;
        this.orderId = orderId;
        this.foodItemId = foodItemId;
        this.price = price;
        this.notes = notes;
        this.name = name;
        this.description = description;
    }

    public JpaCustomerOrderFoodItemEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(Integer foodItemId) {
        this.foodItemId = foodItemId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
