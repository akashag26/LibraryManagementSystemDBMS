package com.dbms.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.dbms.connection.ConnectionManagerFactory;
import com.dbms.entity.Faculty;
import com.dbms.entity.Student;
import com.dbms.queries.SqlQueries;

public class Login {

	public static String passID;
	private static JFrame f1;
	private static JPanel top;
	private static JPanel middle;
	private static JPanel bottom;
	private static JLabel headingLabel;
	private static JLabel usernameLabel;
	private static JLabel passwordLabel;
	private static JTextField usernameField;
	private static JPasswordField passwordField;
	private static JButton bLogin;
	private static JRadioButton rbStudent;
	private static JRadioButton rbFaculty;
	private static ButtonGroup bgRadioType;
	public static Connection connect = null;
	
	public void start(){
		new Login().buildLogin();
		connect=ConnectionManagerFactory.createConnection();
	}
	
	private void buildLogin(){
		  f1= new JFrame();
		  top = new JPanel();
		  middle= new JPanel();
		  bottom = new JPanel();
		  headingLabel = new JLabel();
		  usernameLabel= new JLabel();
		  passwordLabel= new JLabel();
		  usernameField = new JTextField();		  
		  passwordField= new JPasswordField();
		  bLogin= new JButton();
		  bgRadioType= new ButtonGroup();		  
		  rbStudent = new JRadioButton("Student",true);
		  rbFaculty = new JRadioButton("Faculty",false);
		  bgRadioType.add(rbFaculty);
		  bgRadioType.add(rbStudent);
		  
	
		  // Labels
		  headingLabel.setText("UNIVERSITY LIBRARY");
		  headingLabel.setForeground(new Color(0,0,0));
		  headingLabel.setVisible(true);
		  

		  usernameLabel.setText("	Username:   ");
		  usernameLabel.setForeground(new Color(50,50,50));
		  usernameLabel.setVisible(true);
		  

		  passwordLabel.setText("	Password:    ");
		  passwordLabel.setForeground(new Color(50,50,50));
		  passwordLabel.setVisible(true);
		  
		  
		  // Buttons
		  bLogin.setText("Login");
		  bLogin.setVisible(true);
		  rbStudent.setVisible(true);
		  rbFaculty.setVisible(true);
		  
		  
		  // Panels
		  top.add(headingLabel);
		  middle.add(usernameLabel);
		  middle.add(usernameField);
		  middle.add(passwordLabel);
		  middle.add(passwordField);
		  middle.add(rbStudent);
		  middle.add(rbFaculty);
		  middle.setLayout(new GridLayout(3,2));
		  bottom.add(bLogin);
		  
		  
		  //Jf1
		  f1.getContentPane().add(BorderLayout.NORTH,top);
		  f1.getContentPane().add(BorderLayout.CENTER,middle);
		  f1.getContentPane().add(BorderLayout.SOUTH,bottom);
		  
		  f1.setSize(300,150);
		  f1.setVisible(true);
		  f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  f1.setLocationRelativeTo(null);
		  
		  
		  
		  bLogin.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try{
					String radioSelection="";
					
					 
					if(rbStudent.isSelected()){
						radioSelection=rbStudent.getText();
					}
					if(rbFaculty.isSelected()){
						radioSelection=rbFaculty.getText();
					}
					
					System.out.println("radioSelection "+radioSelection+"\n");
					
					if(radioSelection.equals("Student")){
						//JOptionPane.showMessageDialog(null, "Successful "+radioSelection+" Login");
						PreparedStatement prepstat=connect.prepareStatement(SqlQueries.LOGIN);
						prepstat.setString(1, usernameField.getText());
						prepstat.setString(2, passwordField.getText());
						ResultSet rset= prepstat.executeQuery();
						int i = 0;
						while(rset.next()){
							passID=rset.getString("studentnumber");
							i++;
						}
						Student student=new Student();
						if(i == 0)
						{
							JOptionPane.showMessageDialog(null,"Not Found");
							return;
						}
						
						/*PreparedStatement prepstat1=connect.prepareStatement(SqlQueries.EXECUTEUPDATEREMINDERPUBLICATIONS);
						prepstat.setString(1, rset.getString("studentnumber"));
						prepstat.setString(2, null);
						ResultSet rset1= prepstat1.executeQuery();
						prepstat1=connect.prepareStatement(SqlQueries.EXECUTEUPDATEROOMRESERVATION);
						rset1= prepstat1.executeQuery();
						prepstat1=connect.prepareStatement(SqlQueries.EXECUTEUPDATEROOMRESERVATION1);	
						rset1= prepstat1.executeQuery();
						prepstat1=connect.prepareStatement(SqlQueries.EXECUTEPUBLICATIONPRIORITY);	
						rset1= prepstat1.executeQuery();
						prepstat1=connect.prepareStatement(SqlQueries.EXECUTECAMERACANCELLATION);	
						rset1= prepstat1.executeQuery();
						prepstat1=connect.prepareStatement(SqlQueries.EXECUTECAMERACONFIRMATION);	
						rset1= prepstat1.executeQuery();
						*/
						
						 CallableStatement cs1= connect.prepareCall ( "{call UpdateReminderPublications (?,?)}" ) ; 
						 cs1.setString(1,passID);
						 cs1.setString(2, null);
						 	cs1.executeUpdate();
						cs1 = connect.prepareCall ( "{call UpdateRoomReservations}" ) ; // stored proc
						cs1.executeUpdate();
						cs1 = connect.prepareCall ( "{call UpdateRoomReservations1}" ) ; // stored proc
						cs1.executeUpdate();		 
						cs1 = connect.prepareCall ( "{call PublicationPriority}" ) ; // stored proc
						cs1.executeUpdate();		 
						cs1 = connect.prepareCall ( "{call CameraCancellation}" ) ; // stored proc
						cs1.executeUpdate();		 
						cs1 = connect.prepareCall ( "{call CameraConfirmation}" ) ; // stored proc
						cs1.executeUpdate();		 
						
						
						
						student.setStudentId(passID);
						new StudentHome(student,null);
							rset.close();
						prepstat.close();
						connect.close();
					}
					else if(radioSelection.equals("Faculty")){
						
						PreparedStatement prepstat=connect.prepareStatement(SqlQueries.LOGINFACULTY);
						prepstat.setString(1, usernameField.getText());
						prepstat.setString(2, passwordField.getText());
						ResultSet rset= prepstat.executeQuery();
						int i=0;
						while(rset.next()){
			
							passID=rset.getString(1);
							i++;
						}
						if(i == 0)
						{
							JOptionPane.showMessageDialog(null,"Not Found");
							return;
						}
					/*	PreparedStatement prepstat1=connect.prepareStatement(SqlQueries.EXECUTEUPDATEREMINDERPUBLICATIONS);
						prepstat.setString(1, null);
						prepstat.setString(2, rset.getString(1));
						ResultSet rset1= prepstat1.executeQuery();
						prepstat1=connect.prepareStatement(SqlQueries.EXECUTEUPDATEROOMRESERVATION);
						rset1= prepstat1.executeQuery();
						prepstat1=connect.prepareStatement(SqlQueries.EXECUTEUPDATEROOMRESERVATION1);	
						rset1= prepstat1.executeQuery();
						prepstat1=connect.prepareStatement(SqlQueries.EXECUTEPUBLICATIONPRIORITY);	
						rset1= prepstat1.executeQuery();
						prepstat1=connect.prepareStatement(SqlQueries.EXECUTECAMERACANCELLATION);	
						rset1= prepstat1.executeQuery();
						prepstat1=connect.prepareStatement(SqlQueries.EXECUTECAMERACONFIRMATION);	
						rset1= prepstat1.executeQuery();
					*/
						

						 CallableStatement cs1= connect.prepareCall ( "{call UpdateReminderPublications (?,?)}" ) ; 
						 cs1.setString(2,passID);
						 cs1.setString(1, null);
						 	cs1.executeUpdate();
						cs1 = connect.prepareCall ( "{call UpdateRoomReservations}" ) ; // stored proc
						cs1.executeUpdate();
						cs1 = connect.prepareCall ( "{call UpdateRoomReservations1}" ) ; // stored proc
						cs1.executeUpdate();		 
						cs1 = connect.prepareCall ( "{call PublicationPriority}" ) ; // stored proc
						cs1.executeUpdate();		 
						cs1 = connect.prepareCall ( "{call CameraCancellation}" ) ; // stored proc
						cs1.executeUpdate();		 
						cs1 = connect.prepareCall ( "{call CameraConfirmation}" ) ; // stored proc
						cs1.executeUpdate();	
						
						Faculty faculty=new Faculty();
						faculty.setFacultyId(passID);
						new StudentHome(null,faculty);
						rset.close();
						prepstat.close();
						connect.close();
					}
					f1.setVisible(false);
					f1.dispose();
				}catch(Exception e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e);
				}
			}
		  });
	}
}
