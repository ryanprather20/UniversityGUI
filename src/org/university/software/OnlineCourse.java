package org.university.software;
import java.io.Serializable;

import org.university.people.*;

public class OnlineCourse extends Course implements Serializable{
	
	//default constructor
	OnlineCourse(){};
	OnlineCourse(String n, int c, int cr) {
		this.setName(n);
		this.setCourseNumber(c);
		this.setCreditUnits(cr);
	};
	
	//
	public void addStudentToRoster(Student aStudent){ //add student OR staff to 
		if (availableTo(aStudent) == true) {
			roster.add(aStudent);
		}
		/* “Student ‘student name’ has only ‘number of campus units’ enrolled. Students should
		 * have at least 6 units registered before registering for online courses.”
		 */
	}
	
	public Boolean availableTo(Student aStudent) {
		if (aStudent.getEnrolledCredits() >= 6) {
			return true;
		}
		else {
			return false;
		}
	}

	public void printSchedule() {}
}
