package com.sas.sm_app.controller;

import com.sas.sm_app.domain.Usuario;
import com.sas.sm_app.model.HistoriaDTO;
import com.sas.sm_app.repos.UsuarioRepository;
import com.sas.sm_app.service.HistoriaService;
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
@RequestMapping("/historias")
public class HistoriaController {

    private final HistoriaService historiaService;
    private final UsuarioRepository usuarioRepository;

    public HistoriaController(final HistoriaService historiaService,
            final UsuarioRepository usuarioRepository) {
        this.historiaService = historiaService;
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
        model.addAttribute("historias", historiaService.findAll());
        return "historia/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("historia") final HistoriaDTO historiaDTO) {
        return "historia/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("historia") @Valid final HistoriaDTO historiaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "historia/add";
        }
        historiaService.create(historiaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("historia.create.success"));
        return "redirect:/historias";
    }

    @GetMapping("/edit/{idStory}")
    public String edit(@PathVariable(name = "idStory") final Integer idStory, final Model model) {
        model.addAttribute("historia", historiaService.get(idStory));
        return "historia/edit";
    }

    @PostMapping("/edit/{idStory}")
    public String edit(@PathVariable(name = "idStory") final Integer idStory,
            @ModelAttribute("historia") @Valid final HistoriaDTO historiaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "historia/edit";
        }
        historiaService.update(idStory, historiaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("historia.update.success"));
        return "redirect:/historias";
    }

    @PostMapping("/delete/{idStory}")
    public String delete(@PathVariable(name = "idStory") final Integer idStory,
            final RedirectAttributes redirectAttributes) {
        historiaService.delete(idStory);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("historia.delete.success"));
        return "redirect:/historias";
    }

}
