package com.Eduline.LMS.courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@CrossOrigin
	@GetMapping("/getall")
	public List<Course> getAllCourses() {
		return courseService.getAllCourses();
	}

	@CrossOrigin
	@PostMapping("/add")
	public ResponseEntity<Course> addCourse(@RequestBody Course course) {
		Course newCourse = courseService.addCourse(course);
		return ResponseEntity.ok().body(newCourse);
	}

	@CrossOrigin
	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		String directory = "C:\\Users\\Shranay\\Desktop\\LMS_Angular_Spring\\UploadedMedia\\Thumbnails";
		String filename = file.getOriginalFilename();
		Path filepath = Paths.get(directory, filename);
		try {
			Files.write(filepath, file.getBytes());
			return ResponseEntity.ok().body(filepath.toString());
		} catch (IOException e) {
			return ResponseEntity.status(500).body("Failed to upload file: " + e.getMessage());
		}
	}

	@CrossOrigin
	@GetMapping("/unpublished/{instructorId}")
	public List<Course> getAllUnpublishedAndUndeletedCoursesByInstructorId(@PathVariable Long instructorId) {
		return courseService.getAllUnpublishedAndUndeletedCoursesByInstructorId(instructorId);
	}

	@CrossOrigin
	@GetMapping("/published/{instructorId}")
	public List<Course> getAllPublishedAndUndeletedCoursesByInstructorId(@PathVariable Long instructorId) {
		return courseService.getAllPublishedAndUndeletedCoursesByInstructorId(instructorId);
	}

	@CrossOrigin
	@PutMapping("/{id}/publish")
	public ResponseEntity<Course> updateIsPublished(@PathVariable Long id, @RequestBody Course request) {
		Course updatedCourse = courseService.updateIsPublishedById(id, request.getIsPublished());
		return ResponseEntity.ok(updatedCourse);
	}

	@CrossOrigin
	@PutMapping("/{id}/delete")
	public ResponseEntity<Course> updateIsDeleted(@PathVariable Long id, @RequestBody Course request) {
		Course updatedCourse = courseService.updateIsDeletedById(id, request.getIsDeleted());
		return ResponseEntity.ok(updatedCourse);
	}

	@CrossOrigin
	@PutMapping("/{id}/authorize")
	public ResponseEntity<Course> updateIsAuthorized(@PathVariable Long id, @RequestBody Course request) {
		Course updatedCourse = courseService.updateIsAuthorizedById(id, request.getIsAuthorized());
		return ResponseEntity.ok(updatedCourse);
	}

}
