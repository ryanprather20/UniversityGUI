package org.university.hardware;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

import org.university.people.*;
import org.university.software.*;

public class Department implements Serializable{

	@Serial
	private static final long serialVersionUID = 1L;
	
	private String deptName;
	private ArrayList<Professor> professorList;
	private ArrayList<Student> studentList;
	private ArrayList<Staff> staffList;
	public ArrayList<Course> courseList;
	public ArrayList<CampusCourse> cCourseList;
	public ArrayList<OnlineCourse> oCourseList;

	// Default Constructor
	public Department() {
		professorList = new ArrayList<Professor>();
		studentList = new ArrayList<Student>();
		staffList = new ArrayList<Staff>();
		courseList = new ArrayList<Course>();
		cCourseList = new ArrayList<CampusCourse>();
		oCourseList = new ArrayList<OnlineCourse>();
	}
	
	public String getDepartmentName() {
		return deptName;
	}
	public void setDepartmentName(String name) {
		this.deptName = name;
	}
	public ArrayList<Professor> getProfessorList() {
		return professorList;
	}
	public ArrayList<Student> getStudentList() {
		return studentList;
	}
	public ArrayList<Staff> getStaffList() {
		return staffList;
	}
	public ArrayList<Course> getAllCourseList() {
		return courseList;
	}
	public ArrayList<CampusCourse> getCCourses() {
		return cCourseList;
	}
	public ArrayList<OnlineCourse> getOCourses() {
		return oCourseList;
	}
	public void addStudent(Student s1) {
		studentList.add(s1);
	}

	public void addCourse(CampusCourse c1) {
		courseList.add(c1);
		cCourseList.add(c1);
		c1.setDepartment(this);
	}

	public void addCourse(OnlineCourse o1) {
		courseList.add(o1);
		oCourseList.add(o1);
		o1.setDepartment(this);
	}

	public void addProfessor(Professor p1) {
		professorList.add(p1);
	}

	public void addStaff(Staff sf1) {
		staffList.add(sf1);
	}

	public void printCourseList() {  // for testing
		for(Course c : courseList) {
			System.out.println(c.getDepartment().getDepartmentName() + c.getCourseNumber()
					+ " " + c.getName());
		}
	}

	public void printStudentList() {  // for testing
		for(Student s : studentList) {
			System.out.println(s.getName());
		}
	}

	public void printProfessorList() {  // for testing
		for(Professor p : professorList) {
			System.out.println(p.getName());
		}
	}
	public void printStaffList() {  // for testing
		if(!staffList.isEmpty()) {
			for (Staff s : staffList) {
				System.out.println(s.getName());
			}
		}
		else {
			System.out.println("No staff to display");
		}
	}

	public void printAll() { // used for University admin's print all button
		System.out.println("\nDepartment " + deptName);

		System.out.println("\nPrinting professor schedules:");
		for(Professor p : professorList) {
			System.out.println("\nThe schedule for Prof. " + p.getName() + ":");
			p.printSchedule();
		}

		System.out.println("\nPrinting student schedules:");
		for(Student s :studentList) {
			System.out.println("\nThe schedule for student " + s.getName() + ":");
			s.printSchedule();
		}

		System.out.println("\nPrinting staff schedules:");
		for(Staff s : staffList) {
			System.out.println("\nThe schedule for employee " + s.getName() + ":");
			s.printSchedule();
			double x = s.getMonthlyHours() * s.getPayRate();
			System.out.println("\nStaff: " + s.getName() + " earns " + x + " this month");
		}

		System.out.println("\nThe rosters for courses offered by " + deptName);
		for(Course c : cCourseList) {
			System.out.println("\nThe roster for course " + deptName + c.getCourseNumber());
			c.printRoster();
		}
	}
}
