package com.sas.sm_app.controller;

import com.sas.sm_app.domain.Publicacion;
import com.sas.sm_app.domain.Usuario;
import com.sas.sm_app.model.LikesDTO;
import com.sas.sm_app.repos.PublicacionRepository;
import com.sas.sm_app.repos.UsuarioRepository;
import com.sas.sm_app.service.LikesService;
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
@RequestMapping("/likess")
public class LikesController {

    private final LikesService likesService;
    private final PublicacionRepository publicacionRepository;
    private final UsuarioRepository usuarioRepository;

    public LikesController(final LikesService likesService,
            final PublicacionRepository publicacionRepository,
            final UsuarioRepository usuarioRepository) {
        this.likesService = likesService;
        this.publicacionRepository = publicacionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("publicacionValues", publicacionRepository.findAll(Sort.by("idPost"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Publicacion::getIdPost, Publicacion::getTexto)));
        model.addAttribute("usuarioValues", usuarioRepository.findAll(Sort.by("idUser"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getIdUser, Usuario::getNombreUsuario)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("likeses", likesService.findAll());
        return "likes/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("likes") final LikesDTO likesDTO) {
        return "likes/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("likes") @Valid final LikesDTO likesDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "likes/add";
        }
        likesService.create(likesDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("likes.create.success"));
        return "redirect:/likess";
    }

    @GetMapping("/edit/{idLike}")
    public String edit(@PathVariable(name = "idLike") final Integer idLike, final Model model) {
        model.addAttribute("likes", likesService.get(idLike));
        return "likes/edit";
    }

    @PostMapping("/edit/{idLike}")
    public String edit(@PathVariable(name = "idLike") final Integer idLike,
            @ModelAttribute("likes") @Valid final LikesDTO likesDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "likes/edit";
        }
        likesService.update(idLike, likesDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("likes.update.success"));
        return "redirect:/likess";
    }

    @PostMapping("/delete/{idLike}")
    public String delete(@PathVariable(name = "idLike") final Integer idLike,
            final RedirectAttributes redirectAttributes) {
        likesService.delete(idLike);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("likes.delete.success"));
        return "redirect:/likess";
    }

}
