package by.exposit.alarm.rest;

import by.exposit.alarm.dto.exception.AlarmException;
import by.exposit.alarm.dto.model.AlarmMessageDto;
import by.exposit.alarm.service.AlarmMessageService;
import com.atlassian.jira.bc.EntityNotFoundException;
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

/**
 * REST resource for manipulating alerts in user profile section
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
    public Response getAlarms() {
       return Response.ok(alarmMessageService.getAlarmMessagesbyUser(jiraAuthenticationContext.getLoggedInUser()))
               .build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{alarmId}")
    public Response getAlarmById(@PathParam("alarmId") int alarmId) throws AlarmException {
        return Response.ok(alarmMessageService.getAlarmMessageById(alarmId,
                jiraAuthenticationContext.getLoggedInUser())).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createAlarm(@Context UriInfo uriInfo, final AlarmMessageDto createDto) throws AlarmException{
        URI location = uriInfo.getAbsolutePathBuilder().path("/" +
                String.valueOf(alarmMessageService.createAlarmMessage(jiraAuthenticationContext.getLoggedInUser(),
                        createDto))).build();
        return Response.created(location).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/{alarmId}")
    public Response updateAlarm(@PathParam("alarmId") int alarmId,  final AlarmMessageDto updateDto)
            throws AlarmException {
        return Response.ok(alarmMessageService.updateAlarmMessage(jiraAuthenticationContext.getLoggedInUser(),
                alarmId, updateDto)).build();
    }
}