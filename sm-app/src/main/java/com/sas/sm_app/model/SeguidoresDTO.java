package com.sas.sm_app.model;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class SeguidoresDTO {

    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime fecSeguimiento;

    @NotNull
    private Integer seguidor;

    @NotNull
    private Integer seguido;

}
