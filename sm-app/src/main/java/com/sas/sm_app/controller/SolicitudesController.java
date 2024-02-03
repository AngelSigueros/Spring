package com.sas.sm_app.controller;

import com.sas.sm_app.domain.Usuario;
import com.sas.sm_app.model.SolicitudesDTO;
import com.sas.sm_app.repos.UsuarioRepository;
import com.sas.sm_app.service.SolicitudesService;
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
@RequestMapping("/solicitudess")
public class SolicitudesController {

    private final SolicitudesService solicitudesService;
    private final UsuarioRepository usuarioRepository;

    public SolicitudesController(final SolicitudesService solicitudesService,
            final UsuarioRepository usuarioRepository) {
        this.solicitudesService = solicitudesService;
        this.usuarioRepository = usuarioRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("solicitanteValues", usuarioRepository.findAll(Sort.by("idUser"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getIdUser, Usuario::getNombreUsuario)));
        model.addAttribute("receptorValues", usuarioRepository.findAll(Sort.by("idUser"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getIdUser, Usuario::getNombreUsuario)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("solicitudeses", solicitudesService.findAll());
        return "solicitudes/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("solicitudes") final SolicitudesDTO solicitudesDTO) {
        return "solicitudes/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("solicitudes") @Valid final SolicitudesDTO solicitudesDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "solicitudes/add";
        }
        solicitudesService.create(solicitudesDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("solicitudes.create.success"));
        return "redirect:/solicitudess";
    }

    @GetMapping("/edit/{idRequest}")
    public String edit(@PathVariable(name = "idRequest") final Integer idRequest,
            final Model model) {
        model.addAttribute("solicitudes", solicitudesService.get(idRequest));
        return "solicitudes/edit";
    }

    @PostMapping("/edit/{idRequest}")
    public String edit(@PathVariable(name = "idRequest") final Integer idRequest,
            @ModelAttribute("solicitudes") @Valid final SolicitudesDTO solicitudesDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "solicitudes/edit";
        }
        solicitudesService.update(idRequest, solicitudesDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("solicitudes.update.success"));
        return "redirect:/solicitudess";
    }

    @PostMapping("/delete/{idRequest}")
    public String delete(@PathVariable(name = "idRequest") final Integer idRequest,
            final RedirectAttributes redirectAttributes) {
        solicitudesService.delete(idRequest);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("solicitudes.delete.success"));
        return "redirect:/solicitudess";
    }

}
