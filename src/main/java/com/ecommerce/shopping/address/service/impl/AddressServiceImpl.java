package com.ecommerce.shopping.address.service.impl;

import com.ecommerce.shopping.address.dto.AddressRequest;
import com.ecommerce.shopping.address.dto.AddressResponse;
import com.ecommerce.shopping.address.entity.Address;
import com.ecommerce.shopping.address.mapper.AddressMapper;
import com.ecommerce.shopping.address.repository.AddressRepository;
import com.ecommerce.shopping.address.service.AddressService;
import com.ecommerce.shopping.customer.entity.Customer;
import com.ecommerce.shopping.enums.UserRole;
import com.ecommerce.shopping.exception.AddressNotFoundException;
import com.ecommerce.shopping.exception.AlreadyAddressExistException;
import com.ecommerce.shopping.exception.UserNotExistException;
import com.ecommerce.shopping.seller.entity.Seller;
import com.ecommerce.shopping.seller.repository.SellerRepository;
import com.ecommerce.shopping.user.entity.User;
import com.ecommerce.shopping.user.repositoty.UserRepository;
import com.ecommerce.shopping.utility.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final AddressMapper addressMapper;

    //----------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(AddressRequest addressRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotExistException("UserId : " + userId + ", is not exist"));
        Address address = addressMapper.mapAddressRequestToAddress(addressRequest, new Address());

        if (user.getUserRole().equals(UserRole.CUSTOMER)) {
            address.setCustomer((Customer) user);
        } else {
            Seller seller = (Seller) user;
            Address address1 = seller.getAddress();
            if (address1 == null) {
                address = addressRepository.save(address);
                seller.setAddress(address);
                sellerRepository.save(seller);
            } else {
                throw new AlreadyAddressExistException("Address already present, modify existing address");
            }
        }
        address = addressRepository.save(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<AddressResponse>()
                .setStatus(HttpStatus.CREATED.value())
                .setMessage("Address created")
                .setData(addressMapper.mapAddressToAddressResponse(address)));
    }

    //----------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<List<AddressResponse>>> getAddress(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotExistException("UserId : " + userId + ", is not exist"));

        if (user.getUserRole().equals(UserRole.CUSTOMER)) {
            List<Address> addresses = addressRepository.findByCustomer((Customer) user);
            List<AddressResponse> addressResponses = addresses.stream().map(addressMapper::mapAddressToAddressResponse).toList();
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<List<AddressResponse>>()
                    .setStatus(HttpStatus.FOUND.value())
                    .setMessage("Addresses are founded")
                    .setData(addressResponses));
        } else if (user.getUserRole().equals(UserRole.SELLER)) {
            Seller seller = (Seller) user;
            Address address = seller.getAddress();
            if (address == null) {
                throw new AddressNotFoundException("Address not found for seller");
            }
            AddressResponse addressResponse = addressMapper.mapAddressToAddressResponse(address);
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<List<AddressResponse>>()
                    .setStatus(HttpStatus.FOUND.value())
                    .setMessage("Address is founded")
                    .setData(List.of(addressResponse)));
        } else {
            throw new UserNotExistException("User role not supported");
        }
    }

    @Override
    public ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(Long addressId, AddressRequest addressRequest) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException("Address Id : " + addressId + ", is not exist"));
        address = addressMapper.mapAddressRequestToAddress(addressRequest, address);
        address = addressRepository.save(address);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<AddressResponse>()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Address updated done")
                .setData(addressMapper.mapAddressToAddressResponse(address)));
    }
//----------------------------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------------------------

}
