package org.licesys.common.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Entity
@Table(name = "LICENSE")
public class License extends GenericEntity {

    @Getter @Setter
    @Column(unique = true, nullable = false, name = "LICENSE_NUMBER")
    private String licenseNumber;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "OWNER_ID", nullable = false, referencedColumnName = "ID")
    private Owner owner;

    @Getter @Setter
    @Column(nullable = false, name = "ISSUE_DATE")
    private LocalDate issueDate;

    @Getter @Setter
    @Column(nullable = false, name = "EXPIRATION_DATE")
    private LocalDate expirationDate;

    @Getter @Setter
    @Column(nullable = false, name = "TYPE")
    private LicenseType type;

    @Getter @Setter
    @Column(nullable = false, name = "STATUS")
    private Status status;
}
