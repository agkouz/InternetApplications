package com.gkouzias.InternetApps.domain.DayWeatherId;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class DayWeatherId implements Serializable {
    private String eventDate;
    private String weatherClass;

}
