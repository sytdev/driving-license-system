package org.licesys.common.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "audits")
public class Audit {

    @EqualsAndHashCode.Include
    @Getter
    @Id
    private String id;

    @Getter @Setter
    @Field
    private String action;

    @Getter @Setter
    @Field
    private LocalDateTime operationDate;

    @Getter @Setter
    @Field
    private String username;

    @Getter @Setter
    @Field
    private String userFullName;
}
