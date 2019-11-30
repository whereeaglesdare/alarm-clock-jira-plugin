package by.exposit.alarm.dto.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.util.Date;

/**
 * DTO for AlertMessage
 */
@Getter
@Setter
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class AlarmMessageDto {

    @XmlElement(name = "id")
    private int id;

    @XmlElement(name = "description", required = true)
    private String description;

    @XmlElement(name = "date", required = true)
    private String alarmDate;

    @XmlElement(name = "isAdministrative")
    private Boolean isAdministrative;

    @XmlElement(name = "isAcknowledged")
    private Boolean isAcknowledged;

    public AlarmMessageDto() {};
}