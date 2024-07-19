package com.ecommerce.shopping.address.repository;

import com.ecommerce.shopping.address.entity.Address;
import com.ecommerce.shopping.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByCustomer(Customer customer);

}
