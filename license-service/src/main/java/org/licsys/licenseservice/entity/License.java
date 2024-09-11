package org.licsys.licenseservice.entity;


import java.time.LocalDate;

public class License {

    private Integer id;

    private String licenseNumber;

    private String ownerIdCard;

    private LocalDate issuedDate;

    private LocalDate expirationDate;

    private LicenseType type;


}
