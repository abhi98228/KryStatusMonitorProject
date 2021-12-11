package com.kry.api.statusmonitorproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class Health {
    private String status;
    private Instant time;
}
