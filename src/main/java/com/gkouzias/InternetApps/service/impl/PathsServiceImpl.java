package com.gkouzias.InternetApps.service.impl;

import com.gkouzias.InternetApps.domain.Path;
import com.gkouzias.InternetApps.model.PathDTO;
import com.gkouzias.InternetApps.model.PointDTO;
import com.gkouzias.InternetApps.model.StopDTO;
import com.gkouzias.InternetApps.repository.PathsRepository;
import com.gkouzias.InternetApps.service.PathsService;
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

    // foreach path, create and set it's DTO and calculate it's polyline ditsance and coords
    @Override
    public PathDTO calculate_coords_distance(Path path) {
        PathDTO pathDTO = new PathDTO();
        pathDTO.setId(path.getId());
        pathDTO.setOrigin_stop(new StopDTO(path.getOrigin_stop()));             // next_arrival and weather will be set
        pathDTO.setDestination_stop(new StopDTO(path.getDestination_stop()));
        pathDTO.setName(path.getName());


        // calculate distance
        // -------------------------------------------------------------------------------------------------------------
        String coords[] = path.getPolyline().split(" "); // split polyline string given using space
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