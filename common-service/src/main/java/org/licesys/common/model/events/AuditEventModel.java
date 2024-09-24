package org.licesys.common.model.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
public class AuditEventModel {

    @Getter @Setter
    private String action;

    @Getter @Setter
    private LocalDateTime operationDate;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String userFullName;

}
