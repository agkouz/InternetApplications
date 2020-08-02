package com.gkouzias.InternetApps.service.impl;

import com.gkouzias.InternetApps.domain.Stop;
import com.gkouzias.InternetApps.domain.WeatherCondition;
import com.gkouzias.InternetApps.repository.WeatherConditionRepository;
import com.gkouzias.InternetApps.service.WeatherConditionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WeatherConditionsServiceImpl implements WeatherConditionsService {
    @Autowired
    WeatherConditionRepository weatherConditionRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public WeatherCondition save(WeatherCondition wc) {
        return weatherConditionRepository.save(wc);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public WeatherCondition findById(int id) {
        return weatherConditionRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public WeatherCondition findByStopWc(Stop origin_stop) {
        return weatherConditionRepository.findByStopWc(origin_stop).orElse(null);
    }


}
