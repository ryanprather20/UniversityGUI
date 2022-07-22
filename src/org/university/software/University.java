package org.university.software;

import java.io.FileInputStream;  
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


import org.university.hardware.*;
import org.university.people.*;

public class University implements Serializable {
	public ArrayList<Department> departmentList;
	public ArrayList<Classroom> classroomList;
	private String name;

	University() {
		departmentList = new ArrayList<>();
		classroomList = new ArrayList<>();
	}
	
	University(String n) {
		this.name = n;
		departmentList = new ArrayList<>();
		classroomList = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Department> getDepartments() {
		return departmentList;
	}
	public ArrayList<Classroom> getClassrooms() {
		return classroomList;
	}
	public void addDepartment(Department newDepartment) {
		departmentList.add(newDepartment);
	}
	
	public void printDepartmentList() {
		for(Department department : departmentList) {
			System.out.println(department.getDepartmentName());
		}
	}

	public ArrayList<Person> getAllStudents() {
		ArrayList<Person> allStudents = new ArrayList<Person>();
		for(Department department : departmentList) {
			allStudents.addAll(department.getStudentList());
		}
		return allStudents;
	}
	public void printStudentList() {
		for(Department department : departmentList) {
			for(Student student : department.getStudentList()) {
				System.out.println(student.getName());
			}
		}
	}

	public ArrayList<Course> getAllCourses() {
		ArrayList<Course> allCourses = new ArrayList<Course>();
		for(Department department : departmentList) {
			allCourses.addAll(department.getAllCourseList());
		}
		return allCourses;
	}
	
	public void printDepartmentDetails() {
		for(Department d : departmentList) {
			System.out.println("\nDepartment " + d.getDepartmentName());
			System.out.println("\nPrinting professor schedules:");
			for(Professor p : d.getProfessorList()) {
				System.out.println("\nThe schedule for Prof. " + p.getName() + ":");
				p.printSchedule();
			}
			System.out.println("\nPrinting student schedules:");
			for(Student s : d.getStudentList()) {
				System.out.println("\nThe schedule for student " + s.getName() + ":");
				s.printSchedule();
			}
			System.out.println("\nPrinting staff schedules:");
			for(Staff s : d.getStaffList()) {
				System.out.println("\nThe schedule for employee " + s.getName() + ":");
				s.printSchedule();
				double x = s.getMonthlyHours() * s.getPayRate();
				System.out.println("\nStaff: " + s.getName() + " earns " + x + " this month");
			}
			if(d.getStaffList().isEmpty()) System.out.println();
			System.out.println("\nThe rosters for courses offered by " + d.getDepartmentName());
			for(Course c : d.getCCourses()) {
				System.out.println("\nThe roster for course " + c.getDepartment().getDepartmentName() + c.getCourseNumber());
				c.printRoster();
			}	
		}
		System.out.print("\n");
	}
	
	public void printCourseList() { //campus then online ig
		for(Department d : departmentList) {
			System.out.println("\nThe course list for department " + d.getDepartmentName());
			if(!departmentList.isEmpty()) {
				for(CampusCourse c : d.cCourseList) {
					System.out.println(c.getDepartment().getDepartmentName()+c.getCourseNumber()+" "+c.getName());
				}
				for(OnlineCourse o : d.oCourseList) {
					System.out.println(o.getDepartment().getDepartmentName()+o.getCourseNumber()+" "+o.getName());
				}
			}
		}
	}

	public void printProfessorList() {
		for(Department d : departmentList) {
			System.out.println("\nThe professor list for department " + d.getDepartmentName());
			for(Professor p : d.getProfessorList()) {
				System.out.println(p.getName());
			}
		}
	}
	
	public void printStaffList() {
		for(Department d : departmentList) {
			for(Staff s : d.getStaffList()) {
				System.out.println(s.getName());
			}
		}
	}

	public void printAll() {
		System.out.println("\nDepartment list:");
		printDepartmentList();
		
		System.out.println("\nClassroom list:");
		for(Classroom c : classroomList) {
			System.out.println(c.getRoomNumber());
		}

		printProfessorList();

		printCourseList();
		
		for(Classroom c : classroomList) {
			System.out.println("\nThe schedule for classroom " + c.getRoomNumber());
			c.printSchedule();
		}
		
		printDepartmentDetails();
	}

	public static void saveData(University u) {
		FileOutputStream fileOut;
		ObjectOutputStream objOut;

		try {
			fileOut = new FileOutputStream("root/university.ser");
			objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(u);
			objOut.close();
			fileOut.close();
		}
		
		catch(IOException i) {
			i.printStackTrace();
		}
	}

	public static University loadData() {
		FileInputStream fileIn;
		ObjectInputStream objIn;
		University u = new University();
		
		try {
			fileIn = new FileInputStream("root/university.ser");
			objIn = new ObjectInputStream(fileIn);
			u = (University)objIn.readObject();
			objIn.close();
			fileIn.close();
		}
		
		catch(IOException | ClassNotFoundException i) {
			i.printStackTrace();
		}

		return u;
	}
}