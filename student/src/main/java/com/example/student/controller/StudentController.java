package com.example.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.entity.Student;
import com.example.student.exception.ResourceNotFoundException;
import com.example.student.repository.StudentRepository;


@RestController
@RequestMapping("/api/tudents")
public class StudentController {
	
	@Autowired
	private StudentRepository student;
	
	
	//getall students
	@GetMapping
	public List<Student> getAllStudents(){
		return student.findAll();	
	}
	
	
	//get student by id
	@GetMapping("/{id}")
	public Student getStudentById(@PathVariable (value = "id") Long studentid ) {
	
		return student.findById(studentid).orElseThrow(() -> new ResourceNotFoundException("student not found with id :" + studentid));
		
	}
	
	//create student
	@PostMapping
	public Student createStudent(@RequestBody Student student) {
		
		// this represents the current object
		return this.student.save(student);     
	}
	
	
	//update student
	@PutMapping("/{id}")
	public Student updateStudent(@RequestBody Student student,@PathVariable (value="id") Long studentid) {
		//here we are finding the id and save in student object from that object we are updating
		Student existing =this.student.findById(studentid).orElseThrow(() -> new ResourceNotFoundException("student not found with id :" + studentid));
		
		existing.setFirstName(student.getFirstName());
		existing.setLastName(student.getLastName());
		existing.setEmailId(student.getEmailId());
		
		return this.student.save(existing);
	}
	
	
	//delete student
	@DeleteMapping("/{id}")
	public ResponseEntity<Student> deleteStudent(@PathVariable("id") Long studentid){
		//we has to get the existing id from db
		Student existing =this.student.findById(studentid).orElseThrow(() -> new ResourceNotFoundException("student not found with id :" + studentid));
		
		 student.delete(existing);
		 return ResponseEntity.ok().build();
		
	}

}
