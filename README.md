# Work Item Processor
Work Item Processor is a Spring Boot project that processes and reports work items. A work item is an entity that has an id, value, status and result property. This application consists of a set of RESTful APIs for performing basic CRUD operations on a work item, and utilizes a producer-consumer design pattern to process created work items asynchronously via a RabbitMQ message broker. It also serves a report of the existing work items on a single web page, using the Thymeleaf templating engine.
   
Work item processing involves the squaring its `value` to obtain the `result` property and updating the status value from `PROCESSING` to `COMPLETED`. The time it takes for a work item to be processed can be determined by multiplying the value of the work item by 1000ms.

## Getting Started
### Prerequisites
For building and running the application you need the following (NB: versions may vary):
1. JDK 17
2. Maven 3
3. Docker

### Key Dependencies
For backend
1. Spring Web
2. Spring Data Mongo
3. Spring AMQP
4. Thymeleaf

## How to Run
To run the application locally:
* Clone the repository using the following command:
  ```bash
  git clone https://github.com/<your-git-username>/work-item-processor.git
  ```
* Build the project and run the tests by running:
  ```
  mvn clean package
  ```
* Run the docker compose file using the command: 
  ```bash
  docker-compose up -d
  ```
  This will setup the following in a container: 
    * A MongoDB database running on `port 27019`
    * A Mongo Express admin UI running on `port 8081` (`http://localhost:8081`)
    * A RabbitMQ message broker running on `port 5672`, and an admin UI running on `port 15672` (`http://localhost:15672`). To log into         this UI, enter username and password as `guest`.  
  
* Run the app using one of the following commands:
  ```bash
  java -jar -Dspring.profiles.active=test target/ping-me-0.0.1-SNAPSHOT.jar

   or

  mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"
      ```
* Upon startup, the following infrastructure will be created on RabbitMQ:
  * Exchange: `internal.exchange`
  * Dead-Letter Exchange: `internal-dl.exchange`
  * Queue: `work-item.queue`
  * Dead-Letter Queue: `work-item-dl.queue`
* The app will be started on `http://localhost:8080`

## API Documentation

The application contains the following endpoints:

1. `POST '/api/v1/work-items'`

  - Creates a new work item, and emits an event which queues up the work item for processing.
  - Body: A JSON containing the value property of the work item. This value must be between 1 and 10:

    ```json
    {
        "value": 5,
    }
    ```
  - Returns: A JSON with the created work item's ID as shown below:

    ```json
    {
        "status": "SUCCESSFUL",
        "message": "Work Item created successfully",
        "data": {
          "id": "507f1f77bcf86cd799439011"
        }
    }
    ```

2. `GET '/api/v1/work-items/{itemId}'`

  - Fetches a single work item using the work item id as a path variable.
  - Returns: A JSON of the work item's details as shown below:

    ```json
    {
        "status": "SUCCESSFUL",
        "message": null,
        "data": {
          "id": "507f1f77bcf86cd799439011",
          "value": 5,
          "status": "COMPLETED",
          "result": 25
        }
    }
    ```
3. `DELETE '/api/v1/work-items/{itemId}'`

  - Deletes a work item using the work item id as a path variable.
  - Returns: A JSON as shown below:

    ```json
    {
        "status": "SUCCESSFUL",
        "message": "Work Item deleted successfully",
        "data": null
    }
    ```

4. `GET '/api/v1/work-items/report'`

  - Fetches a report of all work items on the database, grouped by value and providing details of the number of work items and the number     processed.

    ```json
    {
        "status": "SUCCESSFUL",
        "message": null,
        "data": [
          {
            "value": 5,
            "numberOfWorkItems": 3,
            "numberOfProcessedWorkItems": 2
          },
          {
            "value": 7,
            "numberOfWorkItems": 2,
            "numberOfProcessedWorkItems": 2
          }
        ]
    }
    ```
### Report Web Page
The see the work item report UI visit `http://localhost:8080/work-item-report`

### Status Codes
* `200` 
  - This is returned when the request is successful
* `400`
  - This is returned when user makes an invalid request like attempting to create a work item with an invalid value (value that is not an     integer between 1 - 10)
* `404`
  - This is returned when a the work item in question does not exist on the database
* `500`
  - This is returned when you attempt to delete an item that has been processed

## Author

- Chijioke Ibekwe (https://github.com/chijioke-ibekwe)
