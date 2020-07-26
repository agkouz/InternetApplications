package com.gkouzias.InternetApps.Errors;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

}
