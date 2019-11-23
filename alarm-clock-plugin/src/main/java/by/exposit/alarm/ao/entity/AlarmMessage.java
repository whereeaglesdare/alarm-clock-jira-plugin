package by.exposit.alarm.ao.entity;

import net.java.ao.schema.Table;

import java.util.Date;

@Table("ALARM_MESSAGE")
public interface AlarmMessage {
    Long getUserId();

    void setUserId(Long userId);

    String getDescription();

    void setDescription(String description);

    Date getAlarmDate();

    void setAlarmDate(Date alarmDate);
}
