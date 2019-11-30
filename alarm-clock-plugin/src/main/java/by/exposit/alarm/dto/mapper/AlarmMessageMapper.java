package by.exposit.alarm.dto.mapper;

import by.exposit.alarm.ao.entity.AlarmMessage;
import by.exposit.alarm.dto.exception.AlarmException;
import by.exposit.alarm.dto.model.AlarmMessageDto;
import com.atlassian.jira.datetime.DateTimeFormatter;
import com.atlassian.jira.datetime.DateTimeFormatterFactory;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Mapping AlarmMessage entity to AlarmMessageDto
 */
@Component
public class AlarmMessageMapper {
    private final String DTF = "yyyy-MM-dd HH:mm";

    @ComponentImport
    @Autowired
    private JiraAuthenticationContext jiraAuthenticationContext;

    @Autowired
    @ComponentImport
    private DateTimeFormatterFactory formatterFactory;

    /**
     * converting from java.util.Date to String with user's timezone
     *
     * TODO:
     * Use ContextResolver or XmlAdapter for marshaling/unmarshaling date (???)
     * ContextResolver
     * @param date Date of alarm
     * @return string with date pattern yyyy-MM-dd HH:mm
     */
    private String convertToDateString(Date date) {
        DateTimeFormatter formatter = formatterFactory.formatter().forUser(jiraAuthenticationContext.getLoggedInUser());
        org.joda.time.format.DateTimeFormatter dtf = DateTimeFormat.forPattern(DTF);
        DateTimeZone dtz = DateTimeZone.forTimeZone(formatter.getZone());
        return new DateTime(date, dtz).toString(dtf);
    }

    /**
     * Method for converting AlarmMessage to AlarmMessageDto
     * Used for administrative resources
     * @param alarmMessage AlarmMessage entity
     * @return AlarmMessageDto
     */
    public AlarmMessageDto toAlertMessageDto(AlarmMessage alarmMessage) {
        return new AlarmMessageDto()
                .setDescription(alarmMessage.getDescription())
                .setAlarmDate(convertToDateString(alarmMessage.getAlarmDate()))
                .setIsAcknowledged(alarmMessage.isAcknowledged())
                .setIsAdministrative(alarmMessage.isAdministrative())
                .setId(alarmMessage.getID());
    }


}
