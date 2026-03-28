package pl.edu.s30853.s30853tpo10.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.s30853.s30853tpo10.model.Link;

public interface LinkRepository extends CrudRepository<Link, String> {
    boolean existsByTargetUrl(String target);
    Link findByName(String name);
}
