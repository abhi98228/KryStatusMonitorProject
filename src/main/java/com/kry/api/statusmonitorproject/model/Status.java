package com.kry.api.statusmonitorproject.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Status {
    private String url;
    private String name;
    private List<Health> healthList = new ArrayList<>();

    public Status(String url, String name) {
        this.url = url;
        this.name = name;
    }
}
