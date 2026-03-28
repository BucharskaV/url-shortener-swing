package pl.edu.s30853.s30853tpo10.services;

import org.springframework.stereotype.Service;
import pl.edu.s30853.s30853tpo10.model.Link;
import pl.edu.s30853.s30853tpo10.model.LinkDTO;
import pl.edu.s30853.s30853tpo10.model.LinkResponseDTO;
import pl.edu.s30853.s30853tpo10.repositories.LinkRepository;

import java.util.Optional;
import java.util.Random;

@Service
public class LinkService {
    private final LinkRepository linkRepository;
    private final LinkDTOMapper linkDTOMapper;
    private static final int ID_LENGTH = 10;

    public LinkService(LinkRepository linkRepository, LinkDTOMapper linkDTOMapper) {
        this.linkRepository = linkRepository;
        this.linkDTOMapper = linkDTOMapper;
    }

    public LinkResponseDTO createLink(LinkDTO dto) {
        String id;
        do {
            id = generateId();
        } while (linkRepository.existsById(id));
        dto.setId(id);
        Link link = linkDTOMapper.map(dto);
        return linkDTOMapper.toResponseDTO(linkRepository.save(link));
    }

    public Optional<LinkResponseDTO> getLinkById(String id) {
        return linkRepository.findById(id).map(linkDTOMapper::toResponseDTO);
    }

    public Optional<LinkDTO> getLinkByIdDTO(String id) {
        return linkRepository.findById(id).map(linkDTOMapper::map);
    }

    public Optional<Link> getLinkByIdLink(String id) {
        return linkRepository.findById(id);
    }

    public Link getLinkByNameLink(String name) {
        return linkRepository.findByName(name);
    }

    public LinkResponseDTO getLinkByName(String name) {
        Link link = linkRepository.findByName(name);
        LinkResponseDTO dto = linkDTOMapper.toResponseDTO(link);
        return dto;
    }
    public String redirect(String id) {
        Link link = linkRepository.findById(id).orElse(null);
        if (link == null) return null;
        link.setVisitsCount(link.getVisitsCount() + 1);
        linkRepository.save(link);
        return link.getTargetUrl();
    }

    public Optional<Link> update(LinkDTO dto){
        return linkRepository.findById(dto.getId())
                .map(existingLink -> {
                    existingLink.setTargetUrl(dto.getTargetUrl());
                    existingLink.setName(dto.getName());
                    return linkRepository.save(existingLink);
                });
    }

    public void deleteLink(String id) {
        linkRepository.deleteById(id);
    }

    private String generateId() {
        String alphabetCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String result = "";
        Random random = new Random();
        for (int i = 0; i < ID_LENGTH; i++) {
            result += alphabetCharacters.charAt(random.nextInt(alphabetCharacters.length()));
        }
        return result;
    }
}
