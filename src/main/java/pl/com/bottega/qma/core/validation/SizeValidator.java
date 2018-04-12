package pl.com.bottega.qma.core.validation;

import java.lang.reflect.Field;
import java.util.Optional;

class SizeValidator extends FieldValidator {
  private final ValidateSize config;

  public SizeValidator(Field field, ValidateSize config) {
    super(field);
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
    size.orElseThrow(() -> new IllegalArgumentException(String.format("Can't read size of %s", value.getClass())));
  }

  private void addError(ValidationErrors errors) {
    addError(errors, String.format("must have size between %d and %d", config.min(), config.max()));
  }
}
