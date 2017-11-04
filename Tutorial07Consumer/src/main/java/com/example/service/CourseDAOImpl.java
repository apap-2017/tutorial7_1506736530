package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.dao.CourseDAO;
import com.example.model.CourseModel;


@Service
public class CourseDAOImpl implements CourseDAO
{
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	   // Do any additional configuration here
	   return builder.build();
	}
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public CourseModel selectCourse (String id)
	{
		CourseModel course =
		restTemplate.getForObject(
		"http://localhost:8080/rest/course/view/"+id,
		CourseModel.class);
		System.out.println("Course " + course);
		return course;
	}
	
	@Override
	public List<CourseModel> selectAllCourses ()
	{
		ResponseEntity<List<CourseModel>> course =
				restTemplate.exchange(
				"http://localhost:8080/rest/course/viewall", HttpMethod.GET, null, new ParameterizedTypeReference<List<CourseModel>>() {
	            });
		List<CourseModel> courses = course.getBody();
		System.out.println("Course " + courses);
				return courses;
	}


}