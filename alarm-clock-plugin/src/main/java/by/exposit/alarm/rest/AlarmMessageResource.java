package by.exposit.alarm.rest;

import by.exposit.alarm.dto.exception.AlarmException;
import by.exposit.alarm.dto.model.AlarmMessageDto;
import by.exposit.alarm.service.AlarmMessageService;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * CRUD REST resource for manipulating alerts in user profile section
 */
@Path("/alarm")
@Component
public class AlarmMessageResource {
    @Autowired
    private AlarmMessageService alarmMessageService;

    @ComponentImport
    @Autowired
    private JiraAuthenticationContext jiraAuthenticationContext;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<AlarmMessageDto> getAlarms() {
        return alarmMessageService.getAlarmMessagesbyUser(jiraAuthenticationContext.getLoggedInUser());
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{alarmId}")
    public AlarmMessageDto getAlarmById(@PathParam("alarmId") int alarmId) throws AlarmException {
        return alarmMessageService.getAlarmMessageById(alarmId, jiraAuthenticationContext.getLoggedInUser());
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createAlarm(@Context UriInfo uriInfo, final AlarmMessageDto createDto) throws AlarmException{
        URI location = uriInfo.getAbsolutePathBuilder().path("/" +
               alarmMessageService.createAlarmMessage(jiraAuthenticationContext.getLoggedInUser(),
                        createDto)).build();
        return Response.created(location).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/{alarmId}")
    public AlarmMessageDto updateAlarm(@PathParam("alarmId") int alarmId, final AlarmMessageDto updateDto)
            throws AlarmException {
        return alarmMessageService.updateAlarmMessage(jiraAuthenticationContext.getLoggedInUser(), alarmId, updateDto);
    }

    @DELETE
    @Path("/{alarmId}")
    public Response removeAlarm(@PathParam("alarmId") int alarmId) {
        alarmMessageService.removeAlarm(alarmId);
        return Response.noContent().build();
    }

}