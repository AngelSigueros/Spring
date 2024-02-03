package com.sas.sm_app.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class ComentarioDTO {

    private Integer idComment;

    @NotNull
    @Size(max = 280)
    private String texto;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime fecCreacion;

    @NotNull
    private Integer user;

    @NotNull
    private Integer post;

}
