package com.ecommerce.shopping.customer.entity;

import com.ecommerce.shopping.address.entity.Address;
import com.ecommerce.shopping.cartproduct.entity.CartProduct;
import com.ecommerce.shopping.order.entity.Order;
import com.ecommerce.shopping.user.entity.User;
import com.ecommerce.shopping.wishlist.entity.Wishlist;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
public class Customer extends User {

    @OneToMany(mappedBy = "customer")
    private List<Address> addresses;

    @OneToMany
    private List<Order> orders;

    @OneToMany
    private List<CartProduct> cartProducts;

    @OneToOne
    private Wishlist wishlist;

}
