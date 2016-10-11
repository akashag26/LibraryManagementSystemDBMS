package com.dbms.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.dbms.connection.ConnectionManagerFactory;
import com.dbms.docfilter.DateLabelFormatter;
import com.dbms.entity.Faculty;
import com.dbms.entity.RoomReservation;
import com.dbms.entity.Student;
import com.dbms.models.RoomTableModel;
import com.dbms.queries.SqlQueries;

public class RoomQueryScreen extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2424505556237004673L;
	private static JPanel heading;
	private static JPanel middle;
	private static JPanel bottom;
	private Student studentInfo;
	private Faculty facultyInfo;
	private static JLabel headingLabel;
	private static JLabel l1;//Occupants
	private static JLabel l2;//LibrarName
	private static JLabel l3;//Date Picker
	private static JLabel l4;//Start Time Picker
	private static JLabel l5;//Duration Picker 1/2/3
	
	/*
	private static JLabel l6;//Capacity
	private static JLabel l7;//Type
	private static JLabel l8;
	private static JLabel l9;
	private static JLabel l10;
	private static JLabel l11;
	private static JLabel l12;
	private static JLabel l13;
	private static JLabel l14;
	private static JLabel l15;
	*/	
	
	private static JComboBox<Integer> occupantsCount;
	private static JComboBox<String> libraryName;
	private static JComboBox<Integer> numberOfHours;
	private static UtilDateModel dateModel;
	private static JDatePickerImpl datePicker;
	private static JDatePanelImpl datePanel;
	private static Properties i18properties; 
	private static JSpinner timeSpinner;

	/*
	private static JTextField roomNumber;
	private static JTextField categoryName;
	private static JTextField floorNumber;
	private static JTextField capcity;
	private static JTextField type;
	private static JTextField categoryId;
	private static JTextField DOB;
	private static JTextField Sex;
	private static JTextField Nat;
	private static JTextField Dept;
	private static JTextField Class;
	private static JTextField Degree;
	private static JTextField Year;
	*/
	
	private static JButton query;
	
	public RoomQueryScreen(Student studentInfo,Faculty facultyInfo)
	{
		this.studentInfo =studentInfo;
		this.facultyInfo =facultyInfo;
		
		i18properties = new Properties();
		/*********************************/
		i18properties.put("text.today","Today");
		i18properties.put("text.month","Month");
		i18properties.put("text.year","Year");
		/*********************************/
		
		/***** Date field ******/
		dateModel = new UtilDateModel();
		datePanel = new JDatePanelImpl(dateModel,i18properties);
		datePicker = new JDatePickerImpl(datePanel,new DateLabelFormatter());
		/***** Date field ******/
		
		/** for handling time selector**/
		timeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
		timeSpinner.setEditor(timeEditor);
		timeSpinner.setValue(new Date());
		/** for handling time selector**/
		
	    heading = new JPanel();
        middle=new JPanel();
		bottom=new JPanel();
		 
		headingLabel = new JLabel();
		l1 = new JLabel();
		l2= new JLabel();
		l3= new JLabel();
		l4= new JLabel();
		l5= new JLabel();

		/*
		l6= new JLabel();
		l7= new JLabel();
		l8= new JLabel();
		l9= new JLabel();
		l10= new JLabel();
		l11= new JLabel();
		l12= new JLabel();
		l13= new JLabel();
		l14= new JLabel();
		l15= new JLabel();
		l12= new JLabel();*/
		 
		headingLabel.setText("Query Room");
		headingLabel.setForeground(new Color(0,0,0));
		headingLabel.setVisible(true);
	  
		occupantsCount = new JComboBox<Integer>();
		libraryName= new JComboBox<String>();
		numberOfHours= new JComboBox<Integer>();
		
		/*
		roomNumber = new JTextField() ;
		categoryName= new JTextField();
		floorNumber= new JTextField();
		capcity= new JTextField();
		type= new JTextField();
		DOB= new JTextField();
		Sex= new JTextField();
		Nat= new JTextField();
		Dept= new JTextField();
		Class= new JTextField();
		Degree= new JTextField();
		Year= new JTextField();*/
		 
		query=new JButton();
		 
		// Labels
		l1.setText("	# of Occupants:   ");
		l1.setForeground(new Color(50, 50, 50));
		l1.setVisible(true);
		l2.setText("	Library Name:    ");
		l2.setForeground(new Color(50, 50, 50));
		l2.setVisible(true);
		l3.setText("	Date Picker:    ");
		l3.setForeground(new Color(50, 50, 50));
		l3.setVisible(true);
		l4.setText("	Time Picker:    ");
		l4.setForeground(new Color(50, 50, 50));
		l4.setVisible(true);
		l5.setText("	# of Hours:    ");
		l5.setForeground(new Color(50, 50, 50));
		l5.setVisible(true);
		
		/*
		l6.setText("	Capcity:    ");
		l6.setForeground(new Color(50, 50, 50));
		l6.setVisible(true);
		l7.setText("	Type:    ");
		l7.setForeground(new Color(50, 50, 50));
		l7.setVisible(true);
		l8.setText("	Category ID:    ");
		l8.setForeground(new Color(50, 50, 50));
		l8.setVisible(true);
  		l9.setText("	Date of Birth:    ");
		l9.setForeground(new Color(50, 50, 50));
		l9.setVisible(true);
		l10.setText("	Sex:    ");
		l10.setForeground(new Color(50, 50, 50));
		l10.setVisible(true);
		l11.setText("	Nationality:    ");
		l11.setForeground(new Color(50, 50, 50));
		l11.setVisible(true);
		l12.setText("	Department:    ");
		l12.setForeground(new Color(50, 50, 50));
		l12.setVisible(true);
		l13.setText("	Classification:    ");
		l13.setForeground(new Color(50, 50, 50));
		l13.setVisible(true);
		l14.setText("	Degree:    ");
		l14.setForeground(new Color(50, 50, 50));
		l14.setVisible(true);
		l15.setText("	Year:    ");
		l15.setForeground(new Color(50, 50, 50));
		l15.setVisible(true);*/
		 
		//panels
		heading.add(headingLabel);
		middle.setLayout(new GridLayout(15, 15));
		middle.add(l1);
		middle.add(occupantsCount);
		middle.add(l2);
		middle.add(libraryName);
		bottom.add(query);
		middle.add(l3);
		middle.add(datePicker);
		middle.add(l4);
		middle.add(timeSpinner);
		middle.add(l5);
		middle.add(numberOfHours);
		
		/*
		middle.add(l6);
		middle.add(capcity);
		middle.add(l7);
		middle.add(type);
		middle.add(l8);
		middle.add(categoryId);
  		middle.add(l9);
		middle.add(DOB);
		middle.add(l10);
		middle.add(Sex);
		middle.add(l11);
		middle.add(Nat);
		middle.add(l12);
		middle.add(Dept);
		middle.add(l13);
		middle.add(Class);
		middle.add(l14);
		middle.add(Degree);
		middle.add(l15);
		middle.add(Year);*/
		 
		 //Buttons
		query.setText(" Search ");
		query.setVisible(true);
		
		query.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("datePicker "+(Date)datePicker.getModel().getValue()+"\n");
				
				/*** get value from time picker ***/
				Calendar tempCalendar = Calendar.getInstance();
				tempCalendar.setTime((Date)timeSpinner.getModel().getValue());
				
				Date chosenDate = (Date)datePicker.getModel().getValue();
				
				if(chosenDate == null)
				{
					JOptionPane.showMessageDialog(null, "Choose Date\n");
					return;
				}
				
				Calendar chosenCalInstanceStart= Calendar.getInstance();
				chosenCalInstanceStart.setTime(chosenDate);
				
				chosenCalInstanceStart.set(Calendar.HOUR_OF_DAY, tempCalendar.get(Calendar.HOUR_OF_DAY));
				chosenCalInstanceStart.set(Calendar.MINUTE, tempCalendar.get(Calendar.MINUTE));
				chosenCalInstanceStart.set(Calendar.SECOND, 0);
				chosenCalInstanceStart.set(Calendar.MILLISECOND, 0);
				
				Calendar chosenCalInstanceEnd= Calendar.getInstance();
				chosenCalInstanceEnd.setTime(chosenCalInstanceStart.getTime());
				chosenCalInstanceEnd.add(Calendar.HOUR_OF_DAY,(Integer)numberOfHours.getSelectedItem());
				
				System.out.println("chosenCalInstance Hours"+chosenCalInstanceStart.get(Calendar.HOUR_OF_DAY)+"\n");
				System.out.println("chosenCalInstance Minutes"+chosenCalInstanceStart.get(Calendar.MINUTE)+"\n");
				
				System.out.println("chosenCalInstanceEnd Hours"+chosenCalInstanceEnd.get(Calendar.HOUR_OF_DAY)+"\n");
				System.out.println("chosenCalInstanceEnd Minutes"+chosenCalInstanceEnd.get(Calendar.MINUTE)+"\n");
				
				RoomReservation reservation = new RoomReservation();
				reservation.setStartTime(chosenCalInstanceStart.getTime());
				reservation.setEndTime(chosenCalInstanceEnd.getTime());
				if(studentInfo != null){
					reservation.setStudentNumber(""+studentInfo.getStudentId());
					reservation.setFacultyNumber(null);
				}else
				{
					reservation.setStudentNumber(null);
					reservation.setFacultyNumber(""+facultyInfo.getFacultyId());
				}
				new RoomListModule(RoomTableModel.queryRooms((Integer)occupantsCount.getSelectedItem(),(String)libraryName.getSelectedItem(),chosenCalInstanceStart.getTime(),chosenCalInstanceEnd.getTime()),studentInfo,facultyInfo,reservation);
			}
		});

		this.getContentPane().add(BorderLayout.NORTH, heading);
		this.getContentPane().add(BorderLayout.CENTER, middle);
		this.getContentPane().add(BorderLayout.SOUTH, bottom);
		this.setSize(400, 600);
		this.setLocationRelativeTo(null);
		this.display();
	}
	
	public void display()
	{
		getDisticntRoomCapacity();
		getLibraryNames();
		for(int hours = 1; hours < 4;hours++)
			numberOfHours.addItem(hours);
		this.setVisible(true);
	}
	
	public static void getDisticntRoomCapacity()
	{
		Connection connect = null;
		try {
			connect = ConnectionManagerFactory.createConnection();
			PreparedStatement prepstat = connect.prepareStatement(SqlQueries.GETDISTINCTROOMCAPCITY);
			ResultSet rset = prepstat.executeQuery();
			while (rset.next()) {
				occupantsCount.addItem(rset.getInt("CAPACITY"));
			}
			rset.close();
			prepstat.close();
			connect.close();
		}
		catch(Exception e)
		{
				e.printStackTrace();
    			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public static void getLibraryNames()
	{
		Connection connect = null;
		try {
			connect = ConnectionManagerFactory.createConnection();
			PreparedStatement prepstat = connect.prepareStatement(SqlQueries.GETLIBRARYNAMES);
			ResultSet rset = prepstat.executeQuery();
			while (rset.next()) {
				libraryName.addItem(rset.getString("LIBRARYNAME"));
			}
			rset.close();
			prepstat.close();
			connect.close();
		}
		catch(Exception e)
		{
				e.printStackTrace();
    			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
}
