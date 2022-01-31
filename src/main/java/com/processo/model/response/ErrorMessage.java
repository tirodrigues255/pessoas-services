package com.processo.model.response;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {

    private Date dateTime;
    private String message;
}
