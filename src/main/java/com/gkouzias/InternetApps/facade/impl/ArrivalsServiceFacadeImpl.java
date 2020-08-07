package com.gkouzias.InternetApps.facade.impl;

import com.gkouzias.InternetApps.domain.Arrival;
import com.gkouzias.InternetApps.facade.ArrivalsServiceFacade;
import com.gkouzias.InternetApps.service.ArrivalsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ArrivalsServiceFacadeImpl implements ArrivalsServiceFacade {

    @Autowired
    ArrivalsService arrivalsService;


    @Scheduled(fixedDelay = 1*60*1000)
    @Override
    public void updateArrivals() {
        log.info("start - update arrivals");
        LocalDateTime now = LocalDateTime.now();
        List<Arrival> arrivals = arrivalsService.findAll();

        arrivals.forEach(arrival -> {
            int minutes = arrival.getIn();
            LocalDateTime last_at = arrival.getLast_arrival();
            LocalDateTime next_at = last_at.plusMinutes(minutes);

            // if we are past the next arrival, update
            if(now.isAfter(next_at)){
                minutes = (int)Math.ceil(Math.random() * 15);   // random interval
                arrival.setIn(minutes);
                arrival.setLast_arrival(now);
                arrivalsService.save(arrival);
            }

        });
        log.info("end - update arrivals");
    }
}
