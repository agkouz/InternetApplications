package com.gkouzias.InternetApps.controller;


import com.gkouzias.InternetApps.domain.DayWeather;
import com.gkouzias.InternetApps.domain.NovemberTransport;
import com.gkouzias.InternetApps.domain.Transport;
import com.gkouzias.InternetApps.service.DayWeatherService;
import com.gkouzias.InternetApps.service.NovemberTransportsService;
import com.gkouzias.InternetApps.service.TransportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/api/app3")
public class App3Controller {

    // http://147.102.16.56:8090/services/getWeatherDisc/20191105/15
    private String uri = "http://147.102.16.156:8090/services/getWeatherDisc/";


    @Autowired
    NovemberTransportsService novemberTransportsService;
    @Autowired
    TransportService transportService;




    void populate(HashMap<String, Long> dateUsers){

    }

    @CrossOrigin(origins = "http://localhost:8081")     // allow remote access from http://localhost:8081 [CORS]
    @GetMapping("/novemberPopulate")
    ResponseEntity<?> viewNovemberTransports(){
        List<NovemberTransport> novemberTransports = novemberTransportsService.findAll();

        if(novemberTransports.isEmpty()) return new ResponseEntity<>(ResponseEntity.notFound().build(), HttpStatus.NOT_FOUND);
        else {
            RestTemplate restTemplate = new RestTemplate();
            HashMap<String, Long> dateUsers = new HashMap<>();
            for(NovemberTransport novemberTransport:novemberTransports){

                // accepted format for time period to call api
                String time_period = String.valueOf((int)novemberTransport.getTimePeriod());
                if(time_period.length() == 1) time_period = "0" + time_period;

                // call api foreach novemberTransport and get info
                //System.out.println("CALL -- " + novemberTransport.getEventDate() + " " + time_period);
                final String nameURI = uri + novemberTransport.getEventDate() + "/" + time_period;
                String res = restTemplate.getForObject(nameURI, String.class);
                //System.out.println("RES -- " + res);

                long prev_users = 0;
                if(dateUsers.containsKey(novemberTransport.getEventDate()+";"+res)) prev_users = dateUsers.get(novemberTransport.getEventDate()+";"+res);
                dateUsers.put(novemberTransport.getEventDate()+";"+res, prev_users + novemberTransport.getTotalUsers());


            }
            dateUsers.forEach((k,v) -> {
                // insert into the database
                String tokens[] = k.split(";");
                DayWeather dw = new DayWeather(tokens[0], tokens[1], v);
                dayWeatherService.save(dw);
            });

            return new ResponseEntity<>(dateUsers, HttpStatus.OK);
        }

    }


    @CrossOrigin(origins = "http://localhost:8081")     // allow remote access from http://localhost:8081 [CORS]
    @GetMapping("/populate")
    ResponseEntity<?> populateController(){
        List<Transport> transports = transportService.findAll();

        if(transports.isEmpty()) return new ResponseEntity<>(ResponseEntity.notFound().build(), HttpStatus.NOT_FOUND);
        else {
            RestTemplate restTemplate = new RestTemplate();
            HashMap<String, Long> dateUsers = new HashMap<>();
            for(Transport transport:transports){

                // accepted format for time period to call api
                String time_period = String.valueOf((int)transport.getTimePeriod());
                if(time_period.length() == 1) time_period = "0" + time_period;

                // call api foreach novemberTransport and get info
                //System.out.println("CALL -- " + transport.getEventDate() + " " + time_period);
                final String nameURI = uri + transport.getEventDate() + "/" + time_period;
                String res = restTemplate.getForObject(nameURI, String.class);

                //System.out.println("RES -- " + res);
                if(res == null) continue;
                res = "" + res.trim();


                long prev_users = 0;
                if(dateUsers.containsKey(transport.getEventDate()+";"+res)) prev_users = dateUsers.get(transport.getEventDate()+";"+res);
                dateUsers.put(transport.getEventDate()+";"+res, prev_users + transport.getTotalUsers());


            }
            dateUsers.forEach((k,v) -> {
                // insert into the database
                String tokens[] = k.split(";");
                DayWeather dw = new DayWeather(tokens[0], tokens[1], v);
                dayWeatherService.save(dw);
            });

            return new ResponseEntity<>(dateUsers, HttpStatus.OK);
        }

    }



    @Autowired
    DayWeatherService dayWeatherService;

    @PersistenceContext
    EntityManager em;

    @CrossOrigin(origins = "http://localhost:8081")     // allow remote access from http://localhost:8081 [CORS]
    @GetMapping("/test")
    ResponseEntity<?> test(){
        /*
        dayWeatherService.findAllSummed();
        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("DayWeather");
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DayWeather> q = cb.createQuery(DayWeather.class);
        Root<DayWeather> dw = q.from(DayWeather.class);

        q.multiselect(dw.get("event_date"), dw.get("weather_class"));
        q.where(cb.equal(dw.get("weather_class"), "Clear"));
        */
        Query q = em.createNativeQuery("SELECT max_per_weather.weather_class, max_per_weather.total, event_date FROM\n" +
                "(SELECT MAX(total_users) as total, weather_class FROM day_weather dw1 WHERE event_date LIKE '____08__' GROUP BY weather_class) max_per_weather\n" +
                "JOIN day_weather\n" +
                "ON max_per_weather.total = day_weather.total_users AND max_per_weather.weather_class = day_weather.weather_class");



        return new ResponseEntity<>(transportService.findAll(), HttpStatus.OK);


    }


}
