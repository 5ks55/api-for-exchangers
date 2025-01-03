from fastapi import FastAPI, HTTPException, Query
from pymongo import MongoClient
from typing import List
from pydantic import BaseModel
from datetime import datetime
import logging

# Уstawienia loggera
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger("CurrencyTrackerAPI")

# Подłączenie do MongoDB
client = MongoClient("mongodb://localhost:27017/")
db_dev = client["currency_tracker_db_dev"]
db_prod = client["currency_tracker_db_prod"]

# Tworzenie aplikacji FastAPI
app = FastAPI(title="Currency Tracker API", description="API do zarządzania danymi finansowymi i użytkownikami", version="1.0")

# Modele danych
class ExchangeRate(BaseModel):
    id: str
    currencyPair: str
    buyRate: float
    sellRate: float
    lastUpdated: datetime
    platformId: int

class ExchangeRateHistory(BaseModel):
    id: str
    currencyPair: str
    buyRate: float
    sellRate: float
    lastUpdated: datetime
    platformId: int

class ExchangePlatform(BaseModel):
    id: str
    name: str
    parseUrl: str

class User(BaseModel):
    id: str
    username: str
    email: str
    notificationPreferences: dict

class Report(BaseModel):
    id: str
    userId: str
    currencyPair: List[str]
    timeRange: dict
    createdOn: datetime

# Funkcja pomocnicza do wyboru bazy danych
def get_db(environment: str):
    if environment == "prod":
        logger.info("Używana baza danych: produkcyjna")
        return db_prod
    elif environment == "dev":
        logger.info("Używana baza danych: deweloperska")
        return db_dev
    else:
        logger.error("Nieprawidłowy parametr środowiska: %s", environment)
        raise HTTPException(status_code=400, detail="Nieprawidłowy parametr środowiska. Użyj 'dev' lub 'prod'.")

# Endpointy
@app.get("/api/exchange-rates", response_model=List[ExchangeRate], summary="Pobierz kursy wymiany walut", description="Zwraca listę kursów wymiany walut dla wybranego środowiska ('dev' lub 'prod').")
async def get_exchange_rates(environment: str = Query("dev", description="Środowisko: 'dev' lub 'prod'")):
    db = get_db(environment)
    collection = db["exchange_rates"]
    data = list(collection.find())

    for item in data:
        item["id"] = str(item["_id"])
        del item["_id"]

    logger.info("Pobrano %d kursów wymiany walut.", len(data))
    return data

@app.get("/api/exchange-rate-history", response_model=List[ExchangeRateHistory], summary="Pobierz historię kursów wymiany", description="Zwraca historię kursów wymiany walut dla wybranego środowiska ('dev' lub 'prod').")
async def get_exchange_rate_history(environment: str = Query("dev", description="Środowisko: 'dev' lub 'prod'")):
    db = get_db(environment)
    collection = db["exchange_rate_history"]
    data = list(collection.find())

    for item in data:
        item["id"] = str(item["_id"])
        del item["_id"]

    logger.info("Pobrano %d rekordów historii kursów wymiany.", len(data))
    return data

@app.get("/api/exchange-platforms", response_model=List[ExchangePlatform], summary="Pobierz platformy wymiany", description="Zwraca listę platform wymiany walut dla wybranego środowiska ('dev' lub 'prod').")
async def get_exchange_platforms(environment: str = Query("dev", description="Środowisko: 'dev' lub 'prod'")):
    db = get_db(environment)
    collection = db["exchange_platforms"]
    data = list(collection.find())

    for item in data:
        item["id"] = str(item["_id"])
        del item["_id"]

    logger.info("Pobrano %d platform wymiany walut.", len(data))
    return data

@app.get("/api/users", response_model=List[User], summary="Pobierz użytkowników", description="Zwraca listę użytkowników dla wybranego środowiska ('dev' lub 'prod').")
async def get_users(environment: str = Query("dev", description="Środowisko: 'dev' lub 'prod'")):
    db = get_db(environment)
    collection = db["users"]
    data = list(collection.find())

    for item in data:
        item["id"] = str(item["_id"])
        del item["_id"]

    logger.info("Pobrano %d użytkowników.", len(data))
    return data

@app.get("/api/reports", response_model=List[Report], summary="Pobierz raporty", description="Zwraca listę raportów dla wybranego środowiska ('dev' lub 'prod').")
async def get_reports(environment: str = Query("dev", description="Środowisko: 'dev' lub 'prod'")):
    db = get_db(environment)
    collection = db["reports"]
    data = list(collection.find())

    for item in data:
        item["id"] = str(item["_id"])
        del item["_id"]

    logger.info("Pobrano %d raportów.", len(data))
    return data

@app.get("/", summary="Status aplikacji", description="Sprawdza, czy API działa poprawnie.")
async def root():
    logger.info("Sprawdzono status API.")
    return {"message": "Currency Tracker API działa poprawnie!"}
