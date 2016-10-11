package com.dbms.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

import com.dbms.connection.ConnectionManagerFactory;
import com.dbms.entity.Student;
import com.dbms.queries.SqlQueries;

public class StudentInfo extends JFrame {
	
	public static Student studentDetail;
	public static Connection connect = null;

	private static JPanel heading;
	private static JPanel middle;
	private static JPanel bottom;

	private static JRadioButton isMale;
	private static JRadioButton isFemale;

	private static JLabel headingLabel;
	private static JLabel studentIdLabel;
	private static JLabel firstNameLabel;
	private static JLabel lastNameLabel;
	private static JLabel phoneNumberLabel;
	private static JLabel alternatePhoneLabel;
	private static JLabel streetLabel;
	private static JLabel cityLabel;
	private static JLabel postalCodeLabel;
	private static JLabel dobLabel;
	private static JLabel sexLabel;
	private static JLabel nationalityLabel;
	private static JLabel departmentLabel;
	private static JLabel classificationLabel;
	private static JLabel degreeLabel;
	private static JLabel yearLabel;
	private static JLabel stateLabel;

	private static JComboBox<String> patternList;

	private static JTextField StudentNumber;
	private static JTextField FName;
	private static JTextField LName;
	private static JTextField Pno;
	private static JTextField AltPno;
	private static JTextField Street;
	private static JTextField City;
	private static JTextField Postcode;
	private static JTextField DOB;
	private static JTextField Nat;
	private static JTextField Dept;
	private static JTextField Class;
	private static JTextField Degree;
	private static JTextField Year;
	private static JTextField State;

	private static JButton Edit;
	private static JButton Update;

	public void display() {
		try {
			connect = ConnectionManagerFactory.createConnection();
			PreparedStatement prepstat = connect.prepareStatement(SqlQueries.STUDENT_DETAILS);
			prepstat.setString(1, studentDetail.getStudentId());
			ResultSet rset = prepstat.executeQuery();

			while (rset.next()) {
				System.out.println(rset);
				StudentNumber.setText(studentDetail.getStudentId());
				FName.setText(rset.getString(2));
				LName.setText(rset.getString(3));
				Pno.setText(rset.getString(4));
				AltPno.setText(rset.getString(5));
				Street.setText(rset.getString(6));
				City.setText(rset.getString(7));
				Postcode.setText(rset.getString(8));

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
				String dateInString = rset.getString(9);
				Date date = formatter.parse(dateInString);
				DOB.setText(formatter.format(date).toString());

				if (rset.getString(10).equals("M") || rset.getString(10).equals("m"))
					patternList.setSelectedIndex(0);
				else
					patternList.setSelectedIndex(1);

				// Sex.setText(rset.getString(10));
				Nat.setText(rset.getString(11));
				Dept.setText(rset.getString(12));
				Class.setText(rset.getString(13));
				Degree.setText(rset.getString(14));
				Year.setText(rset.getString(15));
				State.setText(rset.getString(16));

				StudentNumber.setEditable(false);
				FName.setEditable(false);
				LName.setEditable(false);
				Pno.setEditable(false);
				AltPno.setEditable(false);
				Street.setEditable(false);
				City.setEditable(false);
				Postcode.setEditable(false);
				DOB.setEditable(false);
				// isMale.setEditable(false);
				Nat.setEditable(false);
				Dept.setEditable(false);
				Class.setEditable(false);
				Degree.setEditable(false);
				Year.setEditable(false);
				isMale.setEnabled(false);
				isFemale.setEnabled(false);
				patternList.setEditable(false);
				patternList.setEnabled(false);
				State.setEditable(false);
			}

			connect.close();
			prepstat.close();
			rset.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}

	}

	public StudentInfo() {
		connect = ConnectionManagerFactory.createConnection();
		setTitle("WELCOME STUDENT");
		setSize(400, 400);
	}

	public StudentInfo(Student s1) {
		String[] values = { "Male", "Female" };
		patternList = new JComboBox<String>(values);
		patternList.setVisible(true);

		studentDetail = s1;

		System.out.println("Student:" + studentDetail.getStudentId());
		StudentInfo me = new StudentInfo();

		heading = new JPanel();
		middle = new JPanel();
		bottom = new JPanel();

		headingLabel = new JLabel();
		studentIdLabel = new JLabel();
		firstNameLabel = new JLabel();
		lastNameLabel = new JLabel();
		phoneNumberLabel = new JLabel();
		alternatePhoneLabel = new JLabel();
		streetLabel = new JLabel();
		cityLabel = new JLabel();
		postalCodeLabel = new JLabel();
		dobLabel = new JLabel();
		sexLabel = new JLabel();
		nationalityLabel = new JLabel();
		departmentLabel = new JLabel();
		classificationLabel = new JLabel();
		degreeLabel = new JLabel();
		yearLabel = new JLabel();
		stateLabel = new JLabel();

		headingLabel.setText("Student Information");
		headingLabel.setForeground(new Color(0, 0, 0));
		headingLabel.setVisible(true);

		isMale = new JRadioButton();
		isFemale = new JRadioButton();

		StudentNumber = new JTextField();
		FName = new JTextField();
		LName = new JTextField();
		Pno = new JTextField();
		AltPno = new JTextField();
		Street = new JTextField();
		City = new JTextField();
		Postcode = new JTextField();
		DOB = new JTextField();
		Nat = new JTextField();
		Dept = new JTextField();
		Class = new JTextField();
		Degree = new JTextField();
		Year = new JTextField();
		State = new JTextField();

		Edit = new JButton();
		Update = new JButton();

		// Labels
		studentIdLabel.setText("	Student ID:   ");
		studentIdLabel.setForeground(new Color(50, 50, 50));
		studentIdLabel.setVisible(true);
		firstNameLabel.setText("	First Name:    ");
		firstNameLabel.setForeground(new Color(50, 50, 50));
		firstNameLabel.setVisible(true);
		lastNameLabel.setText("	Last Name:    ");
		lastNameLabel.setForeground(new Color(50, 50, 50));
		lastNameLabel.setVisible(true);
		phoneNumberLabel.setText("	Phone Number:    ");
		phoneNumberLabel.setForeground(new Color(50, 50, 50));
		phoneNumberLabel.setVisible(true);
		alternatePhoneLabel.setText("	Alternate Phone Number:    ");
		alternatePhoneLabel.setForeground(new Color(50, 50, 50));
		alternatePhoneLabel.setVisible(true);
		streetLabel.setText("	Street:    ");
		streetLabel.setForeground(new Color(50, 50, 50));
		streetLabel.setVisible(true);
		cityLabel.setText("	City:    ");
		cityLabel.setForeground(new Color(50, 50, 50));
		cityLabel.setVisible(true);
		postalCodeLabel.setText("	Postal Code:    ");
		postalCodeLabel.setForeground(new Color(50, 50, 50));
		postalCodeLabel.setVisible(true);
		dobLabel.setText("	Date of Birth(yyyy-mm-dd):    ");
		dobLabel.setForeground(new Color(50, 50, 50));
		dobLabel.setVisible(true);
		sexLabel.setText("	Sex:    ");
		sexLabel.setForeground(new Color(50, 50, 50));
		sexLabel.setVisible(true);
		nationalityLabel.setText("	Nationality:    ");
		nationalityLabel.setForeground(new Color(50, 50, 50));
		nationalityLabel.setVisible(true);
		departmentLabel.setText("	Department:    ");
		departmentLabel.setForeground(new Color(50, 50, 50));
		departmentLabel.setVisible(true);
		classificationLabel.setText("	Classification:    ");
		classificationLabel.setForeground(new Color(50, 50, 50));
		classificationLabel.setVisible(true);
		degreeLabel.setText("	Degree:    ");
		degreeLabel.setForeground(new Color(50, 50, 50));
		degreeLabel.setVisible(true);
		yearLabel.setText("	Year:    ");
		yearLabel.setForeground(new Color(50, 50, 50));
		yearLabel.setVisible(true);
		stateLabel.setText("	State:    ");
		stateLabel.setForeground(new Color(50, 50, 50));
		stateLabel.setVisible(true);

		heading.add(headingLabel);
		middle.setLayout(new GridLayout(16, 16));
		middle.add(studentIdLabel);
		middle.add(StudentNumber);
		middle.add(firstNameLabel);
		middle.add(FName);
		middle.add(lastNameLabel);
		middle.add(LName);
		bottom.add(Edit);
		bottom.add(Update);
		middle.add(phoneNumberLabel);
		middle.add(Pno);
		middle.add(alternatePhoneLabel);
		middle.add(AltPno);
		middle.add(streetLabel);
		middle.add(Street);
		middle.add(cityLabel);
		middle.add(City);
		middle.add(stateLabel);
		middle.add(State);
		middle.add(postalCodeLabel);
		middle.add(Postcode);
		middle.add(dobLabel);
		middle.add(DOB);
		// middle.add(sex);
		middle.add(sexLabel);
		middle.add(patternList);
		middle.add(nationalityLabel);
		middle.add(Nat);
		middle.add(departmentLabel);
		middle.add(Dept);
		middle.add(classificationLabel);
		middle.add(Class);
		middle.add(degreeLabel);
		middle.add(Degree);
		middle.add(yearLabel);
		middle.add(Year);

		// Buttons
		Edit.setText("EDIT");
		Edit.setVisible(true);
		Update.setText("UPDATE");
		Update.setVisible(true);
		Update.setEnabled(false);

		me.getContentPane().add(BorderLayout.NORTH, heading);
		me.getContentPane().add(BorderLayout.CENTER, middle);
		me.getContentPane().add(BorderLayout.SOUTH, bottom);

		// me.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		me.setVisible(true);
		me.setLocationRelativeTo(null);

		me.display();

		Edit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				FName.setEditable(true);
				LName.setEditable(true);
				Pno.setEditable(true);
				AltPno.setEditable(true);
				Street.setEditable(true);
				City.setEditable(true);
				Postcode.setEditable(true);
				DOB.setEditable(true);
				Nat.setEditable(true);
				Dept.setEditable(true);
				Class.setEditable(true);
				Degree.setEditable(true);
				Year.setEditable(true);
				isMale.setEnabled(true);
				isFemale.setEnabled(true);
				Update.setEnabled(true);
				Edit.setEnabled(false);
				patternList.setEditable(true);
				patternList.setEnabled(true);
				State.setEditable(true);
			}
		});

		Update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				String input = Postcode.getText();
				if (!input.matches("[0-9]*")) {
					JOptionPane.showMessageDialog(null, "Postcode can only be numerics[0-9]");
					return;
				}

				if (!Pno.getText().matches("[1-9][0-9]{9}")) {
					JOptionPane.showMessageDialog(null, "Phonenumber must be 10 digits and should not start from 0");
					return;
				}

				if (!AltPno.getText().trim().isEmpty()) {
					if (!AltPno.getText().matches("[1-9][0-9]{9}")) {
						JOptionPane.showMessageDialog(null,
								"Alternate Phonenumber must be 10 digits and should not start from 0");
						return;
					}
				}

				if (!DOB.getText().matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")) {
					JOptionPane.showMessageDialog(null, "Invalid Date Format");
					return;
				}

				try {
					int program = 0;
					connect = ConnectionManagerFactory.createConnection();
					PreparedStatement prepstat = connect.prepareStatement(SqlQueries.GETPROGRAMME);
					ResultSet rset = prepstat.executeQuery();
					int flag = 0;

					while (rset.next()) {

						if ((Class.getText().equalsIgnoreCase(rset.getString(1)))
								&& (Degree.getText().equalsIgnoreCase(rset.getString(2)))
								&& (Year.getText().equalsIgnoreCase(rset.getString(3)))) {
							flag = 1;
							program = Integer.parseInt(rset.getString(4));
							break;
						}

					}

					if (flag == 0) {
						JOptionPane.showMessageDialog(null,
								"Invalid Combination of Classification,Degree and Year(Year Format 1st, 2nd etc.)");
						return;
					}

					PreparedStatement prepstat1 = connect.prepareStatement(SqlQueries.UPDATESTUDENT);
					prepstat1.setString(1, FName.getText());
					prepstat1.setString(2, LName.getText());
					prepstat1.setString(3, Pno.getText());
					prepstat1.setString(4, AltPno.getText());
					prepstat1.setString(5, Street.getText());
					prepstat1.setString(6, City.getText());
					prepstat1.setString(7, Postcode.getText());
					prepstat1.setString(8, DOB.getText());

					if (patternList.getSelectedIndex() == 0)
						prepstat1.setString(9, "M");
					else
						prepstat1.setString(9, "F");

					prepstat1.setString(10, Nat.getText());
					prepstat1.setString(11, Dept.getText());
					prepstat1.setString(12, Integer.toString(program));
					prepstat1.setString(13, State.getText());
					prepstat1.setString(14, studentDetail.getStudentId());

					ResultSet rset1 = prepstat1.executeQuery();

					JOptionPane.showMessageDialog(null, "Details Updated");

					rset1.close();
					prepstat1.close();
					connect.close();
					prepstat.close();
					rset.close();

				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e);
				}

				FName.setEditable(false);
				LName.setEditable(false);
				Pno.setEditable(false);
				AltPno.setEditable(false);
				Street.setEditable(false);
				City.setEditable(false);
				Postcode.setEditable(false);
				DOB.setEditable(false);
				Nat.setEditable(false);
				Dept.setEditable(false);
				Class.setEditable(false);
				Degree.setEditable(false);
				Year.setEditable(false);
				isMale.setEnabled(false);
				isFemale.setEnabled(false);
				Update.setEnabled(false);
				patternList.setEditable(false);
				Edit.setEnabled(true);
				patternList.setEnabled(false);
				State.setEditable(true);
			}
		});

	}

}
