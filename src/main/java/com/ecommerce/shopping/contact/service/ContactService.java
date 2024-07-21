package com.ecommerce.shopping.contact.service;

import com.ecommerce.shopping.contact.entity.Contact;
import com.ecommerce.shopping.utility.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContactService {

    ResponseEntity<ResponseStructure<Contact>> addContact(Contact contact, Long addressId);

    ResponseEntity<ResponseStructure<Contact>> updateContact(Contact contact, Long contactId);

    ResponseEntity<ResponseStructure<List<Contact>>> getContacts(Long addressId);
}
