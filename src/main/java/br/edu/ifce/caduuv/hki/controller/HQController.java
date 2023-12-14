package br.edu.ifce.caduuv.hki.controller;

import br.edu.ifce.caduuv.hki.model.HQ;
import br.edu.ifce.caduuv.hki.model.Usuario;
import br.edu.ifce.caduuv.hki.repository.HQRepository;
import br.edu.ifce.caduuv.hki.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class HQController {
    @Autowired
    private HQRepository hqRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public String redirect() {
        return "redirect:hq/list";
    }

    @GetMapping("/hq/list")
    public String listHQs(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        List<HQ> hqs = hqRepository.findByUsuario(usuario.get());

        model.addAttribute("hqs", hqs);
        model.addAttribute("username", username);
        return "hq/list";
    }

    @GetMapping("/hq/add")
    public String addHQForm(Model model) {
        model.addAttribute("hq", new HQ());
        return "hq/add";
    }

    @PostMapping("/hq/add")
    public String addHQ(@ModelAttribute HQ hq) {
        // Obtém o usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        hq.setUsuario(usuario);
        hqRepository.save(hq);
        return "redirect:/hq/list";
    }

    @GetMapping("/hq/delete/{id}")
    public String deleteHQ(@PathVariable Long id) {
        hqRepository.deleteById(id);
        return "redirect:/hq/list";
    }

    @GetMapping("/hq/toggle-read/{id}")
    public String toggleRead(@PathVariable Long id) {
        Optional<HQ> optionalHQ = hqRepository.findById(id);

        if (optionalHQ.isPresent()) {
            HQ hq = optionalHQ.get();
            hq.setRead(!hq.isRead());
            hqRepository.save(hq);

            if (hq.isRead()) {
                return "redirect:/hq/review/" + hq.getId();
            }
        }

        return "redirect:/hq/list";
    }

    @GetMapping("/hq/edit/{id}")
    public String editHQ(@PathVariable Long id, Model model) {
        Optional<HQ> optionalHQ = hqRepository.findById(id);

        if (optionalHQ.isPresent()) {
            HQ hq = optionalHQ.get();
            model.addAttribute("hq", hq);
            return "hq/edit";
        } else {
            return "redirect:/hq/list";
        }
    }

    @PostMapping("/hq/edit/{id}")
    public String updateHQ(@PathVariable Long id, @ModelAttribute("hq") @Valid HQ updatedHQ,
                           BindingResult result) {

        Optional<HQ> optionalHQ = hqRepository.findById(id);

        if (optionalHQ.isPresent()) {
            HQ hq = optionalHQ.get();

            hq.setTitle(updatedHQ.getTitle());
            hq.setAuthor(updatedHQ.getAuthor());
            hq.setReview(updatedHQ.getReview());
            hq.setRead(updatedHQ.isRead());
            hq.setSynopsis(updatedHQ.getSynopsis());

            hqRepository.save(hq);
        }

        return "redirect:/hq/list";
    }

    @GetMapping("/hq/review/{id}")
    public String addReview(@PathVariable Long id, Model model) {
        Optional<HQ> optionalHQ = hqRepository.findById(id);

        if (optionalHQ.isPresent()) {
            HQ hq = optionalHQ.get();
            model.addAttribute("hq", hq);
            return "hq/addReview";
        } else {
            return "redirect:/hq/list";
        }
    }

    @PostMapping("/hq/saveReview")
    public String saveReview(@ModelAttribute("hq") @Valid HQ hq,
                             BindingResult result) {
        Optional<HQ> optionalHQ = hqRepository.findById(hq.getId());

        if (optionalHQ.isPresent()) {
            HQ hqOriginal = optionalHQ.get();
            hqOriginal.setReview(hq.getReview());
            hqRepository.save(hqOriginal);
        }

        return "redirect:/hq/list";
    }

}

