package com.company.spacetrans.app.validation;

import com.company.spacetrans.entity.Spaceport;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidSpaceportValidator implements ConstraintValidator<ValidSpaceport, Spaceport> {
    @Override
    public boolean isValid(Spaceport spaceport, ConstraintValidatorContext context) {
        if ((spaceport.getMoon() == null && spaceport.getPlanet() != null)
                || (spaceport.getMoon() != null && spaceport.getPlanet() == null)) {
            return true;
        }
        return false;
    }
}
