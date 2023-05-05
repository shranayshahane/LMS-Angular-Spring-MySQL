package com.Eduline.LMS.course_materials;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseMaterialRepository extends JpaRepository<CourseMaterial, Long> {
	List<CourseMaterial> findByCourseId(Long courseId);

}