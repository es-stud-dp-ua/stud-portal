package ua.dp.stud.studie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;



@Controller(value="CoursesController")
@RequestMapping(value = "VIEW")

public class CoursesController {
	@RenderMapping(params="view=allcourses")
	public String viewAllCourses() {
		return "viewAllCourses";
	}
	@RenderMapping(params="add=course")
	public String addCourse() {
		return "addCourse";
	}
	@RenderMapping(params="edit=course")
	public String editCourse() {
		return "editCourse";
	}
	@RenderMapping(params="view=course")
	public String viewCourse() {
		return "viewCourse";
	}
	@RenderMapping(params="delete=course")
	public String deleteCourse() {
		return "viewAllCourse";
	}
	@ActionMapping(value="saveCourse")
	public void saveCourse(){
	}
	@ActionMapping(value="saveNewCourse")
	public void saveNewCourse(){
	}
}