package com.petcare.pet_care.adapters.outbound.repositories;

import com.petcare.pet_care.adapters.outbound.entities.JpaUserEntity;
import com.petcare.pet_care.adapters.outbound.persistence.user.UserMapper;
import com.petcare.pet_care.domain.user.User;
import com.petcare.pet_care.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        JpaUserEntity saved = jpaUserRepository.save(userMapper.toJpaEntity(user));
        return userMapper.toDomain(saved);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaUserRepository.findById(id)
                .map(userMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    public void deleteById(UUID id) {
        jpaUserRepository.deleteById(id);
    }
}
