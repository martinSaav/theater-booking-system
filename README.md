[![Deploy to EC2](https://github.com/martinSaav/theater-booking-system/actions/workflows/deploy.yml/badge.svg)](https://github.com/martinSaav/theater-booking-system/actions/workflows/deploy.yml)
![Vercel Deploy](https://deploy-badge.vercel.app/vercel/teatro-gran-espectaculo-front)
# Theater Booking System

This project is a theater event booking system built with **Spring Boot 3** and **PostgreSQL**, containerized using **Docker**.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- - [Backend](#backend)
- - [Frontend](#frontend)
- [Usage](#usage)
- - [Backend](#backend)
- - [Frontend](#frontend)
- [Author](#author)
- [License](#license)

### Features

- CRUD for events (theater plays, concerts, talks)
- CRUD for reservations (tickets)
- Admin-oriented functionality
- PostgreSQL as the database engine
- Swagger UI for exploring and testing the API
- Fully dockerized environment with Docker Compose

### Prerequisites

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

### Installation

#### Backend

1. **Clone the repository**

```bash
git clone https://github.com/your-username/theater-booking-system.git
cd theater-booking-system
```

2. **Set Environment Variables**

You should create a `.env` file in the root directory of the project to set your database credentials. The file should look like this:

```env
DB_URL=jdbc:postgresql://db:5432/theater
DB_USERNAME=admin
DB_PASSWORD=admin123
SPRING_PROFILES_ACTIVE=prod
```

3. **Start the application using Docker Compose**

```bash
docker compose up --build
```

## Usage
### Backend

Access the API

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- REST API Base URL: `http://localhost:8080/api/v1/events`

> 💡 Make sure port `8080` and `5432` are not being used by other services.


### API Documentation 📘

Once the app is running, visit:

- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

You can test all endpoints from there.

### Author

- Martín Alejandro Estrada Saavedra  
- [martin996@hotmail.com.ar](mailto:martin996@hotmail.com.ar)  
- [https://github.com/martinSaav](https://github.com/martinSaav)

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
