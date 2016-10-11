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
import java.util.HashMap;
import java.util.Iterator;

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
import com.dbms.entity.Faculty;
import com.dbms.entity.Student;
import com.dbms.queries.SqlQueries;

public class FacultyInfoScreen extends JFrame {
	public static Faculty facultyInfo;
	public static Connection connect = null;
	public static HashMap<String,Integer> catMap = new HashMap<String,Integer>();
	private static JPanel heading;
	private static JPanel middle;
	private static JPanel bottom;

	private static JLabel headingLabel;
	
	private static JLabel idLabel;
	private static JLabel firstNameLabel;
	private static JLabel lastNameLabel;
	private static JLabel nationlityLabel;
	private static JLabel departmentLabel;
	private static JLabel categoryLabel;

	private static JTextField facultyNumber;
	private static JTextField FName;
	private static JTextField LName;
	private static JTextField Nat;
	private static JTextField Dept;
	private static JComboBox categoryDesc;

	private static JButton Edit;
	private static JButton Update;

	public void display() {
		try {
			connect = ConnectionManagerFactory.createConnection();
			PreparedStatement prepstat = connect.prepareStatement(SqlQueries.GETFACULTYINFO);
			prepstat.setString(1, facultyInfo.getFacultyId());
			ResultSet rset = prepstat.executeQuery();

			while (rset.next()) {
				System.out.println(rset);
				facultyNumber.setText(facultyInfo.getFacultyId());
				FName.setText(rset.getString("FNAME"));
				LName.setText(rset.getString("LNAME"));
				Nat.setText(rset.getString("NATIONALITY"));
				Dept.setText(rset.getString("DEPARTMENT"));
				String catstr = rset.getString("CATEGORYDESC");
				categoryDesc.setSelectedItem(catMap.get(catstr));

				facultyNumber.setEditable(false);
				FName.setEditable(false);
				LName.setEditable(false);
				Nat.setEditable(false);
				Dept.setEditable(false);
				categoryDesc.setEnabled(false);
			}

			connect.close();
			prepstat.close();
			rset.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}

	}

	public static void populateBox()
	{
		try {
			Iterator<String> iter = catMap.keySet().iterator(); 
			while (iter.hasNext()) {
				categoryDesc.addItem(iter.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public static void getCatMap()
	{
		try {
			connect = ConnectionManagerFactory.createConnection();
			PreparedStatement prepstat = connect.prepareStatement(SqlQueries.GETCATEGORYMAP);
			ResultSet rset = prepstat.executeQuery();
			while (rset.next()) {
				System.out.println(rset);
				catMap.put(rset.getString("CATEGORYDESC"),rset.getInt("CATEGORYID"));
			}
			connect.close();
			prepstat.close();
			rset.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}

	
	}
	
	public FacultyInfoScreen(Faculty faculty) {
		this.facultyInfo = faculty;
		getCatMap();

		setTitle("WELCOME FACULTY");
		setSize(400, 400);

		System.out.println("faculty:" + facultyInfo.getFacultyId());
		heading = new JPanel();
		middle = new JPanel();
		bottom = new JPanel();

		headingLabel = new JLabel();
		idLabel = new JLabel();
		firstNameLabel = new JLabel();
		lastNameLabel = new JLabel();
		nationlityLabel = new JLabel();
		departmentLabel = new JLabel();
		categoryLabel = new JLabel();

		headingLabel.setText("Faculty Information");
		headingLabel.setForeground(new Color(0, 0, 0));
		headingLabel.setVisible(true);

		facultyNumber = new JTextField();
		FName = new JTextField();
		LName = new JTextField();
		Nat = new JTextField();
		Dept = new JTextField();
		categoryDesc = new JComboBox();
		populateBox();

		// Labels
		idLabel.setText("	Faculty ID:   ");
		idLabel.setForeground(new Color(50, 50, 50));
		idLabel.setVisible(true);
		firstNameLabel.setText("	First Name:    ");
		firstNameLabel.setForeground(new Color(50, 50, 50));
		firstNameLabel.setVisible(true);
		lastNameLabel.setText("	Last Name:    ");
		lastNameLabel.setForeground(new Color(50, 50, 50));
		lastNameLabel.setVisible(true);
		nationlityLabel.setText("	Nationality:    ");
		nationlityLabel.setForeground(new Color(50, 50, 50));
		nationlityLabel.setVisible(true);
		departmentLabel.setText("	Department:    ");
		departmentLabel.setForeground(new Color(50, 50, 50));
		departmentLabel.setVisible(true);
		categoryLabel.setText("	Category:    ");
		categoryLabel.setForeground(new Color(50, 50, 50));
		
		heading.add(headingLabel);
		middle.setLayout(new GridLayout(6, 2));
		middle.add(idLabel);
		middle.add(facultyNumber);
		middle.add(firstNameLabel);
		middle.add(FName);
		middle.add(lastNameLabel);
		middle.add(LName);
		middle.add(nationlityLabel);
		middle.add(Nat);
		middle.add(departmentLabel);
		middle.add(Dept);
		middle.add(categoryLabel);
		middle.add(categoryDesc);
		
		Edit = new JButton();
		Update = new JButton();
		bottom.add(Edit);
		bottom.add(Update);
		
		// Buttons
		Edit.setText("EDIT");
		Edit.setVisible(true);
		Update.setText("UPDATE");
		Update.setVisible(true);
		Update.setEnabled(false);

		this.getContentPane().add(BorderLayout.NORTH, heading);
		this.getContentPane().add(BorderLayout.CENTER, middle);
		this.getContentPane().add(BorderLayout.SOUTH, bottom);

		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.display();

		Edit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				FName.setEditable(true);
				LName.setEditable(true);
				Nat.setEditable(true);
				Dept.setEditable(true);
				categoryDesc.setEnabled(true);
				Update.setEnabled(true);
				Edit.setEnabled(false);
			}
		});

		Update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					connect = ConnectionManagerFactory.createConnection();
					PreparedStatement prepstat = connect.prepareStatement(SqlQueries.UPDATEFACULTY);
					prepstat.setString(1, FName.getText());
					prepstat.setString(2, LName.getText());
					prepstat.setString(3, Nat.getText());
					prepstat.setString(4, Dept.getText());
					prepstat.setInt(5, catMap.get(categoryDesc.getSelectedItem()));
					prepstat.setString(6, facultyNumber.getText());
					
					ResultSet rset1 = prepstat.executeQuery();

					JOptionPane.showMessageDialog(null, "Details Updated");

					rset1.close();
					prepstat.close();
					connect.close();
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e);
				}

				FName.setEditable(false);
				LName.setEditable(false);
				Nat.setEditable(false);
				Dept.setEditable(false);
				categoryDesc.setEnabled(false);
				Update.setEnabled(false);
				Edit.setEnabled(true);
			}
		});

	}
}
