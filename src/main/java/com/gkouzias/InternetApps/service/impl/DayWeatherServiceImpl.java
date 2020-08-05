package com.gkouzias.InternetApps.service.impl;

import com.gkouzias.InternetApps.domain.DayWeather;
import com.gkouzias.InternetApps.repository.DayWeatherRepository;
import com.gkouzias.InternetApps.service.DayWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.List;

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


    // how to sort
    // return dayWeatherRepository.findAll(Sort.by(Sort.Direction.ASC, "event_date", "weather_class"));

}
