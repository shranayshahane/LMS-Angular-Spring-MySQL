package com.Eduline.LMS.courses;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

	List<Course> findByIsPublishedFalseAndInstructorId(Long instructorId);

	List<Course> findByIsPublishedTrueAndInstructorId(Long instructorId);

	List<Course> findByIsPublishedFalseAndIsDeletedFalseAndInstructorId(Long instructorId);

	List<Course> findByIsPublishedTrueAndIsDeletedFalseAndInstructorId(Long instructorId);

}