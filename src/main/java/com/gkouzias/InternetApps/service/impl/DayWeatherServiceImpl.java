package com.gkouzias.InternetApps.service.impl;

import com.gkouzias.InternetApps.domain.DayWeather;
import com.gkouzias.InternetApps.domain.Transport;
import com.gkouzias.InternetApps.repository.DayWeatherRepository;
import com.gkouzias.InternetApps.service.DayWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Tuple;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DayWeatherServiceImpl implements DayWeatherService {
    @Autowired
    DayWeatherRepository dayWeatherRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public DayWeather save(DayWeather dw) {
        return dayWeatherRepository.save(dw);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public List<DayWeather> findAll() {
        return dayWeatherRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public List<Tuple> findMaxPerWeatherDay(String month1, String month2) {
        return dayWeatherRepository.findMaxPerWeatherDay(month1, month2);
    }

    @Override
    public Map<String, Long> populateDayWeather(List<Transport> transports) {

        String uri = "http://147.102.16.156:8090/services/getWeatherDisc/";
        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, Long> dateUsers = new HashMap<>();
        for (Transport transport : transports) {

            // accepted format for time period to call api
            String time_period = String.valueOf((int) transport.getTimePeriod());
            if (time_period.length() == 1) time_period = "0" + time_period;

            // call api foreach novemberTransport and get info
            //System.out.println("CALL -- " + transport.getEventDate() + " " + time_period);
            final String nameURI = uri + transport.getEventDate() + "/" + time_period;
            String res = restTemplate.getForObject(nameURI, String.class);

            //System.out.println("RES -- " + res);
            if (res == null) continue;
            res = "" + res.trim();


            long prev_users = 0;
            if (dateUsers.containsKey(transport.getEventDate() + ";" + res))
                prev_users = dateUsers.get(transport.getEventDate() + ";" + res);
            dateUsers.put(transport.getEventDate() + ";" + res, prev_users + transport.getTotalUsers());


        }
        dateUsers.forEach((k, v) -> {
            // insert into the database
            String tokens[] = k.split(";");
            DayWeather dw = new DayWeather(tokens[0], tokens[1], v);
            save(dw);
        });
        return dateUsers;
    }


    // how to sort
    // return dayWeatherRepository.findAll(Sort.by(Sort.Direction.ASC, "event_date", "weather_class"));

}
