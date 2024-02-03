package com.sas.sm_app.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class HobbyDTO {

    private Integer idHobby;

    @NotNull
    @Size(max = 50)
    private String nombre;

    @Size(max = 200)
    private String descripcion;

}
