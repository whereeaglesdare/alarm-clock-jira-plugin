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
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link by.exposit.alarm.service.AlarmMessageService}
 * @author  Bohdan Belokur <belokur.bv@gmail.com>
 */
@Component
public class AlarmMessageServiceImpl implements AlarmMessageService {
    /**
     * Date format recieving from JSON 'date' param
     */
    private final String DTF = "yyyy-MM-dd HH:mm";

    @Autowired
    private AlarmMessageDao alarmMessageDao;

    @Autowired
    private AlarmMessageMapper mapper;

    @Autowired
    @ComponentImport
    private DateTimeFormatterFactory formatterFactory;


    /**
     * Convert user datetime string to Date objects with TZ settings in user profile
     * All date methods from 3rd party org.joda.time library
     * @param date string with date and yyyy-MM-dd hh:mm pattern
     * @param user jira instance user
     * @return
     */
    private Date convertToDate(String date, ApplicationUser user) throws AlarmException{
        DateTimeFormatter formatter = formatterFactory.formatter().forUser(user);
        org.joda.time.format.DateTimeFormatter dtf = DateTimeFormat.forPattern(DTF);
        try {
            DateTimeZone dtz = DateTimeZone.forTimeZone(formatter.getZone());
            DateTime dt = dtf.parseDateTime(date).withZone(dtz);
            return dt.toDate();
        } catch (IllegalArgumentException ex) {
            throw new AlarmException("Incorrect date value");
        }
    }

    private void checkDate(Date date) throws AlarmException {
        if (new DateTime(date).compareTo(DateTime.now(DateTimeZone.UTC)) < 0 ) {
            throw new AlarmException("You can not set alarm on this time");
        }
    }

    private void checkUserPermission(AlarmMessage alarmMessage, ApplicationUser user) throws AlarmException {
        if (!user.getId().equals(alarmMessage.getUserId())) {
            throw new AlarmException("Alarm not found");
        }
    }

    @Override
    public int createAlarmMessage(ApplicationUser user, AlarmMessageDto alarmMessageDto) throws AlarmException {
        Date utcDate = convertToDate(alarmMessageDto.getAlarmDate(), user);
        checkDate(utcDate);
        return alarmMessageDao.createAlarmMessage(user.getId(),
                alarmMessageDto.getDescription(), utcDate,
                false).getID();
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
                alarmMessageDto.getDescription(), convertToDate(alarmMessageDto.getAlarmDate(), user)));
    }

    @Override
    public int createAdministrativeAlarmMessage(ApplicationUser user, AlarmMessageDto alarmMessageDto) throws
            AlarmException {
        Date utcDate = convertToDate(alarmMessageDto.getAlarmDate(), user);
        checkDate(utcDate);
        return alarmMessageDao.createAlarmMessage(user.getId(),
                alarmMessageDto.getDescription(), utcDate,
                true).getID();
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
