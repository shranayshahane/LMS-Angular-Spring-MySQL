package com.Eduline.LMS.courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	public List<Course> getAllCourses(){
		return courseRepository.findAll();
	}

	public Course addCourse(Course course) {
		return courseRepository.save(course);
	}

	public String uploadFile(MultipartFile file) throws Exception {
		// Specify the directory where the file will be saved
		String directory = "http://127.0.0.1:8887/Thumbnails";

		// Create the directory if it doesn't exist
		Path path = Paths.get(directory);
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}

		// Get the original file name and generate a new unique file name
		String originalFileName = file.getOriginalFilename();
		String uniqueFileName = System.currentTimeMillis() + "_" + originalFileName;

		// Save the file to the specified directory with the unique file name
		Path filePath = Paths.get(directory + uniqueFileName);
		Files.write(filePath, file.getBytes());

		// Return the URL of the saved file
		String serverUrl = "http://127.0.0.1:8887/"; // Change this to the URL of your web server
		return serverUrl + uniqueFileName;
	}

	public List<Course> getAllUnpublishedCoursesByInstructorId(Long instructorId) {
		return courseRepository.findByIsPublishedFalseAndInstructorId(instructorId);
	}

	public List<Course> getAllPublishedCoursesByInstructorId(Long instructorId) {
		return courseRepository.findByIsPublishedTrueAndInstructorId(instructorId);
	}

	public List<Course> getAllUnpublishedAndUndeletedCoursesByInstructorId(Long instructorId) {
		return courseRepository.findByIsPublishedFalseAndIsDeletedFalseAndInstructorId(instructorId);
	}

	public List<Course> getAllPublishedAndUndeletedCoursesByInstructorId(Long instructorId) {
		return courseRepository.findByIsPublishedTrueAndIsDeletedFalseAndInstructorId(instructorId);
	}

	public Course updateIsPublishedById(Long courseId, boolean isPublished) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new RuntimeException("Course not found"));
		course.setIsPublished(isPublished);
		return courseRepository.save(course);
	}

	public Course updateIsDeletedById(Long courseId, boolean isDeleted) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new RuntimeException("Course not found"));
		course.setIsDeleted(isDeleted);
		return courseRepository.save(course);
	}

	public Course updateIsAuthorizedById(Long courseId, boolean isAuthorized) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new RuntimeException("Course not found"));
		course.setIsAuthorized(isAuthorized);
		return courseRepository.save(course);
	}

}
