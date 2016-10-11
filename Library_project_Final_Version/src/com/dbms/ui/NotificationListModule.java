package com.dbms.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.dbms.entity.Notification;
import com.dbms.entity.Student;
import com.dbms.models.JournalTableModel;
import com.dbms.models.NotificationTableModel;

public class NotificationListModule extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6497544032038136174L;
	private List<Notification> notifications;
	private static JPanel middle;
	JournalTableModel dataModel;

	public NotificationListModule(List<Notification> notifications)
	   {
		   setTitle("NOTIFICATIONS");
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
		   
		   this.notifications = notifications;
		   JTable table = new JTable(new NotificationTableModel(this.notifications));
		   table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		   table.setColumnSelectionAllowed(false);
		   table.setRowSelectionAllowed(true);
		   
		   //Create the scroll pane and add the table to it.
		   JScrollPane scrollPane = new JScrollPane(table);
		   this.setSize(1200, 600);
		   //Add the scroll pane to this panel.
		   middle.add(scrollPane);
	   }
}