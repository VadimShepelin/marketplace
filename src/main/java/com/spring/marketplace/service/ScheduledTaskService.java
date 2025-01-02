package com.spring.marketplace.service;

import com.spring.marketplace.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@ConditionalOnProperty(value = "app.scheduling.enabled", matchIfMissing = false)
@Slf4j
@RequiredArgsConstructor
public class ScheduledTaskService {

    private final ProductRepository repository;

    @Transactional
    @Scheduled(fixedDelayString = "${app.scheduling.period}")
    public void increasePrice(){
        repository.updateAllProductsPrice();
        log.info("Increase price by scheduled task");
    }
}
