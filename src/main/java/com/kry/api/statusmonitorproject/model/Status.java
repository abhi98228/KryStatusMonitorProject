package com.kry.api.statusmonitorproject.model;

import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return name.equals(status.getName()) || url.equals(status.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,url);
    }
}
