package com.sas.sm_app.service;

import com.sas.sm_app.domain.Comentario;
import com.sas.sm_app.domain.Configuracion;
import com.sas.sm_app.domain.Historia;
import com.sas.sm_app.domain.Hobby;
import com.sas.sm_app.domain.Likes;
import com.sas.sm_app.domain.MensajePrivado;
import com.sas.sm_app.domain.Publicacion;
import com.sas.sm_app.domain.Seguidores;
import com.sas.sm_app.domain.Solicitudes;
import com.sas.sm_app.domain.Usuario;
import com.sas.sm_app.model.UsuarioDTO;
import com.sas.sm_app.repos.ComentarioRepository;
import com.sas.sm_app.repos.ConfiguracionRepository;
import com.sas.sm_app.repos.HistoriaRepository;
import com.sas.sm_app.repos.HobbyRepository;
import com.sas.sm_app.repos.LikesRepository;
import com.sas.sm_app.repos.MensajePrivadoRepository;
import com.sas.sm_app.repos.PublicacionRepository;
import com.sas.sm_app.repos.SeguidoresRepository;
import com.sas.sm_app.repos.SolicitudesRepository;
import com.sas.sm_app.repos.UsuarioRepository;
import com.sas.sm_app.util.NotFoundException;
import com.sas.sm_app.util.WebUtils;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final HobbyRepository hobbyRepository;
    private final PublicacionRepository publicacionRepository;
    private final ComentarioRepository comentarioRepository;
    private final HistoriaRepository historiaRepository;
    private final MensajePrivadoRepository mensajePrivadoRepository;
    private final ConfiguracionRepository configuracionRepository;
    private final SeguidoresRepository seguidoresRepository;
    private final SolicitudesRepository solicitudesRepository;
    private final LikesRepository likesRepository;

    public UsuarioService(final UsuarioRepository usuarioRepository,
            final HobbyRepository hobbyRepository,
            final PublicacionRepository publicacionRepository,
            final ComentarioRepository comentarioRepository,
            final HistoriaRepository historiaRepository,
            final MensajePrivadoRepository mensajePrivadoRepository,
            final ConfiguracionRepository configuracionRepository,
            final SeguidoresRepository seguidoresRepository,
            final SolicitudesRepository solicitudesRepository,
            final LikesRepository likesRepository) {
        this.usuarioRepository = usuarioRepository;
        this.hobbyRepository = hobbyRepository;
        this.publicacionRepository = publicacionRepository;
        this.comentarioRepository = comentarioRepository;
        this.historiaRepository = historiaRepository;
        this.mensajePrivadoRepository = mensajePrivadoRepository;
        this.configuracionRepository = configuracionRepository;
        this.seguidoresRepository = seguidoresRepository;
        this.solicitudesRepository = solicitudesRepository;
        this.likesRepository = likesRepository;
    }

    public List<UsuarioDTO> findAll() {
        final List<Usuario> usuarios = usuarioRepository.findAll(Sort.by("idUser"));
        return usuarios.stream()
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .toList();
    }

    public UsuarioDTO get(final Integer idUser) {
        return usuarioRepository.findById(idUser)
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UsuarioDTO usuarioDTO) {
        final Usuario usuario = new Usuario();
        mapToEntity(usuarioDTO, usuario);
        return usuarioRepository.save(usuario).getIdUser();
    }

    public void update(final Integer idUser, final UsuarioDTO usuarioDTO) {
        final Usuario usuario = usuarioRepository.findById(idUser)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usuarioDTO, usuario);
        usuarioRepository.save(usuario);
    }

    public void delete(final Integer idUser) {
        usuarioRepository.deleteById(idUser);
    }

    private UsuarioDTO mapToDTO(final Usuario usuario, final UsuarioDTO usuarioDTO) {
        usuarioDTO.setIdUser(usuario.getIdUser());
        usuarioDTO.setNombreUsuario(usuario.getNombreUsuario());
        usuarioDTO.setContrasena(usuario.getContrasena());
        usuarioDTO.setNombreCompleto(usuario.getNombreCompleto());
        usuarioDTO.setFechaNacimiento(usuario.getFechaNacimiento());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setTelefono(usuario.getTelefono());
        usuarioDTO.setFecRegistro(usuario.getFecRegistro());
        usuarioDTO.setFotoPerfil(usuario.getFotoPerfil());
        usuarioDTO.setPrivada(usuario.getPrivada());
        usuarioDTO.setUsuarioHobbyHobbies(usuario.getUsuarioHobbyHobbies().stream()
                .map(hobby -> hobby.getIdHobby())
                .toList());
        return usuarioDTO;
    }

    private Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setContrasena(usuarioDTO.getContrasena());
        usuario.setNombreCompleto(usuarioDTO.getNombreCompleto());
        usuario.setFechaNacimiento(usuarioDTO.getFechaNacimiento());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setFecRegistro(usuarioDTO.getFecRegistro());
        usuario.setFotoPerfil(usuarioDTO.getFotoPerfil());
        usuario.setPrivada(usuarioDTO.getPrivada());
        final List<Hobby> usuarioHobbyHobbies = hobbyRepository.findAllById(
                usuarioDTO.getUsuarioHobbyHobbies() == null ? Collections.emptyList() : usuarioDTO.getUsuarioHobbyHobbies());
        if (usuarioHobbyHobbies.size() != (usuarioDTO.getUsuarioHobbyHobbies() == null ? 0 : usuarioDTO.getUsuarioHobbyHobbies().size())) {
            throw new NotFoundException("one of usuarioHobbyHobbies not found");
        }
        usuario.setUsuarioHobbyHobbies(usuarioHobbyHobbies.stream().collect(Collectors.toSet()));
        return usuario;
    }

    public String getReferencedWarning(final Integer idUser) {
        final Usuario usuario = usuarioRepository.findById(idUser)
                .orElseThrow(NotFoundException::new);
        final Publicacion userPublicacion = publicacionRepository.findFirstByUser(usuario);
        if (userPublicacion != null) {
            return WebUtils.getMessage("usuario.publicacion.user.referenced", userPublicacion.getIdPost());
        }
        final Comentario userComentario = comentarioRepository.findFirstByUser(usuario);
        if (userComentario != null) {
            return WebUtils.getMessage("usuario.comentario.user.referenced", userComentario.getIdComment());
        }
        final Historia userHistoria = historiaRepository.findFirstByUser(usuario);
        if (userHistoria != null) {
            return WebUtils.getMessage("usuario.historia.user.referenced", userHistoria.getIdStory());
        }
        final MensajePrivado usuarioEmisorMensajePrivado = mensajePrivadoRepository.findFirstByUsuarioEmisor(usuario);
        if (usuarioEmisorMensajePrivado != null) {
            return WebUtils.getMessage("usuario.mensajePrivado.usuarioEmisor.referenced", usuarioEmisorMensajePrivado.getIdMensaje());
        }
        final MensajePrivado usuarioReceptorMensajePrivado = mensajePrivadoRepository.findFirstByUsuarioReceptor(usuario);
        if (usuarioReceptorMensajePrivado != null) {
            return WebUtils.getMessage("usuario.mensajePrivado.usuarioReceptor.referenced", usuarioReceptorMensajePrivado.getIdMensaje());
        }
        final Configuracion userConfiguracion = configuracionRepository.findFirstByUser(usuario);
        if (userConfiguracion != null) {
            return WebUtils.getMessage("usuario.configuracion.user.referenced", userConfiguracion.getIdConfig());
        }
        final Seguidores seguidorSeguidores = seguidoresRepository.findFirstBySeguidor(usuario);
        if (seguidorSeguidores != null) {
            return WebUtils.getMessage("usuario.seguidores.seguidor.referenced", seguidorSeguidores.getId());
        }
        final Seguidores seguidoSeguidores = seguidoresRepository.findFirstBySeguido(usuario);
        if (seguidoSeguidores != null) {
            return WebUtils.getMessage("usuario.seguidores.seguido.referenced", seguidoSeguidores.getId());
        }
        final Solicitudes solicitanteSolicitudes = solicitudesRepository.findFirstBySolicitante(usuario);
        if (solicitanteSolicitudes != null) {
            return WebUtils.getMessage("usuario.solicitudes.solicitante.referenced", solicitanteSolicitudes.getIdRequest());
        }
        final Solicitudes receptorSolicitudes = solicitudesRepository.findFirstByReceptor(usuario);
        if (receptorSolicitudes != null) {
            return WebUtils.getMessage("usuario.solicitudes.receptor.referenced", receptorSolicitudes.getIdRequest());
        }
        final Likes usuarioLikes = likesRepository.findFirstByUsuario(usuario);
        if (usuarioLikes != null) {
            return WebUtils.getMessage("usuario.likes.usuario.referenced", usuarioLikes.getIdLike());
        }
        return null;
    }

}
