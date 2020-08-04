package com.gkouzias.InternetApps.repository;

import com.gkouzias.InternetApps.domain.DayWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DayWeatherRepository extends JpaRepository<DayWeather, Integer> {
    List<DayWeather> findAll();

/*
    @Query(value="SELECT new DayWeather(eventDate, weatherClass, SUM(totalUsers)) FROM DayWeather GROUP BY weatherClass, eventDate")
    List<DayWeather> findAllSummed();


    SELECT * FROM
(SELECT MAX(total_users) as total, weather_class FROM day_weather dw1 GROUP BY weather_class) max_per_weather
JOIN day_weather
ON max_per_weather.total = day_weather.total_users AND max_per_weather.weather_class = day_weather.weather_class
;
*/

    @Query(value="SELECT MAX(dw.totalUsers), dw.weatherClass FROM DayWeather dw GROUP BY dw.weatherClass")
    List<?> findAllSummed();





}
