package com.er7.er7foodapi.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraint) {
        this.valorField = constraint.valorField();
        this.descricaoField = constraint.descricaoField();
        this.descricaoObrigatoria = constraint.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object obg, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;
        try {
            BigDecimal valor = (BigDecimal) BeanUtils
                    .getPropertyDescriptor(obg.getClass(), this.valorField)
                    .getReadMethod().invoke(obg);

            String descricao = (String) BeanUtils
                    .getPropertyDescriptor(obg.getClass(), this.descricaoField)
                    .getReadMethod().invoke(obg);

            if (valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
                valid = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
            }

            return valid;
        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }
}
