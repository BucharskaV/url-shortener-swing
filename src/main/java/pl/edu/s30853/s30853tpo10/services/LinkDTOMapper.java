package pl.edu.s30853.s30853tpo10.services;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.edu.s30853.s30853tpo10.model.Link;
import pl.edu.s30853.s30853tpo10.model.LinkDTO;
import pl.edu.s30853.s30853tpo10.model.LinkResponseDTO;

@Component
public class LinkDTOMapper {
    public LinkDTO map(Link link) {
        LinkDTO dto = new LinkDTO();
        dto.setId(link.getId());
        dto.setName(link.getName());
        dto.setTargetUrl(link.getTargetUrl());
        dto.setRedirectUrl("http://localhost:8080/red/" + link.getId());
        dto.setPassword(link.getPassword());
        dto.setVisitsCount(link.getVisitsCount());
        return dto;
    }

    public Link map(LinkDTO dto) {
        Link link = new Link();
        link.setId(dto.getId());
        link.setName(dto.getName());
        link.setTargetUrl(dto.getTargetUrl());
        link.setPassword(dto.getPassword());
        link.setVisitsCount(dto.getVisitsCount());
        return link;
    }

    public LinkResponseDTO toResponseDTO(Link link) {
        LinkResponseDTO res = new LinkResponseDTO();
        res.setId(link.getId());
        res.setName(link.getName());
        res.setTargetUrl(link.getTargetUrl());
        res.setRedirectUrl("http://localhost:8080/red/" + link.getId());
        res.setvisits(link.getVisitsCount());
        return res;
    }
}
