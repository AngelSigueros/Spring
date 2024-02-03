package com.sas.sm_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Usuarios")
@Getter
@Setter
public class Usuario {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    @Column(nullable = false, length = 50)
    private String nombreUsuario;

    @Column(nullable = false, length = 50)
    private String contrasena;

    @Column(nullable = false, length = 100)
    private String nombreCompleto;

    @Column
    private LocalDate fechaNacimiento;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String telefono;

    @Column
    private OffsetDateTime fecRegistro;

    @Column(length = 100)
    private String fotoPerfil;

    @Column(nullable = false)
    private Boolean privada;

    @OneToMany(mappedBy = "user")
    private Set<Publicacion> userPublicacions;

    @OneToMany(mappedBy = "user")
    private Set<Comentario> userComentarios;

    @OneToMany(mappedBy = "user")
    private Set<Historia> userHistorias;

    @OneToMany(mappedBy = "usuarioEmisor")
    private Set<MensajePrivado> usuarioEmisorMensajePrivadoes;

    @OneToMany(mappedBy = "usuarioReceptor")
    private Set<MensajePrivado> usuarioReceptorMensajePrivadoes;

    @OneToMany(mappedBy = "user")
    private Set<Configuracion> userConfiguracions;

    @OneToMany(mappedBy = "seguidor")
    private Set<Seguidores> seguidorSeguidoreses;

    @OneToMany(mappedBy = "seguido")
    private Set<Seguidores> seguidoSeguidoreses;

    @OneToMany(mappedBy = "solicitante")
    private Set<Solicitudes> solicitanteSolicitudeses;

    @OneToMany(mappedBy = "receptor")
    private Set<Solicitudes> receptorSolicitudeses;

    @ManyToMany
    @JoinTable(
            name = "UsuarioHobbies",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idHobby")
    )
    private Set<Hobby> usuarioHobbyHobbies;

}
