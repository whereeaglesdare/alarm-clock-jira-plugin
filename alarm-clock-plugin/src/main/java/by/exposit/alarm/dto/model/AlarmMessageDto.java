package by.exposit.alarm.dto.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.*;
import java.util.Date;


@Getter
@Setter
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class AlarmMessageDto {

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "userId")
    private Long userId;

    @XmlElement(name = "date")
    private Date alarmDate;

    @XmlElement(name = "isAdministrative")
    private Boolean isAdministrative;

    @XmlElement(name = "isAcknowledged")
    private Boolean isAcknowledged;

    public AlarmMessageDto() {};
}