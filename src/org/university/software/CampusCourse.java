package org.university.software;

import java.io.Serializable; 
import java.util.ArrayList;
import java.util.Collections;

import org.university.hardware.*;
import org.university.people.*;

public class CampusCourse extends Course implements Serializable{
	
	//fields
	private int maxStudents;
	private ArrayList<Integer> schedule;
	private Classroom classroom;
	private static String[] week = {"Mon", "Tue", "Wed", "Thu", "Fri"};
	private static String[] slot = {"8:00am to 9:15am","9:30am to 10:45am","11:00am to 12:15pm","2:00pm to 3:15pm","3:30pm to 4:45pm"};
	
	//default constructor
	CampusCourse() {
		schedule = new ArrayList<Integer>();
	}
	//Parameterized Constructor
	CampusCourse(String name, int courseNum, int max, int credits) {
		this.setName(name);
		this.setCourseNumber(courseNum);
		this.setMaxCourseLimit(max);
		this.setCreditUnits(credits);
	}
	
	//getters setters
	public int getMaxCourseLimit() {
		return maxStudents;
	}
	public void setMaxCourseLimit(int maxStudents) {
		this.maxStudents = maxStudents;
	}
	public ArrayList<Integer> getSchedule() {
		return schedule;
	}
	public void setSchedule(int time) {
		schedule.add(time);
	}
	public Classroom getClassroom() {
		return classroom;
	}
	
	public void setRoomAssigned(Classroom cr2) {
		if(!detectClassroomConflict(cr2)) {
			classroom = cr2;
			cr2.addCourse(this);
		}
	}
	
	public void setRoomGUI(Classroom c) {
		classroom = c;
		c.addCourse(this);
	}
	
	public Boolean detectClassroomConflict(Classroom r) {
		Boolean check = false;
		for(Integer i : getSchedule()) {
			for(CampusCourse cc : r.getCourseList()) {
				for(Integer j : cc.getSchedule()) {
					if(i.equals(j)) {
						System.out.print(this.department.getDepartmentName() + this.getCourseNumber());
						System.out.print(" conflicts with " + cc.getDepartment().getDepartmentName() + cc.getCourseNumber() + ". ");
						
						int day = (j / 100) - 1;
						int time = (j  % 10) - 1;
						
						System.out.print("Conflicting time slot " + week[day] + " " + slot[time] + ". ");
						System.out.print(this.department.getDepartmentName() + this.getCourseNumber() + " course cannot be added to ");
						System.out.println(r.getRoomNumber() + "'s Schedule.");
						check = true;
					}
				}
			}
		}
		return check;
	}
	
	public Boolean detectClassroomConflictBOOL(Classroom r) {
		Boolean check = false;
		for(Integer i : getSchedule()) {
			for(CampusCourse cc : r.getCourseList()) {
				for(Integer j : cc.getSchedule()) {
					if(i.equals(j)) {
						check = true;
					}
				}
			}
		}
		return check;
	}
	
	public String getConflictingClassroomSTRING(Classroom r) {
		String x = null;
		Loop:
		for(Integer i : getSchedule()) {
			for(CampusCourse cc : r.getCourseList()) {
				for(Integer j : cc.getSchedule()) {
					if(i.equals(j)) {
						x = this.department.getDepartmentName() + this.getCourseNumber();
						x = x.concat(" conflicts with " + cc.getDepartment().getDepartmentName() + cc.getCourseNumber() + ". ");
						
						int day = (j / 100) - 1;
						int time = (j  % 10) - 1;
						
						x = x.concat("Conflicting time slot " + week[day] + " " + slot[time] + ". ");
						x = x.concat(this.department.getDepartmentName() + this.getCourseNumber() + " course cannot be added to ");
						x = x.concat(r.getRoomNumber() + "'s Schedule.");
						break Loop;
					}
				}
			}
		}
		return x;
	}
	
	//methods
	public Boolean availableTo(Student aStudent) {
		if (roster.size() < maxStudents) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Boolean availableTo(Staff aStaff) {
		if (roster.size() < maxStudents) {
			return true;
		}
		else {
			return false;
		}
	}

	public void printSchedule() {
		Collections.sort(schedule);
		for(Integer num : schedule) {
			int day = (num / 100) - 1;
			int time = (num % 10) - 1;
			System.out.println(week[day] + " " + slot[time] + " " + this.getClassroom().getRoomNumber());
		}
	}
}