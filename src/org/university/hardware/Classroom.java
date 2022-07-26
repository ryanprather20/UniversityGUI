package org.university.hardware;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import org.university.software.*;

public class Classroom implements Serializable {
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	private String roomNumber;
	public ArrayList<CampusCourse> cCourses;
	private ArrayList<Integer> schedule;
	private static final String[] week = {"Mon", "Tue", "Wed", "Thu", "Fri"};
	private static final String[] slot = {"8:00am to 9:15am","9:30am to 10:45am","11:00am to 12:15pm",
			"2:00pm to 3:15pm","3:30pm to 4:45pm"};
	
	public Classroom() {
		cCourses = new ArrayList<CampusCourse>();
		schedule = new ArrayList<Integer>();
	}

	public String getRoomNumber() { return roomNumber; }
	public void setRoomNumber(String string) {
		this.roomNumber = string;
	}
	public ArrayList<CampusCourse> getCourseList() {
		return cCourses;
	}
	public void addCourse(CampusCourse cCourse) { // adds course to classroom's course list and fills all time slots
		cCourses.add(cCourse);
		schedule.addAll(cCourse.getSchedule());
	}
	
	public ArrayList<Integer> getSchedule() {
		return schedule;
	}

	public void printSchedule() { // Prints schedule in chronological order for readability
		System.out.println("\nThe schedule for classroom " + roomNumber);
		Collections.sort(schedule);
		for(Integer num : schedule) {
			int day = (num / 100) - 1;
			int time = (num % 10) - 1;

			System.out.print(week[day] + " " + slot[time] + " ");
			for(CampusCourse c : cCourses) {
				for(Integer i : c.getSchedule()) {
					if(num.equals(i)) {
						System.out.println(c.getDepartment().getDepartmentName()+ c.getCourseNumber() + " " + c.getName());
					}
				}
			}
		}
	}
}
