package com.dbms.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.dbms.entity.Conference;
import com.dbms.entity.Faculty;
import com.dbms.entity.Student;
import com.dbms.models.ConferencesTableModel;
import com.dbms.queries.SqlQueries;

public class ConferenceDetailModule extends JFrame {

	/**
	 * 
	 */
	private Student studentinfo;
	private Faculty facultyInfo;
	public static Connection connect = null;
	private static final long serialVersionUID = 3305181182449998L;
	private static JPanel heading;
	private static JPanel middle;
	private static JPanel bottom;

	private static JLabel headingLabel;
	private static JLabel l1;// conferenceNumber
	private static JLabel l2;// title
	private static JLabel l3;// year
	private static JLabel l4;// libraryId
	private static JLabel l5;// categoryId
	private static JLabel l6;// resourceId

	private static JTextField conferenceNumber;
	private static JTextField title;
	private static JTextField year;
	private static JTextField libraryId;
	private static JTextField categoryId;
	private static JTextField resourceId;

	private static JButton Edit;
	private Conference conferenceDetail;

	private static JButton Request;
	private static JButton Renew;
	private static JButton checkoutE_copy;

	public ConferenceDetailModule(Conference conference, Student studentInfo, Faculty facultyInfo) {
		this.studentinfo = studentInfo;
		this.facultyInfo = facultyInfo;
		this.conferenceDetail = conference;
		heading = new JPanel();
		middle = new JPanel();
		bottom = new JPanel();

		headingLabel = new JLabel();
		l1 = new JLabel();
		l2 = new JLabel();
		l3 = new JLabel();
		l4 = new JLabel();
		l5 = new JLabel();
		l6 = new JLabel();

		headingLabel.setText("Conference Information");
		headingLabel.setForeground(new Color(0, 0, 0));
		headingLabel.setVisible(true);

		conferenceNumber = new JTextField();
		title = new JTextField();
		year = new JTextField();
		libraryId = new JTextField();
		categoryId = new JTextField();
		resourceId = new JTextField();

		Request = new JButton();
		Renew = new JButton();
		checkoutE_copy= new JButton();

		Request.setText("REQUEST");
		Request.setVisible(true);
		Renew.setText("RENEW");
		Renew.setVisible(false);
		checkoutE_copy.setText("E-COPY-REQUEST");
		checkoutE_copy.setVisible(false);

		// Edit=new JButton();

		// Labels
		l1.setText("	Conference Number:   ");
		l1.setForeground(new Color(50, 50, 50));
		l1.setVisible(true);
		l2.setText("	Title:    ");
		l2.setForeground(new Color(50, 50, 50));
		l2.setVisible(true);
		l3.setText("	Year:    ");
		l3.setForeground(new Color(50, 50, 50));
		l3.setVisible(true);
		l4.setText("	Library ID:    ");
		l4.setForeground(new Color(50, 50, 50));
		l4.setVisible(true);
		l5.setText("	Category ID:    ");
		l5.setForeground(new Color(50, 50, 50));
		l5.setVisible(true);
		l6.setText("	Resourec ID:    ");
		l6.setForeground(new Color(50, 50, 50));
		l6.setVisible(true);

		// panels
		heading.add(headingLabel);
		middle.setLayout(new GridLayout(15, 15));
		middle.add(l1);
		middle.add(conferenceNumber);
		middle.add(l2);
		middle.add(title);
		middle.add(l3);
		middle.add(year);
		middle.add(l4);
		middle.add(libraryId);
		middle.add(l5);
		middle.add(categoryId);
		middle.add(l6);
		middle.add(resourceId);

		bottom.add(Request);
		bottom.add(Renew);
		bottom.add(checkoutE_copy);

		this.getContentPane().add(BorderLayout.NORTH, heading);
		this.getContentPane().add(BorderLayout.CENTER, middle);
		this.getContentPane().add(BorderLayout.SOUTH, bottom);
		this.setSize(400, 600);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.display();
	}

	public void display() {
		conferenceNumber.setText("" + conferenceDetail.getConferenceNumber());
		title.setText("" + conferenceDetail.getTitle());
		year.setText("" + conferenceDetail.getYear());
		libraryId.setText("" + conferenceDetail.getLibraryId());
		categoryId.setText("" + conferenceDetail.getCategoryId());
		resourceId.setText("" + conferenceDetail.getResourceId());

		conferenceNumber.setEditable(false);
		title.setEditable(false);
		year.setEditable(false);
		libraryId.setEditable(false);
		categoryId.setEditable(false);
		resourceId.setEditable(false);
		
		if(conferenceDetail.getEcopy().equalsIgnoreCase("1"))
			checkoutE_copy.setVisible(true);
		else
			checkoutE_copy.setVisible(false);

		try {

			int check = 0;
			boolean isReserved = false;
			connect = ConnectionManagerFactory.createConnection();
			PreparedStatement prepstat;
			if (studentinfo != null)
				prepstat = connect.prepareStatement(SqlQueries.CONFERENCEALREADYISSUEDFORSTUDENT);
			else
				prepstat = connect.prepareStatement(SqlQueries.CONFERENCEALREADYISSUEDFORFACULTY);

			String temp = Integer.toString(conferenceDetail.getResourceId());
			prepstat.setString(1, temp);
			if (studentinfo != null)
				prepstat.setString(2, studentinfo.getStudentId());
			else
				prepstat.setString(2, facultyInfo.getFacultyId());
			prepstat.setString(3, conferenceNumber.getText());
			ResultSet rset = prepstat.executeQuery();
			int flag = 0;
			boolean isRenewable = false;

			while (!rset.next()) {
				flag = 1;
				break;
			}

			if (flag == 0) {
				Renew.setVisible(true);
				Request.setVisible(false);
				isRenewable = true;
			}
			rset.close();
			prepstat.close();
			connect.close();
		}

		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}

		checkoutE_copy.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ConferencesTableModel.performEpublicationCheckout(conferenceDetail, studentinfo, facultyInfo);
				JOptionPane.showMessageDialog(null, "The E-Copy of the Conference-paper have been checked-out");
				ConferenceDetailModule.this.setVisible(false);
				ConferenceDetailModule.this.dispose();
				return;
			}
		});
		
		Renew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					connect = ConnectionManagerFactory.createConnection();
					PreparedStatement prepstat1 = connect.prepareStatement(SqlQueries.ISCONFERENCERENEWABLE);
					String abc = Integer.toString(conferenceDetail.getResourceId());
					prepstat1.setString(1, abc);
					ResultSet rset1 = prepstat1.executeQuery();
					int check = 0;
					while (!rset1.next()) {
						check = 1;
						break;
					}

					if (check == 1) {
						DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
						Date date = new Date();
						String currentTime = dateFormat.format(date);
						String returnTime;
						final long SECONDS = 1000;
						final long MINUTES = 60 * SECONDS;
						final long HOURS = 60 * MINUTES;

						date.setTime(date.getTime() + 12 * HOURS);
						returnTime = dateFormat.format(date);

						PreparedStatement prepstat3;
						if (studentinfo != null)
							prepstat3 = connect.prepareStatement(SqlQueries.CHECKOUTUPDATEFORSTUDENT);
						else
							prepstat3 = connect.prepareStatement(SqlQueries.CHECKOUTUPDATEFORFACULTY);

						String temp = Integer.toString(conferenceDetail.getResourceId());
						prepstat3.setString(1, currentTime);
						prepstat3.setString(2, returnTime);
						prepstat3.setString(3, temp);
						if (studentinfo != null)
							prepstat3.setString(4, studentinfo.getStudentId());
						else
							prepstat3.setString(4, facultyInfo.getFacultyId());
						ResultSet rset3 = prepstat3.executeQuery();
						JOptionPane.showMessageDialog(null, "The respective Conference have been renewed");
						rset3.close();
						prepstat3.close();
					} else {
						JOptionPane.showMessageDialog(null, "Cant be renewed as there is waiting on this item");
					}
					connect.close();
					rset1.close();
					prepstat1.close();
					ConferenceDetailModule.this.setVisible(false);
					ConferenceDetailModule.this.dispose();
				}
				catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});

		Request.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					connect = ConnectionManagerFactory.createConnection();
					PreparedStatement prepstat = connect.prepareStatement(SqlQueries.CONFERENCEAVAILABILITY);
					prepstat.setString(1, conferenceNumber.getText());
					ResultSet rset = prepstat.executeQuery();
					int number = 0;
					while (rset.next()) {
						number = Integer.parseInt(rset.getString(1));
					}

					if (number > 0) {
						// System.out.println(isReserved);
						new ConferenceConfirmation(studentinfo,facultyInfo,conferenceNumber.getText(),
								conferenceDetail.getResourceId());
					} else {
						//check if he is in waitlist
						if(CheckIfAlreadyInWaitList(conferenceDetail.getResourceId(),studentinfo,facultyInfo))
						{
							JOptionPane.showMessageDialog(null,
									" You are already in the waitlist table");
							return;
						}
						prepstat = connect.prepareStatement(SqlQueries.CONFERENCEWAIT);
						String abc = Integer.toString(conferenceDetail.getResourceId());
						DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
						Date date = new Date();
						String currentTime = dateFormat.format(date);
						prepstat.setString(1, abc);
						prepstat.setString(2, currentTime);
						if (studentinfo != null) {
							prepstat.setString(3, studentinfo.getStudentId());
							prepstat.setString(4, null);
						} else {
							prepstat.setString(3, null);
							prepstat.setString(4, facultyInfo.getFacultyId());
						}

						String returnTime;
						// System.out.println(isReserved);
						dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
						final long SECONDS = 1000;
						final long MINUTES = 60 * SECONDS;
						final long HOURS = 60 * MINUTES;
						// System.out.println(isReserved);
						date.setTime(date.getTime() + 12 * HOURS);
						returnTime = dateFormat.format(date);
						prepstat.setString(5, returnTime);
						prepstat.setString(6, "Conference");
						prepstat.setString(7, conferenceNumber.getText());
						rset = prepstat.executeQuery();
						JOptionPane.showMessageDialog(null,
								"This conference is currently not available. You are currently added to the waitlist table");
					}
					rset.close();
					prepstat.close();
					connect.close();
					ConferenceDetailModule.this.setVisible(false);
					ConferenceDetailModule.this.dispose();
				}
				catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
	}
	
	public static boolean CheckIfAlreadyInWaitList(int resourceId,Student studentInfo,Faculty facultyInfo)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			if(studentInfo != null)
			{
				stat = conn.prepareStatement(SqlQueries.CHECKINCHECKOUTWAITSTUDENT);
				stat.setString(2, studentInfo.getStudentId());
				
			}else
			{
				stat = conn.prepareStatement(SqlQueries.CHECKINCHECKOUTWAITFACULTY);
				stat.setString(2, facultyInfo.getFacultyId());
			}
			stat.setInt(1, resourceId);
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					ConnectionManagerFactory.CloseConnection(conn, stat,rs);
					return true;
				}
				ConnectionManagerFactory.CloseConnection(conn, stat,rs);
			}
			return false;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Check in checkout Wait\n");
		}
		return false;
	}

}
