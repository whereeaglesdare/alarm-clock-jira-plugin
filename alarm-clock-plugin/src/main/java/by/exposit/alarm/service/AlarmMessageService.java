package by.exposit.alarm.service;

import by.exposit.alarm.dto.exception.AlarmException;
import by.exposit.alarm.dto.model.AlarmMessageDto;
import com.atlassian.jira.user.ApplicationUser;

import java.util.Date;
import java.util.List;

/**
 * Service for working with Alarms
 * Creating, updating and deleting alarm created by Jira users
 */
public interface AlarmMessageService {
    /**
     * Creating of AlarmMessage entity
     * @param user ApplicationUser of jira instance
     * @param alarmMessageDto DTO with user alarm data
     * @return id of created AlarmMessageEntity
     * @throws AlarmException Basic exception for wrong alarm params
     */
    int createAlarmMessage(ApplicationUser user, AlarmMessageDto alarmMessageDto) throws AlarmException;

    /**
     * Updating user alarm
     * @param user Jira ApplicationUser
     * @param alarmId id of AlarmMessage entity
     * @param alarmMessageDto model for updating alarmMessage
     * @return AlarmMessageDto updated model
     * @throws AlarmException basic exception for wrong alarm params
     */
    AlarmMessageDto updateAlarmMessage(ApplicationUser user, int alarmId, AlarmMessageDto alarmMessageDto)
            throws AlarmException;

    /**
     * Getting all unacknowledged alarmMessage by user
     * @param user Jira ApplicationUser
     * @return List of AlarmMessageDto
     */
    List<AlarmMessageDto> getAlarmMessagesbyUser(ApplicationUser user);

    /**
     * Retrieving user's AlarmMessage information
     * @param alarmId id of AlarmMessage entity
     * @param user Jira application user
     * @return User's alarm message
     * @throws AlarmException basic exception if entity not found
     */
    AlarmMessageDto getAlarmMessageById(int alarmId, ApplicationUser user) throws AlarmException;

    /**
     *  Retrieving administrative alarms
     * @return List of administrative alarms
     */
    List<AlarmMessageDto> getAdministrativeAlarms();

    /**
     * Removing user alarms
     * @param alarmMessageId AlarmMessage entity id
     */
    void removeAlarm(int alarmMessageId);

    /**
     * Creating administrative alarms from admin UI
     * @param user ApplicationUser
     * @param alarmMessageDto model of AM entity
     * @return id of created AlarmMessage entity
     * @throws AlarmException basic validation exception
     */
    int createAdministrativeAlarmMessage(ApplicationUser user, AlarmMessageDto alarmMessageDto) throws AlarmException;

    /**
     * Method for triggering alarm
     * Using in scheduler for notify user
     * @param date system current date
     * @see by.exposit.alarm.ao.entity.AlarmMessage
     */
    void checkActiveAlarm(Date date);
}
