# Receipt Processor

This project is a Spring Boot application for processing receipts. Follow the below steps to run the application

## Prerequisites

- Docker installed on your machine.
- A terminal or command prompt to run commands.

## Getting Started

### Step 1: Build the Docker Image

Open a terminal, navigate to the directory containing the Dockerfile, and run the following command to build the Docker image. Replace `receipt-processor` with your preferred image name:

```sh
docker build -t receipt-processor .
```

### Step 2: Run the Docker Container

After the image is built, run the container using this command. Replace receipt-processor with your image name:

```sh
docker run -p 8080:8080 receipt-processor
```

### Step 3: Run the Docker Container

After the image is built. You can test the application with the inputs provided in the challenge

```sh
curl -X POST http://localhost:8080/receipts/process \                                 
    -H "Content-Type: application/json" \
    -d '{
  "retailer": "M&M Corner Market",
  "purchaseDate": "2022-03-20",
  "purchaseTime": "14:33",
  "items": [
    {
      "shortDescription": "Gatorade",
      "price": "2.25"
    },{
      "shortDescription": "Gatorade",
      "price": "2.25"
    },{
      "shortDescription": "Gatorade",
      "price": "2.25"
    },{
      "shortDescription": "Gatorade",
      "price": "2.25"
    }
  ],
  "total": "9.00"
}'
```
