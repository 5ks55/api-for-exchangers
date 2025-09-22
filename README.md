# Currency Tracker API

**Project:** Currency Tracker (API for web application)

**Description**
This repository contains the backend API for a future web application that monitors and compares currency exchange rates gathered from several exchange platforms. The API provides current exchange rates, historical data, platform metadata, user management, real-time notifications via WebSocket, and report generation (XLS).

---

## Key Features
- Fetch current exchange rates from external sources (APIs or scraping).
- Compare rates across multiple exchange platforms.
- Store and serve historical exchange rate data for analysis.
- Filter data by currency pair and time range.
- Real-time notifications about rate changes using WebSocket.
- Generate XLS reports based on selected data and time ranges.

---

## Non-functional Requirements
- **Performance:** Real-time data processing with minimal latency.
- **Reliability:** Stable updates and graceful handling of external service failures.
- **Scalability:** Easy to add new data sources or currency pairs.
- **Security:** Restrict access to sensitive data and protect the API from unauthorized use.
- **Usability:** Clear and intuitive endpoints for frontend consumption.

---

## Project Structure (important folders)
- `api-for-exchangers-java` — Java backend service.
- `api-for-exchangers-python` — Python backend service (FastAPI).
- `api-for-exchangers-web-client` — simple web client for testing WebSocket (contains `WebSocket.html`).
- `DB/backup_dev/` and `DB/backup_prod/` — MongoDB backup dumps.

---

## Prerequisites
- Java (JDK) installed for the Java server.
- Python 3.9+ and pip for the Python server.
- MongoDB running locally at `localhost:27017` (or update connection string in configuration).
- `mongorestore` utility if you want to import provided database dumps.

---

## Local Setup & Run
1. **Start MongoDB** (local instance):
   - Ensure MongoDB is running on `localhost:27017`.
   - If databases `currency_tracker_db_dev` and `currency_tracker_db_prod` are missing, restore them from backups:

```bash
mongorestore --db=currency_tracker_db_dev backup_dev/currency_tracker_db_dev
mongorestore --db=currency_tracker_db_prod backup_prod/currency_tracker_db_prod
```

2. **Run Java server**
   - Path: `api-for-exchangers-java`
   - Start the Java server according to the project README/build script (build with Maven/Gradle where applicable).
   - The Java server exposes REST endpoints on port `8080` by default.
   - To stop the Java server you can use your terminal/IDE stop command.

3. **Run Python server (FastAPI)**
   - Path: `api-for-exchangers-python`
   - Start the server with Uvicorn from the project folder:

```bash
uvicorn main:app --reload
```

   - Python API is available on port `8000` by default.

4. **WebSocket test client**
   - Open `api-for-exchangers-web-client/WebSocket.html` in a browser.
   - Click **Connect**, enter the currency pairs you want to monitor, then click **Subscribe**.

---

## Important API Endpoints
> Use `?environment=prod` to switch calls to production profile where supported.

- Get all exchange rates (Java):
  - `http://localhost:8080/api/exchange-rates`
- Get all exchange rate histories (Java):
  - `http://localhost:8080/api/exchange-rate-history`
- Get all exchange platforms (Java):
  - `http://localhost:8080/api/exchange-platforms`
- Get all users (Java):
  - `http://localhost:8080/api/users`
- Get all reports (Java):
  - `http://localhost:8080/api/reports`

API documentation pages:
- Java Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- Python (FastAPI) docs: `http://localhost:8000/docs`

---

## Data Model (MongoDB collections)
- `ExchangeRate` — current exchange rate data (currency pair, buy/sell rates, last update timestamp).
- `ExchangeRateHistory` — historical changes for currency pairs (with timestamps).
- `ExchangePlatform` — metadata about tracked exchange platforms (name, API url / parsing url).
- `User` — application users and their notification settings/subscriptions.
- `Report` — generated reports metadata (creation date, currencies, time range).

The project is designed with MongoDB document collections to preserve flexibility (JSON/BSON documents with optional nested fields).

---

## WebSocket Notifications
- Real-time notifications are implemented using WebSocket.
- Use the included `WebSocket.html` client to simulate subscriptions: connect → subscribe to currency pairs → receive updates.

---

## Report Generation
- The API supports generating XLS reports for selected currencies and time ranges. The result is stored as a `Report` object and can be downloaded or accessed by the frontend.

---

## Notes & Troubleshooting
- If external source requests fail, ensure API URLs for exchange platforms are correct and reachable.
- Monitor logs (Java and Python services) to diagnose integration issues with external providers.
- When switching between `dev` and `prod` profiles, remember to add the `?environment=prod` parameter to requests if needed.
