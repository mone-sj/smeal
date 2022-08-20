package com.campfire.smeal.handler;

import com.campfire.smeal.dto.ErrorResponseDto;
import com.campfire.smeal.handler.exception.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static com.campfire.smeal.handler.exception.SmErrorCode.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ErrorResponseDto handleException(
            GeneralException e,
            HttpServletRequest request
    ) {
        System.out.println("왜 exceptionhandler안됨?");
        log.error("errorCode: {}, url: {}, message: {}",
                e.getSmErrorCode(),request.getRequestURI(),e.getDetailMessage());

        return ErrorResponseDto.builder()
                .errorCode(e.getSmErrorCode())
                .errorMessage(e.getDetailMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponseDto handleException(
            Exception e,
            HttpServletRequest request
    ) {
        log.error("url: {}, message: {}",
                request.getRequestURI(),e.getMessage());

        // 사용자에게 보여줄 메시지
//        return ErrorResponseDto.builder()
//                .errorCode(INTERNAL_SERVER_ERROR)
//                .errorMessage(INTERNAL_SERVER_ERROR.getMessage())
//                .build();

        return ErrorResponseDto.builder()
                .errorCode(INTERNAL_SERVER_ERROR)
                .errorMessage(e.getMessage())
                .build();
    }
}
