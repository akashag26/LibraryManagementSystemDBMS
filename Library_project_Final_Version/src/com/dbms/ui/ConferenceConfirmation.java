package com.dbms.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.dbms.connection.ConnectionManagerFactory;
import com.dbms.entity.Faculty;
import com.dbms.entity.Student;
import com.dbms.queries.SqlQueries;

public class ConferenceConfirmation {
	public static Connection connect = null;
	private static Student studentinfo;
	private static Faculty facultyInfo;
	private static String ConferenceNumber;
	private static int ResourceId;
	private static boolean isReserved;
	private static JFrame f1;
	private static JPanel top;
	private static JPanel middle;
	private static JPanel bottom;
	private static JLabel CheckoutTime;
	private static JLabel ReturnTime;
	private static JTextField t1;
	private static JTextField t2;
	private static JButton Submit;

	ConferenceConfirmation(Student StudentInfo, Faculty facultyInfo, String conferencenumber, int resourceID) {
		this.studentinfo = StudentInfo;
		this.facultyInfo = facultyInfo;
		this.ConferenceNumber = conferencenumber;
		this.ResourceId = resourceID;

		f1 = new JFrame();
		top = new JPanel();
		middle = new JPanel();
		bottom = new JPanel();
		CheckoutTime = new JLabel();
		ReturnTime = new JLabel();
		t1 = new JTextField();
		t2 = new JTextField();
		Submit = new JButton();
		t1.setEditable(false);
		t2.setEditable(false);


		CheckoutTime.setText("Check out Time:");
		CheckoutTime.setForeground(new Color(0, 0, 0));
		CheckoutTime.setVisible(true);

		ReturnTime.setText("	Return Time:   ");
		ReturnTime.setForeground(new Color(50, 50, 50));
		ReturnTime.setVisible(true);

		Submit.setText("Confirm");
		Submit.setVisible(true);

		middle.add(CheckoutTime);
		middle.add(t1);
		middle.add(ReturnTime);
		middle.add(t2);

		middle.setLayout(new GridLayout(2, 2));
		bottom.add(Submit);

		f1.getContentPane().add(BorderLayout.NORTH, top);
		f1.getContentPane().add(BorderLayout.CENTER, middle);
		f1.getContentPane().add(BorderLayout.SOUTH, bottom);

		f1.setSize(400, 150);
		f1.setVisible(true);
		// f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setLocationRelativeTo(null);

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
		Date date = new Date();
		String currentTime = dateFormat.format(date);
		t1.setText(currentTime);
		final long SECONDS = 1000;
		final long MINUTES = 60 * SECONDS;
		final long HOURS = 60 * MINUTES;

		date.setTime(date.getTime() + 12 * HOURS);
		currentTime = dateFormat.format(date);
		t2.setText(currentTime);

		Submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					connect = ConnectionManagerFactory.createConnection();
					PreparedStatement prepstat = connect.prepareStatement(SqlQueries.CHECKOUT);
					String abc = Integer.toString(ResourceId);
					prepstat.setString(1, abc);
					prepstat.setString(2, t1.getText());
					prepstat.setString(3, t2.getText());
					prepstat.setString(4, null);
					if (studentinfo != null) {
						prepstat.setString(5, StudentInfo.getStudentId());
						prepstat.setString(6, null);
					} else {
						prepstat.setString(5, null);
						prepstat.setString(6, facultyInfo.getFacultyId());
					}
					prepstat.setString(7, "Conference");
					prepstat.setString(8, ConferenceNumber);
					ResultSet rset = prepstat.executeQuery();

					prepstat = connect.prepareStatement(SqlQueries.UPDATECONFERENCECOUNTER);
					prepstat.setString(1, ConferenceNumber);
					rset = prepstat.executeQuery();
					JOptionPane.showMessageDialog(null, "The respective Conference have been issued");
					rset.close();
					prepstat.close();
					connect.close();
					f1.setVisible(false);
					f1.dispose();
				}
				catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
	}
}
