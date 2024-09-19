package org.licesys.common.documents;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@RequiredArgsConstructor
public class Owner {

    @Getter @Setter
    @Field
    private String idCard;

    @Getter @Setter
    @Field
    private String firstName;

    @Getter @Setter
    @Field
    private String lastName;

    @Getter @Setter
    @Field
    private Integer age;
}
