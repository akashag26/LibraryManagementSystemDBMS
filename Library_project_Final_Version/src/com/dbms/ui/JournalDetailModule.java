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
import com.dbms.entity.Author;
import com.dbms.entity.Faculty;
import com.dbms.entity.Journal;
import com.dbms.entity.Student;
import com.dbms.models.JournalTableModel;
import com.dbms.queries.SqlQueries;

public class JournalDetailModule extends JFrame {

	/**
	 * 
	 */
	private Student studentInfo;
	private Faculty facultyInfo;
	public static Connection connect = null;
	private static final long serialVersionUID = 4236125386452659027L;
	private static JPanel heading;
	private static JPanel middle;
	private static JPanel bottom;

	private static JLabel headingLabel;
	private static JLabel l1;// ISSN
	private static JLabel l2;// title
	private static JLabel l3;// year
	private static JLabel l4;// LibraryName
	private static JLabel l5;// categoryName
	private static JLabel l6;// ecopy
	private static JLabel l7;// authorName

	private static JTextField issn;
	private static JTextField title;
	private static JTextField year;
	private static JTextField libraryName;
	private static JTextField categoryName;
	private static JTextField ecopy;
	private static JTextField authorName;

	private static JButton Request;
	private static JButton Renew;
	private static JButton checkoutE_copy;

	// private static JButton Edit;
	private Journal journal;

	public JournalDetailModule(Journal journal, Student studentInfo, Faculty facultyInfo) {
		this.studentInfo = studentInfo;
		this.facultyInfo = facultyInfo;

		this.journal = journal;
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
		l7 = new JLabel();

		headingLabel.setText("Journal Information");
		headingLabel.setForeground(new Color(0, 0, 0));
		headingLabel.setVisible(true);

		issn = new JTextField();
		title = new JTextField();
		year = new JTextField();
		libraryName = new JTextField();
		categoryName = new JTextField();
		ecopy = new JTextField();
		authorName = new JTextField();

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
		l1.setText("	ISSN:   ");
		l1.setForeground(new Color(50, 50, 50));
		l1.setVisible(true);
		l2.setText("	Title:    ");
		l2.setForeground(new Color(50, 50, 50));
		l2.setVisible(true);
		l3.setText("	Year:    ");
		l3.setForeground(new Color(50, 50, 50));
		l3.setVisible(true);
		l4.setText("	Library Name:    ");
		l4.setForeground(new Color(50, 50, 50));
		l4.setVisible(true);
		l5.setText("	Category Name:    ");
		l5.setForeground(new Color(50, 50, 50));
		l5.setVisible(true);
		l6.setText("	ECopy:    ");
		l6.setForeground(new Color(50, 50, 50));
		l6.setVisible(true);
		l7.setText("	Author Name:    ");
		l7.setForeground(new Color(50, 50, 50));
		l7.setVisible(true);

		// panels
		heading.add(headingLabel);
		middle.setLayout(new GridLayout(15, 15));
		middle.add(l1);
		middle.add(issn);
		middle.add(l2);
		middle.add(title);
		middle.add(l3);
		middle.add(year);
		// bottom.add(Edit);
		middle.add(l4);
		middle.add(libraryName);
		middle.add(l5);
		middle.add(categoryName);
		middle.add(l6);
		middle.add(ecopy);
		middle.add(l7);
		middle.add(authorName);

		bottom.add(Request);
		bottom.add(Renew);
		bottom.add(checkoutE_copy);

		// Buttons
		// Edit.setText("EDIT");
		// Edit.setVisible(true);

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
		StringBuffer buffer = new StringBuffer();
		for (Author name : journal.getAuthorNames())
			buffer.append(name.getAuthorName() + ",");
		if (buffer.toString() != null && buffer.toString().length() > 1)
			authorName.setText(buffer.toString().substring(0, buffer.toString().length() - 1));
		else
			authorName.setText(buffer.toString());
		issn.setText(journal.getISSN());
		title.setText(journal.getTitle());
		year.setText(journal.getYear());
		categoryName.setText(journal.getCategoryName());
		libraryName.setText(journal.getLibraryName());
		ecopy.setText("" + journal.getEcopy());
		
		if(journal.getEcopy().equalsIgnoreCase("1"))
			checkoutE_copy.setVisible(true);
		else
			checkoutE_copy.setVisible(false);
		
		/* */
		issn.setEditable(false);
		title.setEditable(false);
		year.setEditable(false);
		libraryName.setEditable(false);
		categoryName.setEditable(false);
		ecopy.setEditable(false);
		authorName.setEditable(false);

		try {
			connect = ConnectionManagerFactory.createConnection();
			PreparedStatement prepstat;
			if (studentInfo != null)
				prepstat = connect.prepareStatement(SqlQueries.JOURNALSREADYISSUEDFORSTUDENT);
			else
				prepstat = connect.prepareStatement(SqlQueries.JOURNALSREADYISSUEDFORFACULTY);

			String abc = Integer.toString(journal.getResourceId());
			prepstat.setString(1, abc);
			if (studentInfo != null)
				prepstat.setString(2, studentInfo.getStudentId());
			else
				prepstat.setString(2, facultyInfo.getFacultyId());
			prepstat.setString(3, issn.getText());
			ResultSet rset = prepstat.executeQuery();
			int flag = 0;

			while (!rset.next()) {
				// System.out.println("Fresh");
				flag = 1;
				break;
			}

			if (flag == 0) {

				Renew.setVisible(true);
				Request.setVisible(false);
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
				JournalTableModel.performEpublicationCheckout(journal, studentInfo, facultyInfo);
				JOptionPane.showMessageDialog(null, "The E-Copy of the Journal-paper have been checked-out");
				JournalDetailModule.this.setVisible(false);
				JournalDetailModule.this.dispose();
				return;
			}
		});
		

		Renew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
					connect = ConnectionManagerFactory.createConnection();
					PreparedStatement prepstat1 = connect.prepareStatement(SqlQueries.ISJOURNALRENEWABLE);
					String abc = Integer.toString(journal.getResourceId());
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
						// System.out.println(isReserved);

						date.setTime(date.getTime() + 12 * HOURS);
						returnTime = dateFormat.format(date);

						PreparedStatement prepstat3;
						if (studentInfo != null)
							prepstat3 = connect.prepareStatement(SqlQueries.CHECKOUTUPDATEFORSTUDENT);
						else
							prepstat3 = connect.prepareStatement(SqlQueries.CHECKOUTUPDATEFORFACULTY);
						String xyz = Integer.toString(journal.getResourceId());

						prepstat3.setString(1, currentTime);
						prepstat3.setString(2, returnTime);
						prepstat3.setString(3, xyz);
						if (studentInfo != null)
							prepstat3.setString(4, studentInfo.getStudentId());
						else
							prepstat3.setString(4, facultyInfo.getFacultyId());

						ResultSet rset3 = prepstat3.executeQuery();

						JOptionPane.showMessageDialog(null, "The respective Journal have been renewed");
						rset3.close();
						prepstat3.close();

					} else {
						JOptionPane.showMessageDialog(null, "Cant be renewed as there is waiting on this item");
					}
					connect.close();
					rset1.close();
					prepstat1.close();
					JournalDetailModule.this.setVisible(false);
					JournalDetailModule.this.dispose();
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
					PreparedStatement prepstat = connect.prepareStatement(SqlQueries.JOURNALAVAILABILITY);
					prepstat.setString(1, issn.getText());
					ResultSet rset = prepstat.executeQuery();
					int number = 0;
					while (rset.next()) {
						number = Integer.parseInt(rset.getString(1));
					}

					if (number > 0) {
						System.out.println(issn.getText());
						new JournalConfirmation(studentInfo,facultyInfo,issn.getText(), journal.getResourceId());
						JournalDetailModule.this.setVisible(false);
						JournalDetailModule.this.dispose();
					} else {
						//check if he is in waitlist
						if(ConferenceDetailModule.CheckIfAlreadyInWaitList(journal.getResourceId(),studentInfo,facultyInfo))
						{
							JOptionPane.showMessageDialog(null,
									" You are already in the waitlist table");
							return;
						}
						prepstat = connect.prepareStatement(SqlQueries.JOURNALWAIT);
						String journalId = Integer.toString(journal.getResourceId());
						DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
						Date date = new Date();
						String currentTime = dateFormat.format(date);
						prepstat.setString(1, journalId);
						prepstat.setString(2, currentTime);
						
						if (studentInfo != null) {
							prepstat.setString(3, studentInfo.getStudentId());
							prepstat.setString(4, null);
						} else {
							prepstat.setString(3, null);
							prepstat.setString(4, facultyInfo.getFacultyId());
						}

						dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");

						final long SECONDS = 1000;
						final long MINUTES = 60 * SECONDS;
						final long HOURS = 60 * MINUTES;

						date.setTime(date.getTime() + 12 * HOURS);
						String returnTime = dateFormat.format(date);

						prepstat.setString(5, returnTime);
						prepstat.setString(6, "Journal");
						prepstat.setString(7, issn.getText());

						rset = prepstat.executeQuery();
						JOptionPane.showMessageDialog(null,
								"This Journal is currently not available. You are currently added to the waitlist table");
					}
					rset.close();
					prepstat.close();
					connect.close();
					JournalDetailModule.this.setVisible(false);
					JournalDetailModule.this.dispose();
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
	}
}