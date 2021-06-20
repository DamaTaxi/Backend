package com.example.damataxi.domain.taxiPot.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class TaxiPotTargetConverter implements AttributeConverter<TaxiPotTarget, String> {

    @Override
    public String convertToDatabaseColumn(TaxiPotTarget target) {
        return target.name();
    }

    @Override
    public TaxiPotTarget convertToEntityAttribute(String target) {
        return TaxiPotTarget.valueOf(target);
    }
}

