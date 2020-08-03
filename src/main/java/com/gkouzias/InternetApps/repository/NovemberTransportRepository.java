package com.gkouzias.InternetApps.repository;


import com.gkouzias.InternetApps.domain.NovemberTransport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NovemberTransportRepository extends JpaRepository<NovemberTransport,Integer> {
    List<NovemberTransport> findAll();

}
