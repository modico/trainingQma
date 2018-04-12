package pl.com.bottega.qma.core.validation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

abstract class FieldValidator<ValueT> implements Validator<ValueT> {

  private final Field field;

  public FieldValidator(Field field) {
    this.field = field;
  }

  @Override
  public void validate(ValueT toValidate, ValidationErrors errors) {
    try {
      ValueT value = (ValueT) field.get(toValidate);
      validateValue(value, errors);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  protected void addError(ValidationErrors errors, String error) {
    errors.addError(field.getName(), error);
  }

  protected abstract void validateValue(ValueT value, ValidationErrors errors);

  protected Optional<Integer> fieldSize(Object value) {
    return sizeMethod().map(method -> {
      try {
        return (Integer) method.invoke(value);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }

  private Optional<Method> sizeMethod() {
    Class fieldClass = field.getDeclaringClass();
    try {
      Method size = fieldClass.getMethod("size");
      return Optional.of(size);
    } catch (NoSuchMethodException e) {
    }

    try {
      Method length = fieldClass.getMethod("length");
      Optional.of(length);
    } catch (NoSuchMethodException e) {
    }

    return Optional.empty();
  }

}
