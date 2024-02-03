package com.sas.sm_app.controller;

import com.sas.sm_app.domain.Usuario;
import com.sas.sm_app.model.SeguidoresDTO;
import com.sas.sm_app.repos.UsuarioRepository;
import com.sas.sm_app.service.SeguidoresService;
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
@RequestMapping("/seguidoress")
public class SeguidoresController {

    private final SeguidoresService seguidoresService;
    private final UsuarioRepository usuarioRepository;

    public SeguidoresController(final SeguidoresService seguidoresService,
            final UsuarioRepository usuarioRepository) {
        this.seguidoresService = seguidoresService;
        this.usuarioRepository = usuarioRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("seguidorValues", usuarioRepository.findAll(Sort.by("idUser"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getIdUser, Usuario::getNombreUsuario)));
        model.addAttribute("seguidoValues", usuarioRepository.findAll(Sort.by("idUser"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getIdUser, Usuario::getNombreUsuario)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("seguidoreses", seguidoresService.findAll());
        return "seguidores/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("seguidores") final SeguidoresDTO seguidoresDTO) {
        return "seguidores/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("seguidores") @Valid final SeguidoresDTO seguidoresDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "seguidores/add";
        }
        seguidoresService.create(seguidoresDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("seguidores.create.success"));
        return "redirect:/seguidoress";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("seguidores", seguidoresService.get(id));
        return "seguidores/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("seguidores") @Valid final SeguidoresDTO seguidoresDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "seguidores/edit";
        }
        seguidoresService.update(id, seguidoresDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("seguidores.update.success"));
        return "redirect:/seguidoress";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        seguidoresService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("seguidores.delete.success"));
        return "redirect:/seguidoress";
    }

}
