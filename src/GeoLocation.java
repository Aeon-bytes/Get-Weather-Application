import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class GeoLocation {
    public static void main(String[] args) {
        System.out.println("----------------------------------------------");
        System.out.println("Welcome to the Weather Application");
        System.out.println("----------------------------------------------");
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the city name: ");
        String name = input.nextLine();
        try {
            JSONObject apiData = getApiData(name);

            assert apiData != null;
            JSONArray dataResults = (JSONArray) apiData.get("results");
            JSONObject results = (JSONObject) dataResults.get(0);

            String name2 = (String) results.get("admin2");
            double longitude = (double) results.get("longitude");
            double latitude = (double) results.get("latitude");


            JSONObject weatherData = getWeatherData(longitude, latitude);

            assert weatherData != null;
            JSONObject weather_results = (JSONObject) weatherData.get("current");
            double temp = (double) weather_results.get("temperature_2m");
            long humidity = (long) weather_results.get("relative_humidity_2m");
            double wind_speed = (double) weather_results.get("wind_speed_10m");

            JSONObject weather_units = (JSONObject) weatherData.get("current_units");
            String  temp_units = (String) weather_units.get("temperature_2m");
            String humidity_unit = (String) weather_units.get("relative_humidity_2m");
            String wind_speed_unit = (String) weather_units.get("wind_speed_10m");

            System.out.println("----------------------------------------------");
            if (name2 != null) {
                System.out.println("Here are the weather results of " + name + ", " + name2 + ": ");
            } else {
                System.out.println("Here are the weather results of " + name + ": ");
            }

            System.out.println("The temperature: " + temp + temp_units);
            System.out.println("Relative humidity: " + humidity + humidity_unit);
            System.out.println("Wind speed: " + wind_speed + wind_speed_unit);

            System.out.println("----------------------------------------------");

        } catch (NullPointerException e) {
            System.out.println("The results for the city "+ name +" is not available.");
        }
    }

    public static JSONObject getApiData(String city_name) {
        city_name = city_name.replaceAll(" ", "+");

        String url = "https://geocoding-api.open-meteo.com/v1/search?name="+city_name+"&count=1&language=en&format=json";

        try{
            HttpURLConnection apiConnection = fetchApiResponse(url);

            if(apiConnection.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return null;
            }

            String jsonResponse = readApiResponse(apiConnection);

            JSONParser parser = new JSONParser();
            JSONObject resultsJsonObj = (JSONObject) parser.parse(jsonResponse);

            return (JSONObject) resultsJsonObj;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getWeatherData(double longitude, double latitude) {
        String url = "https://api.open-meteo.com/v1/forecast?latitude="+latitude+"&longitude="+longitude+"&current=temperature_2m,relative_humidity_2m,wind_speed_10m&forecast_days=1";

        try{
            HttpURLConnection apiConnection = fetchApiResponse(url);

            if(apiConnection.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return null;
            }

            String jsonResponse = readApiResponse(apiConnection);

            JSONParser parser = new JSONParser();
            JSONObject resultsJsonObj = (JSONObject) parser.parse(jsonResponse);

            return (JSONObject) resultsJsonObj;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static String readApiResponse(HttpURLConnection apiConnection) {
        try {
            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(apiConnection.getInputStream());

            while (scanner.hasNext()) {
                resultJson.append(scanner.nextLine());
            }
            scanner.close();
            return resultJson.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpURLConnection fetchApiResponse(String url) {
        try {
            URL url1 = new URI(url).toURL();
            HttpURLConnection connection = (HttpsURLConnection) url1.openConnection();
            connection.setRequestMethod("GET");
            return connection;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
