package pl.com.bottega.qma.core.validation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

public class SizeValidator extends FieldValidator {
  private final ValidateSize config;

  public SizeValidator(Field field, ValidateSize config) {
    super(field);
    Optional<Method> sizeMethod = sizeMethod();
    sizeMethod.orElseThrow(() -> new IllegalArgumentException(String.format("Can't read size of %s", field.getType())));
    this.config = config;
  }

  @Override
  protected void validateValue(Object value, ValidationErrors errors) {
    if (value == null) {
      return;
    }
    Optional<Integer> size = fieldSize(value);
    size.ifPresent((s) -> {
      if (s > config.max() || s < config.min()) {
        addError(errors);
      }
    });
  }

  private void addError(ValidationErrors errors) {
    addError(errors, String.format("must have size between %d and %d", config.min(), config.max()));
  }
}
