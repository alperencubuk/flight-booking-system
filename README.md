# Flight Booking System

---

### Summary:

```
Spring Boot API provides flight booking.
Can create, update, list and delete flights and seats.
Seats can be purchased. (Same seat can't be purchased simultaneously.)

ThreadPoolTaskExecutor used for Async task management.
```

### Requirements:

```
JDK (11+)
```

### Docs:

```
Use postman_collection for example requests/responses.
```

### Endpoints:

```http request
POST   /flights                    # create flight
GET    /flights/{flight_id}        # get flight
GET    /flights/{flight_id}/empty  # get flight only empty seats
GET    /flights                    # get all flights (paginated)
GET    /flights/empty              # get all flights only empty seats (paginated)
PUT    /flights/{flight_id}        # update flight
DELETE /flights/{flight_id}        # delete flight

POST   /seats                      # create seat
GET    /seats/{seat_id}            # get seat
PUT    /seats/{seat_id}            # update seat
DELETE /seats/{seat_id}            # delete seat

POST   /payments                   # purchase seat
```

---
