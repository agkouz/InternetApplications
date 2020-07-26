package com.gkouzias.InternetApps.controller;


import com.gkouzias.InternetApps.Errors.StopError;
import com.gkouzias.InternetApps.domain.Stop;
import com.gkouzias.InternetApps.model.StopDTO;
import com.gkouzias.InternetApps.service.StopsService;
import com.gkouzias.InternetApps.validation.StopValidator;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StopController {

    @Autowired
    private StopsService stopsService;

    @Autowired
    private StopValidator stopValidator;

    @Autowired
    private StopError err;

    @PostMapping("/api/stops/createStop")
    public ResponseEntity<?> createStopController(@RequestBody Stop stop, BindingResult result){

       stopValidator.validate(stop, result);    // validations
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getAllErrors(),HttpStatus.BAD_REQUEST);
        }else {
            if(stopsService.existsByName(stop.getName())){

                return new ResponseEntity<>("A", HttpStatus.BAD_REQUEST);
            }else {

                ModelMapper m = new ModelMapper();
                return new ResponseEntity<>(
                        //m.map(stopsService.save(stop), StopDTO.class),
                        stop,
                        HttpStatus.OK
                );
            }
        }


    }
}
