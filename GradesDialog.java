import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.text.NumberFormat;

public class GradesDialog extends JDialog {
  
  //model
  static Student selected;
  static Subject slctdsbj;
  
  static JTable  tbgsbj;
  JScrollPane    spgsbj;
  static boolean flag;
  
  
  //view
  JLabel        lbgid  = new JLabel("id");
  JLabel        lbgnme = new JLabel("fullname");
  JLabel        lbgcrs = new JLabel("crs");
  JLabel        lbgyr  = new JLabel("year");
  JLabel        lbgwpa = new JLabel("WPA");
  JTextField    tfgwpa = new JTextField(5);
  JLabel        lbgmsc = new JLabel("MSCA");
  JTextField    tfgmsc = new JTextField(5);
  JLabel        lbgstt = new JLabel();
  
  JButton       btgok  = new JButton("CLOSE");
  JButton       btggr  = new JButton("CHANGE GRADE");
  
  GradesDialog(JFrame parent, Student thisStudent) {
    super(parent, true);
    btggr.setEnabled(false);
    selected = thisStudent;
    setGrdTable();
    spgsbj = new JScrollPane(tbgsbj);
    
    setTitle("Student Academic Information");
    Container cont = getContentPane();
    EasierGridLayout layout = new EasierGridLayout();
    cont.setLayout(layout);
    cont.add(lbgid);
    cont.add(lbgnme);
    cont.add(lbgcrs);
    cont.add(lbgyr);
    
    cont.add(spgsbj);
    
    cont.add(lbgwpa);	cont.add(tfgwpa);
    cont.add(lbgmsc);	cont.add(tfgmsc);
    cont.add(lbgstt);
    
    cont.add(btgok);  cont.add(btggr);
    tfgwpa.setEditable(false);
    tfgmsc.setEditable(false);
    
    
    layout.setConstraints(lbgstt,7,1,6,1);
    layout.setConstraints(lbgid,1,1,6,1);
    layout.setConstraints(lbgnme,2,1,6,1);
    layout.setConstraints(lbgcrs,3,1,6,1);
    layout.setConstraints(lbgyr,4,1,6,1);
    layout.setConstraints(spgsbj,5,1,6,1);
    layout.setConstraints(lbgwpa,6,1,1,1);
    layout.setConstraints(tfgwpa,6,2,1,1);
    layout.setConstraints(lbgmsc,6,3,1,1);
    layout.setConstraints(tfgmsc,6,4,1,1);
    layout.setConstraints(btgok,7,6,1,1);
    layout.setConstraints(btggr,6,6,1,1);
    fillFormWith();
    
    btgok.addActionListener(new ButtonListener());
    btggr.addActionListener(new ButtonListener());
    
    lbgstt.setFont(new Font("Verdana", 3, 12));
    lbgstt.setForeground(new Color(190,0,0));
    
    NumberFormat dec = NumberFormat.getInstance();
    dec.setMaximumFractionDigits(2);
    dec.setMinimumFractionDigits(2);
    
    String MSCA = ""+dec.format(selected.getMSCA());
    String WPA = ""+dec.format(selected.getWPA());
    
    tfgwpa.setText(WPA);
    tfgmsc.setText(MSCA);
    
    ListSelectionModel tbModel = tbgsbj.getSelectionModel();
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
  
  //tokenize and get code on tostring of subject
  private String getSCode(String thisToString){
    StringTokenizer str = new StringTokenizer(thisToString," -");
    String thisCode = str.nextToken().trim();
    return thisCode;
  }
  
  //set-up table and model
  public void setGrdTable(){
    tbgsbj = new JTable(new GrdModel());
    spgsbj = new JScrollPane(tbgsbj);
    this.getContentPane().add(spgsbj);
    tbgsbj.setPreferredScrollableViewportSize(new Dimension(500,150));
    tbgsbj.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }
  static class GrdModel extends AbstractTableModel {
    private String[] columnNames = {"SUBJECT","GRADE"};
    private Object[][] data = storeObject();
    
    private Object[][] storeObject(){
      Hashtable studs = ViewElements.currentSem.getStudentsEnrolled();
      Student thisStud = (Student)studs.get(selected.getIdNumber());
      Hashtable subjects = thisStud.getSubjects();
      
      Set sKeys = subjects.keySet();
      Vector keys = new Vector(sKeys);
      Collections.sort(keys);
      Object[][] temp = new Object[keys.size()][2];
      int a=0;
      for(Iterator i=keys.iterator();i.hasNext();a++){
        String thisKey = ""+i.next();
        Subject temp2 = (Subject)subjects.get(thisKey);
        temp[a][0] = ""+temp2;
        temp[a][1] = ""+temp2.getGrade();
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
  private void fillFormWith(){
  	Person temp = new Person(selected.getFirstName(),selected.getLastName(),selected.getMiddleName());
    lbgid.setText("ID NUMBER:   "+selected.getIdNumber());
    lbgnme.setText("FULL NAME:   "+temp);
    lbgcrs.setText("COURSE:  "+selected.getCourse());
    lbgyr.setText("YEAR:  "+selected.getYear());
    lbgstt.setText(selected.getStat());
  }
  
  //controller
  private class ButtonListener implements ActionListener {
    private double ms;
    private double wp;
    public void actionPerformed(ActionEvent ev) {
      if(ev.getSource()==btgok){
        dispose();
      }
      if(ev.getSource()==btggr){
        JFrame temp = new JFrame();
        EditGradeDialog editdlg = new EditGradeDialog(temp,slctdsbj);
        editdlg.show();
        if(flag){
          selected.setStat("");
          System.out.println("Computing...");
          compute();
          lbgstt.setText(selected.getStat());
        }
      }
    }
    public void compute(){
      NumberFormat dec = NumberFormat.getInstance();
      dec.setMaximumFractionDigits(2);
      dec.setMinimumFractionDigits(2);

      String MSCA = ""+dec.format(selected.getMSCA());
      String WPA = ""+dec.format(selected.getWPA());
      
      int maj = 0;		int min = 0;
      int majf = 0;		int minf = 0;
      Hashtable ht = selected.getSubjects();
      Set kset = ht.keySet();
      Vector keys = new Vector(kset);
      for(Iterator i=keys.iterator();i.hasNext();){
        String code = ""+i.next();
        Subject temp = (Subject)ht.get(code);
        if(temp.isMajor()){
          maj++;
          if(temp.getGrade()<75)
            majf++;
        }
        else{
          min++;
          if(temp.getGrade()<75)
            minf++;
        }
      }

      ms = Double.parseDouble(MSCA);
      wp = Double.parseDouble(WPA);
      
      tfgwpa.setText(WPA);
      tfgmsc.setText(MSCA);
      
      if((wp<75)||(ms<75)||(majf>1)){
        Object[] options = { "RECOMMEND TO SHIFT", "GIVE PROBATION" };
        int option = JOptionPane.showOptionDialog(null, "Please select decision","STUDENT IS ADVICED TO SHIFT",
                 JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                 null, options, options[0]);
        if(option == 0)
          selected.setProbationStatus(Student.SHIFT);
        else if(option == 1)
          selected.setProbationStatus(Student.SECOND_PROB);
        ViewElements.currentSem.enrollStudent(selected);
        ViewElements.SemList.put(ViewElements.currentSem.getSemCode(),ViewElements.currentSem);
        StudRec.writeFiles();	StudRec.readFiles();
          
      }
      else if((wp<83)||(ms<80)||(majf>0)||(minf>1)){
        Object[] options = { "FIRST PROBATION", "SECOND PROBATION" };
        int option = JOptionPane.showOptionDialog(null, "Please select decision","STUDENT IS UNDER PROBATION",
                 JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                 null, options, options[0]);
        if(option == 0)
          selected.setProbationStatus(Student.FIRST_PROB);
        else if(option == 1)
          selected.setProbationStatus(Student.SECOND_PROB);
        ViewElements.currentSem.enrollStudent(selected);
        ViewElements.SemList.put(ViewElements.currentSem.getSemCode(),ViewElements.currentSem);
        StudRec.writeFiles();	StudRec.readFiles();
      }
    }
  }
  private class SubjTableListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) {
      if(e.getValueIsAdjusting()) return;
      ListSelectionModel lsm = (ListSelectionModel)e.getSource();
      if(lsm.isSelectionEmpty()) {
        btggr.setEnabled(false);
      }
      else {
        btggr.setEnabled(true);
        int selectedRow = lsm.getMinSelectionIndex();
        GrdModel tempMod = new GrdModel();
        String code = ""+tempMod.getValueAt(selectedRow,0);
        Hashtable ht = selected.getSubjects();
        slctdsbj = (Subject)ht.get(getSCode(code));
     }
    }
  }
  private class MyWindowAdapter extends WindowAdapter {
    public void windowClosing(WindowEvent we) {
      dispose();
    }
  }
}