package by.exposit.alarm.dto.mapper;

import by.exposit.alarm.ao.entity.AlarmMessage;
import by.exposit.alarm.dto.model.AlarmMessageDto;
import org.springframework.stereotype.Component;

@Component
public class AlarmMessageMapper {

    public static AlarmMessageDto toAlertMessageDto(AlarmMessage alarmMessage) {
        return new AlarmMessageDto()
                .setUserId(alarmMessage.getUserId())
                .setDescription(alarmMessage.getDescription())
                .setAlarmDate(alarmMessage.getAlarmDate())
                .setIsAcknowledged(alarmMessage.isAcknowledged())
                .setIsAdministrative(alarmMessage.isAdministrative());
    }

}
