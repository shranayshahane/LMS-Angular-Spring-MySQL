package com.Eduline.LMS.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

	List<Enrollment> findByStudentId(Long studentId);

	List<Enrollment> findByCourseId(Long courseId);

	Enrollment findByCourseIdAndStudentId(Long courseId, Long studentID);

	void deleteByCourseIdAndStudentId(Long courseId, Long studentId);
}