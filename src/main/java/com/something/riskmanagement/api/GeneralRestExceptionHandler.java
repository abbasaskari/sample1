package com.something.riskmanagement.api;

import com.something.riskmanagement.api.model.ResponseModel;
import com.something.riskmanagement.api.model.ResponseType;
import com.something.riskmanagement.common.config.ServiceProperties;
import com.something.riskmanagement.common.exception.AuthenticationException;
import com.something.riskmanagement.common.exception.BaseException;
import com.something.riskmanagement.common.exception.BaseRuntimeException;
import com.something.riskmanagement.common.util.LogUtil;
import com.something.riskmanagement.common.util.message.MessageUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by h_gohargazi
 * on 8/23/2022
 */

@ControllerAdvice
public class GeneralRestExceptionHandler {
    private static final Logger LOG = LogUtil.getDefaultLogger(GeneralRestExceptionHandler.class);

    private final ServiceProperties props;
    private final MessageUtil messageUtil;

    @Autowired
    public GeneralRestExceptionHandler(ServiceProperties props) {
        this.props = props;
        this.messageUtil = new MessageUtil(props.getMessageFileNames());
    }

    @ExceptionHandler({org.springframework.security.core.AuthenticationException.class})
    private ResponseEntity<ResponseModel<?>> authenticationExceptionHandler(org.springframework.security.core.AuthenticationException ex) {
        throw ex;
    }

    @ExceptionHandler({AccessDeniedException.class})
    private ResponseEntity<ResponseModel<?>> accessDeniedExceptionHandler(AccessDeniedException ex) {
        throw ex;
    }

    @ExceptionHandler({AuthenticationException.class})
    private ResponseEntity<ResponseModel<?>> authenticationExceptionHandler(AuthenticationException ex) {
        String message = messageUtil.getMessage(ex.getMessage(), ex.getParams(), "", props.getLocale());
        throw new org.springframework.security.core.AuthenticationException(message) {
            @Override
            public String getMessage() {
                return super.getMessage();
            }
        };
    }

    @ExceptionHandler({BaseException.class})
    private ResponseEntity<ResponseModel<?>> baseExceptionHandler(BaseException ex) {
        String message = messageUtil.getMessage(ex.getMessage(), ex.getParams(), "", props.getLocale());
        ResponseModel<?> responseModel = new ResponseModel<>(ResponseType.FAILURE, null, message);
        LOG.error(message, ex);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @ExceptionHandler({BaseRuntimeException.class})
    private ResponseEntity<ResponseModel<?>> baseRuntimeExceptionHandler(BaseRuntimeException ex) {
        String message = messageUtil.getMessage(ex.getMessage(), ex.getParams(), "", props.getLocale());
        ResponseModel<?> responseModel = new ResponseModel<>(ResponseType.FAILURE, null, message);
        LOG.error(message, ex);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    private ResponseEntity<ResponseModel<?>> methodArgumentValidationExceptionHandler(MethodArgumentNotValidException ex) {
        ArrayList<String> messages = getAllValidationMessages(ex);
        String message = concatMessages(messages);
        ResponseModel<?> responseModel = new ResponseModel<>(ResponseType.FAILURE, null, message);
        LOG.error(message, ex);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @ExceptionHandler({Throwable.class})
    private ResponseEntity<ResponseModel<?>> throwableExceptionHandler(Throwable ex) {
        String message = messageUtil.getMessage("system.exception.unmanaged", null, "", props.getLocale());
        ResponseModel<?> responseModel = new ResponseModel<>(ResponseType.SERVER_ERROR, null, message);
        LOG.error(message, ex);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    private String concatMessages(ArrayList<String> messages) {
        if (messages.size() == 0) return "";
        StringBuilder message = new StringBuilder();
        for (String mess : messages) {
            message.append(mess).append("|");
        }
        message.deleteCharAt(message.length() - 1);
        return message.toString();
    }

    private ArrayList<String> getAllValidationMessages(MethodArgumentNotValidException ex) {
        String message = null;
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        ArrayList<String> messages = new ArrayList<>(fieldErrors.size());
        for (FieldError fieldError : fieldErrors) {
            String defaultMessage = fieldError.getDefaultMessage();
            message = messageUtil.getMessage(defaultMessage, null, "پیام خطای مورد نظر یافت نشد", props.getLocale());
            messages.add(message);
        }
        return messages;
    }

}
