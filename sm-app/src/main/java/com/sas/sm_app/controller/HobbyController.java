package com.sas.sm_app.controller;

import com.sas.sm_app.model.HobbyDTO;
import com.sas.sm_app.service.HobbyService;
import com.sas.sm_app.util.WebUtils;
import jakarta.validation.Valid;
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
@RequestMapping("/hobbies")
public class HobbyController {

    private final HobbyService hobbyService;

    public HobbyController(final HobbyService hobbyService) {
        this.hobbyService = hobbyService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("hobbies", hobbyService.findAll());
        return "hobby/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("hobby") final HobbyDTO hobbyDTO) {
        return "hobby/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("hobby") @Valid final HobbyDTO hobbyDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "hobby/add";
        }
        hobbyService.create(hobbyDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("hobby.create.success"));
        return "redirect:/hobbies";
    }

    @GetMapping("/edit/{idHobby}")
    public String edit(@PathVariable(name = "idHobby") final Integer idHobby, final Model model) {
        model.addAttribute("hobby", hobbyService.get(idHobby));
        return "hobby/edit";
    }

    @PostMapping("/edit/{idHobby}")
    public String edit(@PathVariable(name = "idHobby") final Integer idHobby,
            @ModelAttribute("hobby") @Valid final HobbyDTO hobbyDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "hobby/edit";
        }
        hobbyService.update(idHobby, hobbyDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("hobby.update.success"));
        return "redirect:/hobbies";
    }

    @PostMapping("/delete/{idHobby}")
    public String delete(@PathVariable(name = "idHobby") final Integer idHobby,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = hobbyService.getReferencedWarning(idHobby);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            hobbyService.delete(idHobby);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("hobby.delete.success"));
        }
        return "redirect:/hobbies";
    }

}
