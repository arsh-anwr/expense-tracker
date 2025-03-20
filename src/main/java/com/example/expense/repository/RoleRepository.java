package com.example.expense.repository;

import com.example.expense.entity.Role;
import com.example.expense.model.enums.RoleEnum;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, ObjectId> {
    Optional<Role> findByName(RoleEnum roleName);
}
