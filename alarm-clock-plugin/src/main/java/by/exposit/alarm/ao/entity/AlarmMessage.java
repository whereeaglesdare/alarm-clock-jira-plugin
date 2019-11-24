package by.exposit.alarm.ao.entity;

import net.java.ao.Entity;
import net.java.ao.schema.Table;

import java.util.Date;

@Table("ALARM_MESSAGE")
public interface AlarmMessage extends Entity {
    Long getUserId();

    void setUserId(Long userId);

    String getDescription();

    void setDescription(String description);

    Date getAlarmDate();

    void setAlarmDate(Date alarmDate);

    Boolean isAcknowledged();

    void setAcknowledged(Boolean isAcknowledged);

    Boolean isRemoved();

    void setRemoved(Boolean isRemoved);

    Boolean isAdministrative();

    void setAdministrative(Boolean isAdministrative);
}
