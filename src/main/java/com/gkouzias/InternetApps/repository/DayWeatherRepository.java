package com.gkouzias.InternetApps.repository;

import com.gkouzias.InternetApps.domain.DayWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Tuple;
import java.util.List;

public interface DayWeatherRepository extends JpaRepository<DayWeather, Integer> {

    @Query(value="SELECT MAX(dw.totalUsers), dw.weatherClass FROM DayWeather dw GROUP BY dw.weatherClass")
    List<?> findAllSummed();

    @Query(value = "SELECT max_per_weather.weather_class, max_per_weather.total, event_date FROM " +
            "(SELECT MAX(total_users) as total, weather_class FROM day_weather dw1 WHERE event_date LIKE ?1 OR event_date LIKE ?2 GROUP BY weather_class) max_per_weather " +
            "JOIN day_weather " +
            "ON max_per_weather.total = day_weather.total_users AND max_per_weather.weather_class = day_weather.weather_class", nativeQuery = true)
    List<Tuple> findMaxPerWeatherDay(String month1, String month2);



}
