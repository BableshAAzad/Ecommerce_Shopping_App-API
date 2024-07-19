package com.ecommerce.shopping.seller.entity;

import com.ecommerce.shopping.address.entity.Address;
import com.ecommerce.shopping.product.entity.Product;
import com.ecommerce.shopping.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Seller extends User {
    @OneToOne
    private Address address;
    @OneToMany
    private List<Product> products;
}
