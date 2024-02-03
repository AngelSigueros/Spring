package com.sas.sm_app.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ConfiguracionDTO {

    private Integer idConfig;

    @NotNull
    private Boolean notificaciones;

    @NotNull
    private Boolean mostrarEdad;

    @NotNull
    private Integer user;

}
