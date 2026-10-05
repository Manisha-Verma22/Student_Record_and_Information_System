/**
 *@title Computer Studies Division Student Information System
 *@ver 1.2 (BETA)
 *@author Jeshurun Ray Flores
 *@since jdk 1.4, jdk 1.5
 */
import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;

public class StudRec extends ViewElements
{
  //main
  public static void main(String[] args){
    System.out.println("Loading...");
    try {
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    } catch (Exception e) {}//*/
    String title = "Computer Studies Division Academc and Student Information System (BETA)";
    JFrame main = new StudRec(title);
    Toolkit thKit = main.getToolkit();
    Dimension wndSze = thKit.getScreenSize();
    main.pack();
    int wd = main.getWidth();
    int ht = main.getHeight();
    int x = (int)((wndSze.getWidth()/2)-(wd/2));
    int y = (int)((wndSze.getHeight()/2)-(ht/2));
    main.setBounds(x,y,wd,ht);
    main.setVisible(true);
    main.setResizable(false);
    main.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
  }
  StudRec(String title){
    super(title);
    System.out.println("setting up user interface\ngathering resources.");
    Container cont = getContentPane();
    System.out.println("loading tables");
    setTabs();
    readFiles();    readMisc();
    setStudTable();
    setSbjTable();
    setEnrTable();
    setGrdTable();
    setLayouts();
    cont.add(tabs);
    addListeners();
    addWindowListener(new winListener());
  }
  //misc methods
  //sets all tabs
  private void setTabs(){
    String[] titles = {"Student Masterlist","All Classes",
                       "Enroll Students","Grades","Registrar Settings"};
    String[] ttips = {"Create new student.","View classes","Student registration",
                      "Edit/View Grades","Registrar Settings"};
    addTabComponents();
    
    tabs.addTab(titles[0],new ImageIcon("icons/clown.gif"),tab1,ttips[0]);
    tabs.addTab(titles[1],new ImageIcon("icons/notes.gif"),tab2,ttips[1]);
    tabs.addTab(titles[2],new ImageIcon("icons/files.gif"),tab3,ttips[2]);
    tabs.addTab(titles[3],new ImageIcon("icons/pencil.gif"),tab4,ttips[3]);
    tabs.addTab(titles[4],new ImageIcon("icons/lock.gif"),tab5,ttips[4]);
    
  }
  //adds all components to each tab
  private void addTabComponents(){
    //tab1
    tab1.add(lb1add);			tab1.add(lb1cm);
    tab1.add(lb1id);			tab1.add(tf1id);
    tab1.add(lb1nme);			tab1.add(tf1ln);
    tab1.add(tf1fn);			tab1.add(tf1mi);
    tab1.add(lb1mn);			tab1.add(tf1mn);
    tab1.add(bt1rst);			tab1.add(bt1ok);
    tab1.add(bt1edit);		tab1.add(bt1del);
    tab1.add(lb1fn);			tab1.add(lb1ln);
    tab1.add(lb1mi);
    tf1id.setEditable(false);
    tf1mi.setEditable(false);
    
    //tab2
    tab2.add(lb2sbj);			tab2.add(cb2maj);
    tab2.add(lb2cd);			tab2.add(tf2cd);
    tab2.add(lb2unt);			tab2.add(tf2unt);
    tab2.add(lb2desc);		tab2.add(tf2desc);
    tab2.add(bt2rst);			tab2.add(bt2ok);
    tab2.add(bt2del);			tab2.add(bt2edit);
    tab2.add(lb2msg);

    //tab3
    tab3.add(lb3sbj);			tab3.add(bt3add);
    tab3.add(bt3op);
    bt3op.setEnabled(false);
   
    //tab4
    tab4.add(lb4grd);			
    tab4.add(lb4id);			tab4.add(tf4id);
    tab4.add(lb4nme);			tab4.add(tf4ln);
    tab4.add(tf4fn);			tab4.add(tf4mi);
    tab4.add(lb4crs);			tab4.add(tf4crs);
    tab4.add(lb4yr);			tab4.add(tf4yr);
    tab4.add(bt4egrd);
    tf4id.setEditable(false);	tf4mi.setEditable(false);
    tf4ln.setEditable(false);	tf4crs.setEditable(false);
    tf4yr.setEditable(false); tf4fn.setEditable(false);
    
    //tab5
    tab5.add(lb5st);
    tab5.add(lb5sy);		tab5.add(sr5sy);
    tab5.add(lb5sem);		tab5.add(cb5sem);
    tab5.add(bt5ok);
    
    
  }
  //add listeners to all components in tabs
  private void addListeners(){
    //buttons
    bt1ok.addActionListener(new ButtonListener());
    bt1rst.addActionListener(new ButtonListener());
    bt1edit.addActionListener(new ButtonListener());
    bt1del.addActionListener(new ButtonListener());
    bt2ok.addActionListener(new ButtonListener());
    bt2rst.addActionListener(new ButtonListener());
    bt2edit.addActionListener(new ButtonListener());
    bt2del.addActionListener(new ButtonListener());
    bt3add.addActionListener(new ButtonListener());
    bt3op.addActionListener(new ButtonListener());
    bt4egrd.addActionListener(new ButtonListener());
    bt5ok.addActionListener(new ButtonListener());
    
    //tables
    ListSelectionModel tbModel = tb1stud.getSelectionModel();
    tbModel.addListSelectionListener(new StudTableListener());
    tbModel = tb2sbj.getSelectionModel();
    tbModel.addListSelectionListener(new SubjTableListener());
    tbModel = tb3stud.getSelectionModel();
    tbModel.addListSelectionListener(new EnrTableListener());
    tbModel = tb4grd.getSelectionModel();
    tbModel.addListSelectionListener(new GrdTableListener());
    
    //keylisteners
    tf1mn.addKeyListener(new KeyboardListener());
  }
  //file handling routines
  //read/write main files
  static void writeFiles(){
    try{
      FileOutputStream outStream;
      ObjectOutputStream ooStream;

      outStream = new FileOutputStream("StudentList.dat");
      ooStream = new ObjectOutputStream(outStream);
      
      ooStream.writeObject(StdList);
      ooStream.flush();
      outStream.close();
      
      
      outStream = new FileOutputStream("SemesterList.dat");
      ooStream = new ObjectOutputStream(outStream);
      
      ooStream.writeObject(SemList);
      ooStream.flush();
      outStream.close();
      System.out.println("files saved");
      
    }
    catch (IOException exc){System.out.println("Error writing file!");}
  }
  static void readFiles(){
    try{
      FileInputStream inStream;
      ObjectInputStream oiStream;
      
      try{
        inStream = new FileInputStream("SemesterList.dat");
        oiStream = new ObjectInputStream(inStream);
       
        SemList = (SemesterList)oiStream.readObject();
      }
      catch(FileNotFoundException exc){
        System.out.println("semlist not found.");
        sr5sy.setValue(new Integer(2005));
        cb5sem.setValue(new Integer(1));
        int sy = Integer.parseInt(""+sr5sy.getValue());
        int sem = Integer.parseInt(""+cb5sem.getValue());
        Semester temp = new Semester(sy,sem);
        currentSem = temp;
        SemList.put(currentSem.getSemCode(),currentSem);
      }
      
      inStream = new FileInputStream("StudentList.dat");
      oiStream = new ObjectInputStream(inStream);
      
      StdList = (StudentList)oiStream.readObject();
      
      System.out.println("files reloaded.");
    }
    catch (FileNotFoundException exc){
      System.out.println("File/s not found!");
      writeMisc();
    }
    catch(IOException exc){
      System.out.println("Error reading file! Creating new database.");
      sr5sy.setValue(new Integer(2005));
      cb5sem.setValue(new Integer(1));
      int sy = Integer.parseInt(""+sr5sy.getValue());
      int sem = Integer.parseInt(""+cb5sem.getValue());
      Semester temp = new Semester(sy,sem);
      currentSem = temp;
      SemList.put(currentSem.getSemCode(),currentSem);
      writeMisc();
    }
    catch(ClassNotFoundException exc){
      System.out.println("Class not found.");
    }
  }
  //read/write miscellaneous files
  static void writeMisc(){
    String settings = Student.getCount()+" ";
    String semset = currentSem.getSemCode()+" ";
    try{
      FileWriter outStream = new FileWriter("misc.inf");
      outStream.write(settings+"\n");
      outStream.write(semset);
      outStream.close();
      System.out.println("miscellaneous files saved.");
    }
    catch(IOException exc){System.out.println("error writing misc file.");}
  }
  static void readMisc(){
    try{
      BufferedReader inStream = new BufferedReader(new FileReader("misc.inf"));
      
      int setThis = Integer.parseInt(inStream.readLine().trim());
      Student.setInitialCount(setThis);
      
      String semCode = inStream.readLine().trim();
      currentSem = (Semester)SemList.get(semCode);
      sr5sy.setValue(new Integer(currentSem.getSchoolYear()));
      cb5sem.setValue(new Integer(currentSem.getSem()));
      System.out.println("miscellaneous files reloaded.");
    }
    catch(IOException exc){
      System.out.println("Error reading misc file");
      writeMisc();
    }
  }
  /********** model ***********/
  
  //table models for each table on tabs
  public void setStudTable(){
    tb1stud = new JTable(new StudModel());
    sp1stud = new JScrollPane(tb1stud);
    tab1.add(sp1stud);
    tb1stud.setPreferredScrollableViewportSize(new Dimension(600,200));
    tb1stud.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    bt1edit.setEnabled(false);
    bt1del.setEnabled(false);
    bt2edit.setEnabled(false);
    bt2del.setEnabled(false);
    bt4egrd.setEnabled(false);
  }
  public void setSbjTable(){
    tb2sbj = new JTable(new SbjModel());
    sp2sbj = new JScrollPane(tb2sbj);
    tab2.add(sp2sbj);
    tb2sbj.setPreferredScrollableViewportSize(new Dimension(600,220));
    tb2sbj.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }
  public void setEnrTable(){
    tb3stud = new JTable(new EnrModel());
    sp3stud = new JScrollPane(tb3stud);
    tab3.add(sp3stud);
    tb3stud.setPreferredScrollableViewportSize(new Dimension(755,300));
    tb3stud.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }
  public void setGrdTable(){
    tb4grd = new JTable(new EnrModel());
    sp4grd = new JScrollPane(tb4grd);
    tab4.add(sp4grd);
    tb4grd.setPreferredScrollableViewportSize(new Dimension(600,200));
    tb4grd.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }
  static class StudModel extends AbstractTableModel {
    private String[] columnNames = {"ID NUMBER","FAMILY NAME","FIRST NAME"};
    private Object[][] data = storeObject();
    
    private Object[][] storeObject(){
      Vector vect = StdList.sort();
      Object[][] temp = new Object[vect.size()][3];
      
      for(int a=0;a<vect.size();a++){
        Student temp2 = (Student)vect.elementAt(a);
        temp[a][0] = temp2.getIdNumber();
        temp[a][1] = temp2.getLastName();
        temp[a][2] = temp2.getFirstName();
      }
      return temp;
    }
    public Object[][] getData(){
      return data;
    }
    public int getColumnCount() {
      return columnNames.length;
    }
    public int getRowCount() {
      return data.length;
    }
    public String getColumnName(int col) {
      return columnNames[col];
    }
    public Object getValueAt(int row, int col) {
      return data[row][col];
    }
    public boolean isCellEditable(int row, int col) {
      return false;
    }
  }
  static class SbjModel extends AbstractTableModel {
    private String[] columnNames = {"SUBJECT CODE","SUBJECT DESCRIPTION","UNITS","MAJOR?"};
    private Object[][] data = storeObject();
    
    private Object[][] storeObject(){
      Hashtable subjects = currentSem.getSubjectsOpen();
      Set sKeys = subjects.keySet();
      Vector keys = new Vector(sKeys);
      Collections.sort(keys);
      Object[][] temp = new Object[keys.size()][4];
      int a=0;
      for(Iterator i=keys.iterator();i.hasNext();a++){
        String thisKey = ""+i.next();
        Subject temp2 = (Subject)subjects.get(thisKey);
        
        temp[a][0] = temp2.getCode();
        temp[a][1] = temp2.getDescription();
        temp[a][2] = ""+temp2.getUnits();
        if(temp2.isMajor())
          temp[a][3] = "YES";
        else
          temp[a][3] = "NO";
      }
      return temp;
    }
    public int getColumnCount() {
      return columnNames.length;
    }
    public int getRowCount() {
      return data.length;
    }
    public String getColumnName(int col) {
      return columnNames[col];
    }
    public Object getValueAt(int row, int col) {
      return data[row][col];
    }
    public boolean isCellEditable(int row, int col) {
      return false;
    }
  }
  static class EnrModel extends AbstractTableModel {
    private String[] columnNames = {"ID NUMBER","STUDENT NAME","YEAR","COURSE"};
    private Object[][] data = storeObject();
        
    private Object[][] storeObject(){
      Hashtable students = currentSem.getStudentsEnrolled();
      Set sKeys = students.keySet();
      Vector keys = new Vector(sKeys);
      Collections.sort(keys);
      Object[][] temp = new Object[keys.size()][4];
      
      int a=0;
      for(Iterator i=keys.iterator();i.hasNext();a++){
        String thisKey = ""+i.next();
        Student temp2 = (Student)students.get(thisKey);
        Person temp3 = new Person(temp2.getFirstName(),
                                  temp2.getLastName(),
                                  temp2.getMiddleName());
        temp[a][0] = temp2.getIdNumber();
        temp[a][1] = temp3;
        temp[a][2] = ""+temp2.getYear();
        temp[a][3] = temp2.getCourse();
      }
      return temp;
    }
    public int getColumnCount() {
      return columnNames.length;
    }
    public int getRowCount() {
      return data.length;
    }
    public String getColumnName(int col) {
      return columnNames[col];
    }
    public Object getValueAt(int row, int col) {
      return data[row][col];
    }
    public boolean isCellEditable(int row, int col) {
      return false;
    }
  }
  /********** controller ***********/
  //component listeners
  private class ButtonListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      //tab1
      if(e.getSource()==bt1ok){
        String fn = tf1fn.getText().trim().toUpperCase();
        String ln = tf1ln.getText().trim().toUpperCase();
        String mn = tf1mn.getText().trim().toUpperCase();
        Student temp = new Student(fn,ln,mn);
        StdList.put(temp.getIdNumber(),temp);
        tf1id.setText(temp.getIdNumber());
        writeFiles();  readFiles();
        writeMisc();   readMisc();
        tb1stud.setModel(new StudModel());
        System.out.println("new student added to masterlist");
      }
      if(e.getSource()==bt1rst){
        tf1fn.setEditable(true);
        tf1ln.setEditable(true);
        tf1mn.setEditable(true);
        bt1ok.setEnabled(true);
        clearTab1();
      }
      if(e.getSource()==bt1edit){
        stddlg = new EditStudentDialog(fmestd,selectedStd);
        stddlg.show();
      }
      if(e.getSource()==bt1del){
        StdList.remove(selectedStd.getIdNumber());
        writeFiles();  readFiles();
        tb1stud.setModel(new StudModel());
        System.out.println("student deleted from masterlist.");
      }
      
      //tab2
      if(e.getSource()==bt2ok){
        lb2msg.setText("");
        try{
          String cd = tf2cd.getText().trim().toUpperCase();
          String desc = tf2desc.getText().trim().toUpperCase();
          int units = Integer.parseInt(tf2unt.getText().trim());
          if(units>0){
            Subject temp = new Subject(cd,desc);
            
            if(cb2maj.isSelected())
              temp.setAsMajor(true);
            else
              temp.setAsMajor(false);
            temp.setUnits(units);
            currentSem.openSubject(temp);
            SemList.put(currentSem.getSemCode(),currentSem);
            writeFiles();  readFiles();
            tb2sbj.setModel(new SbjModel());
            cb2maj.setEnabled(true);
            System.out.println("new subject opened");
            clearTab2();
          }
        }
        catch(NumberFormatException exc){
          lb2msg.setText("Please Enter Correct Number of Units.");
        }
      }
      if(e.getSource()==bt2rst){
        tb2sbj.clearSelection();
        tf2cd.setEditable(true);
        tf2desc.setEditable(true);
        bt2ok.setEnabled(true);
        cb2maj.setEnabled(true);
        cb2maj.setSelected(false);
        tf2unt.setEditable(true);
        clearTab2();
      }
      if(e.getSource()==bt2edit){
        sbjdlg = new EditSubjectDialog(fmesbj,selectedSbj);
        sbjdlg.show();
      }
      if(e.getSource()==bt2del){
        currentSem.closeSubject(selectedSbj.getCode());
        SemList.put(currentSem.getSemCode(),currentSem);
        writeFiles();  readFiles();
        tb2sbj.setModel(new SbjModel());
        System.out.println("subject deleted.");
      }
      
      //tab3
      if(e.getSource()==bt3add){
        enrdlg = new EnrollDialog(fmnenr);
        enrdlg.show();
      }
      if(e.getSource()==bt3op){
        regdlg = new RegistryDialog(fmrreg,selectedStd);
        regdlg.show();
      }
      
      //tab4
      if(e.getSource()==bt4egrd){
        grddlg = new GradesDialog(fmggrd,selectedStd);
        grddlg.show();
      }
      
      //tab5
      if(e.getSource()==bt5ok){
        writeFiles();	readFiles();
        int sy  = Integer.parseInt(""+sr5sy.getValue());
        int sem = Integer.parseInt(""+cb5sem.getValue());
        Semester temp = new Semester(sy,sem);
        System.out.println(SemList.containsKey(temp.getSemCode()));
        if(SemList.containsKey(temp.getSemCode())){
          System.out.println("changing semester");
          currentSem = (Semester)SemList.get(temp.getSemCode());
        }
        else{
          System.out.println("openning new semester");
          currentSem = temp;
          SemList.put(temp.getSemCode(),temp);
        }
        tb2sbj.setModel(new SbjModel());
        tb3stud.setModel(new EnrModel());
        tb4grd.setModel(new EnrModel());
        writeFiles();	readFiles();
        writeMisc();	readMisc();
        tabs.setSelectedIndex(2);
        System.out.println("semester updated");
      }
    }
  }
  private class StudTableListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) {

     if(e.getValueIsAdjusting()) return;
     ListSelectionModel lsm = (ListSelectionModel)e.getSource();
     if(lsm.isSelectionEmpty()) {
       selectedStd=null;
       bt1edit.setEnabled(false);
       bt1del.setEnabled(false);
     }
     else {
       bt1edit.setEnabled(true);
       bt1del.setEnabled(true);
       int selectedRow = lsm.getMinSelectionIndex();
       StudModel tempMod = new StudModel();
       String id = ""+tempMod.getValueAt(selectedRow,0);
       selectedStd = (Student)StdList.get(id);
       tf1id.setText(id);
       tf1fn.setText(selectedStd.getFirstName());
       tf1ln.setText(selectedStd.getLastName());
       tf1mi.setText(selectedStd.getMiddleInitial());
       tf1mn.setText(selectedStd.getMiddleName());
       tf1fn.setEditable(false);
       tf1ln.setEditable(false);
       tf1mn.setEditable(false);
       bt1ok.setEnabled(false);
     }
    }
  }
  private class SubjTableListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) {
     if(e.getValueIsAdjusting()) return;
     
     ListSelectionModel lsm = (ListSelectionModel)e.getSource();
     if(lsm.isSelectionEmpty()) {
       selectedSbj=null;
       clearTab2();
       bt2edit.setEnabled(false);
       bt2del.setEnabled(false);
       cb2maj.setEnabled(false);
       tf2unt.setEditable(false);
     }
     else {
       bt2edit.setEnabled(true);
       bt2del.setEnabled(true);
       cb2maj.setEnabled(true);
       tf2unt.setEditable(true);
       int selectedRow = lsm.getMinSelectionIndex();
       SbjModel tempMod = new SbjModel();
       String code = ""+tempMod.getValueAt(selectedRow,0);
       Hashtable SbjList = currentSem.getSubjectsOpen();
       selectedSbj = (Subject)SbjList.get(code);
       tf2cd.setText(code);
       tf2desc.setText(selectedSbj.getDescription());
       tf2unt.setText(""+selectedSbj.getUnits());
       if(selectedSbj.isMajor())
         cb2maj.setSelected(true);
       else
         cb2maj.setSelected(false);
       tf2cd.setEditable(false);
       tf2desc.setEditable(false);
       cb2maj.setEnabled(false);
       bt2ok.setEnabled(false);
       tf2unt.setEditable(false);
     }
    }
  }
  private class EnrTableListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) {
      if(e.getValueIsAdjusting()) return;
     
      ListSelectionModel lsm = (ListSelectionModel)e.getSource();
      if(lsm.isSelectionEmpty()) {
        bt3op.setEnabled(false);
      }
      else {
        bt3op.setEnabled(true);
        int selectedRow = lsm.getMinSelectionIndex();
        EnrModel tempMod = new EnrModel();  
        String id = ""+tempMod.getValueAt(selectedRow,0);
        Hashtable StudList = currentSem.getStudentsEnrolled();
        Student temp = (Student)StudList.get(id);
        selectedStd = temp;
      }
    }
  }
  private class GrdTableListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) {
      if(e.getValueIsAdjusting()) return;
     
      ListSelectionModel lsm = (ListSelectionModel)e.getSource();
      if(lsm.isSelectionEmpty()) {
        bt4egrd.setEnabled(false);
        clearTab4();
      }
      else {
        bt4egrd.setEnabled(true);
        int selectedRow = lsm.getMinSelectionIndex();
        EnrModel tempMod = new EnrModel();
        String id = ""+tempMod.getValueAt(selectedRow,0);
        Hashtable ht = currentSem.getStudentsEnrolled();
        Student stud = (Student)ht.get(id);
        tf4id.setText(stud.getIdNumber());
        tf4ln.setText(stud.getLastName());
        tf4fn.setText(stud.getFirstName());
        tf4mi.setText(stud.getMiddleInitial());
        tf4crs.setText(stud.getCourse());
        tf4yr.setText(""+stud.getYear());
        selectedStd = stud;
      }
    }
  }
  private class winListener extends WindowAdapter {
    public void windowClosing(WindowEvent we) {
      Object[] options = { "YES", "NO" };
      int option = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?","EXIT",
                   JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                   null, options, options[0]);
      if (option == JOptionPane.OK_OPTION){
        System.out.println("Thank You For Using Me.. :D");
        System.exit(0);
      }
    }
  }
  private class KeyboardListener implements KeyListener{
    public void keyTyped(KeyEvent e){
    }
    public void keyReleased(KeyEvent e){
      try{
        String temp = tf1mn.getText();
        String temp2 = ""+temp.toUpperCase().charAt(0)+".";
        tf1mi.setText(temp2);
      }
      catch(StringIndexOutOfBoundsException exc){
        tf1mi.setText("");
      }
    }
    public void keyPressed(KeyEvent e){
    }
  }
}