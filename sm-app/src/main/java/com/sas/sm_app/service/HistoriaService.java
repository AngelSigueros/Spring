package com.sas.sm_app.service;

import com.sas.sm_app.domain.Historia;
import com.sas.sm_app.domain.Usuario;
import com.sas.sm_app.model.HistoriaDTO;
import com.sas.sm_app.repos.HistoriaRepository;
import com.sas.sm_app.repos.UsuarioRepository;
import com.sas.sm_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class HistoriaService {

    private final HistoriaRepository historiaRepository;
    private final UsuarioRepository usuarioRepository;

    public HistoriaService(final HistoriaRepository historiaRepository,
            final UsuarioRepository usuarioRepository) {
        this.historiaRepository = historiaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<HistoriaDTO> findAll() {
        final List<Historia> historias = historiaRepository.findAll(Sort.by("idStory"));
        return historias.stream()
                .map(historia -> mapToDTO(historia, new HistoriaDTO()))
                .toList();
    }

    public HistoriaDTO get(final Integer idStory) {
        return historiaRepository.findById(idStory)
                .map(historia -> mapToDTO(historia, new HistoriaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final HistoriaDTO historiaDTO) {
        final Historia historia = new Historia();
        mapToEntity(historiaDTO, historia);
        return historiaRepository.save(historia).getIdStory();
    }

    public void update(final Integer idStory, final HistoriaDTO historiaDTO) {
        final Historia historia = historiaRepository.findById(idStory)
                .orElseThrow(NotFoundException::new);
        mapToEntity(historiaDTO, historia);
        historiaRepository.save(historia);
    }

    public void delete(final Integer idStory) {
        historiaRepository.deleteById(idStory);
    }

    private HistoriaDTO mapToDTO(final Historia historia, final HistoriaDTO historiaDTO) {
        historiaDTO.setIdStory(historia.getIdStory());
        historiaDTO.setTexto(historia.getTexto());
        historiaDTO.setFecCreacion(historia.getFecCreacion());
        historiaDTO.setUser(historia.getUser() == null ? null : historia.getUser().getIdUser());
        return historiaDTO;
    }

    private Historia mapToEntity(final HistoriaDTO historiaDTO, final Historia historia) {
        historia.setTexto(historiaDTO.getTexto());
        historia.setFecCreacion(historiaDTO.getFecCreacion());
        final Usuario user = historiaDTO.getUser() == null ? null : usuarioRepository.findById(historiaDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        historia.setUser(user);
        return historia;
    }

}
