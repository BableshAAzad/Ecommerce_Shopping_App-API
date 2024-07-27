package com.ecommerce.shopping.utility;

import com.ecommerce.shopping.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    private ResponseEntity<ErrorStructure<String>> errorResponse(HttpStatus status, String errorMessage, String rootCause) {
        return ResponseEntity.status(status).body(new ErrorStructure<String>().setStatus(status.value())
                .setMessage(errorMessage).setRootCause(rootCause));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorStructure<String>> handleUserAlreadyExist(UserAlreadyExistException ex) {
        return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "Already User is exist");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorStructure<String>> handleUserNotExist(UserNotExistException ex) {
        return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "User not exist");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorStructure<String>> handleOtpExpired(OtpExpiredException ex) {
        return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "Session expired try again...");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorStructure<String>> handleInvalidOpt(InvalidOtpException ex) {
        return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "Otp Mismatch");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorStructure<String>> handleIllegalOperation(IllegalOperationException ex) {
        return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "Illegal Operation ...Please fill correct information");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorStructure<String>> handleInvalidCredential(BadCredentialsException ex) {
        return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "User Not Exist.... Please Provide correct user details");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorStructure<String>> handleTokenExpired(TokenExpiredException ex) {
        return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "Token Expired");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorStructure<String>> handleAlreadyAddressExist(AlreadyAddressExistException ex) {
        return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "Seller can not be add multiple addresses");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorStructure<String>> handleAddressNotFound(AddressNotExistException ex) {
        return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Addresses not found");
    }

    @ExceptionHandler
       public ResponseEntity<ErrorStructure<String>> handleContactNotExist(ContactNotExistException ex){
        return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Contact not found");
    }

    @ExceptionHandler
        public ResponseEntity<ErrorStructure<String>> handleContactAlreadyExist(ContactAlReadyExistException ex){
        return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "2 numbers is already exist in this address");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorStructure<String>> handelCustomerNotExist(CustomerNotExistException ex){
        return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Customer not found");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorStructure<String>> handleCartProductNotExist(CartProductNotExistException ex){
        return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Cart Product not found");
    }

    public ResponseEntity<ErrorStructure<String>> handleOrderNotExist(OrderNotExistException ex){
        return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Order not found");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorStructure<Map<String, String>>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        List<ObjectError> objErrors = ex.getAllErrors();
        Map<String, String> allErrors = new HashMap<>();
        objErrors.forEach(error -> {
            FieldError fieldError = (FieldError) error;
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            allErrors.put(field, message);
        });

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorStructure<Map<String, String>>().setStatus(HttpStatus.NOT_FOUND.value())
                        .setMessage("Invalid Input").setRootCause(allErrors));
    }
}
