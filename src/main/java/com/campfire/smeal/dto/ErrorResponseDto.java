package com.campfire.smeal.dto;

import com.campfire.smeal.handler.exception.SmErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponseDto {
    private SmErrorCode errorCode;
    private String errorMessage;

}
