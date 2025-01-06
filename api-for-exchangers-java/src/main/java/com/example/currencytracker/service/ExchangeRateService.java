/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.service;

/**
 *
 * @author Nkt, Nazar
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

   
    public List<ExchangeRateDto> getAllExchangeRates() {
        List<ExchangeRate> exchangeRates = exchangeRateRepository.findAll();
        return exchangeRates.stream()
                .map(exchangeRate -> new ExchangeRateDto(exchangeRate)) 
                .collect(Collectors.toList());
    }

 
    public ExchangeRateDto getExchangeRateById(String id) {
        ExchangeRate exchangeRate = exchangeRateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exchange rate not found"));
        return new ExchangeRateDto(exchangeRate); 
    }

    public ExchangeRateDto addExchangeRate(ExchangeRateDto exchangeRateDto) {
        ExchangeRate exchangeRate = new ExchangeRate();

        exchangeRate.setCurrencyPair(exchangeRateDto.getCurrencyPair());
        exchangeRate.setBuyRate(exchangeRateDto.getBuyRate());
        exchangeRate.setSellRate(exchangeRateDto.getSellRate());
        exchangeRate.setPlatformId(exchangeRateDto.getPlatformId());
        exchangeRate.setLastUpdated(exchangeRateDto.getLastUpdated());  

        exchangeRate = exchangeRateRepository.save(exchangeRate);

        return new ExchangeRateDto(exchangeRate); 
    }

    public ExchangeRateDto updateExchangeRate(String id, ExchangeRateDto exchangeRateDto) {
        ExchangeRate exchangeRate = exchangeRateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exchange rate not found"));

        if (exchangeRateDto.getBuyRate() != 0) {  
            exchangeRate.setBuyRate(exchangeRateDto.getBuyRate());
        }

        if (exchangeRateDto.getSellRate() != 0) {  
            exchangeRate.setSellRate(exchangeRateDto.getSellRate());
        }

        if (exchangeRateDto.getLastUpdated() != null) { 
            exchangeRate.setLastUpdated(exchangeRateDto.getLastUpdated());
        }

        exchangeRate = exchangeRateRepository.save(exchangeRate);

        return new ExchangeRateDto(exchangeRate);
    }


    public void deleteExchangeRate(String id) {
        ExchangeRate exchangeRate = exchangeRateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exchange rate not found"));
        exchangeRateRepository.delete(exchangeRate);
    }
}
