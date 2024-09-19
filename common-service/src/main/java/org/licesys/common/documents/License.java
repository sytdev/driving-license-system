package org.licesys.common.documents;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.licesys.common.entities.LicenseType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

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
    @Field
    private Owner owner;

    @Getter @Setter
    @Field
    private LocalDate issuedDate;

    @Getter @Setter
    @Field
    private LocalDate expirationDate;

    @Getter @Setter
    @Field
    private String type;

    @Getter @Setter
    @Field
    private String status;
}
