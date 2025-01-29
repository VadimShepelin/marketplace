package com.spring.marketplace.service.impl;

import com.spring.marketplace.service.RateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {

    private static String defaultRateValue;

    @Override
    public String getRateValue(String rate) {
        log.info("Get rate value from the microservice: rate-service");
        if(rate!=null && rate.equals("USD")){
            String exchangeRate = "105.32";
            defaultRateValue = exchangeRate;
            return exchangeRate;
        }
        else if(rate!=null && rate.equals("EUR")){
            String exchangeRate = "123.32";
            defaultRateValue = exchangeRate;
            return exchangeRate;
        }
        else if(rate!=null && rate.equals("RUB")){
            String exchangeRate = "1";
            defaultRateValue = exchangeRate;
            return exchangeRate;
        }
        else{
            return defaultRateValue !=null? defaultRateValue :"1";
        }
    }
}
