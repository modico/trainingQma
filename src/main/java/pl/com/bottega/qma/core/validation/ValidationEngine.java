package pl.com.bottega.qma.core.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ValidationEngine {

  private final ValidatorFactory validatorFactory = new ValidatorFactory();
  private final Map<Class, List<Validator>> validatorsCache = new HashMap<>();

  public ValidationErrors validate(Object object) {
    var chain = validatorsChain(object.getClass());
    ValidationErrors errors = new ValidationErrors();
    chain.forEach(validator -> validator.validate(object, errors));
    return errors;
  }

  private List<Validator> validatorsChain(Class clazz) {
    if(validatorsCache.containsKey(clazz)) {
      return validatorsCache.get(clazz);
    }
    var chain = createChain(clazz);
    validatorsCache.put(clazz, chain);
    return chain;
  }

  private List<Validator> createChain(Class clazz) {
    var chain = new LinkedList<Validator>();
    for(Field field : clazz.getDeclaredFields()) {
      for (Annotation annotation : field.getDeclaredAnnotations()) {
        validatorFactory.createValidator(field, annotation).ifPresent(validator -> chain.add(validator));
      }
    }
    return chain;
  }
}
