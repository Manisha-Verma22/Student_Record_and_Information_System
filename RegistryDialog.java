import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;

public class RegistryDialog extends JDialog {
  
  //model
  static Student selected;
  Vector addedSbj = new Vector();
  Subject slctdsbj;
  
  //view
  static JTable tbrsbj;
  JScrollPane   sprsbj;
  JLabel        lbrid   = new JLabel("id here");
  JLabel        lbrnme  = new JLabel("name here");
  JLabel        lbrcrs  = new JLabel("Course");
  JLabel        lbryr   = new JLabel("Year");
  SpinnerModel  mod     = new SpinnerNumberModel(1,1,4,1);
  JSpinner      srryr   = new JSpinner(mod);
  String[]      crs     = {"BS-CS","BS-IT","BS-IM"};
  JComboBox     cbrcrs  = new JComboBox(crs);
  JButton       btrok   = new JButton("Save");
  JButton       btrcan  = new JButton("Close");
  JButton       btradd  = new JButton("Add Subject");
  JButton       btrdrp  = new JButton("Drop Subject");
  
  RegistryDialog(JFrame parent, Student selectedStud) {
    super(parent, true);
    btrdrp.setEnabled(true);
    selected = selectedStud;
    setSbjTable();
    setTitle("Student Registration Information");
    Container cont = getContentPane();
    EasierGridLayout layout = new EasierGridLayout();
    cont.setLayout(layout);

    cont.add(lbrid);		cont.add(lbrnme);
    cont.add(btrok);		cont.add(btrcan);
    cont.add(lbryr);		cont.add(srryr);
    cont.add(lbrcrs);		cont.add(cbrcrs);
    cont.add(sprsbj);		cont.add(btradd);
    cont.add(btrdrp);
    
    layout.setConstraints(lbrid,1,1,2,1);
    layout.setConstraints(lbrnme,2,1,2,1);
    layout.setConstraints(lbrcrs,3,1,1,1);
    layout.setConstraints(lbryr,4,1,1,1);
    layout.setConstraints(sprsbj,5,1,3,1);
    layout.defineOwnConstraints(cbrcrs,3,2,1,1, 0,0, 0,17, 0,0,0,0);
    layout.defineOwnConstraints(srryr,4,2,1,1, 0,0, 0,17, 0,0,0,0);
    layout.defineOwnConstraints(btradd,4,2,2,1, 0,0, 0,13, 0,0,0,0);
    layout.defineOwnConstraints(btrdrp,6,1,2,1, 0,0, 0,17, 0,0,0,0);
    layout.defineOwnConstraints(btrok,6,3,1,1, 0,0, 0,13, 5,0,0,0);
    layout.defineOwnConstraints(btrcan,7,3,1,1, 0,0, 0,13, 5,0,0,0);
    fillFormWith(selected);
    
    btrok.addActionListener(new ButtonListener());
    btrcan.addActionListener(new ButtonListener());
    btradd.addActionListener(new ButtonListener());
    btrdrp.addActionListener(new ButtonListener());
    tbrsbj.setModel(new SubjectModel());
    ListSelectionModel tbModel = tbrsbj.getSelectionModel();
    tbModel.addListSelectionListener(new SubjTableListener());
    
    Toolkit thKit = getToolkit();
    Dimension wndSze = thKit.getScreenSize();
    pack();
    
    int wd = getWidth();
    int ht = getHeight();
    int x  = (int)((wndSze.getWidth()/2)-(wd/2));
    int y  = (int)((wndSze.getHeight()/2)-(ht/2));
    setBounds(x,y,wd,ht);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  //fill up for with necessary information.
  private void fillFormWith(Student thisStudent){
    Person temp = new Person(thisStudent.getFirstName(),
                             thisStudent.getLastName(),
                             thisStudent.getMiddleName());
    lbrnme.setText("Full Name:     "+temp);
    lbrid.setText("ID Number:     "+thisStudent.getIdNumber());
    String stdCourse = thisStudent.getCourse();
    int index = -1;
    if(stdCourse.trim().equals("BS-CS"))
      index = 0;
    else if(stdCourse.trim().equals("BS-IT"))
      index = 1;
    else if(stdCourse.trim().equals("BS-IM"))
      index = 2;
    cbrcrs.setSelectedIndex(index);
    srryr.setValue(new Integer(thisStudent.getYear()));
  }
  private String getSCode(String thisToString){
    StringTokenizer str = new StringTokenizer(thisToString," -");
    String thisCode = str.nextToken().trim();
    return thisCode;
  }
  //setup table and table model
  public void setSbjTable(){
    tbrsbj = new JTable(new SubjectModel());
    sprsbj = new JScrollPane(tbrsbj);
    tbrsbj.setPreferredScrollableViewportSize(new Dimension(550,150));
    tbrsbj.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }
  static class SubjectModel extends AbstractTableModel {
    private String[] columnNames = {"SUBJECT","UNITS","MAJOR?"};
    private Object[][] data = storeObject();
    
    private Object[][] storeObject(){
    	Hashtable subj = selected.getSubjects();
    	Set sKeys = subj.keySet();
      Vector keys = new Vector(sKeys);
      Collections.sort(keys);
      Object[][] temp = new Object[keys.size()][3];
      
      int a=0;
      for(Iterator i=keys.iterator();i.hasNext();a++){
        String thisKey = ""+i.next();
        Subject temp2 = (Subject)subj.get(thisKey);
        temp[a][0] = ""+temp2;
        temp[a][1] = ""+temp2.getUnits();
        if(temp2.isMajor())
          temp[a][2] = "YES";
        else
          temp[a][2] = "NO";
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
  
  //controller
  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent ev) {
      if(ev.getSource()==btrok){
      	String crs = ""+cbrcrs.getSelectedItem();
      	int yr = Integer.parseInt(""+srryr.getValue());
      	selected.setCourse(crs);
      	selected.setYear(yr);
      	ViewElements.tb4grd.setModel(new StudRec.EnrModel());
      	ViewElements.tb3stud.setModel(new StudRec.EnrModel());
      	StudRec.writeFiles();		StudRec.readFiles();
        dispose();
      }
      if(ev.getSource()==btrcan){
        dispose();
      }
      if(ev.getSource()==btradd){
      	AddSubjectDialog asbj = new AddSubjectDialog(new JFrame(),selected);
      	asbj.show();
      }
      if(ev.getSource()==btrdrp){
        selected.dropSubject(""+slctdsbj.getCode());
        ViewElements.currentSem.enrollStudent(selected);
        StudRec.writeFiles();		StudRec.readFiles();
        tbrsbj.setModel(new SubjectModel());
      	System.out.println("subject dropped.");
      }
    }
  }
  private class SubjTableListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) {
      if(e.getValueIsAdjusting()) return;
      ListSelectionModel lsm = (ListSelectionModel)e.getSource();
      if(lsm.isSelectionEmpty()) {
        btrdrp.setEnabled(false);
      }
      else {
        btrdrp.setEnabled(true);
        int selectedRow = lsm.getMinSelectionIndex();
        SubjectModel tempMod = new SubjectModel();
        String subj = ""+tempMod.getValueAt(selectedRow,0);
        String code = getSCode(subj);
        Hashtable ht = ViewElements.currentSem.getSubjectsOpen();
        slctdsbj = (Subject)ht.get(code);
     }
    }
  }
  private class MyWindowAdapter extends WindowAdapter {
    public void windowClosing(WindowEvent we) {
      dispose();
    }
  }
}