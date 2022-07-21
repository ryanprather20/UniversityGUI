package org.university.software;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serial;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.university.hardware.Classroom;
import org.university.hardware.Department;
import org.university.people.Person;
import org.university.people.Student;

public class UniversityGUI extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;
    private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu fileMenu;
    private JMenu studentMenu;
    private University univ1;

    //file menu
    JMenuItem save;
    JMenuItem load;
    JMenuItem exit;

    //student menu
    JMenuItem addCourse;
    JMenuItem dropCourse;
    JMenuItem printSchedule;

    //admin menu
    JMenuItem adminPrintAll;
    JMenuItem adminNewCC;
    JMenuItem adminNewOC;
    JMenuItem adminAssignClassroom;


    public UniversityGUI(String windowTitle, University uni) {
        super(windowTitle);

        univ1 = uni;
        setSize(350, 150);

        setLayout(new FlowLayout(FlowLayout.CENTER));
        setLocationRelativeTo(null);
        add(new JLabel("<HTML><center>" + windowTitle +
                "<BR>Choose an action from the above menus.</center></HTML>"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildGUI();
        setVisible(true);
    }

    public void buildGUI() {
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        studentMenu = new JMenu("Students");
        adminMenu = new JMenu("Administrators");

        save = new JMenuItem("Save");
        load = new JMenuItem("Load");
        exit = new JMenuItem("Exit");
        save.addActionListener(new MenuListener());
        load.addActionListener(new MenuListener());
        exit.addActionListener(new MenuListener());
        fileMenu.add(save);
        fileMenu.add(load);
        fileMenu.add(exit);


        addCourse = new JMenuItem("Add Course");
        dropCourse = new JMenuItem("Drop Course");
        printSchedule = new JMenuItem("Print Schedule");
        addCourse.addActionListener(new MenuListener());
        dropCourse.addActionListener(new MenuListener());
        printSchedule.addActionListener(new MenuListener());
        studentMenu.add(addCourse);
        studentMenu.add(dropCourse);
        studentMenu.add(printSchedule);


        adminPrintAll = new JMenuItem("Print All Info");
        adminNewCC = new JMenuItem("New Campus Course");
        adminNewOC = new JMenuItem("New Online Course");
        adminAssignClassroom = new JMenuItem("Assign Course Classroom");
        adminPrintAll.addActionListener(new MenuListener());
        adminNewCC.addActionListener(new MenuListener());
        adminNewOC.addActionListener(new MenuListener());
        adminAssignClassroom.addActionListener(new MenuListener());
        adminMenu.add(adminPrintAll);
        adminMenu.add(adminNewCC);
        adminMenu.add(adminNewOC);
        adminMenu.add(adminAssignClassroom);


        menuBar.add(fileMenu);
        menuBar.add(studentMenu);
        menuBar.add(adminMenu);

        setJMenuBar(menuBar);
    }

    private class MenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JMenuItem source = (JMenuItem) (e.getSource());

            if (source.equals(save)) handleSave();
            else if (source.equals(load)) handleLoad();
            else if (source.equals(exit)) handleExit();
            else if (source.equals(addCourse)) handleAddCourse();
            else if (source.equals(dropCourse)) handleDropCourse();
            else if (source.equals(printSchedule)) handlePrintSchedule();
            else if (source.equals(adminPrintAll)) handlePrintAll();
            else if (source.equals(adminNewCC)) handleNewCC();
            else if (source.equals(adminNewOC)) handleNewOC();
            else if (source.equals(adminAssignClassroom)) handleClassroom();
        }

        private void handleSave() {
            University.saveData(univ1);
        }

        private void handleLoad() {
            University uni = University.loadData();
            if (uni != null) {
                univ1 = uni;
                //uni.printAll(); // for testing
            }
        }

        private void handleExit() {
            System.exit(0);
        }

        private void handlePrintAll() {
            if (univ1 != null) {
                ByteArrayOutputStream outp = new ByteArrayOutputStream();
                PrintStream print = new PrintStream(outp);
                PrintStream old = System.out;
                System.setOut(print);
                univ1.printAll();
                System.out.flush();
                System.setOut(old);

                JFrame f = new JFrame("University Info");
                JTextArea area = new JTextArea();
                area.setText(outp.toString());
                area.setEditable(false);
                f.setSize(600, 700);
                f.setLocationRelativeTo(null);
                JScrollPane scroll = new JScrollPane(area);
                f.add(scroll);
                f.setVisible(true);
            }
        }

        private void handleNewCC() {

            JFrame createCourseFrame = new JFrame("Create a New Campus Course");
            setLocationRelativeTo(null);
            createCourseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JTextField courseName = new JTextField();
            JTextField department = new JTextField();
            JTextField courseNum = new JTextField();
            JTextField maxStu = new JTextField();
            JTextField credits = new JTextField();

            UIManager.put("OptionPane.minimumSize", new Dimension(350, 300));
            int okay = JOptionPane.showOptionDialog(createCourseFrame, new Object[]{"Course name: ", courseName, "Department: ", department,
                    "Course number: ", courseNum, "Max # of students: ", maxStu, "Credits: ", credits}, "Create a new Campus Course",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (okay == JOptionPane.OK_OPTION) {
                String name = courseName.getText().trim();
                String dep = department.getText().trim();
                String num = courseNum.getText().trim();
                int number = Integer.parseInt(num);
                String max = maxStu.getText().trim();
                int maximum = Integer.parseInt(max);
                String cred = credits.getText().trim();
                int creds = Integer.parseInt(cred);

                boolean x = false;
                for (Department d : univ1.getDepartments()) {
                    if (dep.equals(d.getDepartmentName())) {
                        x = true;
                        CampusCourse c = new CampusCourse(name, number, maximum, creds);
                        d.addCourse(c);
                        JOptionPane.showMessageDialog(createCourseFrame, "Campus Course created successfully", "Success", JOptionPane.PLAIN_MESSAGE);
                    }
                }

                if (!x) JOptionPane.showMessageDialog(createCourseFrame, "Department " + dep + " isn't a valid department ",
                        "Error Creating Campus Course", JOptionPane.WARNING_MESSAGE);
            }
        }

        private void handleNewOC() {
            JFrame createCourseFrame = new JFrame("Create a New Online Course");
            setLocationRelativeTo(null);
            createCourseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JTextField courseName = new JTextField();
            JTextField department = new JTextField();
            JTextField courseNum = new JTextField();
            JTextField credits = new JTextField();

            int okayClick = JOptionPane.showOptionDialog(createCourseFrame, new Object[]{"Course name: ", courseName, "Department: ", department, "Course number: ", courseNum,
                    "Credits: ", credits}, "Create a new Online Course", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (okayClick == JOptionPane.OK_OPTION) {
                String name = courseName.getText().trim();
                String dep = department.getText().trim();
                String num = courseNum.getText().trim();
                int number = Integer.parseInt(num);
                String cred = credits.getText().trim();
                int creditos = Integer.parseInt(cred);

                boolean x = false;
                for (Department d : univ1.getDepartments()) {
                    if (dep.equals(d.getDepartmentName())) {
                        x = true;
                        OnlineCourse o = new OnlineCourse(name, number, creditos);
                        d.addCourse(o);
                        JOptionPane.showMessageDialog(createCourseFrame, "Online Course created successfully", "Success", JOptionPane.PLAIN_MESSAGE);
                    }
                }

                if (!x) JOptionPane.showMessageDialog(createCourseFrame, "Department " + dep + " isn't a valid department ",
                        "Error Creating Online Course", JOptionPane.WARNING_MESSAGE);
            }
        }

        private void handleClassroom() {
            JFrame createClassroomFrame = new JFrame();
            setLocationRelativeTo(null);
            createClassroomFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JTextField dep = new JTextField();
            JTextField courseNum = new JTextField();
            JTextField roomString = new JTextField();

            UIManager.put("OptionPane.minimumSize", new Dimension(350, 200));
            int okayClick = JOptionPane.showOptionDialog(null, new Object[]{"Course Department: ", dep, "Course Number: ", courseNum, "Classroom Number: ", roomString},
                    "Assign Campus Course to Classroom", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (okayClick == JOptionPane.OK_OPTION) {
                String depart = dep.getText().trim();
                String num = courseNum.getText().trim();
                int numb = Integer.parseInt(num);
                String room = roomString.getText().trim();

                //errors for time conflicts, department, course, classroom not existing

                boolean x = false; //dep exist
                boolean y = false; //course exist
                boolean z = false; //room exists

                for (Department d : univ1.getDepartments()) {
                    if (depart.equals(d.getDepartmentName())) x = true;
                    for (CampusCourse c : d.getCCourses()) {
                        if (numb == c.getCourseNumber()) {
                            y = true;
                            break;
                        }
                    }
                    for (Classroom cl : univ1.getClassrooms()) {
                        if (room.equals(cl.getRoomNumber())) {
                            z = true;
                            break;
                        }
                    }
                }

                //Error Messages
                if (!x) JOptionPane.showMessageDialog(createClassroomFrame, "Department " + depart + " isn't a valid department ",
                        "Error ", JOptionPane.WARNING_MESSAGE);
                if (!y) JOptionPane.showMessageDialog(createClassroomFrame, "Course " + depart + numb + " isn't a valid course ",
                        "Error ", JOptionPane.WARNING_MESSAGE);
                if (!z) JOptionPane.showMessageDialog(createClassroomFrame, "Classroom " + room + " isn't a valid classroom ",
                        "Error ", JOptionPane.WARNING_MESSAGE);

                if (x && y && z) {
                    for (Department d : univ1.getDepartments()) {
                        if (depart.equals(d.getDepartmentName())) {
                            for (CampusCourse poo : d.getCCourses()) {
                                if (poo.getCourseNumber() == numb) {
                                    for (Classroom rooom : univ1.getClassrooms()) {
                                        if (rooom.getRoomNumber().equals(room)) {
                                            if (poo.detectClassroomConflictBOOL(rooom)) {
                                                JOptionPane.showMessageDialog(createClassroomFrame, poo.getConflictingClassroomSTRING(rooom),
                                                        "Error Assigning Campus Course Classroom", JOptionPane.WARNING_MESSAGE);
                                            } else {
                                                poo.setRoomGUI(rooom);
                                                JOptionPane.showMessageDialog(createClassroomFrame, "Success you have assigned " + depart + numb + "to " +
                                                        room, "Success", JOptionPane.PLAIN_MESSAGE);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        private void handleAddCourse() {
            JFrame addCourseFrame = new JFrame("Add course");
            setLocationRelativeTo(null);
            addCourseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JTextField name = new JTextField();
            JTextField dep = new JTextField();
            JTextField courseNum = new JTextField();

            int okayClick = JOptionPane.showOptionDialog(addCourseFrame, new Object[]{"Student Name: ", name, "Department: ", dep,
                            "Course Number: ", courseNum},
                    "Add Course", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, null, null);

            if (okayClick == JOptionPane.OK_OPTION) {
                String n = name.getText().trim();
                String d = dep.getText().trim();
                String c = courseNum.getText().trim();
                int number = Integer.parseInt(c);

                boolean x = false; //dep exist
                boolean y = false; //student exist
                boolean z = false; //course exists
                Boolean OC = null; //true = campus | False = online
                Object classType = null;
                Student student = null;
                CampusCourse campus = null;
                OnlineCourse online = null;

                for (Department de : univ1.getDepartments()) {
                    if (d.equals(de.getDepartmentName())) x = true;

                    for (Student stud : de.getStudentList()) {
                        if (stud.getName().equals(n)) {
                            y = true;
                            student = stud;
                        }
                    }
                    for (CampusCourse cl : de.getCCourses()) {
                        if (cl.getCourseNumber() == number) {
                            z = true;
                            OC = true;
                            campus = cl;
                            classType = cl;
                        }
                    }
                    for (OnlineCourse cl : de.getOCourses()) {
                        if (cl.getCourseNumber() == number) {
                            z = true;
                            OC = false;
                            online = cl;
                            classType = cl;
                        }
                    }
                }

                //Error Messages
                if (!x) JOptionPane.showMessageDialog(addCourseFrame, "Department " + d + " doesn't exist",
                        "Error ", JOptionPane.WARNING_MESSAGE);
                if (!y) JOptionPane.showMessageDialog(addCourseFrame, "Student " + n + " doesn't exist",
                        "Error ", JOptionPane.WARNING_MESSAGE);
                if (!z) JOptionPane.showMessageDialog(addCourseFrame, "Course: " + d + c + " doesn't exist",
                        "Error ", JOptionPane.WARNING_MESSAGE);

                if (x && y && z) {
                    Boolean modality = null;
                    try {
                        modality = Class.forName("org.university.software.CampusCourse").isInstance(classType);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (modality) {
                        String oops = "";
                        for (Person spoon : campus.getStudentRoster()) {
                            if (spoon.getName().equals(student.getName())) {
                                oops = spoon.getName();
                            }
                        }
                        if (student.getName().equals(oops)) {
                            JOptionPane.showMessageDialog(addCourseFrame, "Student " + student.getName() + " is already enrolled in " +
                                    campus.getDepartment().getDepartmentName() + campus.getCourseNumber(), "Error ", JOptionPane.WARNING_MESSAGE);
                        } else if (!campus.availableTo(student)) { //class full
                            JOptionPane.showMessageDialog(addCourseFrame, student.getName() + " can't add campus Course " + campus.getDepartment().getDepartmentName()
                                            + campus.getCourseNumber() + " " + campus.getName() + ". Because this campus course has enough students.",
                                    "Error ", JOptionPane.WARNING_MESSAGE);
                        } else if (student.detectConflictBool(campus)) {
                            JOptionPane.showMessageDialog(addCourseFrame, student.detectConflictString(campus), "Error ", JOptionPane.WARNING_MESSAGE);
                        } else if (!student.detectConflictBool(campus) && campus.availableTo(student)) { //add class and credits to enrolled credits to this student
                            student.getCampusCourseList().add(campus);
                            student.enrolledCredits = (student.enrolledCredits + campus.getCreditUnits());
                            student.cCredits = student.cCredits + campus.getCreditUnits();
                            campus.addStudentToRoster(student);
                            JOptionPane.showMessageDialog(addCourseFrame, "Success you have added " + campus.getName(), "Success", JOptionPane.PLAIN_MESSAGE);
                        }
                    } else {
                        if (!online.availableTo(student)) {
                            JOptionPane.showMessageDialog(addCourseFrame, "Student " + student.getName() + " has only " + student.getcCredits() +
                                    " on campus credits enrolled. This student should have at least 6 credits registered before registering online courses.\n" +
                                    student.getName() + " can't add online course " + online.getDepartment().getDepartmentName() + online.getCourseNumber() + " " +
                                    online.getName() + ". Because this student doesn't have enough campus course credit.", "Error ", JOptionPane.WARNING_MESSAGE);
                        } else {
                            student.getOnlineCourseList().add(online);
                            student.enrolledCredits = student.enrolledCredits + online.getCreditUnits();
                            student.oCredits = student.oCredits + online.getCreditUnits();
                            online.addStudentToRoster(student);
                            JOptionPane.showMessageDialog(addCourseFrame, "Success you have added " + online.getName(), "Success", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }
            }
        }

        private void handleDropCourse() {
            JFrame dropCourseFrame = new JFrame("Drop course");
            setLocationRelativeTo(null);
            dropCourseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JTextField name = new JTextField();
            JTextField dep = new JTextField();
            JTextField courseNum = new JTextField();

            int spongebob = JOptionPane.showOptionDialog(dropCourseFrame, new Object[]{"Student Name: ", name, "Department: ", dep, "Course Number: ", courseNum},
                    "Drop Course", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (spongebob == JOptionPane.OK_OPTION) {
                String n = name.getText().trim();
                String d = dep.getText().trim();
                String c = courseNum.getText().trim();
                int number = Integer.parseInt(c);

                boolean x = false; //dep exist
                boolean y = false; //student exist
                boolean z = false; //course exists
                Boolean OC = null; //true = campus | False = online
                Object classType = null;
                Student ryan = null;
                CampusCourse campus = null;
                OnlineCourse online = null;

                for (Department de : univ1.getDepartments()) {
                    if (d.equals(de.getDepartmentName())) x = true;

                    for (Student student : de.getStudentList()) {
                        if (student.getName().equals(n)) {
                            y = true;
                            ryan = student;
                        }
                    }
                    for (CampusCourse cl : de.getCCourses()) {
                        if (cl.getCourseNumber() == number) {
                            z = true;
                            OC = true;
                            campus = cl;
                            classType = cl;
                        }
                    }
                    for (OnlineCourse cl : de.getOCourses()) {
                        if (cl.getCourseNumber() == number) {
                            z = true;
                            OC = false;
                            online = cl;
                            classType = cl;
                        }
                    }
                }

                //Error Messages
                if (!x)
                    JOptionPane.showMessageDialog(dropCourseFrame, "Department " + d + " doesn't exist", "Error ", JOptionPane.WARNING_MESSAGE);
                if (!y)
                    JOptionPane.showMessageDialog(dropCourseFrame, "Student " + n + " doesn't exist", "Error ", JOptionPane.WARNING_MESSAGE);
                if (!z)
                    JOptionPane.showMessageDialog(dropCourseFrame, "Course: " + d + c + " doesn't exist", "Error ", JOptionPane.WARNING_MESSAGE);

                if (x && y && z) {
                    Boolean modality = null;
                    try {
                        modality = Class.forName("org.university.software.CampusCourse").isInstance(classType);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (modality) {
                        if (ryan.campusCourseList.contains(campus)) {
                            if (ryan.getOnlineCourseList().size() >= 1) {
                                //if student will have 6 credits left after dropping this class (to keep oCourse) then drop course
                                if ((ryan.getcCredits() - campus.getCreditUnits()) >= 6) {
                                    //remove student from course, course from student, subtract credits
                                    campus.getStudentRoster().remove(ryan);
                                    ryan.getCampusCourseList().remove(campus);
                                    ryan.enrolledCredits -= campus.getCreditUnits();
                                    ryan.cCredits -= campus.getCreditUnits();
                                    JOptionPane.showMessageDialog(dropCourseFrame, "Success you have dropped " + campus.getName(), "Success", JOptionPane.PLAIN_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(dropCourseFrame, ryan.getName() + " can't drop this campus course, because this student"
                                            + " doesn't have enough campus course credits to hold the online course", "Error ", JOptionPane.WARNING_MESSAGE);
                                }
                            } else { //if not in online course drop course
                                campus.getStudentRoster().remove(ryan);
                                ryan.getCampusCourseList().remove(campus);
                                ryan.enrolledCredits -= campus.getCreditUnits();
                                ryan.cCredits -= campus.getCreditUnits();
                                JOptionPane.showMessageDialog(dropCourseFrame, "Success you have dropped " + campus.getName(), "Success", JOptionPane.PLAIN_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(dropCourseFrame, "The course " + campus.getDepartment().getDepartmentName() + campus.getCourseNumber() +
                                    " could not be dropped because " + ryan.getName() + " is not enrolled in " + campus.getDepartment().getDepartmentName() +
                                    campus.getCourseNumber() + ".", "Error ", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        if (ryan.onlineCourseList.contains(online)) {            //if in roster drop
                            online.getStudentRoster().remove(ryan);
                            ryan.getOnlineCourseList().remove(online);
                            ryan.enrolledCredits -= online.getCreditUnits();
                            ryan.oCredits -= online.getCreditUnits();
                            JOptionPane.showMessageDialog(dropCourseFrame, "Success you have dropped " + online.getName(), "Success", JOptionPane.PLAIN_MESSAGE);
                        } else {    //error message if not in roster
                            JOptionPane.showMessageDialog(dropCourseFrame, "The course " + online.getDepartment().getDepartmentName() + online.getCourseNumber() +
                                    " could not be dropped because " + ryan.getName() + " is not enrolled in " + online.getDepartment().getDepartmentName() +
                                    online.getCourseNumber() + ".");
                        }
                    }
                }
            }
        }

        private void handlePrintSchedule() {
            JFrame scheduleFrame = new JFrame();
            setLocationRelativeTo(null);
            scheduleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JTextField name = new JTextField();

            int selection = JOptionPane.showOptionDialog(scheduleFrame, new Object[]{"Student Name: ", name},
                    "Drop Course", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (selection == JOptionPane.OK_OPTION) {
                String n = name.getText().trim();
                Student s = new Student();
                s = null;
                for (Department department : univ1.getDepartments()) {
                    for (Student student : department.getStudentList()) {
                        if (n.equals(student.getName())) s = student;
                    }
                }

                if (s != null) {
                    JOptionPane.showMessageDialog(scheduleFrame, s.printScheduleGUI(), "Student " + s.getName()
                            + "'s Schedule", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(scheduleFrame, "Student " + n + " doesn't exist", "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
}
