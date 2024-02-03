package com.sas.sm_app.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class UsuarioDTO {

    private Integer idUser;

    @NotNull
    @Size(max = 50)
    private String nombreUsuario;

    @NotNull
    @Size(max = 50)
    private String contrasena;

    @NotNull
    @Size(max = 100)
    private String nombreCompleto;

    private LocalDate fechaNacimiento;

    @Size(max = 100)
    private String email;

    @Size(max = 20)
    private String telefono;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime fecRegistro;

    @Size(max = 100)
    private String fotoPerfil;

    @NotNull
    private Boolean privada;

    private List<Integer> usuarioHobbyHobbies;

}
