package org.university.people;

import java.io.Serializable;

import org.university.hardware.*;
import org.university.software.*;

public class Professor extends Employee implements Serializable{
	//fields
	private double salary;
	private Department department;
	
	//Default constructor
	public Professor() {
		this.salary = 0;
		this.department = null;
	}

	//getters setters
	public double getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department aDepartment) {
		this.department = aDepartment;
	}
	
	public double earns() {
		return salary/26;
	}

	public void raise(double percent) {
		salary = salary * (1 + (percent/100));
	}

	public void addCourse(CampusCourse cCourse) {
		if((cCourse.getProfessor() == null) && (!detectConflict(cCourse))) { //if there is no professor assigned and there is no schedule conflict
				cCourse.setProfessor(this);
				campusCourseList.add(cCourse);
		}
		else {
			System.out.print("The professor " + this.getName() + " cannot be assigned to this campus course because professor "
					+ cCourse.getProfessor().getName()+" is already assigned to the course " + cCourse.getName() + ".");
		} 
	}

	public void addCourse(OnlineCourse oCourse) {
		if(oCourse.getProfessor() == null) { //if there is no professor assigned
			oCourse.setProfessor(this);
			onlineCourseList.add(oCourse);
		}
		else {
			System.out.print("The professor cannot be assigned to this online course because professor " + oCourse.getProfessor().getName()
					+ " is already assigned to the online course " + oCourse.getName() + ".");
		}
	}
	
}
