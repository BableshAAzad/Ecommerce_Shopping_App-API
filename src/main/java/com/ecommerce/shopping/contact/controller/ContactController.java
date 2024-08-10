package com.ecommerce.shopping.contact.controller;

import com.ecommerce.shopping.contact.entity.Contact;
import com.ecommerce.shopping.contact.service.ContactService;
import com.ecommerce.shopping.utility.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ContactController {
    private final ContactService contactService;
    //-----------------------------------------------------------------------------------------------

    @PostMapping("/addresses/{addressId}/contacts")
    public ResponseEntity<ResponseStructure<Contact>> addContact(
            @RequestBody Contact contact,
            @PathVariable Long addressId) {
        return contactService.addContact(contact, addressId);
    }
    //-----------------------------------------------------------------------------------------------

    @PutMapping("/addresses/{addressId}/contacts/{contactId}")
    public ResponseEntity<ResponseStructure<Contact>> updateContact(
            @PathVariable Long addressId,
            @PathVariable Long contactId,
            @RequestBody Contact contact) {
        return contactService.updateContact(addressId, contactId, contact);
    }
    //-----------------------------------------------------------------------------------------------

    @GetMapping("/addresses/{addressId}/contacts")
    public ResponseEntity<ResponseStructure<Set<Contact>>> getContacts(
            @PathVariable Long addressId) {
        return contactService.getContacts(addressId);
    }
    //-----------------------------------------------------------------------------------------------

}
