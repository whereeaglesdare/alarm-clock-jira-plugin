package by.exposit.alarm.service;

import by.exposit.alarm.ao.dao.AlarmMessageDao;
import by.exposit.alarm.ao.entity.AlarmMessage;
import by.exposit.alarm.dto.exception.AlarmException;
import by.exposit.alarm.dto.mapper.AlarmMessageMapper;
import by.exposit.alarm.dto.model.AlarmMessageDto;
import com.atlassian.jira.datetime.DateTimeFormatter;
import com.atlassian.jira.datetime.DateTimeFormatterFactory;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlarmMessageServiceImpl implements AlarmMessageService {
    @Autowired
    private AlarmMessageDao alarmMessageDao;

    @Autowired
    private AlarmMessageMapper mapper;

    @Autowired
    @ComponentImport
    private DateTimeFormatterFactory formatterFactory;

    private Date convertDateToUtc(Date date, ApplicationUser user) {
        DateTimeFormatter formatter = formatterFactory.formatter().forUser(user);
        DateTimeZone dtz = DateTimeZone.forTimeZone(formatter.getZone());
        return new Date(dtz.convertLocalToUTC(date.getTime(), true));
    }

    private Date convertDateToLocalTime(Date date, ApplicationUser user) {
        DateTimeFormatter formatter = formatterFactory.formatter().forUser(user);
        LocalDateTime zonedDate = new LocalDateTime(date, DateTimeZone.forTimeZone(formatter.getZone()));
        return zonedDate.toDateTime(DateTimeZone.UTC).toDate();
    }

    private void checkDate(Date date) throws AlarmException {
        if (new DateTime(date).compareTo(DateTime.now(DateTimeZone.UTC)) < 0 ) {
            throw new AlarmException("Incorrect date value");
        }
    }

    private void checkUserPermission(AlarmMessage alarmMessage, ApplicationUser user) throws AlarmException {
        if (!user.getId().equals(alarmMessage.getUserId())) {
            throw new AlarmException("Alarm not found");
        }
    }

    @Override
    public int createAlarmMessage(ApplicationUser user, AlarmMessageDto alarmMessageDto) throws AlarmException {
        Date utcDate = convertDateToUtc(alarmMessageDto.getAlarmDate(), user);
        checkDate(utcDate);
        return alarmMessageDao.createAlertMessage(user.getId(),
                alarmMessageDto.getDescription(), convertDateToUtc(alarmMessageDto.getAlarmDate(), user),
                alarmMessageDto.getIsAdministrative()).getID();
    }

    @Override
    public AlarmMessageDto updateAlarmMessage(ApplicationUser user, int alarmId, AlarmMessageDto alarmMessageDto) throws
            AlarmException {
        AlarmMessage alarmMessage = alarmMessageDao.getAlarmMessage(alarmId);
        if(alarmMessage == null) {
            throw new AlarmException("Alarm not found");
        }
        checkUserPermission(alarmMessage, user);
        return mapper.toAlertMessageDto(alarmMessageDao.updateAlarmMessage(alarmId,
                alarmMessageDto.getDescription(), alarmMessageDto.getAlarmDate()));
    }

    @Override
    public AlarmMessageDto getAlarmMessageById(int alarmId, ApplicationUser user) throws AlarmException {
        AlarmMessage alarmMessage = alarmMessageDao.getAlarmMessage(alarmId);
        if(alarmMessage == null) {
            throw new AlarmException("Alarm not found");
        }
        checkUserPermission(alarmMessage, user);
        return mapper.toAlertMessageDto(alarmMessageDao.getAlarmMessage(alarmId));
    }

    @Override
    public List<AlarmMessageDto> getAlarmMessagesbyUser(ApplicationUser user) {
        return  alarmMessageDao.getAlarmMessagesbyUserId(user.getId()).stream()
                .map(mapper::toAlertMessageDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AlarmMessageDto> getAdministrativeAlarms() {
        return  alarmMessageDao.getAdministrativeAlarmMessages().stream()
                .map(mapper::toAlertMessageDto)
                .collect(Collectors.toList());
    }

    @Override
    public void removeAlarm(int alarmMessageId) {
        AlarmMessage alarmMessage = alarmMessageDao.getAlarmMessage(alarmMessageId);
        if(alarmMessage != null) {
            alarmMessageDao.removeAlarmMessage(alarmMessage);
        }
    }

    @Override
    public void checkActiveAlarm(Date date) {
        // Not implemented yet
    }
}
