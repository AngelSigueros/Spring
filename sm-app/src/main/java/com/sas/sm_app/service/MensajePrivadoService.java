package com.sas.sm_app.service;

import com.sas.sm_app.domain.MensajePrivado;
import com.sas.sm_app.domain.Usuario;
import com.sas.sm_app.model.MensajePrivadoDTO;
import com.sas.sm_app.repos.MensajePrivadoRepository;
import com.sas.sm_app.repos.UsuarioRepository;
import com.sas.sm_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MensajePrivadoService {

    private final MensajePrivadoRepository mensajePrivadoRepository;
    private final UsuarioRepository usuarioRepository;

    public MensajePrivadoService(final MensajePrivadoRepository mensajePrivadoRepository,
            final UsuarioRepository usuarioRepository) {
        this.mensajePrivadoRepository = mensajePrivadoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<MensajePrivadoDTO> findAll() {
        final List<MensajePrivado> mensajePrivadoes = mensajePrivadoRepository.findAll(Sort.by("idMensaje"));
        return mensajePrivadoes.stream()
                .map(mensajePrivado -> mapToDTO(mensajePrivado, new MensajePrivadoDTO()))
                .toList();
    }

    public MensajePrivadoDTO get(final Integer idMensaje) {
        return mensajePrivadoRepository.findById(idMensaje)
                .map(mensajePrivado -> mapToDTO(mensajePrivado, new MensajePrivadoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final MensajePrivadoDTO mensajePrivadoDTO) {
        final MensajePrivado mensajePrivado = new MensajePrivado();
        mapToEntity(mensajePrivadoDTO, mensajePrivado);
        return mensajePrivadoRepository.save(mensajePrivado).getIdMensaje();
    }

    public void update(final Integer idMensaje, final MensajePrivadoDTO mensajePrivadoDTO) {
        final MensajePrivado mensajePrivado = mensajePrivadoRepository.findById(idMensaje)
                .orElseThrow(NotFoundException::new);
        mapToEntity(mensajePrivadoDTO, mensajePrivado);
        mensajePrivadoRepository.save(mensajePrivado);
    }

    public void delete(final Integer idMensaje) {
        mensajePrivadoRepository.deleteById(idMensaje);
    }

    private MensajePrivadoDTO mapToDTO(final MensajePrivado mensajePrivado,
            final MensajePrivadoDTO mensajePrivadoDTO) {
        mensajePrivadoDTO.setIdMensaje(mensajePrivado.getIdMensaje());
        mensajePrivadoDTO.setTexto(mensajePrivado.getTexto());
        mensajePrivadoDTO.setFecEnvio(mensajePrivado.getFecEnvio());
        mensajePrivadoDTO.setUsuarioEmisor(mensajePrivado.getUsuarioEmisor() == null ? null : mensajePrivado.getUsuarioEmisor().getIdUser());
        mensajePrivadoDTO.setUsuarioReceptor(mensajePrivado.getUsuarioReceptor() == null ? null : mensajePrivado.getUsuarioReceptor().getIdUser());
        return mensajePrivadoDTO;
    }

    private MensajePrivado mapToEntity(final MensajePrivadoDTO mensajePrivadoDTO,
            final MensajePrivado mensajePrivado) {
        mensajePrivado.setTexto(mensajePrivadoDTO.getTexto());
        mensajePrivado.setFecEnvio(mensajePrivadoDTO.getFecEnvio());
        final Usuario usuarioEmisor = mensajePrivadoDTO.getUsuarioEmisor() == null ? null : usuarioRepository.findById(mensajePrivadoDTO.getUsuarioEmisor())
                .orElseThrow(() -> new NotFoundException("usuarioEmisor not found"));
        mensajePrivado.setUsuarioEmisor(usuarioEmisor);
        final Usuario usuarioReceptor = mensajePrivadoDTO.getUsuarioReceptor() == null ? null : usuarioRepository.findById(mensajePrivadoDTO.getUsuarioReceptor())
                .orElseThrow(() -> new NotFoundException("usuarioReceptor not found"));
        mensajePrivado.setUsuarioReceptor(usuarioReceptor);
        return mensajePrivado;
    }

}
