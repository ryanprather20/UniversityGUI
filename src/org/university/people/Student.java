package org.university.people;

import org.university.software.*;

import java.io.Serializable;

import org.university.hardware.*;

public class Student extends Person implements Serializable{
	//fields
	private Department department;
	private int completedUnits;
	private int RequiredCredits;
	public int enrolledCredits;
	public int oCredits;
	public int cCredits;
	private int tuitionFee;
	
	//default constructor
	public Student() {}
	
	//getters & setters
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	//////////////////////////////
	public int getCompletedUnits() {
		return completedUnits;
	}
	public void setCompletedUnits(int unitsCompleted) {
		this.completedUnits = unitsCompleted;
	}
	public int getRequiredCredits() {
		return RequiredCredits;
	}
	public void setRequiredCredits(int unitsNeededGrad) {
		this.RequiredCredits = unitsNeededGrad;
	}
	public int requiredToGraduate() {
		return (RequiredCredits - completedUnits - enrolledCredits);
	}
	public int getEnrolledCredits() {
		return enrolledCredits;
	}
	public void setEnrolledCredits(int enrolledCredits) {
		this.enrolledCredits = enrolledCredits;
	}
	/////////////////////
	public int getcCredits() {
		return cCredits;
	}
	public int getTuitionFee() {
		//campus
		tuitionFee += (cCredits * 300);
		//online
		for(OnlineCourse o : onlineCourseList) {
			if(o.getCreditUnits() == 3) {
				tuitionFee += 2000;
			}
			else {
				tuitionFee += 3000;
			}
		}
		return tuitionFee;
	}
	public void setTuitionFee(int tuitionFee) {
		//tuition is added when in respective addCourse
		this.tuitionFee = tuitionFee;
	}

	public void addCourse(CampusCourse cCourse) { //check for conflicts and if class is available to student
		if(!cCourse.availableTo(this)) {
			System.out.print(getName() + " can't add Campus Course " + cCourse.getDepartment().getDepartmentName() + cCourse.getCourseNumber()
					+ " " + cCourse.getName() + ". Because this Campus course has enough students.");
		}
		else if (!detectConflict(cCourse) && cCourse.availableTo(this)) { //add class and credits to enrolled credits to this student
			campusCourseList.add(cCourse);
			enrolledCredits = enrolledCredits + cCourse.getCreditUnits();
			cCredits = cCredits + cCourse.getCreditUnits();
			cCourse.addStudentToRoster(this);
		}
	}
	
	public void addCourse(OnlineCourse oCourse) { //check availability and add credits
		if(oCourse.availableTo(this)) {
			onlineCourseList.add(oCourse);
			enrolledCredits = enrolledCredits + oCourse.getCreditUnits();
			oCredits = oCredits + oCourse.getCreditUnits();
			oCourse.addStudentToRoster(this);
		}
		else { // student not enrolled in minimum amount of campus credits (6)
			System.out.print("Student " + this.getName() + " has only " + getcCredits() + " on campus credits enrolled. Should have at least ");
			System.out.println("6 credits registered before registering online courses.");
			System.out.print(this.getName() + " can't add online Course " + oCourse.getDepartment().getDepartmentName()+oCourse.getCourseNumber());
			System.out.println(" " + oCourse.getName() + ". Because this student doesn't have enough Campus course credit.");
		}
	}
	public void dropCourse(CampusCourse cCourse) { // checks remaining credits before the class is officially dropped in case there aren't enough credits
		if (campusCourseList.contains(cCourse)) {
			if  (this.getOnlineCourseList().size() >= 1) {
				//if student will have 6 credits left after dropping this class (to keep oCourse) then drop course
				if ((this.getcCredits() - cCourse.getCreditUnits()) >= 6) {
					//remove student from course, course from student, subtract credits
					cCourse.getStudentRoster().remove(this);
					this.getCampusCourseList().remove(cCourse);
					enrolledCredits -= cCourse.getCreditUnits();
					cCredits -= cCourse.getCreditUnits();
				}
				else { 
					System.out.println(this.getName()
							+ " can't drop this CampusCourse, because this student doesn't have enough campus course credit to hold the online course");
				}
			}
			else { //if not in online course drop course
				cCourse.getStudentRoster().remove(this);
				this.getCampusCourseList().remove(cCourse);
				enrolledCredits -= cCourse.getCreditUnits();
				cCredits -= cCourse.getCreditUnits();
			}
		}
		else { // if student is not enrolled in the class show error message
			System.out.print("The course "+cCourse.getDepartment().getDepartmentName()+cCourse.getCourseNumber());
			System.out.print(" could not be dropped because "+this.getName()+" is not enrolled in ");
			System.out.println(cCourse.getDepartment().getDepartmentName()+cCourse.getCourseNumber()+".");
		}
	}
	
	public void dropCourse(OnlineCourse oCourse) { //if in roster then drop
		if (onlineCourseList.contains(oCourse)) {			//if in roster drop
			oCourse.getStudentRoster().remove(this);
			this.getOnlineCourseList().remove(oCourse);				
			enrolledCredits -= oCourse.getCreditUnits();
			oCredits -= oCourse.getCreditUnits();
			return;
		}
		else {	//error message if not in roster
			System.out.print("The course "+oCourse.getDepartment().getDepartmentName()+oCourse.getCourseNumber());
			System.out.print(" could not be dropped because "+this.getName()+" is not enrolled in ");
			System.out.println(oCourse.getDepartment().getDepartmentName()+oCourse.getCourseNumber()+".");
			return;
		}
	}
}
