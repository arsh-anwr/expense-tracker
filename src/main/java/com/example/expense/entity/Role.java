package com.example.expense.entity;

import com.example.expense.model.enums.RoleEnum;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "roles")
public class Role {

    @Id
    private ObjectId id;

    @NonNull
    @Indexed(unique = true)
    private RoleEnum name;

    @NonNull
    private String description;
}
