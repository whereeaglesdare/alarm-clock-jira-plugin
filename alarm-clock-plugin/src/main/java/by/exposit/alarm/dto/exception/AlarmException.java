package by.exposit.alarm.dto.exception;

import java.io.Serializable;

/**
 * Basic exception for wrong parans for user's alarm message
 */
public class AlarmException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;

    public AlarmException() {
        super();
    }

    public AlarmException(String msg)   {
        super(msg);
    }

    public AlarmException(String msg, Exception e)  {
        super(msg, e);
    }
}
