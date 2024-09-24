package org.licesys.common.model.events;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
public class LicenseEventModel {

    @Getter @Setter
    private String licenseNumber;

    @Getter @Setter
    private LocalDate issueDate;

    @Getter @Setter
    private LocalDate expirationDate;

    @Getter @Setter
    private String type;

    @Getter @Setter
    private String status;

    //TODO replace to OwnerEventModel
    @Getter @Setter
    private String ownerIdCard;

    @Getter @Setter
    private String ownerFirstName;

    @Getter @Setter
    private String ownerLastName;

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
