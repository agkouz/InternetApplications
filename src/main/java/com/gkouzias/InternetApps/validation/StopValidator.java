package com.gkouzias.InternetApps.validation;


import com.gkouzias.InternetApps.Errors.StopError;
import com.gkouzias.InternetApps.domain.Stop;
import com.gkouzias.InternetApps.model.StopDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class StopValidator implements Validator {

    @Autowired
    StopError err;

    // just Stop instances
    @Override
    public boolean supports(Class<?> clazz) {
        return Stop.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", err.BAD_NAME);
        Stop s = (Stop) o;

        // lat validation
        if(s.getLat() < 0){
            errors.rejectValue("lat", err.NEGATIVE_LAT, err.LAT_MSG);
        }else if(s.getLat() > 40){
            errors.rejectValue("lat", err.OVER_LAT, err.LAT_MSG);
        }

        // lon validation
        if(s.getLon() < 0){
            errors.rejectValue("lon", err.NEGATIVE_LON, err.LON_MSG);
        }else if(s.getLon() > 40){
            errors.rejectValue("lon", err.OVER_LON, err.LON_MSG);
        }
    }
}
