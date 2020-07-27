package com.gkouzias.InternetApps.controller;


import com.gkouzias.InternetApps.Errors.StopError;
import com.gkouzias.InternetApps.domain.Stop;
import com.gkouzias.InternetApps.model.StopDTO;
import com.gkouzias.InternetApps.service.StopsService;
import com.gkouzias.InternetApps.validation.StopValidator;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;


@RestController
public class StopController {

    @Autowired
    private StopsService stopsService;

    @Autowired
    private StopValidator stopValidator;

    @Autowired
    private StopError errCodes;


    // CREATE STOP
    @PostMapping("/api/stops/createStop")
    public ResponseEntity<?> createStopController(@RequestBody Stop stop, BindingResult result){

       stopValidator.validate(stop, result);    // validations
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getAllErrors(),HttpStatus.BAD_REQUEST);
        }else {
            if(stopsService.existsByName(stop.getName())){
                result.addError(new ObjectError(errCodes.object, new String[]{errCodes.EXISTS}, null, errCodes.EXISTS_MSG));
                return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
            }else {
                // no validation and db problems --> create the stop using mapper (return dummy DTO)
                ModelMapper m = new ModelMapper();
                return new ResponseEntity<>(
                        m.map(stopsService.save(stop), StopDTO.class),
                        HttpStatus.OK
                );
            }
        }
    }


    @GetMapping("/api/stops/{id}")
    public ResponseEntity<?> getStopByIdController(@PathVariable int id){
        ModelMapper m = new ModelMapper();
        Stop s = stopsService.findById(id);
        if(s == null)  return new ResponseEntity<>(ResponseEntity.notFound().build(), HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(m.map(stopsService.findById(id), StopDTO.class), HttpStatus.OK);
    }

    @GetMapping("/api/stops/{name}")
    public ResponseEntity<?> getStopByNameController(@PathVariable String name){
        ModelMapper m = new ModelMapper();
        Stop s = stopsService.findByName(name);
        if(s == null)  return new ResponseEntity<>(ResponseEntity.notFound().build(), HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(m.map(stopsService.findByName(name),StopDTO.class), HttpStatus.OK);
    }


    @GetMapping("/api/stops/like/{name}")
    public ResponseEntity<?> getStopByNameStartingWithController(@PathVariable String name){
        ModelMapper m = new ModelMapper();
        List<Stop> stops = stopsService.findByNameStartingWith(name);
        if(stops.isEmpty())  return new ResponseEntity<>(ResponseEntity.notFound().build(), HttpStatus.NOT_FOUND);
        else {
            Type type = new TypeToken<List<StopDTO>>(){}.getType();
            List<StopDTO> stopsDTO = m.map(stops, type);
            return new ResponseEntity<>(stopsDTO, HttpStatus.OK);
        }
    }


}
