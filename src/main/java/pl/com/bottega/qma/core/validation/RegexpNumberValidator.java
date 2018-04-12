package pl.com.bottega.qma.core.validation;

import java.lang.reflect.Field;

public class RegexpNumberValidator extends FieldValidator<String> {

  private final ValidateRegexp config;

  public RegexpNumberValidator(Field field, ValidateRegexp config) {
    super(field);
    this.config = config;
  }

  @Override
  protected void validateValue(String value, ValidationErrors errors) {
    if(value == null) {
      return;
    }
    if(!value.matches(config.expression())) {
      addError(errors, "has wrong format");
    }
  }


}
