package by.exposit.alarm.ao.dao;

import by.exposit.alarm.ao.entity.*;
import com.atlassian.activeobjects.tx.Transactional;

import java.util.Date;
import java.util.List;

public interface AlarmMessageDao {
    @Transactional
    AlarmMessage createAlertMessage(Long userId, String message, Date alertDate, Boolean isAdministrative);

    @Transactional
    AlarmMessage updateAlarmMessage(int alarmMessageId, String message, Date alertDate);

    @Transactional
    void removeAlarmMessage(AlarmMessage alarmMessage);

    @Transactional
    void acknowledgeAlarm(AlarmMessage alarmMessage);

    AlarmMessage getAlarmMessage(int alarmMessageId);

    List<AlarmMessage> getAlarmMessagesbyUserId(Long userId);

    List<AlarmMessage> bulkCreateAlarmMessages(List<Long> userIds, String message,
                                               Date alertDate);

    List<AlarmMessage> getAdministrativeAlarmMessages();

    List<AlarmMessage> getAllAlarmMessages();

    List<AlarmMessage> getActiveAlarmMessages();
}
