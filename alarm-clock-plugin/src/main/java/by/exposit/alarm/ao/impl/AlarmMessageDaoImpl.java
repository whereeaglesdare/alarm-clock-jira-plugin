package by.exposit.alarm.ao.impl;

import by.exposit.alarm.ao.dao.*;
import by.exposit.alarm.ao.entity.AlarmMessage;
import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Implementation of {@link by.exposit.alarm.ao.dao.AlarmMessageDao}
 */
@Component
public class AlarmMessageDaoImpl implements AlarmMessageDao {

    @ComponentImport
    private final ActiveObjects ao;

    @Autowired
    public AlarmMessageDaoImpl(ActiveObjects ao) {
        this.ao = ao;
    }

    @Override
    public AlarmMessage createAlertMessage(Long userId, String message, Date alertDate, Boolean isAdministrative) {
        final AlarmMessage alarm = ao.create(AlarmMessage.class);
        alarm.setDescription(message);
        alarm.setAlarmDate(alertDate);
        alarm.setAdministrative(isAdministrative);
        alarm.setUserId(userId);
        alarm.setAcknowledged(Boolean.FALSE);
        alarm.save();
        return alarm;
    }

    @Override
    public AlarmMessage updateAlarmMessage(int alarmMessageId, String message, Date alertDate) {
        final AlarmMessage alarm = getAlarmMessage(alarmMessageId);
        alarm.setDescription(message);
        alarm.setAlarmDate(alertDate);
        alarm.save();
        return alarm;
    }

    @Override
    public List<AlarmMessage> getAlarmMessagesbyUserId(Long userId) {
        return Arrays.asList(ao.find(AlarmMessage.class, "USER_ID = ?", userId));
    }

    @Override
    public AlarmMessage getAlarmMessage(int AlarmMessageId)  {
        return ao.get(AlarmMessage.class, AlarmMessageId);
    }

    @Override
    public List<AlarmMessage> getAdministrativeAlarmMessages() {
        return Arrays.asList(ao.find(AlarmMessage.class, "ADMINISTRATIVE = TRUE"));
    }

    @Override
    public List<AlarmMessage> getAllAlarmMessages() {
        return Arrays.asList(ao.find(AlarmMessage.class));
    }

    @Override
    public List<AlarmMessage> getActiveAlarmMessages() {
        return Arrays.asList(ao.find(AlarmMessage.class, "ACKNOWLEDGED = FALSE"));
    }

    @Override
    public void acknowledgeAlarm(AlarmMessage alarmMessage) {
        alarmMessage.setAcknowledged(Boolean.TRUE);
        alarmMessage.save();
    }

    @Override
    public void removeAlarmMessage(AlarmMessage alarmMessage) {
        alarmMessage.setRemoved(Boolean.TRUE);
        alarmMessage.save();
    }


}
