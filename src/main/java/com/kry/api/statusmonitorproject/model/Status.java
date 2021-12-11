package com.kry.api.statusmonitorproject.model;

import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class Status {
    private String url;
    private String name;
    private Instant creationTime;
    private List<Health> healthList = new ArrayList<>();

    public Status(String url, String name) {
        this.url = url;
        this.name = name;
        this.creationTime = Instant.now();
    }
}
