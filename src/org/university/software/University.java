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
	
	public void printDepartmentList() {
		System.out.println("Department list:");
		for(Department department : departmentList) {
			System.out.println(department.getDepartmentName());
		}
	}

	public void printClassroomList() {
		System.out.println("\nClassroom list:");
		for(Classroom room : classroomList) {
			System.out.println(room.getRoomNumber());
		}
	}

	public void printStudentList() {  // for testing
		for(Department department : departmentList) {
			for(Student student : department.getStudentList()) {
				System.out.println(student.getName());
			}
		}
	}

	public ArrayList<Course> getAllCourses() {   // for testing
		ArrayList<Course> allCourses = new ArrayList<Course>();
		for(Department department : departmentList) {
			allCourses.addAll(department.getAllCourseList());
		}
		return allCourses;
	}
	
	public void printDepartmentDetails() {
		for(Department d : departmentList) {
			d.printAll();
		}
		System.out.print("\n");
	}
	
	public void printCourseList() { // first print campus then online
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

	public void printAll() {
		printDepartmentList();

		printProfessorList();

		printCourseList();

		printClassroomList();

		for(Classroom c : classroomList) c.printSchedule();

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