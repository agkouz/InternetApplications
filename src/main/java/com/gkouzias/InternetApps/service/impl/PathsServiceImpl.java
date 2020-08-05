package com.gkouzias.InternetApps.service.impl;

import com.gkouzias.InternetApps.domain.Path;
import com.gkouzias.InternetApps.model.PathDTO;
import com.gkouzias.InternetApps.model.PointDTO;
import com.gkouzias.InternetApps.model.StopDTO;
import com.gkouzias.InternetApps.repository.PathsRepository;
import com.gkouzias.InternetApps.service.PathsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PathsServiceImpl implements PathsService {
    @Autowired
    PathsRepository pathsRepository;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public List<Path> findAll() {
        return pathsRepository.findAll();
    }

    @Override
    public PathDTO calculate_coords_distance(Path path) {
        ModelMapper m = new ModelMapper();
        m.getConfiguration().setAmbiguityIgnored(true);

        PathDTO pathDTO = new PathDTO();
        pathDTO.setId(path.getId());
        pathDTO.setDestination_stop(m.map(path.getDestination_stop(), StopDTO.class));
        pathDTO.setOrigin_stop(m.map(path.getOrigin_stop(), StopDTO.class));
        pathDTO.setName(path.getName());


        // set next arrival and weather for pathDTO
        pathDTO.getOrigin_stop().setNext_arrival(path.getOrigin_stop().getArrival().getLast_arrival().plusMinutes(path.getOrigin_stop().getArrival().getIn())); // calculate next arrival)
        pathDTO.getOrigin_stop().setWeather(path.getOrigin_stop().getWeatherCondition().getWeather_main());


        // calculate distance
        // -------------------------------------------------------------------------------------------------------------
        String coords[] = path.getPolyline().split(" "); // split polyline string given using space
        double total_distance = 0;   // init total distance
        double prev_lat = path.getOrigin_stop().getLat();  // init starting point
        double prev_lon = path.getOrigin_stop().getLon();

        for(String coord:coords){
            String num_tokens[] = coord.split(",");
            if(num_tokens.length > 1) {
                pathDTO.setTotal_distance(pathDTO.getTotal_distance() + calc_dst(prev_lat, prev_lon, Double.parseDouble(num_tokens[1]), Double.parseDouble(num_tokens[0])));
                prev_lat = Double.parseDouble(num_tokens[1]);
                prev_lon = Double.parseDouble(num_tokens[0]);
                pathDTO.getCoordinates().add(new PointDTO(prev_lat, prev_lon));
            }
        }
        // -------------------------------------------------------------------------------------------------------------

        return pathDTO;
    }

    /**
     * lat1, lon1 Start point lat2, lon2 End point
     * @returns Distance in Meters
     */
    private double calc_dst(double lat1, double lon1, double lat2, double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        return distance;
       // this.total_distance += distance;
    }
}