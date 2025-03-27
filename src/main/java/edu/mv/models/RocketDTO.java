package edu.mv.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RocketDTO {

    private int id;
    private String name;
    private String type;
}
