package me.niravpradhan.hibernate_beginner.entities.converters;

import javax.persistence.AttributeConverter;
import java.sql.Date;
import java.time.LocalDate;

//@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        if(localDate != null) {
            return Date.valueOf(localDate);
        }
        return null;
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        if (date != null) {
            return date.toLocalDate();
        }
        return null;
    }
}
