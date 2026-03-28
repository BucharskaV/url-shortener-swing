package pl.edu.s30853.s30853tpo10.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.edu.s30853.s30853tpo10.repositories.LinkRepository;

public class UniqueUrlValidator implements ConstraintValidator<UniqueUrl, String> {
    private LinkRepository linkRepository;

    public UniqueUrlValidator(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public boolean isValid(String url, ConstraintValidatorContext constraintValidatorContext) {
        return !linkRepository.existsByTargetUrl(url);
    }
}
