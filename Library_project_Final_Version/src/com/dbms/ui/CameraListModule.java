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

import com.dbms.entity.Camera;
import com.dbms.entity.Faculty;
import com.dbms.entity.Student;
import com.dbms.models.CameraTableModel;

public class CameraListModule extends JFrame{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 5617087489020572327L;
	private List<Camera> cameras;
	private Student studentInfo;
	private Faculty facultyInfo;
	private static JPanel middle;
	private CameraTableModel dataModel;
	   
	   public CameraListModule(List<Camera> cameras,Student studentInfo,Faculty facultyInfo)
	   {
		   this.studentInfo=studentInfo;
		   this.facultyInfo=facultyInfo;
		   setTitle("CAMERAS");
		   this.setSize(1200, 600);
		   middle=new JPanel();
		   middle.setVisible(true);
			
			middle.setLayout(new GridLayout(1,0));
			this.add(middle);
		   //System.out.println("123");
		   
		   this.getContentPane().add(BorderLayout.CENTER,middle);
			//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    this.setVisible(true);
		    this.setLocationRelativeTo(null); 
		   
		   this.cameras = cameras;
		   JTable table = new JTable(new CameraTableModel(this.cameras));
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
							new CameraDetailModule(CameraListModule.this.cameras.get(selectIdx),studentInfo,facultyInfo);
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