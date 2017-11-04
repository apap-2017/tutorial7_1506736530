package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.CourseModel;
import com.example.model.StudentModel;
import com.example.service.CourseService;
import com.example.service.StudentService;

@Controller
public class StudentController
{
    @Autowired
    StudentService studentDAO;

    @Autowired
    CourseService courseDAO;

    @RequestMapping("/")
    public String index (Model model)
    {
    	model.addAttribute ("pagetitle", "Home");
        return "index";
    }


    @RequestMapping("/student/add")
    public String add (Model model)
    {
    	model.addAttribute ("pagetitle", "Add students");
        return "form-add";
    }


    @RequestMapping("/student/add/submit")
    public String addSubmit (
            @RequestParam(value = "npm", required = false) String npm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gpa", required = false) double gpa, Model model)
    {
    	StudentModel student = new StudentModel (npm, name, gpa, null);
        studentDAO.addStudent (student);
        model.addAttribute ("pagetitle", "Success Adding Student");
        return "success-add";
    }


    @RequestMapping("/student/view")
    public String view (Model model,
            @RequestParam(value = "npm", required = false) String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            model.addAttribute ("pagetitle", "Student data");
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


    @RequestMapping("/student/view/{npm}")
    public String viewPath (Model model,
            @PathVariable(value = "npm") String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            model.addAttribute ("pagetitle", "student Data");
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }

    @RequestMapping("/student/viewall")
    public String view (Model model)
    {
        List<StudentModel> students = studentDAO.selectAllStudents ();
        model.addAttribute ("students", students);
        model.addAttribute ("pagetitle", "View All Students");

        return "viewall";
    }


    @RequestMapping("/student/delete/{npm}")
    public String delete (Model model, @PathVariable(value = "npm") String npm)
    {
    	for(int i = 0; i< studentDAO.selectAllStudents().size(); i++){
    		if(studentDAO.selectAllStudents().get(i).getNpm().equalsIgnoreCase(npm)){
    			studentDAO.deleteStudent (npm);
    			model.addAttribute ("pagetitle", "Delete Student");
    			return "delete";
    		}
    	}
    	
        return "not-found";
    }
    
    @RequestMapping("/student/update/{npm}")
    public String update (Model model, StudentModel student)
    {
    	
    	List<StudentModel> list = studentDAO.selectAllStudents();
    	for(int i = 0; i< list.size(); i++){
    		if(list.get(i).getNpm().equalsIgnoreCase(student.getNpm())){
    			model.addAttribute("student", student);
    			model.addAttribute ("pagetitle", "Student Update");
    			return "form-update";
    		}
    	}
    	
        return "not-found";
    }
    
    @RequestMapping(value = "/student/update/submit", method = RequestMethod.POST)
    public String updateSubmit (@RequestParam(value = "npm", required = false) String npm,
    		@RequestParam(value = "name", required = false) String name,
    		@RequestParam(value = "gpa", required = false) double gpa, Model model)
    {
    	studentDAO.updateStudent(new StudentModel(npm, name, gpa, null));
    	model.addAttribute ("pagetitle", "Upate succesful");
        return "success-update";
    }
    
    @RequestMapping("/course/view/{id}")
    public String viewCoursePath (Model model,
            @PathVariable(value = "id") String id)
    {
        CourseModel course = courseDAO.selectCourse(id);

        System.out.println(course);
        if (course != null) {
        	
            model.addAttribute ("course", course);
            model.addAttribute ("pagetitle", "Course Data");
            return "view-course";
        } else {
        	model.addAttribute ("id", id);
            return "not-found_course";
        }
    }
    
    @RequestMapping("/course/viewall")
    public String courseView (Model model)
    {
        List<CourseModel> courses = courseDAO.selectAllCourse();
        model.addAttribute ("courses", courses);
        model.addAttribute ("pagetitle", "View All Courses");

        return "viewallCourse";
    }

}
