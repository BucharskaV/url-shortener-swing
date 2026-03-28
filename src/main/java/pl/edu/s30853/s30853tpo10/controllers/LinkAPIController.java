package pl.edu.s30853.s30853tpo10.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edu.s30853.s30853tpo10.model.Link;
import pl.edu.s30853.s30853tpo10.model.LinkDTO;
import pl.edu.s30853.s30853tpo10.model.LinkResponseDTO;
import pl.edu.s30853.s30853tpo10.services.LinkService;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping(
        path = "/api/links",
        produces = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE
        })
public class LinkAPIController {
    private final LinkService linkService;
    private final ObjectMapper objectMapper;

    public LinkAPIController(LinkService linkService, ObjectMapper objectMapper) {
        this.linkService = linkService;
        this.objectMapper = objectMapper;
    }

    private Locale getLocaleFromString(String language) {
        if (language != null) {
            switch (language) {
                case "en":
                    return Locale.ENGLISH;
                case "pl":
                    return new Locale("pl");
                case "de":
                    return Locale.GERMAN;
                default:
                    return Locale.ENGLISH;
            }
        }
        return Locale.ENGLISH;
    }

    @Tag(name = "POST", description = "Create a new link")
    @PostMapping
    public ResponseEntity<LinkResponseDTO> createLink(@RequestParam(required = false) String lang, @Valid @RequestBody LinkDTO dto) {
        LinkResponseDTO response = linkService.createLink(dto);
        URI savedLinkLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(savedLinkLocation).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handle(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String lang = request.getParameter("lang");
        Locale currentLocale = getLocaleFromString(lang);

        ResourceBundle bundle = ResourceBundle.getBundle("messages", currentLocale);
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

        return errors;
    }

    @Tag(name = "GET", description = "Get link information")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<LinkResponseDTO>> getLink(@PathVariable String id) {
        Optional<LinkResponseDTO> responseDTO = linkService.getLinkById(id);
        if(responseDTO.isPresent()) {
            return ResponseEntity.ok(responseDTO);
        }
        else return ResponseEntity.notFound().build();
    }

    @Tag(name = "PATCH", description = "Update link information")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateLink(@PathVariable String id, @RequestBody JsonMergePatch patch) {
        try {
            Optional<Link> optional = linkService.getLinkByIdLink(id);
            Link link = optional.get();
            if (link.getPassword() == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).header("Reason","The password should be set").build();
            LinkDTO linkDTO = linkService.getLinkByIdDTO(id).orElseThrow();
            JsonNode linkNode = objectMapper.valueToTree(linkDTO);
            JsonNode patchNode = patch.apply(linkNode);
            LinkDTO patchedLinkDTO = objectMapper.treeToValue(patchNode, LinkDTO.class);
            JsonNode passwordNode = patchNode.get("password");
            String providedPassword = (passwordNode != null && !passwordNode.isNull()) ? passwordNode.asText() : null;

            if (linkDTO.getPassword() != null && (providedPassword == null || !linkDTO.getPassword().equals(providedPassword))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).header("Reason","wrong password").build();
            }

            linkService.update(patchedLinkDTO);
        }catch (NoSuchElementException ex){
            return ResponseEntity.notFound().build();
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.noContent().build();
    }

    @Tag(name = "DELETE", description = "Delete link")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLink(@PathVariable String id, @RequestParam(required = false) String pass) {
        Optional<Link> optional = linkService.getLinkByIdLink(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        Link link = optional.get();
        if (link.getPassword() == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).header("Reason","The password should be set").build();

        if (!link.getPassword().equals(pass)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).header("Reason","wrong password").build();
        }
        linkService.deleteLink(id);
        return ResponseEntity.noContent().build();
    }
}
