package com.ecommerce.shopping.contact.controller;

import com.ecommerce.shopping.contact.entity.Contact;
import com.ecommerce.shopping.contact.service.ContactService;
import com.ecommerce.shopping.utility.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ContactController {
    private  final ContactService contactService;

    @PostMapping("/addresses/{addressId}/contacts")
    public ResponseEntity<ResponseStructure<Contact>> addContact(@RequestBody Contact contact, @PathVariable Long addressId){
        return contactService.addContact(contact, addressId);
    }

    @PutMapping("/addresses/contacts/{contactId}")
    public ResponseEntity<ResponseStructure<Contact>> updateContact(@RequestBody Contact contact, @PathVariable Long contactId){
        return contactService.updateContact(contact, contactId);
    }

    @GetMapping("/addresses/{addressId}/contacts")
    public ResponseEntity<ResponseStructure<List<Contact>>> getContacts(@PathVariable Long addressId){
        return contactService.getContacts(addressId);
    }

}
