package com.sas.sm_app.service;

import com.sas.sm_app.domain.Solicitudes;
import com.sas.sm_app.domain.Usuario;
import com.sas.sm_app.model.SolicitudesDTO;
import com.sas.sm_app.repos.SolicitudesRepository;
import com.sas.sm_app.repos.UsuarioRepository;
import com.sas.sm_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SolicitudesService {

    private final SolicitudesRepository solicitudesRepository;
    private final UsuarioRepository usuarioRepository;

    public SolicitudesService(final SolicitudesRepository solicitudesRepository,
            final UsuarioRepository usuarioRepository) {
        this.solicitudesRepository = solicitudesRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<SolicitudesDTO> findAll() {
        final List<Solicitudes> solicitudeses = solicitudesRepository.findAll(Sort.by("idRequest"));
        return solicitudeses.stream()
                .map(solicitudes -> mapToDTO(solicitudes, new SolicitudesDTO()))
                .toList();
    }

    public SolicitudesDTO get(final Integer idRequest) {
        return solicitudesRepository.findById(idRequest)
                .map(solicitudes -> mapToDTO(solicitudes, new SolicitudesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final SolicitudesDTO solicitudesDTO) {
        final Solicitudes solicitudes = new Solicitudes();
        mapToEntity(solicitudesDTO, solicitudes);
        return solicitudesRepository.save(solicitudes).getIdRequest();
    }

    public void update(final Integer idRequest, final SolicitudesDTO solicitudesDTO) {
        final Solicitudes solicitudes = solicitudesRepository.findById(idRequest)
                .orElseThrow(NotFoundException::new);
        mapToEntity(solicitudesDTO, solicitudes);
        solicitudesRepository.save(solicitudes);
    }

    public void delete(final Integer idRequest) {
        solicitudesRepository.deleteById(idRequest);
    }

    private SolicitudesDTO mapToDTO(final Solicitudes solicitudes,
            final SolicitudesDTO solicitudesDTO) {
        solicitudesDTO.setIdRequest(solicitudes.getIdRequest());
        solicitudesDTO.setFecSolicitud(solicitudes.getFecSolicitud());
        solicitudesDTO.setEstado(solicitudes.getEstado());
        solicitudesDTO.setSolicitante(solicitudes.getSolicitante() == null ? null : solicitudes.getSolicitante().getIdUser());
        solicitudesDTO.setReceptor(solicitudes.getReceptor() == null ? null : solicitudes.getReceptor().getIdUser());
        return solicitudesDTO;
    }

    private Solicitudes mapToEntity(final SolicitudesDTO solicitudesDTO,
            final Solicitudes solicitudes) {
        solicitudes.setFecSolicitud(solicitudesDTO.getFecSolicitud());
        solicitudes.setEstado(solicitudesDTO.getEstado());
        final Usuario solicitante = solicitudesDTO.getSolicitante() == null ? null : usuarioRepository.findById(solicitudesDTO.getSolicitante())
                .orElseThrow(() -> new NotFoundException("solicitante not found"));
        solicitudes.setSolicitante(solicitante);
        final Usuario receptor = solicitudesDTO.getReceptor() == null ? null : usuarioRepository.findById(solicitudesDTO.getReceptor())
                .orElseThrow(() -> new NotFoundException("receptor not found"));
        solicitudes.setReceptor(receptor);
        return solicitudes;
    }

}
