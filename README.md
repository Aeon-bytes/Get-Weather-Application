# Get Weather Application

A lightweight full-stack weather application that retrieves real-time weather data for any city in the world. Built with a **Spring Boot** backend and a **Vanilla JavaScript** frontend, it leverages free open-source APIs to fetch geolocation and live weather conditions — no API key required.

---

## Introduction

Get Weather Application is a simple yet functional web app that allows users to look up the current weather of any city instantly. The user enters a city name into a text field, and the application fetches real-time weather data including temperature, relative humidity, and wind speed.

The backend is powered by **Spring Boot**, which acts as a REST API layer. It first resolves the city name into geographic coordinates using the **Open-Meteo Geocoding API**, and then queries the **Open-Meteo Weather API** for the current weather conditions at those coordinates. The frontend is a clean, static HTML/CSS/JS interface served directly by Spring Boot's embedded web server.

---

## Technologies Used

### Backend
| Technology | Version | Purpose |
|---|---|---|
| Java | 21 | Core programming language |
| Spring Boot | 4.0.1 | REST API framework & embedded server |
| Spring Web MVC | (via Spring Boot) | HTTP request handling & routing |
| Spring DevTools | (via Spring Boot) | Hot reload during development |
| json-simple | 1.1.1 | Lightweight JSON parsing |
| Maven | (via wrapper) | Build tool & dependency management |

### Frontend
| Technology | Purpose |
|---|---|
| HTML5 | Page structure |
| CSS3 | Styling |
| Vanilla JavaScript | Async API calls & DOM manipulation |

### External APIs (Free, No Key Required)
| API | Purpose |
|---|---|
| [Open-Meteo Geocoding API](https://open-meteo.com/en/docs/geocoding-api) | Resolves city name → latitude & longitude |
| [Open-Meteo Weather API](https://open-meteo.com/en/docs) | Fetches current weather by coordinates |

---

## Features

- **City-based Weather Search** — Enter any city name to retrieve its current weather conditions.
- **Temperature** — Displays the current temperature at 2 metres above ground (in °C).
- **Relative Humidity** — Shows the current humidity level (in %).
- **Wind Speed** — Reports the current wind speed at 10 metres above ground (in km/h).
- **Global Coverage** — Supports cities worldwide via the Open-Meteo geocoding service.
- **No API Key Required** — Built entirely on free, open-access APIs.
- **Lightweight & Fast** — Minimal dependencies; single-page frontend served by the Spring Boot embedded server.
- **Graceful Error Handling** — Returns user-friendly messages when a city is not found or the API is unreachable.

---

## User Manual

### Prerequisites

Before running the application, ensure the following are installed on your machine:

- **Java 21+** — [Download here](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.6+** — [Download here](https://maven.apache.org/download.cgi) *(or use the included Maven Wrapper)*

---

### Running the Application

**1. Clone the repository**
```bash
git clone https://github.com/Aeon-bytes/Get-Weather-Application.git
cd Get-Weather-Application
```

**2. Start the Spring Boot server**

Using the Maven Wrapper (recommended):
```bash
./mvnw spring-boot:run
```

Or with Maven directly:
```bash
mvn spring-boot:run
```

**3. Open the application in your browser**

Navigate to:
```
http://localhost:8080
```

---

### How to Use

1. **Open the app** in your browser at `http://localhost:8080`.
2. **Type a city name** into the input field (e.g., `London`, `Mumbai`, `New York`).
3. **Click the "Get Weather" button**.
4. The current weather results will be displayed below, including:
   - Temperature (°C)
   - Relative Humidity (%)
   - Wind Speed (km/h)

---

### API Endpoint

The backend exposes the following REST endpoint:

| Method | URL | Description |
|---|---|---|
| `POST` | `/api/v1/get-weather/get` | Returns weather data for the given city |

**Request Body (JSON):**
```json
{
  "city": "Chennai"
}
```

**Response Body (JSON):**
```json
{
  "result": "Here are the weather results of Chennai, Tamil Nadu: \nThe temperature: 32.5°C\nRelative humidity: 78%\nWind speed: 14.2km/h"
}
```

---

### Project Structure

```
Get-Weather-Application/
├── src/
│   └── main/
│       ├── java/com/GetWeather/
│       │   ├── GetWeatherApplication.java     # Spring Boot entry point
│       │   ├── GetWeatherController.java      # REST controller & API logic
│       │   ├── GetWeatherRequest.java         # Request model (city name)
│       │   └── GetWeatherResponse.java        # Response model (result string)
│       └── resources/
│           ├── application.properties         # App configuration
│           └── static/
│               ├── index.html                 # Frontend UI
│               ├── styles.css                 # Stylesheet
│               └── app.js                     # Fetch API calls
├── pom.xml                                    # Maven build configuration
└── README.md
```

---