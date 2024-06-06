# Baseball Scoresheet Backend

The Baseball Scoresheet web application addresses the problem of manual game logging in baseball operations. Currently, scoring is done by hand, which is time-consuming and inefficient. This application aims to significantly reduce the time required by providing electronic processing capabilities. The project offers the following features:

- Authorized scorers can log in and configure settings such as selecting teams and participating players.
- An automatically generated score sheet based on the initial configuration.
- Real-time logging of each play via a user-friendly GUI, with the ability to make corrections.
- Game duration flexibility with scalable score sheet sizes.
- Post-game statistical analysis, including metrics like Batting Average and On-Base Average.
- Protest logging via a separate form.
- Electronic signatures from coaches, umpires, and scorers before submitting the score sheet.
- Exporting completed and signed forms as PDFs for submission to the relevant association.

## Table of Contents

- [Swagger](#swagger)
- [Authentication](#authentication)
  - [Running Keycloak Locally](#running-keycloak-locally)
  - [Production Configuration](#production-configuration)
- [Running the Application](#running-the-application)
- [Known Issues](#known-issues)

## Swagger

Start the app and navigate to [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) to explore the API documentation.

## Authentication

### Running Keycloak Locally

To run Keycloak locally, follow these steps:

#### Step 0 - Requirements

Ensure **Docker** is installed on your computer. You can download it from the [Docker download page](https://docs.docker.com/engine/install/).

Verify the installation with:
```bash
docker --version
```

#### Step 1 - Change Working Directory
```bash
# Windows
cd .\docker\local\keycloak\dev
# Linux, MacOS
cd docker/local/keycloak/dev
```

#### Step 2 - Create and Start Containers
```bash
docker compose up  # Run in foreground
# or
docker compose up -d  # Run in background
```

Open Keycloak in your web browser at [http://localhost:8070](http://localhost:8070).

Log in using the admin credentials from the `docker-compose.yml` file (default username and password are `admin`). **Change these credentials before going live.**

#### Step 3 - Create User

1. Change realm to `BaseballScoresheet`.
2. Go to the `Users` page.
3. Add a new user, ensure `Email verified` is activated, and fill in all fields. Click `create`.
4. In the `Credentials` tab, set the password, disable temporary setting, and save.
5. In the `Role Mappings` tab, assign the `user` role to the new user.

#### Step 4 - Generating Access Tokens

Use the `GetBearerToken.http` file to make requests to Keycloak. Update credentials in this file accordingly.

#### Step 5 - Test Endpoints with Postman

1. Start `BaseballScoresheetApplication`.
2. In Postman, create a new GET request for `http://localhost:8080/protected`.
3. In the `Authorization` tab, select `Bearer Token` and enter your access token.
4. Press `Send`.

You should receive a **200 OK** status and JSON response `{"text": "hello from protected backend GET resource"}`.

### Production Configuration

For production setup, refer to this [step-by-step guide](https://www.baeldung.com/spring-boot-keycloak).

#### Step 1 - Creating a Realm

1. Click the `Create realm` button in the upper left corner.
2. Add a new realm called `BaseballScoresheet`.
3. Ensure the realm is enabled and switch to it.

#### Step 2 - Creating a Client

1. Go to the `Clients` page and click `Create`.
2. Name the new client `login-app`.
3. Set the `Valid Redirect URIs` to the application URLs that will use this client for authentication.

#### Step 3 - Creating a Role and a User

1. Go to the `Realm Roles` page and add the `user` role.
2. In the `Users` page, add a new user.
3. In the `Credentials` tab, set the initial password.
4. In the `Role Mappings` tab, assign the `user` role to the new user.
5. In the `Client Scopes` page, set `microprofile-jwt` to “default”.

#### Step 4 - Generating Access Tokens

Use the `GetBearerToken.http` file to make requests to Keycloak, ensuring credentials are updated.

## Running the Application

### Prerequisites

Before starting the `BaseballScoresheetApplication`, start the PostgreSQL Docker container located at `/docker/local/postgres/dev/docker-compose.yml`.

### Starting the Application

1. Ensure the backend, frontend, and both Docker containers (PostgreSQL and Keycloak) are running.
2. Execute the `create-data.sql` script located in the `sql-scripts` folder.
3. Access the frontend at [http://localhost:4200/game/2](http://localhost:4200/game/2) to score the game.

## Known Issues

If you encounter an error `Could not find a valid Docker environment. Please check configuration.` when running tests, ensure you can run Docker without `sudo` using:
```bash
docker ps
```
If you see `permission denied`, enable running Docker without `sudo`.
