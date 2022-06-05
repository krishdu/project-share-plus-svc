package com.krishnendu.projectshareplussvc.repository.interfaces;

import com.krishnendu.projectshareplussvc.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Project Entity
 */
@Repository
public interface IProjectEntityRepository extends JpaRepository<ProjectEntity, String> {
}
