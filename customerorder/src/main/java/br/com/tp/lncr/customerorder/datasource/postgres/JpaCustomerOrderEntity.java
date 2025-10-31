package br.com.tp.lncr.customerorder.datasource.postgres;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer_order",
        schema = "public",
        indexes = {
                @Index(name = "customer_order_id_idx", columnList = "id"),
                @Index(name = "customer_order_customer_id_idx", columnList = "customerId"),
                @Index(name = "customer_order_status_idx", columnList = "statusId"),
                @Index(name = "customer_order_created_idx", columnList = "created")
        })
public class JpaCustomerOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_order_id_seq")
    @SequenceGenerator(name = "customer_order_id_seq", sequenceName = "customer_order_id_seq", allocationSize = 1)
    private Integer id;
    private Double totalCost = 0.0;
    private Integer statusId;
    private Integer customerId;
    private LocalDateTime created;
    private LocalDateTime updated;

    @Transient
    private String customerName;

    public JpaCustomerOrderEntity(Integer id, Double totalCost, Integer statusId, Integer customerId, LocalDateTime created, LocalDateTime updated, String customerName) {
        this.id = id;
        this.totalCost = totalCost;
        this.statusId = statusId;
        this.customerId = customerId;
        this.created = created;
        this.updated = updated;
        this.customerName = customerName;
    }

    public JpaCustomerOrderEntity() {
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Integer getStatusId() {
        return statusId;
    }
    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {return customerName;}
    public void setCustomerName(String customerName) {this.customerName = customerName;}

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    @PrePersist
    public void prePersist() {
        this.created = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updated = LocalDateTime.now();
    }
}
