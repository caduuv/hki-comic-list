package br.edu.ifce.caduuv.hki.controller;

import br.edu.ifce.caduuv.hki.model.HQ;
import br.edu.ifce.caduuv.hki.repository.HQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HQDetailController {
    @Autowired
    private HQRepository hqRepository;

    @GetMapping("/hq/{id}")
    public String hqDetail(@PathVariable Long id, Model model) {
        HQ hq = hqRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("HQ inválida"));
        model.addAttribute("hq", hq);
        return "hq/detail";
    }
}
