package pl.com.bottega.qma.core.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Optional;

class ValidatorFactory {

  Optional<Validator> createValidator(Field field, Annotation annotation) {
    if (annotation instanceof ValidatePresence) {
      return Optional.of(createPresenceValidator(field, (ValidatePresence) annotation));
    }
    else if(annotation instanceof ValidateRegexp) {
      return Optional.of(createRegexpNumberValidator(field, (ValidateRegexp) annotation));
    }
    else if(annotation instanceof ValidateSize) {
      return Optional.of(createSizeValidator(field, (ValidateSize) annotation));
    }
    else if (annotation instanceof ValidateNumber) {
      return Optional.of(createNumberValidator(field, (ValidateNumber) annotation));
    }
    return Optional.empty();
  }

  private Validator createNumberValidator(Field field, ValidateNumber validateNumber) {
    if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
      return new IntegerNumberValidator(field, validateNumber);
    } else if (field.getType().equals(BigDecimal.class)) {
      return new BigDecimalNumberValidator(field, validateNumber);
    } else if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
      return new LongNumberValidator(field, validateNumber);
    } else {
      throw new IllegalArgumentException("Unsupported type for number validator");
    }
  }

  private PresenceValidator createPresenceValidator(Field field, ValidatePresence validatePresence) {
    return new PresenceValidator(field, validatePresence);
  }

  private RegexpNumberValidator createRegexpNumberValidator(Field field, ValidateRegexp validateRegexp) {
    if (!field.getType().equals(String.class)) {
      throw new IllegalArgumentException("ValidateRegexp allowed only on String");
    }
    return new RegexpNumberValidator(field, validateRegexp);
  }

  private SizeValidator createSizeValidator(Field field, ValidateSize validateSize) {
    return new SizeValidator(field, validateSize);
  }
}
