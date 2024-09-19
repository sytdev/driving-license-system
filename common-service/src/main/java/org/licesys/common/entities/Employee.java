package org.licesys.common.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Employee {

    @Id
    private Integer id;

    private String code;

    private String firstName;

    private String lastName;
}
