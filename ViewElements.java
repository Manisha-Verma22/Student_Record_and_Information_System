import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

public class ViewElements extends JFrame
{
	
  ViewElements(String title){
	  super(title);
	  setLookFontsAndColors();
	}
  //misc methods
  //resets/clears tab1
  public void clearTab1(){
    tf1id.setText("");
    tf1ln.setText("");
    tf1fn.setText("");
    tf1mi.setText("");
    tf1mn.setText("");
  }
  //resets/clears tab2
  public void clearTab2(){
    tf2cd.setText("");
    tf2desc.setText("");
    tf2unt.setText("");
    cb2maj.setSelected(false);
  }
  //resets/clears tab4
  public void clearTab4(){
    tf4id.setText("");
    tf4ln.setText("");
    tf4fn.setText("");
    tf4mi.setText("");
    tf4crs.setText("");
    tf4yr.setText("");
  }
  

  EasierGridLayout layout = new EasierGridLayout();
  
  //set layout of all components
  public void setLayouts(){
    tab1.setLayout(layout);
    tab2.setLayout(layout);
    tab3.setLayout(layout);
    tab4.setLayout(layout);
    tab5.setLayout(layout);

    //tab1
    layout.setConstraints(lb1ln,4,2,1,1);
    layout.setConstraints(lb1fn,4,4,1,1);
    layout.setConstraints(lb1mi,4,5,1,1);
    
    layout.setConstraints(sp1stud,6,1,7,1);
    layout.setConstraints(lb1nme,3,1,1,1);
    layout.setConstraints(lb1mn,5,1,1,1);
    layout.setConstraints(tf1mn,5,2,1,1);
    layout.setConstraints(bt1rst,5,6,1,1);
    layout.setConstraints(bt1ok,5,7,1,1);
    layout.setConstraints(bt1del,8,7,1,1);
    layout.setConstraints(bt1edit,8,6,1,1);
    layout.defineOwnConstraints(tf1ln, 3,2,1,1, 0,0, 1,17, 3,3,3,3);
    layout.defineOwnConstraints(tf1fn, 3,4,1,1, 0,0, 1,17, 3,3,3,3);
    layout.defineOwnConstraints(tf1mi, 3,5,1,1, 0,0, 1,17, 3,3,3,3);
    layout.defineOwnConstraints(lb1cm, 3,4,1,1, 0,0, 0,17, 0,0,0,5);
    layout.defineOwnConstraints(lb1id, 2,1,1,1, 0,0, 0,17, 5,3,5,22);
    layout.defineOwnConstraints(lb1add, 1,1,7,1, 0,0, 0,10, 10,0,0,10);
    layout.defineOwnConstraints(tf1id, 2,2,1,1, 0,0, 0,17, 3,3,5,20);
   
    //tab2
    layout.setConstraints(sp2sbj,7,1,7,1);
    layout.setConstraints(lb2desc,4,1,6,1);
    layout.setConstraints(bt2edit,8,6,1,1);
    layout.setConstraints(bt2del,8,7,1,1);
    layout.setConstraints(lb2unt,3,3,1,1);
    layout.setConstraints(tf2unt,3,4,1,1);
    layout.setConstraints(lb2msg,6,1,4,1);
    layout.defineOwnConstraints(cb2maj, 3,5,1,1, 0,0, 0,15, 0,0,40,0);
    layout.defineOwnConstraints(lb2cd, 3,1,1,1, 0,0, 0,17, 0,3,0,0);
    layout.defineOwnConstraints(tf2cd, 3,2,1,1, 50,0, 0,17, 0,5,0,0);
    layout.defineOwnConstraints(lb2sbj, 1,1,8,1, 0,0, 0,15, 10,0,0,10);
    layout.defineOwnConstraints(bt2rst, 6,6,1,1, 0,0, 0,10, 5,10,10,10);
    layout.defineOwnConstraints(bt2ok, 6,7,1,1, 0,0, 0,10, 5,10,10,10);
    layout.defineOwnConstraints(tf2desc, 5,1,7,1, 0,0, 0,10, 0,0,20,0);
    
    //tab3
    layout.setConstraints(sp3stud,3,1,2,1);
    layout.defineOwnConstraints(lb3sbj,1,1,2,1, 0,0, 0,10, 10,0,0,10);
    layout.defineOwnConstraints(bt3add,2,2,1,1, 0,0, 0,13, 3,0,5,0);
    layout.defineOwnConstraints(bt3op,4,1,2,1, 0,0, 0,13, 0,0,3,5);
    
    //tab4
    layout.setConstraints(lb4id,2,1,1,1);
    layout.setConstraints(tf4id,2,2,1,1);
    layout.setConstraints(lb4nme,3,1,1,1);
    layout.setConstraints(lb4crs,4,1,1,1);
    layout.setConstraints(tf4crs,4,2,1,1);
    layout.setConstraints(lb4yr,5,1,1,1);
    layout.setConstraints(tf4yr,5,2,1,1);
    layout.setConstraints(sp4grd,6,1,6,1);
    layout.setConstraints(bt4egrd,7,6,1,1);
    layout.defineOwnConstraints(tf4ln,3,2,1,1, 0,0, 1,17, 0,3,2,0);
    layout.defineOwnConstraints(tf4fn,3,3,1,1, 0,0, 1,17, 0,2,2,0);
    layout.defineOwnConstraints(tf4mi,3,4,1,1, 0,0, 0,17, 0,2,2,0);
    layout.defineOwnConstraints(lb4grd,1,1,6,1, 0,0, 0,10, 10,0,0,10);
    
    //tab5
    layout.defineOwnConstraints(lb5st,1,1,2,1, 0,0, 0,10, 10,0,0,0);
    layout.defineOwnConstraints(lb5sy,2,1,1,1, 0,0, 0,13, 3,0,0,30);
    layout.defineOwnConstraints(lb5sem,3,1,1,1, 0,0, 0,13, 0,0,0,3);
    layout.defineOwnConstraints(sr5sy,2,2,1,1, 0,0, 2,15, 3,10,0,30);
    layout.defineOwnConstraints(cb5sem,3,2,1,1, 0,0, 2,15, 0,10,0,3);
    layout.defineOwnConstraints(bt5ok,4,1,2,1, 0,0, 1,10, 80,0,0,10);
    
    System.out.println("layouts loaded.");
  }
  //set font and colors
  public void setLookFontsAndColors(){
    //headers
    Font fhdr = new Font("Book Antiqua",Font.BOLD,22);
    Color fclr = new Color(95,95,50);
    lb1add.setFont(fhdr);
    lb2sbj.setFont(fhdr);
    lb3sbj.setFont(fhdr);
    lb4grd.setFont(fhdr);
    lb5st.setFont(fhdr);
    
    lb1add.setForeground(fclr);
    lb2sbj.setForeground(fclr);
    lb3sbj.setForeground(fclr);
    lb4grd.setForeground(fclr);
    lb5st.setForeground(fclr);
    
    //labels
    Font flbl = new Font("Century Gothic", 1, 12);
    lb1id.setFont(flbl);
    lb1nme.setFont(flbl);
    lb1mn.setFont(flbl);
    lb1fn.setFont(flbl);
    lb1ln.setFont(flbl);
    lb1mi.setFont(flbl);
    lb1cm.setFont(flbl);
    
    lb2cd.setFont(flbl);
    lb2unt.setFont(flbl);
    lb2desc.setFont(flbl);
    
    lb4id.setFont(flbl);
    lb4nme.setFont(flbl);
    lb4crs.setFont(flbl);
    lb4yr.setFont(flbl);

    lb5sy.setFont(flbl);
    lb5sem.setFont(flbl);


    //text fields
    tf1id.setFont(flbl);
    tf4id.setFont(flbl);
    
    Font ftf = new Font("Century Gothic",0,12);
    
    tf1id.setFont(ftf);
    tf1ln.setFont(ftf);
    tf1fn.setFont(ftf);
    tf1mi.setFont(ftf);
    tf1mn.setFont(ftf);

    tf2cd.setFont(ftf);
    tf2unt.setFont(ftf);
    tf2desc.setFont(ftf);
    
    tf4id.setFont(flbl);
    tf4ln.setFont(ftf);
    tf4fn.setFont(ftf);
    tf4mi.setFont(ftf);
    tf4crs.setFont(ftf);
    tf4yr.setFont(ftf);
    
    lb2msg.setFont(new Font("Century Gothic",3,12));
    lb2msg.setForeground(new Color(190,0,0));
  }

  //main
  JTabbedPane tabs = new JTabbedPane();
  JPanel mainPanel = new JPanel();

  static Student selectedStd;
  static Subject selectedSbj;
  static Semester currentSem;

  //tab1
  static JTable  tb1stud;
  JScrollPane    sp1stud;
  JPanel         tab1     = new JPanel();
  JLabel         lb1add   = new JLabel("CREATE NEW STUDENT REGISTRATION",JLabel.CENTER);
  JLabel         lb1id    = new JLabel("ID Number:");
  JTextField     tf1id    = new JTextField(5);
  JLabel         lb1nme   = new JLabel("Student Name ");
  JTextField     tf1ln    = new JTextField(3);
  JLabel         lb1cm    = new JLabel(",",JLabel.LEFT);
  JTextField     tf1fn    = new JTextField(25);
  JTextField     tf1mi    = new JTextField(2);
  JLabel         lb1mn    = new JLabel("Middle Name:");
  JTextField     tf1mn    = new JTextField(13);
  JButton        bt1ok    = new JButton("SAVE");
  JButton        bt1rst   = new JButton("NEW");
  JButton        bt1del   = new JButton("DELETE");
  JButton        bt1edit  = new JButton("EDIT");
  JLabel         lb1fn    = new JLabel("First Name");
  JLabel         lb1ln    = new JLabel("Last Name");
  JLabel         lb1mi    = new JLabel("MI");

  //edit student
  EditStudentDialog stddlg;
  JFrame         fmestd   = new JFrame("Edit Student");

  //tab2
  static JTable  tb2sbj;
  JScrollPane    sp2sbj;
  JPanel         tab2     = new JPanel();
  JLabel         lb2sbj   = new JLabel("OPEN NEW SUBJECTS");
  JLabel         lb2cd    = new JLabel("SUBJECT CODE:");
  JTextField     tf2cd    = new JTextField(10);
  JLabel         lb2unt   = new JLabel("UNITS:");
  JTextField     tf2unt   = new JTextField(2);
  JCheckBox      cb2maj   = new JCheckBox("Set as Major?");
  JLabel         lb2desc  = new JLabel("SUBJECT DESCRIPTION:");
  JTextField     tf2desc  = new JTextField(45);
  JButton        bt2ok    = new JButton("SAVE");
  JButton        bt2rst   = new JButton("NEW");
  JButton        bt2del   = new JButton("DELETE");
  JButton        bt2edit  = new JButton("EDIT");
  JLabel         lb2msg   = new JLabel();
  
  //edit subject
  EditSubjectDialog sbjdlg;
  JFrame            fmesbj   = new JFrame("Edit Subject");
  
  //tab3
  static JTable  tb3stud;
  JScrollPane    sp3stud;
  JPanel         tab3     = new JPanel();
  JLabel         lb3sbj   = new JLabel("ENROLLMENT AND RESERVATIONS",JLabel.CENTER);
  JButton        bt3add   = new JButton("Add new enrollee");
  JButton        bt3op    = new JButton("Open Student Registration");
  
  //enroll student
  EnrollDialog   enrdlg;
  JFrame         fmnenr   = new JFrame("Student Enrollment");
  
  //student registry
  RegistryDialog regdlg;
  JFrame         fmrreg   = new JFrame();
  
  //tab4
  static JTable  tb4grd;
  JScrollPane    sp4grd;
  JPanel         tab4     = new JPanel();
  JLabel         lb4grd   = new JLabel("STUDENT REGISTRY",JLabel.CENTER);
  JLabel         lb4id    = new JLabel("ID Number");
  JTextField     tf4id    = new JTextField(15);
  JLabel         lb4nme   = new JLabel("Full Name");
  JTextField     tf4ln    = new JTextField(3);
  JTextField     tf4fn    = new JTextField(25);
  JTextField     tf4mi    = new JTextField(3);
  JLabel         lb4crs   = new JLabel("Course");
  JTextField     tf4crs   = new JTextField(10);
  JLabel         lb4yr    = new JLabel("Year");
  JTextField     tf4yr    = new JTextField(2);
  JButton        bt4egrd  = new JButton("EDIT/VIEW GRADES");
  
  //grades
  GradesDialog   grddlg;
  JFrame         fmggrd   = new JFrame();
  
  //tab5
  JPanel         tab5     = new JPanel();
  JLabel         lb5st    = new JLabel("REGISTRAR SETTINGS");
  JLabel         lb5sy    = new JLabel("School Year");
  JLabel         lb5sem   = new JLabel("Semester");
  JButton        bt5ok    = new JButton("UPDATE");

  static JSpinner     sr5sy  = new JSpinner();
  static SpinnerModel mod1   = new SpinnerNumberModel(1,1,3,1);
  static JSpinner     cb5sem = new JSpinner(mod1);

  //models and lists
  static StudentList StdList = new StudentList();
  static SemesterList SemList = new SemesterList();
}