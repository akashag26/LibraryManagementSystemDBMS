package com.dbms.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.dbms.entity.Faculty;
import com.dbms.entity.Student;
import com.dbms.models.BookTableModel;
import com.dbms.models.ConferencesTableModel;
import com.dbms.models.JournalTableModel;

public class StudentPublications extends JFrame{
	private static Student studentInfo;
	private static Faculty facultyInfo;
	private static JPanel middle;
	private static JButton b1;
	private static JButton b2;
	private static JButton b3;
	
	
public static Connection connect = null;
	
	public StudentPublications()
	{
		
		if(this.facultyInfo == null)
			setTitle("WELCOME STUDENT");
		else
			setTitle("WELCOME FACULTY");
	    //setSize(200, 200);
		 setSize(200, 200);
	    
		middle=new JPanel();
		middle.setVisible(true);
		b1=new JButton();
		b2=new JButton();
		b3=new JButton();
		
		b1.setText("BOOKS");
		b2.setText("JOURNALS");
		b3.setText("CONFERENCES");
	
		
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
					new BookListModule(BookTableModel.getAllBooks(),studentInfo,facultyInfo);
					
					
			  }
		});
		
		b2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new JournalListModule(JournalTableModel.getALLJournals(),studentInfo,facultyInfo);	
		  }
		});
		
		
		b3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new ConferenceListModule(ConferencesTableModel.getALLConferencePapers(),studentInfo,facultyInfo);	
		  }
		});
		
	}
	
	public StudentPublications(Student student,Faculty faculty)
	{
		this.studentInfo=student;
		this.facultyInfo=faculty;
		StudentPublications me=new StudentPublications();
		me.getContentPane().add(BorderLayout.CENTER,middle);
		//me.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    me.setVisible(true);
	    me.setLocationRelativeTo(null);  
		//System.out.println("Student:"+s.getStudentId());
	}
	
}
