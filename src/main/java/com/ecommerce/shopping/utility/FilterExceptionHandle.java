package com.ecommerce.shopping.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FilterExceptionHandle {
    public void handleJwtExpire(HttpServletResponse response,
                                int status,
                                String message,
                                String rootCause) throws IOException {
        response.setStatus(status);
        ErrorStructure<String> errorStructure = new ErrorStructure<String>()
                .setStatus(status)
                .setMessage(message)
                .setRootCause(rootCause);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), errorStructure);
    }
}
