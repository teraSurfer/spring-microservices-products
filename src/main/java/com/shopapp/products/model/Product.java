package com.shopapp.products.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

@Entity
@Table(indexes = @Index(columnList = "product_name"))
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Setter
@Getter
public class Product implements Serializable {

    private static final long serialVersionUID = 3117060703292466796L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "product_name", unique = true)
    private String productName;

    @Column()
    private String description;

    @Column(name= "list_price")
    private double listPrice;

    @Column(name="created_at")
    private Timestamp createdAt;

    @Column(name="updated_at")
    private Timestamp updatedAt;

    @PrePersist
    public void beforeCreate() {
        long now = Calendar.getInstance().getTimeInMillis();
        this.createdAt = new Timestamp(now);
        this.updatedAt = new Timestamp(now);
    }

    @PreUpdate
    public void beforeUpdate() {
        this.updatedAt = new Timestamp(Calendar.getInstance().getTimeInMillis());
    }
}
