package com.petcare.pet_care.adapters.outbound.persistence.user;

import com.petcare.pet_care.adapters.outbound.entities.JpaUserEntity;
import com.petcare.pet_care.domain.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(JpaUserEntity entity) {
        if (entity == null) {
            return null;
        }

        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setEmail(entity.getEmail());
        user.setPasswordHash(entity.getPasswordHash());
        user.setRole(entity.getRole());
        user.setCreatedAt(entity.getCreatedAt());

        return user;
    }

    public JpaUserEntity toJpaEntity(User user) {
        if (user == null) {
            return null;
        }

        JpaUserEntity entity = new JpaUserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setPasswordHash(user.getPasswordHash());
        entity.setRole(user.getRole());
        entity.setCreatedAt(user.getCreatedAt());

        return entity;
    }
}
