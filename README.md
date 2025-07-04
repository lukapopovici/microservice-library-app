# Library Microservice

This project is a Spring Boot microservice for managing a library of books. It is written in Kotlin and uses SQLite for data storage, RabbitMQ for messaging, and includes caching with Merkle tree verification.

## Features

- **Book Management:** Store and retrieve books with title, author, publisher, and text.
- **REST API:** Exposes endpoints to print or search for books in various formats (raw, HTML, JSON).
- **Caching:** Query results are cached for 1 hour to improve performance.
- **Merkle Tree:** Uses a Merkle tree to verify cache integrity.
- **RabbitMQ Integration:** Sends file and status messages to queues for further processing.
- **Scheduled Tasks:** 
  - Cache cleanup runs every hour.
  - Merkle tree is rebuilt every minute.
## Service Diagram 
![image](https://github.com/user-attachments/assets/d8f9dc08-738b-446c-885a-59ea4e42996c)

## Endpoints

### Print All Books
```
GET /print?format={raw|html|json}
```
- Returns all books in the specified format.

### Search Books
```
GET /find?author={author}&title={title}&publisher={publisher}&format={raw|html|json}
```
- Search by any combination of author, title, or publisher.
- Returns results in the specified format.
- Results are cached and validated with a Merkle tree.

## Technologies Used

- **Kotlin**
- **Spring Boot**
- **Spring Data JPA**
- **SQLite**
- **RabbitMQ**
- **JUnit 5** (for testing)

## Configuration

Configuration is managed in [`src/main/resources/application.properties`](src/main/resources/application.properties):

- Database: SQLite (`library.db`)
- RabbitMQ: Host, port, username, and password

## Notare
Avand in vedere ca aveai doua clase main, dintre care una din ele in alt pachet, tind sa cred ca ai "importat" proiectul de undeva. Sper ca ma insel. Iata-l ce usor a devenit "functional".
