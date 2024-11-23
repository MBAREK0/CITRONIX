# Citronix Project

## Project Overview

**Citronix** is an application designed for managing a lemon farm, helping farmers track production, harvest, and sales of their lemon crops. The system facilitates the management of farms, fields, trees, harvests, and sales while optimizing productivity tracking based on the age of the trees.

### Key Features

- **Farm Management:**
  - Create, update, and view farm details (name, location, size, creation date).
  - Multi-criteria search for farms using a Criteria Builder.

- **Field Management:**
  - Link fields to farms with defined sizes.
  - Enforce consistency in field areas to ensure they do not exceed the farm’s total area.

- **Tree Management:**
  - Track tree information including plantation date, age, and associated field.
  - Calculate tree age and determine annual productivity based on the tree's age.
    - Young tree (<3 years): 2.5 kg per season.
    - Mature tree (3-10 years): 12 kg per season.
    - Old tree (>10 years): 20 kg per season.

- **Harvest Management:**
  - Track harvests by season (Winter, Spring, Summer, Fall).
  - One harvest per season.
  - Record harvest dates and quantities.

- **Sales Management:**
  - Register sales with details like date, unit price, client, and associated harvest.
  - Calculate revenue: `Revenue = Quantity * Unit Price`.

### Constraints

- Minimum field area: 0.1 hectares (1,000 m²).
- Maximum field area: No field can exceed 50% of the farm’s total area.
- Maximum number of fields: A farm can contain a maximum of 10 fields.
- Tree density: 100 trees per hectare (10 trees per 1,000 m²).
- Tree age limit: Trees become non-productive after 20 years.
- Plantation period: Trees can only be planted between March and May.
- Seasonal limit: Each field can be linked to only one harvest per season.

### Technical Requirements

- **Spring Boot**: Used to create the REST API.
- **Layered Architecture**: Controller, Service, Repository, Entity layers.
- **Data Validation**: With Spring annotations.
- **Exception Handling**: Centralized exception management.
- **Unit Testing**: JUnit and Mockito.
- **Lombok**: For simplifying entity management.
- **MapStruct**: For converting entities to DTOs and View Models.

## Technologies Used

- **Spring Boot** (for building the backend REST API)
- **Spring Data JPA** (for database interactions)
- **MySQL** (for the relational database)
- **JUnit & Mockito** (for testing)
- **Lombok** (for simplifying entity code)
- **MapStruct** (for data transformations)

## Setup and Installation

### Prerequisites

- Java 17+
- Maven
- MySQL (or another relational database)

### Steps to Run the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/citronix.git
   cd citronix
