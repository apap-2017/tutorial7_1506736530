package com.example.dao;
import java.util.*;
import com.example.model.StudentModel;

public interface StudentDAO {
	StudentModel selectStudent(String npm);
	List<StudentModel> selectAllStudents();
}
