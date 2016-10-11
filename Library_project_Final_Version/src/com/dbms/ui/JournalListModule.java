package com.dbms.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.dbms.entity.Faculty;
import com.dbms.entity.Journal;
import com.dbms.entity.Student;
import com.dbms.models.JournalTableModel;

public class JournalListModule extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4600956197410393775L;
	private List<Journal> journals;
	private Student studentInfo;
	private Faculty facultyInfo;
	private static JPanel middle;
	JournalTableModel dataModel;

	public JournalListModule(List<Journal> journals,Student studentInfo,Faculty facultyInfo)
	   {
		   setTitle("JOURNALS");
		   this.setSize(1200, 600);
		   middle=new JPanel();
		   middle.setVisible(true);
			
		   this.facultyInfo=facultyInfo;
		   this.studentInfo=studentInfo;
		   
		   middle.setLayout(new GridLayout(1,0));
		   this.add(middle);
		   //System.out.println("123");
		
		   this.getContentPane().add(BorderLayout.CENTER,middle);
		   //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   this.setVisible(true);
		   this.setLocationRelativeTo(null); 
		   
		   this.journals = journals;
		   JTable table = new JTable(new JournalTableModel(this.journals));
		   table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		   table.setColumnSelectionAllowed(false);
		   table.setRowSelectionAllowed(true);
		   table.getSelectionModel().addListSelectionListener(
				   new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						// TODO Auto-generated method stub
						if (!e.getValueIsAdjusting()) {
							int selectIdx = table.getSelectedRow();
							//JOptionPane.showMessageDialog(CameraListModule.this, CameraListModule.this.cameras.get(selectIdx).getConfiguration());
							new JournalDetailModule(JournalListModule.this.journals.get(selectIdx),studentInfo,facultyInfo);
		                }
					}

		   });
		   //Create the scroll pane and add the table to it.
		   JScrollPane scrollPane = new JScrollPane(table);
		   this.setSize(1200, 600);
		   //Add the scroll pane to this panel.
		   middle.add(scrollPane);
	   }
}