package com.sas.sm_app.controller;

import com.sas.sm_app.domain.Publicacion;
import com.sas.sm_app.domain.Usuario;
import com.sas.sm_app.model.ComentarioDTO;
import com.sas.sm_app.repos.PublicacionRepository;
import com.sas.sm_app.repos.UsuarioRepository;
import com.sas.sm_app.service.ComentarioService;
import com.sas.sm_app.util.CustomCollectors;
import com.sas.sm_app.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;
    private final UsuarioRepository usuarioRepository;
    private final PublicacionRepository publicacionRepository;

    public ComentarioController(final ComentarioService comentarioService,
            final UsuarioRepository usuarioRepository,
            final PublicacionRepository publicacionRepository) {
        this.comentarioService = comentarioService;
        this.usuarioRepository = usuarioRepository;
        this.publicacionRepository = publicacionRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("userValues", usuarioRepository.findAll(Sort.by("idUser"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getIdUser, Usuario::getNombreUsuario)));
        model.addAttribute("postValues", publicacionRepository.findAll(Sort.by("idPost"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Publicacion::getIdPost, Publicacion::getTexto)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("comentarios", comentarioService.findAll());
        return "comentario/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("comentario") final ComentarioDTO comentarioDTO) {
        return "comentario/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("comentario") @Valid final ComentarioDTO comentarioDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "comentario/add";
        }
        comentarioService.create(comentarioDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("comentario.create.success"));
        return "redirect:/comentarios";
    }

    @GetMapping("/edit/{idComment}")
    public String edit(@PathVariable(name = "idComment") final Integer idComment,
            final Model model) {
        model.addAttribute("comentario", comentarioService.get(idComment));
        return "comentario/edit";
    }

    @PostMapping("/edit/{idComment}")
    public String edit(@PathVariable(name = "idComment") final Integer idComment,
            @ModelAttribute("comentario") @Valid final ComentarioDTO comentarioDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "comentario/edit";
        }
        comentarioService.update(idComment, comentarioDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("comentario.update.success"));
        return "redirect:/comentarios";
    }

    @PostMapping("/delete/{idComment}")
    public String delete(@PathVariable(name = "idComment") final Integer idComment,
            final RedirectAttributes redirectAttributes) {
        comentarioService.delete(idComment);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("comentario.delete.success"));
        return "redirect:/comentarios";
    }

}
