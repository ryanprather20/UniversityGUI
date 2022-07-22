package org.university.software;
import java.io.Serializable;

import org.university.people.*;

public class OnlineCourse extends Course implements Serializable{
	
	//default constructor
	OnlineCourse(){}

	OnlineCourse(String n, int c, int cr) { // parameterized constructor
		this.setName(n);
		this.setCourseNumber(c);
		this.setCreditUnits(cr);
		this.setModality(false); // online course
	}

	public void addStudentToRoster(Student aStudent){ //add student OR staff to 
		if (availableTo(aStudent)) {
			roster.add(aStudent);
		}
		/* “Student ‘student name’ has only ‘number of campus units’ enrolled. Students should
		 * have at least 6 units registered before registering for online courses.”
		 */
	}
	
	public Boolean availableTo(Student aStudent) {
		return aStudent.getEnrolledCredits() >= 6;
	}

	public void printSchedule() {}
}
