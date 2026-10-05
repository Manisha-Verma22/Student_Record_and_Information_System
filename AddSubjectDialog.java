import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;

public class AddSubjectDialog extends JDialog {
  //model
  Student selected;
  Subject slctdsbj;
  
  //view
  JTable       tbdsbj;
  JScrollPane  spdsbj;
  JLabel       lbdadd  = new JLabel("SELECT SUBJECT FROM LIST");
  JButton      btdok   = new JButton("Add this Subject");
  JButton      btdcl   = new JButton("Close");
  
  
  AddSubjectDialog(JFrame parent, Student thisStudent) {
    super(parent, true);
    setTitle("Add Subject");

    Container cont = getContentPane();
    EasierGridLayout layout = new EasierGridLayout();
    cont.setLayout(layout);
    
    setSbjTable();
    
    cont.add(lbdadd);
    cont.add(btdok);
    cont.add(btdcl);
    
    layout.setConstraints(lbdadd,1,1,1,1);
    layout.setConstraints(spdsbj,2,1,1,1);
    layout.setConstraints(btdok,3,1,1,1);
    layout.setConstraints(btdcl,4,1,1,1);
    
    btdok.addActionListener(new ButtonListener());
    btdcl.addActionListener(new ButtonListener());
    
    ListSelectionModel tbModel = tbdsbj.getSelectionModel();
    tbModel.addListSelectionListener(new SubjTableListener());
    
    btdok.setEnabled(false);
    Toolkit thKit = getToolkit();
    Dimension wndSze = thKit.getScreenSize();
    pack();
    int wd = getWidth();
    int ht = getHeight();
    int x = (int)((wndSze.getWidth()/2)-(wd/2));
    int y = (int)((wndSze.getHeight()/2)-(ht/2));
    setBounds(x,y,wd,ht);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  //set table
  public void setSbjTable(){
    tbdsbj = new JTable(new StudRec.SbjModel());
    spdsbj = new JScrollPane(tbdsbj);
    this.getContentPane().add(spdsbj);
    tbdsbj.setPreferredScrollableViewportSize(new Dimension(400,200));
    tbdsbj.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }
  
  //controller
  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent ev) {
      if(ev.getSource()==btdok){
      	RegistryDialog.selected.addSubject(slctdsbj);
      	ViewElements.currentSem.enrollStudent(RegistryDialog.selected);
        ViewElements.SemList.put(ViewElements.currentSem.getSemCode(),ViewElements.currentSem);
        RegistryDialog.tbrsbj.setModel(new RegistryDialog.SubjectModel());
        StudRec.writeFiles();		StudRec.readFiles();
        System.out.println("subject added");
        dispose();
      }
      if(ev.getSource()==btdcl){
        System.out.println("closed");
        dispose();
      }
    }
  }
  private class SubjTableListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) {
      if(e.getValueIsAdjusting()) return;
      ListSelectionModel lsm = (ListSelectionModel)e.getSource();
      if(lsm.isSelectionEmpty()) {
        btdok.setEnabled(false);
      }
      else {
        btdok.setEnabled(true);
        int selectedRow = lsm.getMinSelectionIndex();
        StudRec.SbjModel tempMod = new StudRec.SbjModel();
        String code = ""+tempMod.getValueAt(selectedRow,0);
        Hashtable ht = ViewElements.currentSem.getSubjectsOpen();
        slctdsbj = new Subject((Subject)ht.get(code));
     }
    }
  }
  private class MyWindowAdapter extends WindowAdapter {
    public void windowClosing(WindowEvent we) {
      dispose();
    }
  }
}