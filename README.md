# University GUI
Simple GUI to be able to display and edit information pertaining to university systems such as students, courses, and classrooms.

## Project
For an object-oriented software design course we had homework assignments that built off one another that led to a fully functioning University software system with a simple GUI. The first stages of the assignment were mostly laying the groundwork by creating UML diagrams to describe the classes to be used and understand how to translate UML diagrams into Java code. JUnit testing concepts and procedures were also introduced at this time to be able to check our work. As the assignment progressed, more complex features were added such as multiple levels of abstraction and serialization.

There are 3 packages in the project: hardware, software, and people.
* Hardware: This package contains classrooms and departments which exist within the University. 
  * Each classroom has a unique number, list of courses that take place in it, and a schedule of what times those courses happen (day of the week and specific time slot). 
  *Each department contains lists of students and staff associated with it as well as the courses it has.
* Software:
  * This package contains the two types of courses (campus & online), the university, the GUI class, and two driver classes. The courses have their respective inherited fields from an abstract general course, which include name, schedule, classroom (if applicable), department, professor, credits, and a roster of students enrolled. The individual courses do not allow overlap with other courses to occur and can detect the exact course that interferes. The courses also handle detecting if a student is eligible to join based on credits and maximum capacity. 
  * The university class mostly serves as a container for the hardware packages which hold the people and courses. By containing all the information relating the school it is useful for printing out master lists of people or courses.
  * The GUI class handles serialization, enrollment, and creation of new university objects such as courses or classrooms. The class utilizes tools from Java AWT and Swing API to create the interface. Driver2 is used to open the GUI window and is populated with all the necessary university objects.
* People: The abstract person class contains multiple levels of abstraction and handles all human roles at the university. Student and Employee classes extend person and class Professor and Staff extend employee.
  * Parent class Person contains fields: name and both course lists with the corresponding methods. It also contains methods to detect scheduling conflicts.
  * Class Employee is mostly empty and only contains two abstract function calls relating to salary.
  * Class Student contains fields: 
