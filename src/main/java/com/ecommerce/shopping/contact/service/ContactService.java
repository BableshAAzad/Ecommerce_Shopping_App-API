package com.ecommerce.shopping.contact.service;

import com.ecommerce.shopping.contact.entity.Contact;
import com.ecommerce.shopping.utility.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface ContactService {

    ResponseEntity<ResponseStructure<Contact>> addContact(Contact contact, Long addressId);

    ResponseEntity<ResponseStructure<Contact>> updateContact(Long addressId , Long contactId, Contact contact);

    ResponseEntity<ResponseStructure<Set<Contact>>> getContacts(Long addressId);
}
