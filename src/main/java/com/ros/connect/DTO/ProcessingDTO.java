package com.ros.connect.DTO;

import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Setter;
import lombok.Getter;

@JsonSerialize
public class ProcessingDTO {
    @Getter
    @Setter
    private UUID id;

    @Getter
    @Setter
    private String value;

    public ProcessingDTO(UUID id, String value) {
        this.id = id;
        this.value = value;
    }
}
