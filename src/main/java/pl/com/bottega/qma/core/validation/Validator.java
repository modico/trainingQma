package pl.com.bottega.qma.core.validation;

interface Validator<ValueT> {

  void validate(ValueT toValidate, ValidationErrors errors);

}
