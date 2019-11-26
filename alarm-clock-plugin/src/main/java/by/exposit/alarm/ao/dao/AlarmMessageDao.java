package by.exposit.alarm.ao.dao;

import by.exposit.alarm.ao.entity.*;
import com.atlassian.activeobjects.tx.Transactional;

import java.util.Date;
import java.util.List;

/**
 *  Interface for working with {@link by.exposit.alarm.ao.entity.AlarmMessage} entity
 *  Administrative and user alarms stored in one table.
 *
 * @author Belokur Bohdan
 */
public interface AlarmMessageDao {
    /**
     * Creating AlarmMessage entity
     *
     * @param userId ApplicationUser id
     * @param message description of alarm
     * @param alertDate date, when alarm should trigger
     * @param isAdministrative true for alarms created by Jira Administrator or System administrator
     * @return {@link by.exposit.alarm.ao.entity.AlarmMessage} entity
     */
    @Transactional
    AlarmMessage createAlertMessage(Long userId, String message, Date alertDate, Boolean isAdministrative);

    /**
     * Updating AlarmMessage entity
     *
     * @param alarmMessageId id of alarm
     * @param message alarm description
     * @param alertDate alarm date
     * @return updated AlarmMessage entity
     */
    @Transactional
    AlarmMessage updateAlarmMessage(int alarmMessageId, String message, Date alertDate);

    /**
     * "Removing" alarm message.
     * Alarm does not removed from database. "Removed" property setting to true.
     * @param alarmMessage AlarmMessage entity
     */
    @Transactional
    void removeAlarmMessage(AlarmMessage alarmMessage);

    /**
     * Acknowledge alarm
     * <p>User should acknowledge alarm to hide it</p>
     * @param alarmMessage AlarmMessage entity
     */
    @Transactional
    void acknowledgeAlarm(AlarmMessage alarmMessage);

    AlarmMessage getAlarmMessage(int alarmMessageId);

    List<AlarmMessage> getAlarmMessagesbyUserId(Long userId);

    List<AlarmMessage> getAdministrativeAlarmMessages();

    List<AlarmMessage> getAllAlarmMessages();

    /**
     * Getting all unacknowledge AlarmMessages
     *
     * @return List of AlarmMessages
     */
    List<AlarmMessage> getActiveAlarmMessages();
}
