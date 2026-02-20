package com.GetWeather;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.*;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@RestController
@RequestMapping("/api/v1/get-weather")
@CrossOrigin(origins = "http://localhost:3000") // Allow frontend access
public class GetWeatherController {

    private static final HttpClient httpClient = HttpClient.newHttpClient();

    @PostMapping("/get")
    public GetWeatherResponse get(@RequestBody GetWeatherRequest request) throws Exception {
        String result;
        try {
            String cityName = request.getCity();
            System.out.println(cityName);
            JSONObject locationData = getLocationData(cityName);

            if (locationData == null || !locationData.containsKey("results")) {
                result = "The results for the city " + cityName + " is not available.";
                return new GetWeatherResponse(result);
            }

            JSONArray dataResults = (JSONArray) locationData.get("results");
            if (dataResults.isEmpty()) {
                result = "No location found for: " + cityName;
                return new GetWeatherResponse(result);
            }
            JSONObject location = (JSONObject) dataResults.get(0);

            String admin2 = (String) location.get("admin2");
            double longitude = (double) location.get("longitude");
            double latitude = (double) location.get("latitude");

            JSONObject weatherData = getWeatherData(longitude, latitude);

            if (weatherData == null || !weatherData.containsKey("current")) {
                result = "Could not retrieve weather data.";
                return new GetWeatherResponse(result);
            }

            JSONObject weather_results = (JSONObject) weatherData.get("current");
            double temp = (double) weather_results.get("temperature_2m");
            long humidity = (long) weather_results.get("relative_humidity_2m");
            double wind_speed = (double) weather_results.get("wind_speed_10m");

            JSONObject weather_units = (JSONObject) weatherData.get("current_units");
            String  temp_units = (String) weather_units.get("temperature_2m");
            String humidity_unit = (String) weather_units.get("relative_humidity_2m");
            String wind_speed_unit = (String) weather_units.get("wind_speed_10m");

            StringBuilder sb = new StringBuilder();

            if (admin2 != null) {
                sb.append("Here are the weather results of " + cityName + ", " + admin2 + ": \n");
            } else {
                sb.append("Here are the weather results of " + cityName + ": \n");

            }

            sb.append(
                    "The temperature: " + temp + temp_units + "\n" +
                    "Relative humidity: " + humidity + humidity_unit + "\n" +
                    "Wind speed: " + wind_speed + wind_speed_unit
            );

            return new GetWeatherResponse(sb.toString());

        } catch (Exception e) {
            result = e.getMessage();
            return new GetWeatherResponse(result);
        }
    }

    private static JSONObject getLocationData(String cityName) {
        try {

            String encodedCity = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

            String url = "https://geocoding-api.open-meteo.com/v1/search?name="+encodedCity+"&count=1&language=en&format=json";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200){
                System.out.println("Error: API returned status " + response.statusCode());
                return null;
            }

            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(response.body());



        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static JSONObject getWeatherData(double longitude, double latitude) {
        try{
            String url = "https://api.open-meteo.com/v1/forecast?latitude="+latitude+"&longitude="+longitude+"&current=temperature_2m,relative_humidity_2m,wind_speed_10m&forecast_days=1";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200){
                System.out.println("Error: API returned status " + response.statusCode());
                return null;
            }

            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(response.body());



        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

}
