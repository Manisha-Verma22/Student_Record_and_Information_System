import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;

public class AddSubjectDialog extends JDialog {

    // Model
    Student selected;
    Subject slctdsbj;

    // View
    JTable tbdsbj;
    JScrollPane spdsbj;

    JLabel lbdadd = new JLabel("SELECT SUBJECT FROM LIST");
    JButton btdok = new JButton("Add this Subject");
    JButton btdcl = new JButton("Close");

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

        layout.setConstraints(lbdadd, 1, 1, 1, 1);
        layout.setConstraints(spdsbj, 2, 1, 1, 1);
        layout.setConstraints(btdok, 3, 1, 1, 1);
        layout.setConstraints(btdcl, 4, 1, 1, 1);

        btdok.addActionListener(new ButtonListener());
        btdcl.addActionListener(new ButtonListener());

        ListSelectionModel tableModel = tbdsbj.getSelectionModel();
        tableModel.addListSelectionListener(new SubjTableListener());

        btdok.setEnabled(false);

        Toolkit toolkit = getToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        pack();

        int width = getWidth();
        int height = getHeight();

        int x = (int) ((screenSize.getWidth() / 2) - (width / 2));
        int y = (int) ((screenSize.getHeight() / 2) - (height / 2));

        setBounds(x, y, width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Set subject table
    public void setSbjTable() {
        tbdsbj = new JTable(new StudRec.SbjModel());
        spdsbj = new JScrollPane(tbdsbj);

        this.getContentPane().add(spdsbj);

        tbdsbj.setPreferredScrollableViewportSize(
            new Dimension(400, 200)
        );

        tbdsbj.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );
    }

    // Controller
    private class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent ev) {

            if (ev.getSource() == btdok) {

                RegistryDialog.selected.addSubject(slctdsbj);

                ViewElements.currentSem.enrollStudent(
                    RegistryDialog.selected
                );

                ViewElements.SemList.put(
                    ViewElements.currentSem.getSemCode(),
                    ViewElements.currentSem
                );

                RegistryDialog.tbrsbj.setModel(
                    new RegistryDialog.SubjectModel()
                );

                StudRec.writeFiles();
                StudRec.readFiles();

                System.out.println("subject added");

                dispose();
            }

            if (ev.getSource() == btdcl) {
                System.out.println("closed");
                dispose();
            }
        }
    }

    private class SubjTableListener implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {

            if (e.getValueIsAdjusting()) {
                return;
            }

            ListSelectionModel selectionModel =
                (ListSelectionModel) e.getSource();

            if (selectionModel.isSelectionEmpty()) {

                btdok.setEnabled(false);

            } else {

                btdok.setEnabled(true);

                int selectedRow =
                    selectionModel.getMinSelectionIndex();

                StudRec.SbjModel tempModel =
                    new StudRec.SbjModel();

                String code =
                    "" + tempModel.getValueAt(selectedRow, 0);

                Hashtable ht =
                    ViewElements.currentSem.getSubjectsOpen();

                slctdsbj =
                    new Subject((Subject) ht.get(code));
            }
        }
    }

    private class MyWindowAdapter extends WindowAdapter {

        public void windowClosing(WindowEvent we) {
            dispose();
        }
    }
}
