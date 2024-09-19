package org.licesys.common.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Embeddable
public class Audit {

    @Getter @Setter
    @Column(nullable = false, name = "CREATED_BY")
    private String createdBy;

    @Getter @Setter
    @Column(nullable = true, name = "MODIFIED_BY")
    private String lastModifiedBy;

    @Getter @Setter
    @Column(nullable = false, name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Getter @Setter
    @Column(nullable = true, name = "MODIFIED_AT")
    private LocalDateTime lastModifiedAt;

    public Audit(String createdBy){
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
    }

    public void setModifiedBy(String lastModifiedBy){
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedAt = LocalDateTime.now();
    }
}

