package org.university.people;

import java.io.Serializable;

//subclass of person and has subclasses prof and staff
public abstract class Employee extends Person implements Serializable {
	Employee(){}
	
	public abstract double earns();
	public abstract void raise(double percent);
}
