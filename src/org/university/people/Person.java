package org.university.people;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList; 
import java.util.Collections;

import org.university.software.*;

public abstract class Person implements Serializable{

	@Serial
	private static final long serialVersionUID = 1L;

	private String name;
	public ArrayList<CampusCourse> campusCourseList; // Courses accessible by university
	public ArrayList<OnlineCourse> onlineCourseList;
	private static final String[] week = {"Mon", "Tue", "Wed", "Thu", "Fri"};
	private static final String[] slot = {"8:00am to 9:15am","9:30am to 10:45am","11:00am to 12:15pm",
			"2:00pm to 3:15pm","3:30pm to 4:45pm"};
	
	//default constructor
	Person() {
		campusCourseList = new ArrayList<CampusCourse>();
		onlineCourseList = new ArrayList<OnlineCourse>();
	}
	
	// getters / setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<CampusCourse> getCampusCourseList() {
		return campusCourseList;
	}
	public ArrayList<OnlineCourse> getOnlineCourseList() {
		return onlineCourseList;
	}
	
	/*
	 * person can only add classes that do not create schedule conflicts 
	 * method detects conflicts by comparing input course's schedule to person's schedule and 
	 * returns boolean value along with an error message with specific conflict
	*/
	public Boolean detectConflict(CampusCourse newCourse) {
		boolean check = false;

		// used nested for loops to be able to retain info about the course for error message
		for (Integer i : newCourse.getSchedule()) {
			for (CampusCourse cc : campusCourseList) {
				for(Integer j : cc.getSchedule() ) {
					if(i.equals(j)) {
						System.out.print(newCourse.getDepartment().getDepartmentName() + newCourse.getCourseNumber()
								+ " course cannot be added to " + this.name + "'s Schedule. " + newCourse.getDepartment().getDepartmentName()
								+ newCourse.getCourseNumber() + " conflicts with " + cc.getDepartment().getDepartmentName()
								+ cc.getCourseNumber() + ". ");
						
						int day = (j / 100) - 1; // retrieving day of week and time slot
						int time = (j % 10) - 1;
						
						System.out.println("Conflicting time slot is " + week[day] + " " + slot[time] + ".");
						
						check = true; // return value set to signal that there is a conflict
					}
				}
			}
		}
		return check;
	}

	//TODO either merge or figure out better way to do this, seems redundant to have a string+bool then also separate methods
	public Boolean detectConflictBool(CampusCourse aCourse) { // returns boolean value with no error message
		boolean check = false;
		for (Integer i : aCourse.getSchedule()) {
			for (CampusCourse cc : campusCourseList) {
				for(Integer j : cc.getSchedule() ) {
					if (i.equals(j)) {
						check = true;
						break;
					}
				}
			}
		}
		return check;
	}
	
	public String detectConflictString(CampusCourse aCourse) {
		String confString = null;
		for (Integer i : aCourse.getSchedule()) {
			for (CampusCourse cc : campusCourseList) {
				for(Integer j : cc.getSchedule() ) {
					if(i.equals(j)) {
						
						confString = aCourse.getDepartment().getDepartmentName() + aCourse.getCourseNumber()
							+ " course cannot be added to ";
						confString = confString.concat(this.name + "'s Schedule. "
								+ aCourse.getDepartment().getDepartmentName());
						confString = confString.concat(aCourse.getCourseNumber() + " conflicts with "
								+ cc.getDepartment().getDepartmentName() + cc.getCourseNumber() + ". ");
						
						int day = (j / 100) - 1;
						int time = (j % 10) - 1;
						confString = confString.concat("Conflicting time slot is " + week[day] + " " + slot[time] + ".");
					}
				}
			}
		}
		return confString;
	}

	public void printSchedule() {
		//compile times into separate arraylist to be organized
		ArrayList<Integer> orderedSchedule = new ArrayList<Integer>();
		for(CampusCourse cCourse : campusCourseList) {
			orderedSchedule.addAll(cCourse.getSchedule());
		}

		//sort times
		Collections.sort(orderedSchedule);
		
		//iterate through ordered schedule and print 
		for(Integer num : orderedSchedule) {
			//print day and time
			int day = (num / 100) - 1;
			int time = (num % 10) - 1;
			System.out.print(week[day] + " " + slot[time]);
			
			// find matching time slot in courseList to get course info
			for(CampusCourse c : campusCourseList ) {
				for(Integer i : c.getSchedule()) {
					if(num.equals(i)) {
						//print cCourse dept name and number
						System.out.println(" " + c.getDepartment().getDepartmentName() + c.getCourseNumber()
								+ " " + c.getName());
					}
				}
			}
		}
		//print online course names (course# and name)
		for(OnlineCourse o : onlineCourseList) {
			System.out.println(o.getDepartment().getDepartmentName() + o.getCourseNumber() + " " + o.getName());
		}
	}
	
	public String printScheduleGUI() {

		String scheduleText = "";
		ArrayList<Integer> orderedSchedule = new ArrayList<Integer>();

		for(CampusCourse cCourse : campusCourseList) {
			orderedSchedule.addAll(cCourse.getSchedule());
		}

		Collections.sort(orderedSchedule);
		for(Integer num : orderedSchedule) {
			int day = (num / 100) - 1;
			int time = (num % 10) - 1;
			scheduleText = scheduleText.concat(week[day] + " " + slot[time]);
			for(CampusCourse c : campusCourseList ) {
				for(Integer i : c.getSchedule()) {
					if(num.equals(i)) {
						scheduleText = scheduleText.concat(" " + c.getDepartment().getDepartmentName() + c.getCourseNumber()
								+ " " + c.getName() + "\n");
					}
				}
			}
		}
		for(OnlineCourse o : onlineCourseList) {
			scheduleText = scheduleText.concat(o.getDepartment().getDepartmentName() + o.getCourseNumber()
					+ " " + o.getName());
		}
		
		return scheduleText;
	}
	
	public abstract void addCourse(CampusCourse cCourse);
	public abstract void addCourse(OnlineCourse oCourse) ;
}
