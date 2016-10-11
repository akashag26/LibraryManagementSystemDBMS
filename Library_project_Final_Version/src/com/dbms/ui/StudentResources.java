package com.dbms.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.dbms.entity.Faculty;
import com.dbms.entity.Student;
import com.dbms.models.CameraTableModel;

public class StudentResources extends JFrame {
	private static Student studentInfo;
	private static Faculty faculty;
	private static JPanel middle;
	private static JButton b1;
	private static JButton b2;
	private static JButton b3;
	
	public StudentResources()
	{
		if(this.faculty == null)
			setTitle("WELCOME STUDENT");
		else
			setTitle("WELCOME FACULTY");
	    setSize(200, 200);
	    
		middle=new JPanel();
		middle.setVisible(true);
		b1=new JButton();
		b2=new JButton();
		b3=new JButton();
		
		b1.setText("Publications");
		b2.setText("Camera");
		b3.setText("Room");
	
		
		b1.setVisible(true);
		b2.setVisible(true);
		b3.setVisible(true);
		middle.setLayout(new GridLayout(3,3));
		middle.add(b1);
		middle.add(b2);
		middle.add(b3);
	
		
		
		b1.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					new StudentPublications(studentInfo,faculty);
			  }
		});
		
		
		b2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new CameraListModule(CameraTableModel.getALLCameras(),studentInfo,faculty);
		  }
		});
	
		b3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//new RoomListModule(RoomTableModel.getALLRooms(),studentInfo);
				new RoomQueryScreen(studentInfo,faculty);
		  }
		});
	
		
		
	}
	
	public StudentResources(Student student,Faculty faculty)
	{
		this.faculty = faculty;
		this.studentInfo=student;
		StudentResources me=new StudentResources();
		me.getContentPane().add(BorderLayout.CENTER,middle);
		//me.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    me.setVisible(true);
	    me.setLocationRelativeTo(null);  
		//System.out.println("Student:"+s.getStudentId());
	}
	
	
}
