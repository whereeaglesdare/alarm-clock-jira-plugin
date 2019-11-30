package by.exposit.alarm.dto.mapper;

import by.exposit.alarm.dto.exception.AlarmException;
import by.exposit.alarm.dto.model.CustomErrorModel;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AlarmExceptionMapper implements ExceptionMapper<AlarmException>
{
    @Override
    public Response toResponse(AlarmException exception)
    {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new CustomErrorModel().setMessage(exception.getMessage())).build();
    }
}
