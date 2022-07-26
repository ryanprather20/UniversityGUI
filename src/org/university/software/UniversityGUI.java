package org.university.software;

import org.university.hardware.Classroom;
import org.university.hardware.Department;
import org.university.people.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serial;

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
            }
        }

        private void handleExit() { System.exit(0); }

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
                area.setCaretPosition(0);
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

                if (!x)
                    JOptionPane.showMessageDialog(createCourseFrame, "Department " + dep + " isn't a valid department ",
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

                if (!x)
                    JOptionPane.showMessageDialog(createCourseFrame, "Department " + dep + " isn't a valid department ",
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
                if (!x)
                    JOptionPane.showMessageDialog(createClassroomFrame, "Department " + depart + " isn't a valid department ",
                            "Error ", JOptionPane.WARNING_MESSAGE);
                if (!y)
                    JOptionPane.showMessageDialog(createClassroomFrame, "Course " + depart + numb + " isn't a valid course ",
                            "Error ", JOptionPane.WARNING_MESSAGE);
                if (!z)
                    JOptionPane.showMessageDialog(createClassroomFrame, "Classroom " + room + " isn't a valid classroom ",
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
            JTextField nameField = new JTextField();
            JTextField deptField = new JTextField();
            JTextField courseNumField = new JTextField();

            int okayClick = JOptionPane.showOptionDialog(addCourseFrame, new Object[]{"Student Name: ", nameField, "Department: ", deptField,
                            "Course Number: ", courseNumField}, "Add Course", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE
                    , null, null, null);

            if (okayClick == JOptionPane.OK_OPTION) {
                String name = nameField.getText().trim();
                String dept = deptField.getText().trim();
                String courseNum = courseNumField.getText().trim();
                int number = Integer.parseInt(courseNum);

                boolean studExist = false; //student exist
                boolean depExist = false; //dep exist
                boolean courseExist = false; //course exists
                boolean modality = false; // online course to start
                Student studentAdding = null;
                CampusCourse campusCourse = null;
                OnlineCourse onlineCourse = null;

                for (Department d : univ1.getDepartments()) {
                    if (dept.equals(d.getDepartmentName())) {
                        depExist = true;

                        for (Student stud : d.getStudentList()) {
                            if (stud.getName().equals(name)) {
                                studExist = true;
                                studentAdding = stud;
                                break;
                            }
                        }
                        for (CampusCourse CC : d.getCCourses()) {
                            if (CC.getCourseNumber() == number) {
                                courseExist = true;
                                modality = true;
                                campusCourse = CC;
                            }
                        }
                        for (OnlineCourse OC : d.getOCourses()) {
                            if (OC.getCourseNumber() == number) {
                                courseExist = true;
                                modality = false;
                                onlineCourse = OC;
                            }
                        }
                        break;
                    }
                }

                // Error messages
                if (!studExist) JOptionPane.showMessageDialog(addCourseFrame, "Student " + name + " doesn't exist",
                        "Error ", JOptionPane.WARNING_MESSAGE);
                if (!depExist) JOptionPane.showMessageDialog(addCourseFrame, "Department " + dept + " doesn't exist",
                        "Error ", JOptionPane.WARNING_MESSAGE);
                if (!courseExist)
                    JOptionPane.showMessageDialog(addCourseFrame, "Course: " + dept + courseNum + " doesn't exist",
                            "Error ", JOptionPane.WARNING_MESSAGE);

                if (depExist && studExist && courseExist) {
                    if (modality) { // if a campus course
                        if (campusCourse.getStudentRoster().contains(studentAdding)) { // check if already enrolled
                            JOptionPane.showMessageDialog(addCourseFrame, "Student " + name + " is already enrolled in " +
                                    dept + courseNum, "Error ", JOptionPane.WARNING_MESSAGE);
                        }
                        else if (!campusCourse.availableTo(studentAdding)) { // check if the class has space
                            JOptionPane.showMessageDialog(addCourseFrame, name + " can't add campus Course " + dept
                                            + courseNum + " " + campusCourse.getName() + ". Because this campus course has enough students.",
                                    "Error ", JOptionPane.WARNING_MESSAGE);
                        }
                        else if (studentAdding.detectConflictBool(campusCourse)) { // Check for conflicts in student's schedule
                            JOptionPane.showMessageDialog(addCourseFrame, studentAdding.detectConflictString(campusCourse), "Error ", JOptionPane.WARNING_MESSAGE);
                        }
                        else { // add student to class
                            studentAdding.addCourse(campusCourse);
                            JOptionPane.showMessageDialog(addCourseFrame, "Success you have added " + campusCourse.getName(), "Success", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                    else { // if online course
                        if (!onlineCourse.availableTo(studentAdding)) { // check if student has necessary credits
                            JOptionPane.showMessageDialog(addCourseFrame, "Student " + name + " has only " + studentAdding.getcCredits() +
                                    " on campus credits enrolled. This student should have at least 6 credits registered before registering online courses.\n" +
                                    name + " can't add online course " + dept + courseNum + " " +
                                    onlineCourse.getName() + ". Because this student doesn't have enough campus course credit.", "Error ", JOptionPane.WARNING_MESSAGE);
                        }
                        else { // add student to online class
                            studentAdding.addCourse(onlineCourse);
                            JOptionPane.showMessageDialog(addCourseFrame, "Success you have added " + onlineCourse.getName(), "Success", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }
            }
        }

        private void handleDropCourse() {
            JFrame dropCourseFrame = new JFrame("Drop course");
            setLocationRelativeTo(null);
            dropCourseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JTextField nameField = new JTextField();
            JTextField deptField = new JTextField();
            JTextField courseNumField = new JTextField();

            int okayClick = JOptionPane.showOptionDialog(dropCourseFrame, new Object[]{"Student Name: ", nameField, "Department: ", deptField, "Course Number: ", courseNumField},
                    "Drop Course", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (okayClick == JOptionPane.OK_OPTION) {
                String name = nameField.getText().trim();
                String department = deptField.getText().trim();
                String courseNum = courseNumField.getText().trim();
                int number = Integer.parseInt(courseNum);

                boolean deptExist = false; //dep exist
                boolean studExist = false; //student exist
                boolean courseExist = false; //course exists
                boolean modality = false; //true = campus | False = online
                Student studentDropping = null;
                CampusCourse campusCourse = null;
                OnlineCourse onlineCourse = null;

                for (Department d : univ1.getDepartments()) {
                    if (department.equals(d.getDepartmentName())) {
                        deptExist = true;

                        for (Student student : d.getStudentList()) {
                            if (student.getName().equals(name)) {
                                studExist = true;
                                studentDropping = student;
                                break;
                            }
                        }
                        for (CampusCourse CC : d.getCCourses()) {
                            if (CC.getCourseNumber() == number) {
                                courseExist = true;
                                modality = true;
                                campusCourse = CC;
                                break;
                            }
                        }
                        for (OnlineCourse OC : d.getOCourses()) {
                            if (OC.getCourseNumber() == number) {
                                courseExist = true;
                                modality = false;
                                onlineCourse = OC;
                                break;
                            }
                        }
                        break;
                    }
                }

                //Error Messages
                if (!studExist) JOptionPane.showMessageDialog(dropCourseFrame, "Student " + name + " doesn't exist"
                        , "Error ", JOptionPane.WARNING_MESSAGE);
                if (!deptExist)
                    JOptionPane.showMessageDialog(dropCourseFrame, "Department " + department + " doesn't exist"
                            , "Error ", JOptionPane.WARNING_MESSAGE);
                if (!courseExist)
                    JOptionPane.showMessageDialog(dropCourseFrame, "Course: " + department + courseNum + " doesn't exist"
                            , "Error ", JOptionPane.WARNING_MESSAGE);

                if (deptExist && studExist && courseExist) {
                    if (modality) {  // if campus course
                        if (!campusCourse.getStudentRoster().contains(studentDropping)) { // check if enrolled in class
                            JOptionPane.showMessageDialog(dropCourseFrame, "The course " + department + courseNum +
                                    " could not be dropped because " + name + " is not enrolled in " + department +
                                    courseNum + ".", "Error ", JOptionPane.WARNING_MESSAGE);
                        } else if (!studentDropping.getOnlineCourseList().isEmpty()) {  // check if student is enrolled in online class
                            if ((studentDropping.getcCredits() - campusCourse.getCreditUnits()) >= 6) {  // check for necessary credits to drop this class
                                studentDropping.dropCourse(campusCourse);
                                JOptionPane.showMessageDialog(dropCourseFrame, "Success you have dropped "
                                        + campusCourse.getName(), "Success", JOptionPane.PLAIN_MESSAGE);
                            } else {  // student does not have enough credits to hold online class
                                JOptionPane.showMessageDialog(dropCourseFrame, studentDropping.getName()
                                                + " can't drop this campus course, because this student"
                                                + " doesn't have enough campus course credits to hold the online course"
                                        , "Error ", JOptionPane.WARNING_MESSAGE);
                            }
                        } else { //if not in online course drop campus course
                            studentDropping.dropCourse(campusCourse);
                            JOptionPane.showMessageDialog(dropCourseFrame, "Success you have dropped "
                                    + campusCourse.getName(), "Success", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                    else { // no stipulations to dropping online course
                        studentDropping.dropCourse(onlineCourse);
                        JOptionPane.showMessageDialog(dropCourseFrame, "Success you have dropped " + onlineCourse.getName(), "Success", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        }


        private void handlePrintSchedule() {
            JFrame scheduleFrame = new JFrame();
            setLocationRelativeTo(null);
            scheduleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JTextField nameField = new JTextField();

            int okayClick = JOptionPane.showOptionDialog(scheduleFrame, new Object[]{"Student Name: ", nameField},
                    "Print Schedule", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (okayClick == JOptionPane.OK_OPTION) {
                String name = nameField.getText().trim();

                boolean studExist = false;
                Student studentPrinting = new Student();

                outerLoop:
                for(Department d : univ1.getDepartments()) {
                    for (Student student : d.getStudentList()) {
                        if (student.getName().equals(name)) {
                            studExist = true;
                            studentPrinting = student;
                            break outerLoop;
                        }
                    }
                }

                if(studExist) {
                    studentPrinting.printScheduleGUI();
                }
                else {
                    JOptionPane.showMessageDialog(scheduleFrame, "Student " + name + " doesn't exist"
                            , "Error", JOptionPane.WARNING_MESSAGE);
                }

            }
        }
    }
}

