/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.service;

/**
 *
 * @author Nkt
 */

import com.example.currencytracker.dto.ExchangeRateDto;
import com.example.currencytracker.model.ExchangeRate;
import com.example.currencytracker.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExchangeRateService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    // Получение всех курсов
    public List<ExchangeRateDto> getAllExchangeRates() {
        List<ExchangeRate> exchangeRates = exchangeRateRepository.findAll();
        return exchangeRates.stream()
                .map(exchangeRate -> new ExchangeRateDto(exchangeRate)) // Преобразуем ExchangeRate в ExchangeRateDto
                .collect(Collectors.toList());
    }

    // Получение курса по ID
    public ExchangeRateDto getExchangeRateById(String id) {
        ExchangeRate exchangeRate = exchangeRateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exchange rate not found"));
        return new ExchangeRateDto(exchangeRate);  // Преобразуем модель в DTO
    }

    public ExchangeRateDto addExchangeRate(ExchangeRateDto exchangeRateDto) {
        // Создаем новый объект ExchangeRate
        ExchangeRate exchangeRate = new ExchangeRate();

        // Устанавливаем значения из DTO в объект модели
        exchangeRate.setCurrencyPair(exchangeRateDto.getCurrencyPair());
        exchangeRate.setBuyRate(exchangeRateDto.getBuyRate());
        exchangeRate.setSellRate(exchangeRateDto.getSellRate());
        exchangeRate.setPlatformId(exchangeRateDto.getPlatformId());  // Используем Long

        // Сохраняем объект в базе данных через репозиторий
        exchangeRate = exchangeRateRepository.save(exchangeRate);

        // Возвращаем объект DTO с сохраненным курсом
        return new ExchangeRateDto(exchangeRate);  // Возвращаем добавленный курс как DTO
    }




    // Обновление курса
    public ExchangeRateDto updateExchangeRate(String id, ExchangeRateDto exchangeRateDto) {
        ExchangeRate exchangeRate = exchangeRateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exchange rate not found"));

        exchangeRate.setCurrencyPair(exchangeRateDto.getCurrencyPair());
        exchangeRate.setBuyRate(exchangeRateDto.getBuyRate());
        exchangeRate.setSellRate(exchangeRateDto.getSellRate());
        exchangeRate.setLastUpdated(exchangeRateDto.getLastUpdated());
        exchangeRate.setPlatformId(exchangeRateDto.getPlatformId());

        exchangeRate = exchangeRateRepository.save(exchangeRate);
        return new ExchangeRateDto(exchangeRate);  // Возвращаем обновленный курс как DTO
    }

    // Удаление курса
    public void deleteExchangeRate(String id) {
        ExchangeRate exchangeRate = exchangeRateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exchange rate not found"));
        exchangeRateRepository.delete(exchangeRate);
    }
}
