package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.dao.CourseDAO;
import com.example.model.CourseModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Primary
public class CourseServiceRest implements CourseService{
	@Autowired
	private CourseDAO courseDAO;
	
	@Override
	public List<CourseModel> selectAllCourse() {
		log.info("REST - select all courses");
		return courseDAO.selectAllCourses();
	}

	@Override
	public CourseModel selectCourse(String id) {
		// TODO Auto-generated method stub
		return courseDAO.selectCourse(id);
	}
}
