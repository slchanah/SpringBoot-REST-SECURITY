package com.appdeveloperblog.app.ws.mobileappws.ui.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationStatusModel {
    private String operationName;
    private String operationStatus;
}
