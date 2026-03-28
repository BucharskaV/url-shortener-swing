package pl.edu.s30853.s30853tpo10.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.s30853.s30853tpo10.services.LinkService;

import java.net.URI;

@RestController
@RequestMapping(path = "/red")
public class RedirectAPIController {
    private final LinkService linkService;

    public RedirectAPIController(LinkService linkService) {
        this.linkService = linkService;
    }

    @Tag(name = "GET REDIRECTION", description = "Redirect")
    @GetMapping("/{id}")
    public ResponseEntity<Void> redirect(@PathVariable String id) {
        String url = linkService.redirect(id);
        if (url == null) return ResponseEntity.notFound().build();
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
    }
}
