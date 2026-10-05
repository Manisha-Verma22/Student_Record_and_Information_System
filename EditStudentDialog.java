import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class EditStudentDialog extends JDialog {
  
  //view
  JLabel lbestd = new JLabel("EDIT STUDENT INFORMATION");
  JLabel lbeid = new JLabel("ID Number");
  JTextField tfeid = new JTextField(5);
  JLabel lbenme = new JLabel("Student Name");
  JTextField tfeln = new JTextField(15);
  JLabel lbecm = new JLabel(",");
  JTextField tfefn = new JTextField(15);
  JTextField tfemi = new JTextField(2);
  JLabel lbeln = new JLabel("Last Name");
  JLabel lbefn = new JLabel("First Name");
  JLabel lbemi = new JLabel("MI");
  JLabel lbemn = new JLabel("Middle Name");
  JTextField tfemn = new JTextField(10);
  JButton bteok = new JButton("SAVE");
  JButton btecan = new JButton("CANCEL");
  
  EditStudentDialog(JFrame parent, Student selected) {
    super(parent, true);
    setTitle("Edit Student");

    Container cont = getContentPane();
    EasierGridLayout layout = new EasierGridLayout();
    cont.setLayout(layout);
    cont.setLayout(layout);
    cont.add(lbestd);
    cont.add(lbeid);		cont.add(tfeid);
    cont.add(lbenme);		cont.add(lbecm);
    cont.add(tfeln);		cont.add(tfefn);		cont.add(tfemi);
    cont.add(lbeln);		cont.add(lbefn);		cont.add(lbemi);
    cont.add(lbemn);		cont.add(tfemn);
    cont.add(bteok);		cont.add(btecan);
    
    tfeid.setEditable(false);
    tfemi.setEditable(false);
    
    layout.setConstraints(lbestd,1,1,7,1);
    layout.setConstraints(lbeid,2,1,1,1);
    layout.setConstraints(tfeid,2,2,1,1);
    layout.setConstraints(lbenme,3,1,1,1);
    layout.setConstraints(tfeln,3,2,1,1);
    layout.setConstraints(lbecm,3,3,1,1);
    layout.setConstraints(tfefn,3,4,1,1);
    layout.setConstraints(tfemi,3,5,1,1);
    layout.setConstraints(lbeln,4,2,1,1);
    layout.setConstraints(lbefn,4,4,1,1);
    layout.setConstraints(lbemi,4,5,1,1);
    layout.setConstraints(lbemn,5,1,1,1);
    layout.setConstraints(tfemn,5,2,1,1);
    layout.setConstraints(btecan,6,5,1,1);
    layout.setConstraints(bteok,6,4,2,1);
    fillFormWith(selected);

    btecan.addActionListener(new ButtonListener());
    bteok.addActionListener(new ButtonListener());
    tfemn.addKeyListener(new KeyboardListener());
    
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
  private void fillFormWith(Student This){
    tfeid.setText(This.getIdNumber());
    tfefn.setText(This.getFirstName());
    tfeln.setText(This.getLastName());
    tfemn.setText(This.getMiddleName());
    tfemi.setText(This.getMiddleInitial());
  }
  
  //controller
  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      if(event.getSource() == bteok ) {
        String id = tfeid.getText();
        Student temp = (Student)ViewElements.StdList.get(id);
        temp.setFirstName(tfefn.getText().trim().toUpperCase());
        temp.setLastName(tfeln.getText().trim().toUpperCase());
        temp.setMiddleName(tfemn.getText().trim().toUpperCase());
        StudRec.writeFiles();		StudRec.readFiles();
        ViewElements.tb1stud.setModel(new StudRec.StudModel());
        System.out.println("student editted");
        dispose();
      }
      
      if(event.getSource() == btecan) {
        dispose();
      }
    }
  }
  private class KeyboardListener implements KeyListener{
    public void keyTyped(KeyEvent e){
    }
    public void keyReleased(KeyEvent e){
      try{
        String temp = tfemn.getText();
        String temp2 = ""+temp.toUpperCase().charAt(0)+".";
        tfemi.setText(temp2);
      }
      catch(StringIndexOutOfBoundsException exc){
        tfemi.setText("");
      }
    }
    public void keyPressed(KeyEvent e){
    }
  }
  private class MyWindowAdapter extends WindowAdapter {
    public void windowClosing(WindowEvent we) {
      dispose();
    }
  }
}