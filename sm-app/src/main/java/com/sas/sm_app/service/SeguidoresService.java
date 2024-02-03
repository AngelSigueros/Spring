package com.sas.sm_app.service;

import com.sas.sm_app.domain.Seguidores;
import com.sas.sm_app.domain.Usuario;
import com.sas.sm_app.model.SeguidoresDTO;
import com.sas.sm_app.repos.SeguidoresRepository;
import com.sas.sm_app.repos.UsuarioRepository;
import com.sas.sm_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SeguidoresService {

    private final SeguidoresRepository seguidoresRepository;
    private final UsuarioRepository usuarioRepository;

    public SeguidoresService(final SeguidoresRepository seguidoresRepository,
            final UsuarioRepository usuarioRepository) {
        this.seguidoresRepository = seguidoresRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<SeguidoresDTO> findAll() {
        final List<Seguidores> seguidoreses = seguidoresRepository.findAll(Sort.by("id"));
        return seguidoreses.stream()
                .map(seguidores -> mapToDTO(seguidores, new SeguidoresDTO()))
                .toList();
    }

    public SeguidoresDTO get(final Long id) {
        return seguidoresRepository.findById(id)
                .map(seguidores -> mapToDTO(seguidores, new SeguidoresDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SeguidoresDTO seguidoresDTO) {
        final Seguidores seguidores = new Seguidores();
        mapToEntity(seguidoresDTO, seguidores);
        return seguidoresRepository.save(seguidores).getId();
    }

    public void update(final Long id, final SeguidoresDTO seguidoresDTO) {
        final Seguidores seguidores = seguidoresRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(seguidoresDTO, seguidores);
        seguidoresRepository.save(seguidores);
    }

    public void delete(final Long id) {
        seguidoresRepository.deleteById(id);
    }

    private SeguidoresDTO mapToDTO(final Seguidores seguidores, final SeguidoresDTO seguidoresDTO) {
        seguidoresDTO.setId(seguidores.getId());
        seguidoresDTO.setFecSeguimiento(seguidores.getFecSeguimiento());
        seguidoresDTO.setSeguidor(seguidores.getSeguidor() == null ? null : seguidores.getSeguidor().getIdUser());
        seguidoresDTO.setSeguido(seguidores.getSeguido() == null ? null : seguidores.getSeguido().getIdUser());
        return seguidoresDTO;
    }

    private Seguidores mapToEntity(final SeguidoresDTO seguidoresDTO, final Seguidores seguidores) {
        seguidores.setFecSeguimiento(seguidoresDTO.getFecSeguimiento());
        final Usuario seguidor = seguidoresDTO.getSeguidor() == null ? null : usuarioRepository.findById(seguidoresDTO.getSeguidor())
                .orElseThrow(() -> new NotFoundException("seguidor not found"));
        seguidores.setSeguidor(seguidor);
        final Usuario seguido = seguidoresDTO.getSeguido() == null ? null : usuarioRepository.findById(seguidoresDTO.getSeguido())
                .orElseThrow(() -> new NotFoundException("seguido not found"));
        seguidores.setSeguido(seguido);
        return seguidores;
    }

}
