# 🚀 Redis Cache Demo

This project is a **Spring Boot**-based web application designed as a redis cache demo. 🛠️

# 🤖 Used Tools and Technologies 
- Java & Spring Boot
- JPA
- Maven
- PostgreSQL
- Redis
- RESTful API
- Git Version Control
- Also thanks to ChatGPT. 

## 🏠 Project Structure

`com.fatih.redis-cache-spring-boot-demo`

### 🔘 config
  - 🔧 `RedisConig`                            # Redis configuration class

### 🔘 controller (controller layer which implements api layer interfaces')
  - 🔧 `CategoryController`                    
  - 🔧 `ProductController`                    

### 🔘 api (api layer which includes enpoints) 
  - 🔧 `CategoryApi`                         
  - 🔧 `ProductApi`                             

### 🔘 dao/data access objects (provides an abstraction layer for database operations)
  - 🔧 `CategoryDao`
  - 🔧 `ProductDao`

### 🔘 dto/data transformation objects (record objects which used for transforms entities to theirselves)
  - 🔧 `CreateCategoryRequest`             
  - 🔧 `CreateProductRequest`
  - 🔧 `UpdateCategoryRequest`
  - 🔧 `UpdateProductRequest`

  - 🔧 `CategoryResponse`
  - 🔧 `ProductResponse` 

### 🔘 entity
  - 🔧 `BaseEntity`                  
  - 🔧 `CategoryEntity`                   
  - 🔧 `ProductEntity`               

### 🔘 service (service interfaces for managers' implementations)
### 🔘 manager
  - 🔧 `CategoryManager`                                     
  - 🔧 `ProductManager`

### 🔘 mapper (used MapStruct to generated entit-dto transformations)
  - 🔧 `AddressMapper`         
  - 🔧 `CampaignMapper`          
  - 🔧 `CartItemMapper`
  - 🔧 `CartMapper`    
  - 🔧 `InvoiceMapper`
  - 🔧 `OrderMapper`
  - 🔧 `ProductMapper`
  - 🔧 `UserMapper`
  - 🔧 `WalletMapper`

### 🔘 mapper
  - 🔧 `CategoryMapper`                         # Category's DTO - Entity transformations
  - 🔧 `ProductMapper`                          # Product's DTO - Entity transformations

### 🔘 repository
  - 🔧 `CategoryRepository`           
  - 🔧 `ProductRepository`      


## ⚙️ Setup and Run

### 1️⃣ Requirements
- 🖥️ **Java 17+**
- 🌐 **Spring Boot 3+**
- 🗄️ **PostgreSQL**
- 🖥️ **Redis CLI**

### 2️⃣ Clone the Project
```sh
git clone https://github.com/fatihhozkurt/redis-cache-spring-boot-demo.git
cd redis-cache-spring-boot-demo
```

### 3️⃣ Install Dependencies
```sh
mvn clean install
```

### 4️⃣ Start redis-cli
```sh
wsl (windows subsystem for linux to use redis on windows)
redis-cli
```
### finally Run the Application



## 🔥 Features
- ✅ **Product and Category Management** 🏷️
- ✅ **Consistent and Well-Structed Project** 💬
- ✅ **Caching System With Redis** ❤️
- ✅ **Javadocs in Methods For Improving Readability of the Code** 💬
- ✅ **Layered Architecture and Loose Coupling with Dependency Injection** ❤️
- ✅ **PostgreSQL Relational Database ❤️**

## 📌 Example API Usage

### ➕ Create an Product
```http
POST /api/v1/product
```
👥 **Request Body**
```json
{
  "categoryId": *generatedCategoryId*,
  "productName": "Wireless Kulaklık",
  "productDescription": "Ergonomik tasarımı ve uzun pil ömrüyle dikkat çeken kablosuz kulaklık.",
  "stockQuantity": 50,
  "price": 299.99
}
```
🔄 **Response**
```json
{
  "id": *generatedProductId*
  "categoryId": *generatedCategoryId*,
  "productName": "Wireless Kulaklık",
  "productDescription": "Ergonomik tasarımı ve uzun pil ömrüyle dikkat çeken kablosuz mouse.",
  "stockQuantity": 50,
  "price": 299.99
}

```

## 🤝 Contributing

Feel free to open a **pull request** if you’d like to contribute.

## 📝 License

This project is licensed under the **MIT License**.

📌 Developed by **@fatihhozkurt**. 😊
