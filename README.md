## 🏪 Ecommerce Shopping Application API [ecommerce.BableshAAzad.com](https://ecommerce.bableshaazad.com)
- This project is a RESTful web service built using Java Spring Boot.
- It provides an API for e-commerce shopping applications.
- The project is based on an Ecommerce Shopping application prototype, implementing basic e-commerce functionalities.

**📜 API Documentation:**
- View API Postman requests and responses: [ecommerce-shopping-app](https://documenter.getpostman.com/view/32067662/2sAXjJ4sFv)

---

**🏠 Features:**
- Login using Username and Password, providing an Access Token and Refresh Token (AT and RT).
- Login using OAuth2, supporting login with Google or GitHub.

**For Customers:**
- Search, filter, categorize products, view product information, and place orders.
- Manage the shopping cart, view added products, and proceed with orders.
- View past orders and download invoices.

**For Sellers:**
- Allow sellers to add, update, and delete products.
- Search warehouses based on proximity to your location to store products.
- View products and storage information by seller.

---

**🧑‍💻 Technologies Used:**

`Spring Boot` `Spring Security` `RESTful API` `MySQL` `Spring Data JPA` `OpenAPI Documentation` `Validation` `OpenPDF` `HATEOAS` `Mail` `OAuth2 Client` `Google Guava` `Hateoas` `Cloudinary` `WebFlux`

---

**💻 How To Use:**

#### 🚗 <u>Method 1: Using Online Service</u>
- You can directly access the service via [https://ecommerce-shopping-app-bcsb.onrender.com](https://ecommerce-shopping-app-bcsb.onrender.com).
- Refer to the API documentation for guidance on sending requests and handling responses: [ecommerce-shopping-app](https://documenter.getpostman.com/view/32067662/2sAXjJ4sFv).

#### 🚐 <u>Method 2: Using Docker</u>
- Pull the Docker image: `bableshaazad/storehousemanagementsystem`.
- Refer to the API documentation for guidance on sending requests and handling responses: [ecommerce-shopping-app](https://documenter.getpostman.com/view/32067662/2sAXjJ4sFv).
- Set the necessary environment variables as shown below 👇.

#### 🚒 <u>Method 3: Setting Up Your Own Server</u>
- Download the master branch as a zip file.
- Import the project into your IDE and ensure JDK 21 is installed.
- Set the following environment variables 👇.
- Refer to the API documentation for guidance on sending requests and handling responses: [ecommerce-shopping-app](https://documenter.getpostman.com/view/32067662/2sAXjJ4sFv).

#### 🔐 Environment Variables:
<u>For Database:</u>
1. `DB_HOST_NAME`= localhost
2. `DB_NAME`= ecommerce-shopping-application
3. `DB_PASSWORD`= root
4. `DB_PORT`= 3306
5. `DB_USERNAME`= root

<u>For Storehouse Management System App Connection:</u>
6. `CLIENT_API_KEY`= keyReceivedAtTheTimeRegistration
7. `CLIENT_ID`= 1
8. `CLIENT_USERNAME`= aazad@bableshaazad.org

<u>For Storing Images in Cloudinary:</u>
9. `CLOUDINARY_API_KEY`= 123YourKey
10. `CLOUDINARY_API_SECRET`= abcdeYourPassword
11. `CLOUDINARY_CLOUD_NAME`= abcdYourName

<u>For GitHub OAuth2 Connection:</u>
12. `GITHUB_ID`= 12345YourID
13. `GITHUB_SECRET`= dummyKey12345

<u>For Google OAuth2 Connection:</u>
14. `GOOGLE_ID`= YourGoogleIDxyz
15. `GOOGLE_SECRET`= googleSecret

<u>For JWT Secret (Using HS512):</u> [Generate your secret here](https://8gwifi.org/jwsgen.jsp)
16. `JWT_SECRET`= jwtSecret1234567890

<u>For Sending Emails (During Registration, Purchase Order, Password Recovery):</u>
17. `MAIL_PASSWORD`= mailPassword
18. `MAIL_USERNAME`= yourMail@gmail.com

---

#### 📝 Examples

- **Find Products:**

  **Endpoint:** `GET http://localhost:8080/products?page=0&size=1`


**Response:**
  ```json
  {
      "status": 200,
      "message": "Inventories found",
      "data": {
          "links": [],
          "content": [
              {
                  "inventoryId": 1,
                  "productTitle": "Chair",
                  "lengthInMeters": 0.7,
                  "breadthInMeters": 0.8,
                  "heightInMeters": 0.8,
                  "weightInKg": 8,
                  "price": 300,
                  "description": "Chair for general use",
                  "productImage": "http://res.cloudinary.com/dpaf0bjfx/image/upload/c_fill,h_500,w_500/85010851-aafe-4d8e-9146-69e6b1b5c516",
                  "materialTypes": ["WOOD"],
                  "restockedAt": "2024-08-27",
                  "updatedInventoryAt": null,
                  "sellerId": 1,
                  "stocks": [
                      {
                          "stockId": 5,
                          "quantity": 10
                      }
                  ],
                  "discount": 2,
                  "discountType": "NEW"
              }
          ],
          "page": {
              "size": 1,
              "totalElements": 12,
              "totalPages": 12,
              "number": 0
          }
      }
  }
  ```
---
- For User Registration **Endpoint** 
#### Request for register: `http://localhost:8080/customers/register`
```json
{
  "email" : "bableshaazad@gmail.com",
  "password" : "Test@123!"
}
```
#### Submit otp: `http://localhost:8080/users/otpVerification`
```json
{
  "email" : "bableshaazad@gmail.com",
  "otp" : "888549"
}
```
---
#### Login request: `http://localhost:8080/users/otpVerification`
```json
{
    "username" : "bableshaazad",
    "password" : "Test@123!"
}
```
#### Login response: `and at and rt received in header in form of cookies`
```json
{
    "status": 200,
    "message": "User Verified",
    "data": {
        "userId": 1,
        "username": "bableshaazad",
        "userRole": "CUSTOMER",
        "accessExpiration": 3600,
        "refreshExpiration": 1296000
    }
}
```
---
- For more examples seen documentation [ecommerce-shopping-app](https://documenter.getpostman.com/view/32067662/2sAXjJ4sFv)