package org.university.people;

import java.io.Serializable;

import org.university.hardware.*; 
import org.university.software.*;

public class Staff extends Employee implements Serializable{
	//fields
	private Department department;
	private double payRate;
	private int monthlyHours; //per month
	private int tuitionFee;
	private Course currCourse;

	//default constructor
	public Staff(){
		this.currCourse = null;
	};
	
	//getters and setters
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public double getPayRate() {
		return payRate;
	}
	public void setPayRate(double payRate) {
		this.payRate = payRate;
	}
	public double earns() {
		return (payRate * monthlyHours);
	}
	public void raise(double percent) {
		payRate = payRate * (1 + (percent/100));
	}
	public int getMonthlyHours() {
		return monthlyHours;
	}
	public void setMonthlyHours(int i) {
		monthlyHours = i;
	}
	public int getTuitionFee() {
		//cCredits * 300 or online 2000 for 3 credits, 3000 for 4 credits
		//calculated in respective addCourse
		return tuitionFee;
	}
	public void setTuitionFee(int tuitionFee) {
		this.tuitionFee = tuitionFee;
	}

	public void addCourse(CampusCourse cCourse) {
			if (currCourse == null) { //if taking no class currently
				campusCourseList.add(cCourse);
				tuitionFee = (cCourse.getCreditUnits() * 300);
				cCourse.addStudentToRoster(this);
				currCourse = cCourse;
			}
			else { //warn and switch class and set tuitionFee
				System.out.print(currCourse.getDepartment().getDepartmentName() + currCourse.getCourseNumber());
				System.out.print(" is removed from " + this.getName() + "'s schedule (Staff can only take one class at a time). ");
				System.out.print(cCourse.getDepartment().getDepartmentName() + cCourse.getCourseNumber());
				System.out.println(" has been added to " + this.getName() + "'s Schedule.");
				campusCourseList.clear();
				onlineCourseList.clear();
				campusCourseList.add(cCourse);
				tuitionFee = (cCourse.getCreditUnits() * 300);
				cCourse.addStudentToRoster(this);
				currCourse = cCourse;
			}
	}
	public void addCourse(OnlineCourse oCourse) {
		if (currCourse == null) { //if taking no classes
			int x = oCourse.getCreditUnits();
			if (x == 3) {tuitionFee = 2000;}
			else {tuitionFee = 3000;}
			oCourse.addStudentToRoster(this);
			onlineCourseList.add(oCourse);
			currCourse = oCourse;
		}
		else {
			System.out.print(currCourse.getDepartment().getDepartmentName() + currCourse.getCourseNumber());
			System.out.print(" is removed from " + this.getName() + "'s schedule(Staff can only take one class at a time). ");
			System.out.print(oCourse.getDepartment().getDepartmentName() + oCourse.getCourseNumber());
			System.out.println(" has been added to " + this.getName() + "'s Schedule.");
			onlineCourseList.clear();
			campusCourseList.clear();
			onlineCourseList.add(oCourse);

			int x = oCourse.getCreditUnits();
			if (x == 3) tuitionFee = 2000;
			else tuitionFee = 3000;
			oCourse.addStudentToRoster(this);
			currCourse = oCourse;
		}
	}	
}
