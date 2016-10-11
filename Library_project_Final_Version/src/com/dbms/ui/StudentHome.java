package com.dbms.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.dbms.connection.ConnectionManagerFactory;
import com.dbms.entity.Faculty;
import com.dbms.entity.Student;
import com.dbms.models.BookCheckOutLogTabelModel;
import com.dbms.models.BookTableModel;
import com.dbms.models.CameraCheckOutLogTableModel;
import com.dbms.models.CameraTableModel;
import com.dbms.models.ConferencesTableModel;
import com.dbms.models.JournalTableModel;
import com.dbms.models.NotificationTableModel;
import com.dbms.models.RoomTableModel;
import com.dbms.queries.SqlQueries;

public class StudentHome extends JFrame{
	/**
	 * 
	 */
	public static Connection connect = null;
	private static final long serialVersionUID = 7463966331095386163L;
	private static Student studentInfo;
	private static Faculty facultyInfo;
	private static JPanel middle;
	private static JButton b1;
	private static JButton b2;
	private static JButton b3;
	private static JButton b4;
	private static JButton b5;
	private static JButton b6;
	
	public StudentHome()
 {

		setSize(200, 200);

		middle = new JPanel();
		middle.setVisible(true);
		b1 = new JButton();
		b2 = new JButton();
		b3 = new JButton();
		b4 = new JButton();
		b5 = new JButton();
		b6 = new JButton();

		b1.setText("PROFILE");
		b2.setText("RESOURCES");
		b3.setText("CHECKED OUT RESOURCES");
		b4.setText("RESOURCE REQUESTS");
		b5.setText("NOTIFICATIONS");
		b6.setText("DUE BALANCE");

		b1.setVisible(true);
		b2.setVisible(true);
		b3.setVisible(true);
		b4.setVisible(true);
		b5.setVisible(true);
		b6.setVisible(true);
		middle.setLayout(new GridLayout(6, 6));
		middle.add(b1);
		middle.add(b2);
		middle.add(b3);
		middle.add(b4);
		middle.add(b5);
		middle.add(b6);

		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(studentInfo != null)
					new StudentInfo(studentInfo);
				else
					new FacultyInfoScreen(facultyInfo);
			}
		});

		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new StudentResources(studentInfo, facultyInfo);
			}
		});

		b3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new CheckedOutResourcesListModule(BookTableModel.getAllCheckoutBooks(studentInfo,facultyInfo),
						RoomTableModel.getAllCheckedoutRooms(studentInfo,facultyInfo),
						CameraTableModel.getAllCamerasCheckedOut(studentInfo,facultyInfo),
						JournalTableModel.getAllCheckedoutJournals(studentInfo,facultyInfo),
						ConferencesTableModel.getAllCheckoutedConferences(studentInfo,facultyInfo),
						studentInfo,facultyInfo);
			}
		});

		b4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new ReserevedResourcesListModule(RoomTableModel.getReservedRooms(studentInfo,facultyInfo),
						CameraTableModel.getCamerasReserved(studentInfo,facultyInfo), studentInfo,facultyInfo);
			}
		});

		b5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new NotificationListModule(NotificationTableModel.getAllNotifications(studentInfo,facultyInfo));
			}
		});

		b6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new DueListModule(BookCheckOutLogTabelModel.getAllFineForBooks(studentInfo,facultyInfo),
						CameraCheckOutLogTableModel.getAllFineForCameras(studentInfo,facultyInfo), studentInfo,facultyInfo);
			}
		});

	}
	
	public StudentHome(Student student, Faculty faculty) {
		if (faculty == null) {
			
				this.studentInfo = student;
				StudentHome me = new StudentHome();
			
			try{
			connect = ConnectionManagerFactory.createConnection();
			PreparedStatement prepstat = connect.prepareStatement(SqlQueries.CHECKSTUDENTHOLD);
			prepstat.setString(1, studentInfo.getStudentId());
			ResultSet rset = prepstat.executeQuery();
			int flag=0;
			while(!rset.next()){
				flag=1;
			}
			
			if(flag==0){
				if(Integer.parseInt(rset.getString(1))==1){
				b2.setEnabled(false);
				b3.setEnabled(false);
				b4.setEnabled(false);
				b5.setEnabled(false);
					}
			}
			connect.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			
			
			
			me.getContentPane().add(BorderLayout.CENTER, middle);
			// me.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			me.setVisible(true);
			me.setLocationRelativeTo(null);
		} else {
			this.facultyInfo = faculty;
			StudentHome me = new StudentHome();
			me.getContentPane().add(BorderLayout.CENTER, middle);
			// me.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			me.setVisible(true);
			me.setLocationRelativeTo(null);
		}

		// System.out.println("Student:"+s.getStudentId());
	}
	
}
