import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class EditSubjectDialog extends JDialog {
  //model
  Subject selected;
  
  //view
  JLabel      lbsstd  = new JLabel("EDIT SUBJECT");
  JLabel      lbscd   = new JLabel("Subject Code");
  JLabel      lbsunt  = new JLabel("Units:");
  JTextField  tfsunt  = new JTextField(2);
  JTextField  tfscd   = new JTextField(15);
  JCheckBox   cbsmaj  = new JCheckBox("Major?");
  JLabel      lbsds   = new JLabel("Subject Description");
  JTextField  tfsds   = new JTextField(30);
  JButton     btsok   = new JButton("SAVE");
  JButton     btscan  = new JButton("CANCEL");
  
  EditSubjectDialog(JFrame parent, Subject selected) {
    super(parent, true);
    this.selected = selected;
    setTitle("Edit Subject");

    Container cont = getContentPane();
    EasierGridLayout layout = new EasierGridLayout();

    cont.setLayout(layout);	cont.add(lbsstd);
    cont.add(lbscd);				cont.add(tfscd);
    cont.add(lbsunt);       cont.add(tfsunt);
    cont.add(lbsds);				cont.add(tfsds);
    cont.add(btscan);				cont.add(btsok);
    cont.add(cbsmaj);
    
    layout.setConstraints(cbsmaj,2,5,1,1);
    layout.setConstraints(lbsstd,1,1,4,1);
    layout.setConstraints(lbscd,2,1,1,1);
    layout.setConstraints(lbsunt,2,3,1,1);
    layout.setConstraints(tfsunt,2,4,1,1);
    layout.setConstraints(tfscd,2,2,1,1);
    layout.setConstraints(lbsds,3,1,2,1);
    layout.setConstraints(btsok,5,3,2,1);
    layout.setConstraints(btscan,5,5,1,1);
    layout.defineOwnConstraints(tfsds,4,1,5,1, 0,0, 0,10, 0,0,0,0);
    
    if(selected.isMajor())
      cbsmaj.setSelected(true);
    else
      cbsmaj.setSelected(false);
    
    fillFormWith(selected);
    
    btscan.addActionListener(new ButtonListener());
    btsok.addActionListener(new ButtonListener());
    
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
  
  //fill up frame fields
  private void fillFormWith(Subject This){
    tfscd.setText(This.getCode());
    tfsds.setText(This.getDescription());
    tfsunt.setText(""+This.getUnits());
  }
  
  //controller
  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      if(event.getSource() == btsok ) {
        try{
          ViewElements.currentSem.closeSubject(selected.getCode());
          String code = tfscd.getText().trim().toUpperCase();
          String desc = tfsds.getText().trim().toUpperCase();
          double units   = Double.parseDouble(tfsunt.getText().trim());
          if(units>1){
            Subject temp = new Subject(code,desc);
            if(cbsmaj.isSelected())
              temp.setAsMajor(true);
            else
              temp.setAsMajor(false);
            temp.setUnits(units);
            ViewElements.currentSem.openSubject(temp);
            ViewElements.SemList.put(ViewElements.currentSem.getSemCode(),ViewElements.currentSem);
            StudRec.writeFiles();		StudRec.readFiles();
            ViewElements.tb2sbj.setModel(new StudRec.SbjModel());
            System.out.println("subject editted");
            dispose();
          }
        }
        catch(NumberFormatException exc){}
      }
      
      if(event.getSource() == btscan) {
        dispose();
      }
    }
  }
  private class MyWindowAdapter extends WindowAdapter {
    public void windowClosing(WindowEvent we) {
      dispose();
    }
  }
}