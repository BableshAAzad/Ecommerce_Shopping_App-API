package com.ecommerce.shopping.address.entity;

import com.ecommerce.shopping.contact.entity.Contact;
import com.ecommerce.shopping.customer.entity.Customer;
import com.ecommerce.shopping.enums.AddressType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    private String streetAddress;
    private String streetAddressAdditional;
    private String city;
    private String state;
    private String country;
    private int pincode;
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @ManyToOne
    private Customer customer;

    @OneToMany
    private Set<Contact> contacts;

}
