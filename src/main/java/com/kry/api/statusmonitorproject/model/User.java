package com.kry.api.statusmonitorproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@Document(collection = "status_DB")
public class User {
    @Id
    private int id;
    private List<Status> statuses;
}
