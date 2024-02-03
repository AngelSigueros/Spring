package com.sas.sm_app.controller;

import com.sas.sm_app.domain.Hobby;
import com.sas.sm_app.model.UsuarioDTO;
import com.sas.sm_app.repos.HobbyRepository;
import com.sas.sm_app.service.UsuarioService;
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
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final HobbyRepository hobbyRepository;

    public UsuarioController(final UsuarioService usuarioService,
            final HobbyRepository hobbyRepository) {
        this.usuarioService = usuarioService;
        this.hobbyRepository = hobbyRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("usuarioHobbyHobbiesValues", hobbyRepository.findAll(Sort.by("idHobby"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Hobby::getIdHobby, Hobby::getNombre)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "usuario/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("usuario") final UsuarioDTO usuarioDTO) {
        return "usuario/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("usuario") @Valid final UsuarioDTO usuarioDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "usuario/add";
        }
        usuarioService.create(usuarioDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("usuario.create.success"));
        return "redirect:/usuarios";
    }

    @GetMapping("/edit/{idUser}")
    public String edit(@PathVariable(name = "idUser") final Integer idUser, final Model model) {
        model.addAttribute("usuario", usuarioService.get(idUser));
        return "usuario/edit";
    }

    @PostMapping("/edit/{idUser}")
    public String edit(@PathVariable(name = "idUser") final Integer idUser,
            @ModelAttribute("usuario") @Valid final UsuarioDTO usuarioDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "usuario/edit";
        }
        usuarioService.update(idUser, usuarioDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("usuario.update.success"));
        return "redirect:/usuarios";
    }

    @PostMapping("/delete/{idUser}")
    public String delete(@PathVariable(name = "idUser") final Integer idUser,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = usuarioService.getReferencedWarning(idUser);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            usuarioService.delete(idUser);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("usuario.delete.success"));
        }
        return "redirect:/usuarios";
    }

}
