package com.sas.sm_app.service;

import com.sas.sm_app.domain.Configuracion;
import com.sas.sm_app.domain.Usuario;
import com.sas.sm_app.model.ConfiguracionDTO;
import com.sas.sm_app.repos.ConfiguracionRepository;
import com.sas.sm_app.repos.UsuarioRepository;
import com.sas.sm_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ConfiguracionService {

    private final ConfiguracionRepository configuracionRepository;
    private final UsuarioRepository usuarioRepository;

    public ConfiguracionService(final ConfiguracionRepository configuracionRepository,
            final UsuarioRepository usuarioRepository) {
        this.configuracionRepository = configuracionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<ConfiguracionDTO> findAll() {
        final List<Configuracion> configuracions = configuracionRepository.findAll(Sort.by("idConfig"));
        return configuracions.stream()
                .map(configuracion -> mapToDTO(configuracion, new ConfiguracionDTO()))
                .toList();
    }

    public ConfiguracionDTO get(final Integer idConfig) {
        return configuracionRepository.findById(idConfig)
                .map(configuracion -> mapToDTO(configuracion, new ConfiguracionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ConfiguracionDTO configuracionDTO) {
        final Configuracion configuracion = new Configuracion();
        mapToEntity(configuracionDTO, configuracion);
        return configuracionRepository.save(configuracion).getIdConfig();
    }

    public void update(final Integer idConfig, final ConfiguracionDTO configuracionDTO) {
        final Configuracion configuracion = configuracionRepository.findById(idConfig)
                .orElseThrow(NotFoundException::new);
        mapToEntity(configuracionDTO, configuracion);
        configuracionRepository.save(configuracion);
    }

    public void delete(final Integer idConfig) {
        configuracionRepository.deleteById(idConfig);
    }

    private ConfiguracionDTO mapToDTO(final Configuracion configuracion,
            final ConfiguracionDTO configuracionDTO) {
        configuracionDTO.setIdConfig(configuracion.getIdConfig());
        configuracionDTO.setNotificaciones(configuracion.getNotificaciones());
        configuracionDTO.setMostrarEdad(configuracion.getMostrarEdad());
        configuracionDTO.setUser(configuracion.getUser() == null ? null : configuracion.getUser().getIdUser());
        return configuracionDTO;
    }

    private Configuracion mapToEntity(final ConfiguracionDTO configuracionDTO,
            final Configuracion configuracion) {
        configuracion.setNotificaciones(configuracionDTO.getNotificaciones());
        configuracion.setMostrarEdad(configuracionDTO.getMostrarEdad());
        final Usuario user = configuracionDTO.getUser() == null ? null : usuarioRepository.findById(configuracionDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        configuracion.setUser(user);
        return configuracion;
    }

}
