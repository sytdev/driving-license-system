package org.licesys.common.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name = "OWNER")
public class Owner extends GenericEntity {

    @Getter @Setter
    @Column(unique = true, nullable = false, name = "ID_CARD")
    private String idCard;

    @Getter @Setter
    @Column(nullable = false, name = "FIRST_NAME")
    private String firstName;

    @Getter @Setter
    @Column(nullable = false, name = "LAST_NAME")
    private String lastName;

    @Getter @Setter
    @Column(nullable = false, name = "AGE")
    private Integer age;

}
