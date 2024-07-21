package com.ecommerce.shopping.contact.repository;

import com.ecommerce.shopping.contact.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {

//    Optional<Contact> findByContactNumber(Long contactNumber);

}
