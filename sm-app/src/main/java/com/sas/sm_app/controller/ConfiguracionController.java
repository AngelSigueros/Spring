package com.sas.sm_app.controller;

import com.sas.sm_app.domain.Usuario;
import com.sas.sm_app.model.ConfiguracionDTO;
import com.sas.sm_app.repos.UsuarioRepository;
import com.sas.sm_app.service.ConfiguracionService;
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
@RequestMapping("/configuracions")
public class ConfiguracionController {

    private final ConfiguracionService configuracionService;
    private final UsuarioRepository usuarioRepository;

    public ConfiguracionController(final ConfiguracionService configuracionService,
            final UsuarioRepository usuarioRepository) {
        this.configuracionService = configuracionService;
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
        model.addAttribute("configuracions", configuracionService.findAll());
        return "configuracion/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("configuracion") final ConfiguracionDTO configuracionDTO) {
        return "configuracion/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("configuracion") @Valid final ConfiguracionDTO configuracionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "configuracion/add";
        }
        configuracionService.create(configuracionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("configuracion.create.success"));
        return "redirect:/configuracions";
    }

    @GetMapping("/edit/{idConfig}")
    public String edit(@PathVariable(name = "idConfig") final Integer idConfig, final Model model) {
        model.addAttribute("configuracion", configuracionService.get(idConfig));
        return "configuracion/edit";
    }

    @PostMapping("/edit/{idConfig}")
    public String edit(@PathVariable(name = "idConfig") final Integer idConfig,
            @ModelAttribute("configuracion") @Valid final ConfiguracionDTO configuracionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "configuracion/edit";
        }
        configuracionService.update(idConfig, configuracionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("configuracion.update.success"));
        return "redirect:/configuracions";
    }

    @PostMapping("/delete/{idConfig}")
    public String delete(@PathVariable(name = "idConfig") final Integer idConfig,
            final RedirectAttributes redirectAttributes) {
        configuracionService.delete(idConfig);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("configuracion.delete.success"));
        return "redirect:/configuracions";
    }

}
