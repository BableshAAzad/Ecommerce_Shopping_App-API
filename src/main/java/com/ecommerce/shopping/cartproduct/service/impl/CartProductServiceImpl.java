package com.ecommerce.shopping.cartproduct.service.impl;

import com.ecommerce.shopping.cartproduct.dto.CartProductRequest;
import com.ecommerce.shopping.cartproduct.dto.CartProductResponse;
import com.ecommerce.shopping.cartproduct.entity.CartProduct;
import com.ecommerce.shopping.cartproduct.mapper.CartProductMapper;
import com.ecommerce.shopping.cartproduct.repository.CartProductRepository;
import com.ecommerce.shopping.cartproduct.service.CartProductService;
import com.ecommerce.shopping.customer.entity.Customer;
import com.ecommerce.shopping.customer.repository.CustomerRepository;
import com.ecommerce.shopping.exception.CartProductNotExistException;
import com.ecommerce.shopping.exception.CustomerNotExistException;
import com.ecommerce.shopping.product.entity.Product;
import com.ecommerce.shopping.product.repository.ProductRepository;
import com.ecommerce.shopping.utility.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CartProductServiceImpl implements CartProductService {

    private final CartProductMapper cartProductMapper;
    private final CartProductRepository cartProductRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    //----------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<CartProductResponse>> addProductInCart(
            CartProductRequest cartProductRequest, Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotExistException(
                "Customer Id : " + customerId + ", does not exist"));

        List<CartProduct> cartProducts = customer.getCartProducts();
        CartProduct existingCartProduct = null;
        CartProduct newCartProduct = null;

        // Check if the product is already in the customer's cart
        if (cartProducts != null) {
            for (CartProduct cartProduct : cartProducts) {
                if (cartProduct.getProduct().getProductId().equals(cartProductRequest.getProduct().getProductId())) {
                    existingCartProduct = cartProduct;
                    break;
                }
            }
        }

        if (existingCartProduct != null) {
            // Update the quantity of the existing cart product
            existingCartProduct.setSelectedQuantity(existingCartProduct.getSelectedQuantity() + cartProductRequest.getSelectedQuantity());
            cartProductRepository.save(existingCartProduct);
        } else {
            // Add a new cart product
            newCartProduct = cartProductMapper.mapCartProductRequestToCartProduct(cartProductRequest, new CartProduct());
            Product product = productRepository.save(cartProductRequest.getProduct());
            newCartProduct.setProduct(product);
            newCartProduct = cartProductRepository.save(newCartProduct);

            if (cartProducts == null) {
                customer.setCartProducts(List.of(newCartProduct));
            } else {
                cartProducts.add(newCartProduct);
            }
        }

        customerRepository.save(customer);

        CartProductResponse cartProductResponse = cartProductMapper.mapCartProductToCartProductResponse(
                existingCartProduct != null ? existingCartProduct : newCartProduct
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<CartProductResponse>()
                .setStatus(HttpStatus.CREATED.value())
                .setMessage("Cart Product is created or updated")
                .setData(cartProductResponse));
    }

    //----------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<CartProductResponse>> updateCartProduct(
            int selectedQuantity, Long cartProductId) {
        CartProduct cartProduct = cartProductRepository.findById(cartProductId).orElseThrow(() -> new CartProductNotExistException(
                "CartProduct Id : " + cartProductId + ", is not exist"));

        cartProduct.setSelectedQuantity(selectedQuantity);
        cartProduct = cartProductRepository.save(cartProduct);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<CartProductResponse>()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Cart Product is Updated")
                .setData(cartProductMapper.mapCartProductToCartProductResponse(cartProduct)));
    }
    //----------------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<CartProductResponse>> removeCartProduct(Long customerId, Long cartProductId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotExistException(
                "Customer Id : " + customerId + ", is not exist"));

        CartProduct cartProduct = cartProductRepository.findById(cartProductId).orElseThrow(() -> new CartProductNotExistException(
                "CartProduct Id : " + cartProductId + ", is not exist"));

        customer.getCartProducts().remove(cartProduct);
        customerRepository.save(customer);

        cartProductRepository.delete(cartProduct);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<CartProductResponse>()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Cart Product is deleted")
                .setData(cartProductMapper.mapCartProductToCartProductResponse(cartProduct)));
    }

    //----------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<String>> removeAllCartProduct(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotExistException(
                "Customer Id : " + customerId + ", is not exist"));

        List<CartProduct> cartProducts = new ArrayList<>(customer.getCartProducts());
        customer.getCartProducts().clear();
        customerRepository.save(customer);
        cartProducts.forEach(cartProductRepository::delete);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<String>()
                .setStatus(HttpStatus.OK.value())
                .setMessage("All Cart Products are removed from Cart")
                .setData("Successfully removed all products"));
    }

    //----------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<List<CartProductResponse>>> getCartProducts(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotExistException(
                "Customer Id : " + customerId + ", is not exist"));
        List<CartProductResponse> cartProductResponses = customer.getCartProducts()
                .stream()
                .map(cartProductMapper::mapCartProductToCartProductResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<List<CartProductResponse>>()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Cart Products are founded")
                .setData(cartProductResponses));
    }
    //----------------------------------------------------------------------------------------------------------------------------

}
