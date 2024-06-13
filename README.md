# MovieZone API

This is a Java Spring Boot application that provides a RESTful API for MovieZone, a movie information service. The application uses Maven for dependency management.

## Prerequisites

- Java 17
- Maven 3.8.4
- Docker
- An IDE such as IntelliJ IDEA

## Environment Variables

The application requires the following environment variables:

- `DB_URL`: The URL of your database
- `DB_USER`: The username for your database
- `DB_PASSWORD`: The password for your database
- `OBS_URL`: The URL for your OBS
- `BUCKET_NAME`: The name of your bucket
- `TMDB_TOKEN`: Your TMDB token
- `PEPPER`: Your pepper value
- `OBS_ENDPOINT`: Your OBS endpoint

## Building the Application

1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Build the application using Maven:

```bash
mvn clean package -DskipTests
```

This will create a `.jar` file in the `target` directory.

## Running the Application

The application can be run locally using the following command:

```bash
java -jar target/app.jar
```

## Deploying with Docker

The application can also be deployed using Docker. A `Dockerfile` is included in the project root.

1. Build the Docker image:

```bash
docker build -t moviezone-api .
```

2. Run the Docker container:

```bash
docker run -p 8080:8080 -e DB_URL=<your_db_url> -e DB_USER=<your_db_user> -e DB_PASSWORD=<your_db_password> -e OBS_URL=<your_obs_url> -e BUCKET_NAME=<your_bucket_name> -e TMDB_TOKEN=<your_tmdb_token> -e PEPPER=<your_pepper> -e OBS_ENDPOINT=<your_obs_endpoint> moviezone-api
```

Replace `<your_db_url>`, `<your_db_user>`, `<your_db_password>`, `<your_obs_url>`, `<your_bucket_name>`, `<your_tmdb_token>`, `<your_pepper>`, and `<your_obs_endpoint>` with your actual values.

The application will be available at `http://localhost:8080`.



## API Endpoints

### <ins>Authentication Endpoints<ins>

- `DELETE /v1/auths`: Logs out a user. Requires an Authorization header with the access token. Invalidates the refresh token associated with the user.

- `PUT /v1/auths`: Refreshes the access token. Requires a body with the current access token and refresh token. If the refresh token is valid and matches the one in the database, it returns a new access token and refresh token.

- `POST /v1/auths`: Logs in a user. Requires a body with a Google token. Validates the Google token, checks if the user exists in the database, and if not, creates a new user. Returns an access token and refresh token.

### Endpoint Details

##### DELETE /v1/auths
This endpoint is used to log out a user. It requires an Authorization header with the access token. The access token should be passed as a Bearer token in the Authorization header.

##### PUT /v1/auths
This endpoint is used to refresh the access token. It requires a JSON body with the following structure:
```json
{
  "accessToken": "<current access token>",
  "refreshToken": "<current refresh token>"
}
```
If the refresh token is valid and matches the one in the database, it returns a new access token and refresh token.

##### POST /v1/auths
This endpoint is used for user login. It requires a JSON body with the following structure:
```json
{
  "token": "<Google token>"
}
```
It validates the Google token, checks if the user exists in the database, and if not, creates a new user. It then generates and returns an access token and refresh token.


Remember to replace `<current access token>`, `<current refresh token>`, and `<Google token>` with actual values when using these endpoints.


### <ins>Health Endpoints

- `GET /v1/health`: Checks the health status of the application. It checks the status of the external movie database API and the status of the internal database connection. Returns a `HealthDTO` with the status of each component.

### Endpoint Details

##### GET /v1/health
This endpoint is used to check the health status of the application. It checks the status of the external movie database API and the status of the internal database connection. It returns a `HealthDTO` with the status of each component.

### <ins>Movie Endpoints

##### GET /v1/movies/{MovieId}
This endpoint is used to get the details of a specific movie. It requires a path variable `MovieId` which is the ID of the movie. If the `MovieId` is not specified, it returns a `BAD_REQUEST` status.

##### GET /v1/movies
This endpoint is used to discover movies or search for movies based on various parameters. It accepts the following query parameters:

- `page`: The page number for the results. This is required and if not sent, a `BAD_REQUEST` status is returned.
- `search`: The search term to filter the movies.
- `genres`: The genres to filter the movies.
- `orderByScore`: The parameter to order the results by score.
- `orderingScore`: The order in which to sort the results by score.
- `orderByDate`: The parameter to order the results by date.
- `orderingDate`: The order in which to sort the results by date.

If the `search` parameter is not provided, it returns the results from the landing page. If the `search` parameter is provided, it returns the results based on the search term and other parameters.

Remember to replace `{MovieId}` with the actual movie ID when using these endpoints.

### Endpoint Details


##### GET /v1/movies/{MovieId}
```http
GET /v1/movies/123 HTTP/1.1
Host: example.com
```
This request is used to get the details of a movie with ID 123.

##### GET /v1/movies
```http
GET /v1/movies?page=1&search=SPIDERMAN&genres=28&orderByScore=desc&orderingScore=avg&orderByDate=desc&orderingDate=release HTTP/1.1
```
This request is used to search for movies that contain the _"SPIDERMAN"_ string in their name. In this example, we are requesting the first page of action movies, ordered by score in descending order, and by release date in descending order.

### <ins>User Endpoints<ins>

- `POST /v1/users`: Creates a new user. Requires a body with the structure of the `UserDTO` class. If the user is successfully created, it returns the created user and a status of `CREATED`. If there is an internal server error, it returns a status of `INTERNAL_SERVER_ERROR`.

- `PATCH /v1/users`: Modifies an existing user. Requires a body with the structure of the `UserEditableDTO` class. If the user is successfully modified, it returns the modified user and a status of `OK`.

- `DELETE /v1/users/{userId}`: Deletes a user. Requires the user's ID as a path variable. If the user is successfully deleted, it returns a status of `NO_CONTENT`.

- `GET /v1/users/{userId}`: Retrieves a user. Requires the user's ID as a path variable. If the user is found, it returns the user and a status of `OK`. If the user is not found, it returns an error message and a status of `NOT_FOUND`.

### Endpoint Details


##### POST /v1/users
This endpoint is used to create a new user. It requires a JSON body with the structure of the `UserDTO` class. If the user is successfully created, it returns the created user and a status of `CREATED`. If there is an internal server error, it returns a status of `INTERNAL_SERVER_ERROR`.

Example JSON body:
```json
{
  "username": "<username>",
  "password": "<password>",
  "email": "<email>"
}
```

##### PATCH /v1/users
This endpoint is used to modify an existing user. It requires a JSON body with the structure of the `UserEditableDTO` class. If the user is successfully modified, it returns the modified user and a status of `OK`.

Example JSON body:
```json
{
  "id": "<user id>",
  "username": "<new username>",
  "password": "<new password>",
  "email": "<new email>",
  "base64img": "<new base64 image>"
}
```

##### DELETE /v1/users/{userId}
This endpoint is used to delete a user. It requires the user's ID as a path variable. If the user is successfully deleted, it returns a status of `NO_CONTENT`.

##### GET /v1/users/{userId}
This endpoint is used to retrieve a user. It requires the user's ID as a path variable. If the user is found, it returns the user and a status of `OK`. If the user is not found, it returns an error message and a status of `NOT_FOUND`.

Remember to replace `<username>`, `<password>`, `<email>`, `<user id>`, and `<new base64 image>` with actual values when using these endpoints.


