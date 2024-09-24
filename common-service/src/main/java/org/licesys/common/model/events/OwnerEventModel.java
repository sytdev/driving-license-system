package org.licesys.common.model.events;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
public class OwnerEventModel {

    @Getter @Setter
    private String idCard;

    @Getter @Setter
    private String firstName;

    @Getter @Setter
    private String lastName;

    @Getter @Setter
    private Integer age;

    @Getter @Setter
    private String createdBy;

    @Getter @Setter
    private LocalDateTime createdAt;

    @Getter @Setter
    private String modifiedBy;

    @Getter @Setter
    private LocalDateTime modifiedAt;
}
