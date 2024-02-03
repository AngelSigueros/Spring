package com.sas.sm_app.controller;

import com.sas.sm_app.domain.Usuario;
import com.sas.sm_app.model.MensajePrivadoDTO;
import com.sas.sm_app.repos.UsuarioRepository;
import com.sas.sm_app.service.MensajePrivadoService;
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
@RequestMapping("/mensajePrivados")
public class MensajePrivadoController {

    private final MensajePrivadoService mensajePrivadoService;
    private final UsuarioRepository usuarioRepository;

    public MensajePrivadoController(final MensajePrivadoService mensajePrivadoService,
            final UsuarioRepository usuarioRepository) {
        this.mensajePrivadoService = mensajePrivadoService;
        this.usuarioRepository = usuarioRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("usuarioEmisorValues", usuarioRepository.findAll(Sort.by("idUser"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getIdUser, Usuario::getNombreUsuario)));
        model.addAttribute("usuarioReceptorValues", usuarioRepository.findAll(Sort.by("idUser"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getIdUser, Usuario::getNombreUsuario)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("mensajePrivadoes", mensajePrivadoService.findAll());
        return "mensajePrivado/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("mensajePrivado") final MensajePrivadoDTO mensajePrivadoDTO) {
        return "mensajePrivado/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("mensajePrivado") @Valid final MensajePrivadoDTO mensajePrivadoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "mensajePrivado/add";
        }
        mensajePrivadoService.create(mensajePrivadoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("mensajePrivado.create.success"));
        return "redirect:/mensajePrivados";
    }

    @GetMapping("/edit/{idMensaje}")
    public String edit(@PathVariable(name = "idMensaje") final Integer idMensaje,
            final Model model) {
        model.addAttribute("mensajePrivado", mensajePrivadoService.get(idMensaje));
        return "mensajePrivado/edit";
    }

    @PostMapping("/edit/{idMensaje}")
    public String edit(@PathVariable(name = "idMensaje") final Integer idMensaje,
            @ModelAttribute("mensajePrivado") @Valid final MensajePrivadoDTO mensajePrivadoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "mensajePrivado/edit";
        }
        mensajePrivadoService.update(idMensaje, mensajePrivadoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("mensajePrivado.update.success"));
        return "redirect:/mensajePrivados";
    }

    @PostMapping("/delete/{idMensaje}")
    public String delete(@PathVariable(name = "idMensaje") final Integer idMensaje,
            final RedirectAttributes redirectAttributes) {
        mensajePrivadoService.delete(idMensaje);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("mensajePrivado.delete.success"));
        return "redirect:/mensajePrivados";
    }

}
