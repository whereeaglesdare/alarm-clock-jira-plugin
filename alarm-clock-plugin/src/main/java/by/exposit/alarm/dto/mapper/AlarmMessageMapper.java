package by.exposit.alarm.dto.mapper;

import by.exposit.alarm.ao.entity.AlarmMessage;
import by.exposit.alarm.dto.model.AlarmMessageDto;
import org.springframework.stereotype.Component;

/**
 * Mapping AlarmMessage entity to AlarmMessageDto
 */
@Component
public class AlarmMessageMapper {
    /**
     * Static method for converting AlarmMessage to AlarmMessageDto
     * Used for administrative resources
     * @param alarmMessage AlarmMessage entity
     * @return AlarmMessageDto
     */
    public static AlarmMessageDto toAlertMessageDto(AlarmMessage alarmMessage) {
        return new AlarmMessageDto()
                .setUserId(alarmMessage.getUserId())
                .setDescription(alarmMessage.getDescription())
                .setAlarmDate(alarmMessage.getAlarmDate())
                .setIsAcknowledged(alarmMessage.isAcknowledged())
                .setIsAdministrative(alarmMessage.isAdministrative());
    }

    /**
     * Static method for converting AlarmMessage to AlarmMessageDto
     * Used in user profile section
     * @param alarmMessage AlarmMessage entity
     * @return AlarmMessageDto
     */
    public static AlarmMessageDto toAlertMessageUserDto(AlarmMessage alarmMessage) {
        return new AlarmMessageDto()
                .setDescription(alarmMessage.getDescription())
                .setAlarmDate(alarmMessage.getAlarmDate())
                .setIsAcknowledged(alarmMessage.isAcknowledged());
    }

}
