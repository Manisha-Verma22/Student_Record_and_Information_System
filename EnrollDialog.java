import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;

public class EnrollDialog extends JDialog {
  
  //model
  
  Student selected;
  
  //view
  JTable       tbnstud;
  JScrollPane  spnstud;
  JLabel       lbnadd  = new JLabel("STUDENT ENROLLMENT",JLabel.CENTER);
  JLabel       lbncrs  = new JLabel("Course");
  JLabel       lbnyr   = new JLabel("Year");
  SpinnerModel mod    = new SpinnerNumberModel(1,1,4,1);
  JSpinner     srnyr   = new JSpinner(mod);
  String[]     crs     = {"BS-CS","BS-IT","BS-IM"};
  JComboBox    cbncrs  = new JComboBox(crs);
  JButton      btnok   = new JButton("Enroll Student");
  JButton      btncan  = new JButton("Cancel");
  
  
  EnrollDialog(JFrame parent) {
    super(parent, true);
    setTitle("Student Enrollment");
    btnok.setEnabled(false);
    setStudTable();
    Container cont = getContentPane();
    EasierGridLayout layout = new EasierGridLayout();
    cont.setLayout(layout);
    
    cont.add(lbnadd);
    cont.add(lbnyr);		cont.add(srnyr);
    cont.add(lbncrs);		cont.add(cbncrs);
    cont.add(btnok);		cont.add(btncan);
    cont.add(spnstud);
    
    layout.setConstraints(lbnyr,2,1,1,1);
    layout.setConstraints(srnyr,2,2,1,1);
    layout.setConstraints(lbncrs,3,1,1,1);
    layout.setConstraints(cbncrs,3,2,1,1);
    layout.setConstraints(spnstud,4,1,3,1);
    layout.defineOwnConstraints(lbnadd,1,1,3,1, 0,0, 0,10, 0,0,0,0);
    layout.defineOwnConstraints(btnok,5,3,1,1, 0,0, 0,13, 5,0,0,0);
    layout.defineOwnConstraints(btncan,6,3,1,1, 0,0, 0,13, 5,0,0,0);
    
    btnok.addActionListener(new ButtonListener());
    btncan.addActionListener(new ButtonListener());

    ListSelectionModel tbModel = tbnstud.getSelectionModel();
    tbModel.addListSelectionListener(new StudTableListener());
    
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
  
  //set-up table
  public void setStudTable(){
    System.out.println("model initialized");
    tbnstud = new JTable(new StudRec.StudModel());
    spnstud = new JScrollPane(tbnstud);
    tbnstud.setPreferredScrollableViewportSize(new Dimension(400,150));
    tbnstud.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }

  //controller
  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent ev) {
      if(ev.getSource()==btnok){
        selected.setCourse(""+cbncrs.getSelectedItem());
        selected.setYear(Integer.parseInt(""+srnyr.getValue()));
        ViewElements.currentSem.enrollStudent(selected);
        ViewElements.SemList.put(ViewElements.currentSem.getSemCode(),ViewElements.currentSem);
        ViewElements.tb3stud.setModel(new StudRec.EnrModel());
        ViewElements.tb4grd.setModel(new StudRec.EnrModel());
        StudRec.writeFiles();		StudRec.readFiles();
        System.out.println("student enrolled");
        dispose();
        JFrame tmpfm = new JFrame();
        RegistryDialog regdlg = new RegistryDialog(tmpfm,selected);
        regdlg.show();
        
      }
      if(ev.getSource()==btncan){
        dispose();
      }
    }
  }
  private class StudTableListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) {
      if(e.getValueIsAdjusting()) return;
      ListSelectionModel lsm = (ListSelectionModel)e.getSource();
      if(lsm.isSelectionEmpty()) {
        btnok.setEnabled(false);
      }
      else {
        btnok.setEnabled(true);
     
       int selectedRow = lsm.getMinSelectionIndex();
       AbstractTableModel tempMod = new StudRec.StudModel();
       String id = ""+tempMod.getValueAt(selectedRow,0);
       selected = (Student)ViewElements.StdList.get(id);
     }
    }
  }
  private class MyWindowAdapter extends WindowAdapter {
    public void windowClosing(WindowEvent we) {
      dispose();
    }
  }
}