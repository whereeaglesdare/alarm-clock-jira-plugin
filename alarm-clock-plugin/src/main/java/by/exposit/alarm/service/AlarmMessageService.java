package by.exposit.alarm.service;

import by.exposit.alarm.dto.exception.AlarmException;
import by.exposit.alarm.dto.model.AlarmMessageDto;
import com.atlassian.jira.bc.EntityNotFoundException;
import com.atlassian.jira.user.ApplicationUser;

import java.util.Date;
import java.util.List;

public interface AlarmMessageService {
    int createAlarmMessage(ApplicationUser user, AlarmMessageDto alarmMessageDto) throws AlarmException;

    AlarmMessageDto updateAlarmMessage(ApplicationUser user, int alarmId, AlarmMessageDto alarmMessageDto)
            throws AlarmException;

    List<AlarmMessageDto> getAlarmMessagesbyUser(ApplicationUser user);

    AlarmMessageDto getAlarmMessageById(int alarmId, ApplicationUser user) throws AlarmException;

    List<AlarmMessageDto> getAdministrativeAlarms();

    void removeAlarm(int alarmMessageId);

    void checkActiveAlarm(Date date);
}
