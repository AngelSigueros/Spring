package com.sas.sm_app.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class SolicitudesDTO {

    private Integer idRequest;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime fecSolicitud;

    @Size(max = 255)
    private String estado;

    @NotNull
    private Integer solicitante;

    @NotNull
    private Integer receptor;

}
