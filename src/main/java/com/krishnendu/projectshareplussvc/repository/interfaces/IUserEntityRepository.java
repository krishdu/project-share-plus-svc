package com.krishnendu.projectshareplussvc.repository.interfaces;

import com.krishnendu.projectshareplussvc.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for User Entity
 */
@Repository
public interface IUserEntityRepository extends JpaRepository<UserEntity, String> {
    @Query("FROM UserEntity WHERE userEmail=?1")
    UserEntity findByAttributeUserEmail(String email);
}
