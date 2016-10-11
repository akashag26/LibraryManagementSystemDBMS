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
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.dbms.connection.ConnectionManagerFactory;
import com.dbms.docfilter.DateLabelFormatter;
import com.dbms.entity.Camera;
import com.dbms.entity.Faculty;
import com.dbms.entity.Student;
import com.dbms.models.CameraTableModel;
import com.dbms.queries.SqlQueries;

public class CameraConfirmation {

	public static Connection connect = null;
	private static Student studentinfo;
	private static Faculty facultyInfo;
	private static String ISBN;
	private static int ResourceId;
	private static boolean isReserved;
	private static JFrame f1;
	private static JPanel top;
	private static JPanel middle;
	private static JPanel bottom;
	private static JLabel CheckoutDay;
	private static JLabel CheckoutTime;
	private static JTextField t1;
	private static JTextField t2;
	private static JButton Submit;

	private static UtilDateModel dateModel;
	private static JDatePickerImpl datePicker;
	private static JDatePanelImpl datePanel;
	private static Properties i18properties;
	private static JSpinner timeSpinner;

	CameraConfirmation(Student studentInfo, Faculty facultyInfo, Camera camera) {
		this.studentinfo = studentInfo;
		this.facultyInfo = facultyInfo;

		i18properties = new Properties();
		/*********************************/
		i18properties.put("text.today", "Today");
		i18properties.put("text.month", "Month");
		i18properties.put("text.year", "Year");
		/*********************************/

		/***** Date field ******/
		dateModel = new UtilDateModel();
		datePanel = new JDatePanelImpl(dateModel, i18properties);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		/***** Date field ******/

		/** for handling time selector **/
		timeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
		timeSpinner.setEditor(timeEditor);
		timeSpinner.setValue(new Date());
		/** for handling time selector **/

		f1 = new JFrame();
		top = new JPanel();
		middle = new JPanel();
		bottom = new JPanel();
		CheckoutDay = new JLabel();
		CheckoutTime = new JLabel();
		t1 = new JTextField();
		t2 = new JTextField();
		Submit = new JButton();

		CheckoutDay.setText("Check out Day(Only Friday):");
		CheckoutDay.setForeground(new Color(0, 0, 0));
		CheckoutDay.setVisible(true);

		CheckoutTime.setText("	Checkout Time:   ");
		CheckoutTime.setForeground(new Color(50, 50, 50));
		CheckoutTime.setVisible(true);

		Submit.setText("Confirm");
		Submit.setVisible(true);

		middle.add(CheckoutDay);
		middle.add(datePicker);
		middle.add(CheckoutTime);
		middle.add(timeSpinner);
		dateModel.setDate(2015, 10, 13);
		dateModel.setSelected(true);

		middle.setLayout(new GridLayout(2, 2));
		bottom.add(Submit);

		f1.getContentPane().add(BorderLayout.NORTH, top);
		f1.getContentPane().add(BorderLayout.CENTER, middle);
		f1.getContentPane().add(BorderLayout.SOUTH, bottom);

		f1.setSize(300, 150);
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
		// System.out.println(isReserved);
		if (isReserved) {
			date.setTime(date.getTime() + 4 * HOURS);
			currentTime = dateFormat.format(date);
			t2.setText(currentTime);
		} else {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.DATE, 14);
			currentTime = dateFormat.format(c.getTime());
			t2.setText(currentTime);
		}

		Submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {

					/*** get value from time picker ***/
					Calendar tempCalendar = Calendar.getInstance();
					tempCalendar.setTime((Date) timeSpinner.getModel().getValue());

					// System.out.println("prevDay "+prevDay.getTime()+"\n");

					Date chosenDate = (Date) datePicker.getModel().getValue();

					if (chosenDate == null) {
						JOptionPane.showMessageDialog(null, "Choose Date\n");
						return;
					}

					Calendar chosenCalInstanceStart = Calendar.getInstance();
					chosenCalInstanceStart.setTime(chosenDate);

					if (chosenCalInstanceStart.get(Calendar.DAY_OF_WEEK) != 6) {
						JOptionPane.showMessageDialog(null, "Camera can only be checked out on a Friday\n");
						return;
					}

					if (tempCalendar.get(Calendar.HOUR_OF_DAY) < 9) {
						JOptionPane.showMessageDialog(null, "Camera can only be checked out from 9 am to noon am\n");
						return;
					}

					chosenCalInstanceStart.set(Calendar.HOUR_OF_DAY, tempCalendar.get(Calendar.HOUR_OF_DAY));
					chosenCalInstanceStart.set(Calendar.MINUTE, tempCalendar.get(Calendar.MINUTE));
					chosenCalInstanceStart.set(Calendar.SECOND, 0);
					chosenCalInstanceStart.set(Calendar.MILLISECOND, 0);
					Calendar chosenCalInstanceEnd = Calendar.getInstance();
					chosenCalInstanceEnd.setTime(chosenCalInstanceStart.getTime());

					connect = ConnectionManagerFactory.createConnection();

					PreparedStatement prepstat = connect.prepareStatement(SqlQueries.GETCAMERARESERVATION);
					prepstat.setString(1, camera.getCameraId());
					ResultSet rset = prepstat.executeQuery();

					SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
					Date startdate = chosenCalInstanceStart.getTime();
					String start = formatter.format(startdate);
					// System.out.println("time selected "+startdate);
					chosenCalInstanceEnd.add(Calendar.DAY_OF_MONTH, 6);
					System.out.println(chosenCalInstanceStart.getTime());

					Date returndate = chosenCalInstanceEnd.getTime();
					String end = formatter.format(returndate);
					System.out.println("time selected " + start);
					System.out.println("time selected " + end);
					// System.out.println(camera.getCameraId());

					int flag = 0;
					while (!rset.next()) {
						flag = 1;
						break;
					}

					if (flag == 1) {

						PreparedStatement prepstat1 = connect.prepareStatement(SqlQueries.BOOKCAMERA);
						prepstat1.setString(1, Integer.toString(CameraTableModel.getNewCameraReservationId()));
						prepstat1.setString(2, camera.getCameraId());
						prepstat1.setString(3, start);
						prepstat1.setString(4, end);
						if (studentinfo != null) {
							prepstat1.setString(5, studentinfo.getStudentId());
							prepstat1.setString(6, null);
						} else {
							prepstat1.setString(5, null);
							prepstat1.setString(6, facultyInfo.getFacultyId());
						}
						ResultSet rset1 = prepstat1.executeQuery();
						JOptionPane.showMessageDialog(null, "Camera Booked Successfully\n");
						rset1.close();
						prepstat1.close();
						f1.setVisible(false);
						f1.dispose();
						return;
					}

					if (flag == 0) {
						PreparedStatement prepstat3;
						if (studentinfo == null)
							prepstat3 = connect.prepareStatement(SqlQueries.CHECKCAMERARESERVATIONFORSTUDENT);
						else
							prepstat3 = connect.prepareStatement(SqlQueries.CHECKCAMERARESERVATIONFORFACULTY);
						prepstat3.setString(1, camera.getCameraId());
						if (studentinfo != null)
							prepstat3.setString(2, studentinfo.getStudentId());
						else
							prepstat3.setString(2, facultyInfo.getFacultyId());
						ResultSet rset3 = prepstat3.executeQuery();
						int check = 0;
						while (!rset3.next()) {
							check = 1;
							break;
						}

						if (check == 0) {
							JOptionPane.showMessageDialog(null, "You have already reserved the camera");
							f1.setVisible(false);
							f1.dispose();
							return;
						}

						PreparedStatement prepstat5 = connect.prepareStatement(SqlQueries.GETCAMERARESERVATION1);
						prepstat5.setString(1, camera.getCameraId());

						rset = prepstat5.executeQuery();
						while (rset.next()) {

							Date returnTime = rset.getTimestamp(1);
							Date minStartTime = rset.getTimestamp(2);
							if (startdate.after(returnTime) || returndate.before(minStartTime)) {

								PreparedStatement prepstat1 = connect.prepareStatement(SqlQueries.BOOKCAMERA);
								prepstat1.setString(1, Integer.toString(CameraTableModel.getNewCameraReservationId()));
								prepstat1.setString(2, camera.getCameraId());
								prepstat1.setString(3, start);
								prepstat1.setString(4, end);
								if (studentinfo != null) {
									prepstat1.setString(5, studentinfo.getStudentId());
									prepstat1.setString(6, null);
								} else {
									prepstat1.setString(5, null);
									prepstat1.setString(6, facultyInfo.getFacultyId());
								}
								ResultSet rset1 = prepstat1.executeQuery();
								JOptionPane.showMessageDialog(null, "Camera Booked Successfully\n");
								rset1.close();
								prepstat1.close();
								f1.setVisible(false);
								f1.dispose();
								return;
							}

							else {
								PreparedStatement prepstat1 = connect.prepareStatement(SqlQueries.CAMERAQUEUE);
								PreparedStatement prepstat4;
								if(studentInfo != null)
									prepstat4 = connect.prepareStatement(SqlQueries.CHECKCAMERAQUEUEFORSTUDENT);
								else
									prepstat4 = connect.prepareStatement(SqlQueries.CHECKCAMERAQUEUEFORFACULTY);
								prepstat4.setString(1, camera.getCameraId());
								if(studentInfo != null)
									prepstat4.setString(2, studentinfo.getStudentId());
								else
									prepstat4.setString(2, facultyInfo.getFacultyId());
								ResultSet rset4 = prepstat4.executeQuery();

								int check1 = 0;
								while (!rset4.next()) {
									check1 = 1;
									break;
								}

								if (check1 == 0) {

									JOptionPane.showMessageDialog(null, "You are already present in the wait queue");
									f1.setVisible(false);
									f1.dispose();
									return;
								}

								PreparedStatement prepstat2 = connect.prepareStatement(SqlQueries.GETMAXCAMERAQUEUE);
								prepstat2.setString(1, camera.getCameraId());
								ResultSet rset2 = prepstat2.executeQuery();
								int i = 0;
								while (rset2.next()) {
									i = rset2.getInt(1);
									i += 1;
								}
								prepstat1.setInt(2, i);
								prepstat1.setString(1, camera.getCameraId());
								prepstat1.setString(3, start);
								prepstat1.setString(4, end);
								if (studentinfo != null) {
									prepstat1.setString(5, studentinfo.getStudentId());
									prepstat1.setString(6, null);
								} else {
									prepstat1.setString(5, null);
									prepstat1.setString(6, facultyInfo.getFacultyId());
								}
								ResultSet rset1 = prepstat1.executeQuery();
								rset1.close();
								prepstat1.close();
								JOptionPane.showMessageDialog(null,"You request is added to the wait queue. Queue No: " + i);
							}
						}
					}
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