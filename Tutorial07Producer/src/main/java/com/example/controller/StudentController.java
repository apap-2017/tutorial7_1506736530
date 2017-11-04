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
import com.example.service.StudentService;

@Controller
public class StudentController
{
    @Autowired
    StudentService studentDAO;


    @RequestMapping("/")
    public String index ()
    {
        return "index";
    }


    @RequestMapping("/student/add")
    public String add ()
    {
        return "form-add";
    }


    @RequestMapping("/student/add/submit")
    public String addSubmit (
            @RequestParam(value = "npm", required = false) String npm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gpa", required = false) double gpa)
    {
    	StudentModel student = new StudentModel (npm, name, gpa, null);
        studentDAO.addStudent (student);

        return "success-add";
    }


    @RequestMapping("/student/view")
    public String view (Model model,
            @RequestParam(value = "npm", required = false) String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
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

        return "viewall";
    }


    @RequestMapping("/student/delete/{npm}")
    public String delete (Model model, @PathVariable(value = "npm") String npm)
    {
    	for(int i = 0; i< studentDAO.selectAllStudents().size(); i++){
    		if(studentDAO.selectAllStudents().get(i).getNpm().equalsIgnoreCase(npm)){
    			studentDAO.deleteStudent (npm);
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
    			return "form-update";
    		}
    	}
    	
        return "not-found";
    }
    
    @RequestMapping(value = "/student/update/submit", method = RequestMethod.POST)
    public String updateSubmit (@RequestParam(value = "npm", required = false) String npm,
    		@RequestParam(value = "name", required = false) String name,
    		@RequestParam(value = "gpa", required = false) double gpa)
    {
    	studentDAO.updateStudent(new StudentModel(npm, name, gpa, null));
        return "success-update";
    }
    
    @RequestMapping("/course/view/{id}")
    public String viewCoursePath (Model model,
            @PathVariable(value = "id") String id)
    {
        CourseModel course = studentDAO.selectCourse(id);

        if (course != null) {
            model.addAttribute ("course", course);
            return "view-course";
        } else {
        	model.addAttribute ("id", id);
            return "not-found_course";
        }
    }
}
