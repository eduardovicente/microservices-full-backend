package com.vicentemartinez.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vicentemartinez.user.model.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {}
