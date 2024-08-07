package com.moviezone.dai_api.model.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Repository
public class MovieDAOImplementation implements IMovieDAO {

    String savedSearch = "";

    JsonArray savedResults = new JsonArray();


    public JsonObject getMovieDetails(int movieId) { //! NO IMPLEMENTADO

        String API_URL = "https://api.themoviedb.org/3/movie/" +
                movieId +
                "?append_to_response=images%2Ccredits%2Cvideos&language=es-AR";


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        headers.add("Authorization",  System.getenv("TMDB_TOKEN")  );
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(API_URL,HttpMethod.GET, entity, String.class);

        if (response.getStatusCodeValue() == 200) {

            String datos = response.getBody();

            JsonParser parser = new JsonParser();
            JsonObject movieJSON = (JsonObject) parser.parse(datos);

            return movieJSON;
        }
        else
            return null;














//        String API_URL = "https://api.themoviedb.org/3/movie/" +
//                movieId +
//                "?language=es-AR";
//
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("accept", "application/json");
//        headers.add("Authorization",  Dotenv.load().get("TMDB_TOKEN")  );
//        HttpEntity<String> entity = new HttpEntity<String>(headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(API_URL,HttpMethod.GET, entity, String.class);
//
//        if (response.getStatusCodeValue() == 200) {
//
//            String datos = response.getBody();
//
//            JsonParser parser = new JsonParser();
//            JsonObject movieJSON = (JsonObject) parser.parse(datos);
//
//            return movieJSON;
//        }
//        else
//            return null;
    }

    @Override
    public JsonArray discover(String page, String genres) { //? RESULTADOS DE LA LANDING PAGE

        //* FIJAMOS QUE SEAN PELICULAS QUE HAYAN SALIDO YA
        LocalDate fecha = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fecha_actual = fecha.format(formato);

        String API_URL = "https://api.themoviedb.org/3/discover/movie" +
                "?include_adult=false" +
                "&include_video=false" +
                "&language=en-US" +
                "&page=" + page +
                "&primary_release_date.lte=" + fecha_actual +
                "&sort_by=primary_release_date.desc&with_genres=" + genres;


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        headers.add("Authorization", System.getenv("TMDB_TOKEN") );
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(API_URL,HttpMethod.GET, entity, String.class);

        if (response.getStatusCodeValue() == 200) {

            String datos = response.getBody();

            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject) parser.parse(datos);
            JsonArray allMovies = jsonObject.getAsJsonArray("results");

            return allMovies;
        }
        else
            return null;

    }
    @Override
    public JsonArray search(String search) {

        //final int MAX_PAGES_TO_RETURN = 6;

        if (savedSearch.equals(search)) {
            JsonArray finalResponse = savedResults.deepCopy();
            return finalResponse;
        }
        else {
            savedSearch = search;
            savedResults = new JsonArray();
        }


        JsonArray finalResponse = new JsonArray();

        String API_URL = "https://api.themoviedb.org/3/search/multi" +
                "?query="+ search +
                "&include_adult=false" +
                "&language=es-AR" +
                "&page=1";


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        headers.add("Authorization", System.getenv("TMDB_TOKEN"));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(API_URL,HttpMethod.GET, entity, String.class);

        if (response.getStatusCodeValue() == 200) {

            String datos = response.getBody();
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject) parser.parse(datos);
            JsonArray allMovies = jsonObject.getAsJsonArray("results");

            JsonObject person = allMovies.get(0).getAsJsonObject();

            if (person.get("media_type").getAsString().equals("person")) { //? ES UNA PERSONA

                //* HACEMOS EL PRIMER LLAMADO PARA VER LA CANTIDAD DE PAGINAS DE RESULTADOS

                String NEW_API_URL = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=es-AR" +
                        "&page=" + 1 +
                        "&sort_by=popularity.desc&with_cast=" +
                        person.get("id");

                response = restTemplate.exchange(NEW_API_URL,HttpMethod.GET, entity, String.class);

                if (response.getStatusCodeValue() == 200) {

                    datos = response.getBody();
                    parser = new JsonParser();
                    jsonObject = (JsonObject) parser.parse(datos);
                    allMovies = jsonObject.getAsJsonArray("results");

                    //* AGREGAMOS LOS VALORES DE LA PRIMERA REQUEST

                    finalResponse.addAll(allMovies);
                    savedResults.addAll(allMovies);
                }

                //* VERIFICAMOS LA CANTIDAD DE PAGINAS DE RESULTADOS. COLOCAMOS UN MAXIMO DE 6 PAGINAS (120 RESULTADOS).

//                int pages_count = Math.min(jsonObject.get("total_pages").getAsInt(), MAX_PAGES_TO_RETURN);

                int pages_count = jsonObject.get("total_pages").getAsInt();

                //* HACEMOS LAS REQUESTS RESTANTES HASTA OBTENER TODOS LOS VALORES

                for (int i = 2; i <= pages_count; i++) {
                    NEW_API_URL = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=es-AR" +
                            "&page=" + i +
                            "&sort_by=popularity.desc&with_cast=" +
                            person.get("id");


                    response = restTemplate.exchange(NEW_API_URL,HttpMethod.GET, entity, String.class);

                    if (response.getStatusCodeValue() == 200) {

                        datos = response.getBody();
                        parser = new JsonParser();
                        jsonObject = (JsonObject) parser.parse(datos);
                        allMovies = jsonObject.getAsJsonArray("results");
                        if (allMovies == null) continue;

                        finalResponse.addAll(allMovies);
                        savedResults.addAll(allMovies);
                    }
                }
            }

            else { //? NO ES UNA PERSONA

                //* HACEMOS EL PRIMER LLAMADO PARA VER LA CANTIDAD DE PAGINAS DE RESULTADOS

                String NEW_API_URL = "https://api.themoviedb.org/3/search/movie" +
                        "?query="+ search +
                        "&include_adult=false" +
                        "&language=es-AR" +
                        "&page=" + 1;

                response = restTemplate.exchange(NEW_API_URL,HttpMethod.GET, entity, String.class);

                if (response.getStatusCodeValue() == 200) {

                    datos = response.getBody();
                    parser = new JsonParser();
                    jsonObject = (JsonObject) parser.parse(datos);
                    allMovies = jsonObject.getAsJsonArray("results");


                    //* AGREGAMOS LOS VALORES DE LA PRIMERA REQUEST

                    finalResponse.addAll(allMovies);
                    savedResults.addAll(allMovies);
                }

                //* VERIFICAMOS LA CANTIDAD DE PAGINAS DE RESULTADOS. COLOCAMOS UN MAXIMO DE 6 PAGINAS (120 RESULTADOS).
//                int pages_count = Math.min(jsonObject.get("total_pages").getAsInt(), MAX_PAGES_TO_RETURN);
                int pages_count = jsonObject.get("total_pages").getAsInt();
                System.err.println("PAGES_COUNT: " + pages_count);


//                String Threads_URL = "https://api.themoviedb.org/3/search/movie?query={search}&include_adult=false&language=es-AR&page={i}";
//                ExecutorService executorService = Executors.newFixedThreadPool(2); // Ajusta el tamaño del pool de hilos según tus necesidades
//                List<Future<?>> futures = new ArrayList<>();
//
//                for (int i = 2; i <= pages_count; i++) {
//                    int page = i;
//                    JsonArray finalAllMovies = allMovies;
//                    Future<?> future = executorService.submit(() -> {
//                        String url = Threads_URL.replace("{search}", search).replace("{i}", String.valueOf(page));
//
//                        ResponseEntity<String> testResponse = restTemplate.getForEntity(url, String.class, entity);
//                        String testDatos = testResponse.getBody();
//                        JsonParser testParser = new JsonParser();
//                        JsonObject testJsonObject = (JsonObject) testParser.parse(testDatos);
//                        JsonArray moviesArray = testJsonObject.getAsJsonArray("results");
//                        finalAllMovies.add(moviesArray);
//                    });
//                    futures.add(future);
//                }
//
//                // Esperar a que todos los hilos terminen
//                for (Future<?> future : futures) {
//                    try {
//                        future.get();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                executorService.shutdown();
//
//                finalResponse.addAll(allMovies);


















                for (int i = 2; i <= pages_count; i++) { //* HACEMOS LAS REQUESTS RESTANTES HASTA OBTENER TODOS LOS VALORES
                    NEW_API_URL = "https://api.themoviedb.org/3/search/movie" +
                            "?query="+ search +
                            "&include_adult=false" +
                            "&language=es-AR" +
                            "&page=" + i;

                    response = restTemplate.exchange(NEW_API_URL,HttpMethod.GET, entity, String.class);


                    if (response.getStatusCodeValue() == 200) {

                        datos = response.getBody();
                        parser = new JsonParser();
                        jsonObject = (JsonObject) parser.parse(datos);

                        allMovies = jsonObject.getAsJsonArray("results");
                        if (allMovies == null) continue;

                        System.err.println(allMovies);
                        System.err.println("PAGE: " + i);
                        finalResponse.addAll(allMovies);
                        savedResults.addAll(allMovies);
                    }
                }
            }
        }
        return finalResponse;
    }

    @Override
    public JsonArray getGenres(int movieId) {

        String URL = "https://api.themoviedb.org/3/movie/" +
                movieId +
                "/credits?language=es-AR";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        headers.add("Authorization",  System.getenv("TMDB_TOKEN")  );
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URL,HttpMethod.GET, entity, String.class);

        if (response.getStatusCodeValue() == 200) {

            String datos = response.getBody();
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject) parser.parse(datos);
            JsonArray cast = jsonObject.getAsJsonArray("cast");

            return cast;
        }
        else return null;
    }

    @Override
    public JsonArray getImages(int movieId) {

        String URL = "https://api.themoviedb.org/3/movie/" +
                movieId+
                "/images?include_image_language=null";

        System.err.println("URL: " + URL);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        headers.add("Authorization",  System.getenv("TMDB_TOKEN")  );
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URL,HttpMethod.GET, entity, String.class);

        if (response.getStatusCodeValue() == 200) {

            String datos = response.getBody();
            JsonParser parser = new JsonParser();
            JsonObject result = (JsonObject) parser.parse(datos);
            JsonArray images = result.get("backdrops").getAsJsonArray();
            return images;
        }
        else return null;
    }

    @Override
    public JsonObject getCredits(int movieId) {

        String URL = "https://api.themoviedb.org/3/movie/" +
                movieId +
                "/credits?language=es-AR";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "application/json");
            headers.add("Authorization",  System.getenv("TMDB_TOKEN")  );
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<String> response = restTemplate.exchange(URL,HttpMethod.GET, entity, String.class);

            if (response.getStatusCodeValue() == 200) {

                String datos = response.getBody();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = (JsonObject) parser.parse(datos);
                return jsonObject;

            }
            else return null;
        }

    @Override
    public String getTrailer(int movieId) {

        String URL = "https://api.themoviedb.org/3/movie/" +
                movieId+
                "/videos?language=es-AR";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        headers.add("Authorization",  System.getenv("TMDB_TOKEN")  );
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URL,HttpMethod.GET, entity, String.class);

        if (response.getStatusCodeValue() == 200) {

            String datos = response.getBody();
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject) parser.parse(datos);
            JsonArray videos = jsonObject.getAsJsonArray("results");

            for (JsonElement video : videos) {
                JsonObject result = video.getAsJsonObject();

                if (result.get("type").getAsString().equals("Trailer")) {
                    return "https://www.youtube.com/watch?v=" + result.get("key").getAsString();
                }
            }
        }
return null;
    }

    @Override
    public String getCertificacion(int movieId) {

        String URL = "https://api.themoviedb.org/3/movie/" +
                movieId +
                "/release_dates";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        headers.add("Authorization",  System.getenv("TMDB_TOKEN")  );
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URL,HttpMethod.GET, entity, String.class);

        if (response.getStatusCodeValue() == 200) {

            String datos = response.getBody();
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject) parser.parse(datos);
            JsonArray certifications = jsonObject.getAsJsonArray("results");

            for (JsonElement certification : certifications){
                JsonObject certObject = certification.getAsJsonObject();
                if (certObject.get("iso_3166_1").getAsString().equals("US")){
                    JsonArray releaseDates = certObject.getAsJsonArray("release_dates");
                    for (JsonElement date : releaseDates){
                        JsonObject dateObject = date.getAsJsonObject();
                        if (dateObject.get("type").getAsString().equals("3")){
                            return dateObject.get("certification").getAsString();
                        }
                    }
                }
            }
        }
        return "";
    }
}

