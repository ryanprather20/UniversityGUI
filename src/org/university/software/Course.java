package org.university.software;

import java.io.Serializable;
import java.util.ArrayList;

import org.university.hardware.*;
import org.university.people.*;

public abstract class Course implements Serializable {
	//fields
	private String name;
	protected Department department;
	private int courseNumber;
	protected ArrayList<Person> roster;
	private Professor professor;
	private int creditUnits;
	
	//default constructor
	Course() {
		roster = new ArrayList<Person>();
	}
	
	//getters & setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public int getCourseNumber() {
		return courseNumber;
	}
	public void setCourseNumber(int courseNumber) {
		this.courseNumber = courseNumber;
	}
	public ArrayList<Person> getStudentRoster() {
		return roster;
	}
	public void printRoster() {
		for(Person p : roster) {
			System.out.println(p.getName());
		}
	}
	public Professor getProfessor() {
		return professor;
	}
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	public int getCreditUnits() {
		return creditUnits;
	}
	public void setCreditUnits(int creditUnits) {
		this.creditUnits = creditUnits;
	}

	//methods
	public void addStudentToRoster(Person aPerson){ //add student OR staff to 
			roster.add(aPerson);
	}
	
	public abstract Boolean availableTo(Student aStudent);

	public abstract void printSchedule();	
}
