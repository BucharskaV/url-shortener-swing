package pl.edu.s30853.s30853tpo10.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.edu.s30853.s30853tpo10.model.Link;
import pl.edu.s30853.s30853tpo10.model.LinkDTO;
import pl.edu.s30853.s30853tpo10.model.LinkResponseDTO;
import pl.edu.s30853.s30853tpo10.services.LinkService;

import java.util.*;

@Controller
@RequestMapping("/links")
public class LinkController {
    private final LinkService linkService;
    private Locale locale;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
        this.locale = Locale.ENGLISH;
    }

    private void getLocaleFromString(String language) {
        if (language != null) {
            switch (language) {
                case "en":
                    locale = Locale.ENGLISH; break;
                case "pl":
                    locale = new Locale("pl"); break;
                case "de":
                    locale = Locale.GERMAN; break;
                default:
                    locale = Locale.ENGLISH;
            }
        }
    }

    @GetMapping("/show")
    public String showForm(@RequestParam(required = false) String lang,  Model model) {
        getLocaleFromString(lang);
        model.addAttribute("linkDTO", new LinkDTO());
        model.addAttribute("updateDTO", new LinkDTO());
        return "index";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("linkDTO") LinkDTO linkDTO, Model model) {
        LinkResponseDTO link = linkService.createLink(linkDTO);
        model.addAttribute("message", "Success! " + link.toString());
        return "index";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request,
            Model model) {
        String lang = request.getParameter("lang");
        getLocaleFromString(lang);

        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String key = fieldError.getDefaultMessage();
            if (key.startsWith("{") && key.endsWith("}")) {
                key = key.substring(1, key.length() - 1);
            }
            String localizedMessage;
            try {
                localizedMessage = bundle.getString(key);
            } catch (MissingResourceException e) {
                localizedMessage = key;
            }
            errors.put(fieldError.getField(), localizedMessage);
        });
        model.addAttribute("error", errors);
        model.addAttribute("message", "The error has occurred");
        model.addAttribute("linkDTO", new LinkDTO());

        return "index";
    }

    @GetMapping("/view")
    public String view(@RequestParam String name, @RequestParam String password, Model model) {
        Link optional = linkService.getLinkByNameLink(name);
        if(optional == null) {
            return handle_error("Link is not found", model);
        }
        if (optional.getPassword() == null) {
            return handle_error("Link should have a password to be viewed", model);
        }
        if (!optional.getPassword().equals(password)) {
            return handle_error("wrong password", model);
        }
        LinkResponseDTO dto = linkService.getLinkByName(optional.getName());
        model.addAttribute("message", "Success!" + dto.toString());
        return "index";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("updateDTO") LinkDTO linkDTO, @RequestParam String nameaccess, @RequestParam String password, Model model) {
        Link optional = linkService.getLinkByNameLink(nameaccess);
        if(optional == null) {
            return handle_error("Link is not found", model);
        }
        if (optional.getPassword() == null) {
            return handle_error("Link should have a password to be viewed", model);
        }
        if (!optional.getPassword().equals(password)) {
            return handle_error("wrong password", model);
        }
        linkDTO.setId(optional.getId());
        Optional<Link> result = linkService.update(linkDTO);
        model.addAttribute("message", "Success!");
        return "index";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam String name, @RequestParam String password, Model model) {
        Link optional = linkService.getLinkByNameLink(name);
        if(optional == null) {
            return handle_error("Link is not found", model);
        }
        if (optional.getPassword() == null) {
            return handle_error("Link should have a password to be viewed", model);
        }
        if (!password.equals(optional.getPassword())) {
            return handle_error("wrong password", model);
        }
        linkService.deleteLink(optional.getId());
        model.addAttribute("message", "Success!");
        return "index";
    }

    private String handle_error(String error, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("message", "The error has occurred");
        return "index";
    }
}
