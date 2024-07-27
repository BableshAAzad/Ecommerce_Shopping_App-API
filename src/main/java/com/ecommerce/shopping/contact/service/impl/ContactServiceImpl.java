package com.ecommerce.shopping.contact.service.impl;

import com.ecommerce.shopping.address.entity.Address;
import com.ecommerce.shopping.address.repository.AddressRepository;
import com.ecommerce.shopping.contact.entity.Contact;
import com.ecommerce.shopping.contact.repository.ContactRepository;
import com.ecommerce.shopping.contact.service.ContactService;
import com.ecommerce.shopping.exception.AddressNotExistException;
import com.ecommerce.shopping.exception.ContactAlReadyExistException;
import com.ecommerce.shopping.exception.ContactNotExistException;
import com.ecommerce.shopping.exception.IllegalOperationException;
import com.ecommerce.shopping.utility.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final AddressRepository addressRepository;

    @Override
    public ResponseEntity<ResponseStructure<Contact>> addContact(Contact contact, Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotExistException("AddressId " + addressId + ", not exist"));
        if ((contact.getContactNumber() + "").length() != 10) {
            throw new IllegalOperationException("Mobile Number must be 10 digits");
        }
        if (address.getContacts() == null) {
            contact = contactRepository.save(contact);
            address.setContacts(List.of(contact));
        } else {
            List<Contact> contacts = address.getContacts();
            if (contacts.size() >= 2) {
                throw new ContactAlReadyExistException("Only 2 contacts are allowed");
            }
            for (Contact contact1 : contacts) {
                if (contact1.getContactNumber().equals(contact.getContactNumber())) {
                    throw new ContactAlReadyExistException(contact.getContactNumber() + ", is all ready exist");
                }
            }
            contact = contactRepository.save(contact);
            contacts.add(contact);
        }
        addressRepository.save(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<Contact>()
                .setStatus(HttpStatus.CREATED.value())
                .setMessage("Contact Created")
                .setData(contact));
    }

    @Override
    public ResponseEntity<ResponseStructure<Contact>> updateContact(Contact contact, Long contactId) {
        if ((contact.getContactNumber() + "").length() != 10) {
            throw new IllegalOperationException("Mobile Number must be 10 digits");
        }
        Contact contact1 = contactRepository.findById(contactId).orElseThrow(() -> new ContactNotExistException("ContactId : " + contactId + ", is not exist"));
        contact1.setContactNumber(contact.getContactNumber());
        contact1.setPriority(contact.getPriority());
        contact1 = contactRepository.save(contact1);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<Contact>()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Contact Updated")
                .setData(contact1));
    }

    @Override
    public ResponseEntity<ResponseStructure<List<Contact>>> getContacts(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotExistException("AddressId " + addressId + ", not exist"));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<List<Contact>>()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Contact Founded")
                .setData(address.getContacts()));
    }
}
