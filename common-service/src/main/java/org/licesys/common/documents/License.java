package org.licesys.common.documents;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.licesys.common.entities.LicenseType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "licenses")
public class License {

    @EqualsAndHashCode.Include
    @Getter
    @Id
    private String id;

    @Getter @Setter
    @Field
    private String licenseNumber;

    @Getter @Setter
    @DocumentReference
    private Owner owner;

    @Getter @Setter
    @Field
    private LocalDate issueDate;

    @Getter @Setter
    @Field
    private LocalDate expirationDate;

    @Getter @Setter
    @Field
    private String type;

    @Getter @Setter
    @Field
    private String status;

    @Getter @Setter
    @Field
    private String createdBy;

    @Getter @Setter
    @Field
    private LocalDateTime createdAt;

    @Getter @Setter
    @Field(write = Field.Write.ALWAYS)
    private String modifiedBy;

    @Getter @Setter
    @Field(write = Field.Write.ALWAYS)
    private LocalDateTime modifiedAt;
}
