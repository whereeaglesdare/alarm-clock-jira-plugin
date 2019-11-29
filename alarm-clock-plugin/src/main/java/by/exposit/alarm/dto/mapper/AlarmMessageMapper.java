package by.exposit.alarm.dto.mapper;

import by.exposit.alarm.ao.entity.AlarmMessage;
import by.exposit.alarm.dto.model.AlarmMessageDto;
import com.atlassian.jira.datetime.DateTimeFormatter;
import com.atlassian.jira.datetime.DateTimeFormatterFactory;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Mapping AlarmMessage entity to AlarmMessageDto
 */
@Component
public class AlarmMessageMapper {
    @ComponentImport
    @Autowired
    private JiraAuthenticationContext jiraAuthenticationContext;

    @Autowired
    @ComponentImport
    private DateTimeFormatterFactory formatterFactory;

    private Date toEntityDate(Date date) {
        DateTimeFormatter formatter = formatterFactory.formatter().forUser(jiraAuthenticationContext.getLoggedInUser());
        DateTimeZone dtz = DateTimeZone.forTimeZone(formatter.getZone());
        return new Date(dtz.convertLocalToUTC(date.getTime(), true));
    }

    private Date toUserTimezoneDate(Date date) {
        DateTimeFormatter formatter = formatterFactory.formatter().forUser(jiraAuthenticationContext.getLoggedInUser());
        DateTimeZone dtz = DateTimeZone.forTimeZone(formatter.getZone());
        dtz.adjustOffset(date.getTime(), true);
        return new Date(dtz.adjustOffset(date.getTime(), true));
    }

    /**
     * Static method for converting AlarmMessage to AlarmMessageDto
     * Used for administrative resources
     * @param alarmMessage AlarmMessage entity
     * @return AlarmMessageDto
     */
    public AlarmMessageDto toAlertMessageDto(AlarmMessage alarmMessage) {
        return new AlarmMessageDto()
                .setDescription(alarmMessage.getDescription())
                .setAlarmDate(toUserTimezoneDate(alarmMessage.getAlarmDate()))
                .setIsAcknowledged(alarmMessage.isAcknowledged())
                .setIsAdministrative(alarmMessage.isAdministrative())
                .setId(alarmMessage.getID());
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
