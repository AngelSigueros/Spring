package com.sas.sm_app.controller;

import com.sas.sm_app.domain.Usuario;
import com.sas.sm_app.model.PublicacionDTO;
import com.sas.sm_app.repos.UsuarioRepository;
import com.sas.sm_app.service.PublicacionService;
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
@RequestMapping("/publicacions")
public class PublicacionController {

    private final PublicacionService publicacionService;
    private final UsuarioRepository usuarioRepository;

    public PublicacionController(final PublicacionService publicacionService,
            final UsuarioRepository usuarioRepository) {
        this.publicacionService = publicacionService;
        this.usuarioRepository = usuarioRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("userValues", usuarioRepository.findAll(Sort.by("idUser"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getIdUser, Usuario::getNombreUsuario)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("publicacions", publicacionService.findAll());
        return "publicacion/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("publicacion") final PublicacionDTO publicacionDTO) {
        return "publicacion/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("publicacion") @Valid final PublicacionDTO publicacionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "publicacion/add";
        }
        publicacionService.create(publicacionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("publicacion.create.success"));
        return "redirect:/publicacions";
    }

    @GetMapping("/edit/{idPost}")
    public String edit(@PathVariable(name = "idPost") final Integer idPost, final Model model) {
        model.addAttribute("publicacion", publicacionService.get(idPost));
        return "publicacion/edit";
    }

    @PostMapping("/edit/{idPost}")
    public String edit(@PathVariable(name = "idPost") final Integer idPost,
            @ModelAttribute("publicacion") @Valid final PublicacionDTO publicacionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "publicacion/edit";
        }
        publicacionService.update(idPost, publicacionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("publicacion.update.success"));
        return "redirect:/publicacions";
    }

    @PostMapping("/delete/{idPost}")
    public String delete(@PathVariable(name = "idPost") final Integer idPost,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = publicacionService.getReferencedWarning(idPost);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            publicacionService.delete(idPost);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("publicacion.delete.success"));
        }
        return "redirect:/publicacions";
    }

}
