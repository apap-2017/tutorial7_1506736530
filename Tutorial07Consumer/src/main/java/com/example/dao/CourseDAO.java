package com.example.dao;
import java.util.*;

import com.example.model.CourseModel;

public interface CourseDAO {
	CourseModel selectCourse(String id);
	List<CourseModel> selectAllCourses();
}
