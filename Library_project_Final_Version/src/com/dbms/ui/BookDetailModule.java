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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.dbms.connection.ConnectionManagerFactory;
import com.dbms.entity.Author;
import com.dbms.entity.Book;
import com.dbms.entity.Faculty;
import com.dbms.entity.Student;
import com.dbms.models.BookTableModel;
import com.dbms.models.JournalTableModel;
import com.dbms.queries.SqlQueries;

public class BookDetailModule extends JFrame {

	/**
	 * 
	 */

	private Student studentinfo;
	private Faculty facultyInfo;
	public static Connection connect = null;
	private static final long serialVersionUID = 4236125386452659027L;
	private static JPanel heading;
	private static JPanel middle;
	private static JPanel bottom;

	private static JLabel headingLabel;
	private static JLabel l1;// ISBN
	private static JLabel l2;// title
	private static JLabel l3;// edition
	private static JLabel l4;// AuthorNames
	private static JLabel l5;// year of publisher
	private static JLabel l6;// publisher
	private static JLabel l7;// libId
	private static JLabel l8;// categoryId

	private static JTextField isbn;
	private static JTextField title;
	private static JTextField edition;
	private static JTextField authorNames;
	private static JTextField year;
	private static JTextField publisher;
	private static JTextField library;
	private static JTextField category;

	private static JButton Edit;
	private static JButton checkoutE_copy;
	private static JButton Request;
	private static JButton Renew;

	private Book book;

	public BookDetailModule(Book book, Student studentInfo, Faculty facultyInfo) {
		this.studentinfo = studentInfo;
		this.facultyInfo = facultyInfo;
		this.book = book;
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
		l8 = new JLabel();

		headingLabel.setText("Book Information");
		headingLabel.setForeground(new Color(0, 0, 0));
		headingLabel.setVisible(true);

		isbn = new JTextField();
		title = new JTextField();
		year = new JTextField();
		edition = new JTextField();
		authorNames = new JTextField();
		publisher = new JTextField();
		library = new JTextField();
		category = new JTextField();

		Edit = new JButton();
		Request = new JButton();
		Renew = new JButton();
		checkoutE_copy= new JButton();

		Request.setText("REQUEST");
		Request.setVisible(true);
		Renew.setText("RENEW");
		Renew.setVisible(false);
		checkoutE_copy.setText("E-COPY-REQUEST");
		checkoutE_copy.setVisible(false);
		
		// Labels
		l1.setText("	ISBN:   ");
		l1.setForeground(new Color(50, 50, 50));
		l1.setVisible(true);
		l2.setText("	Title:    ");
		l2.setForeground(new Color(50, 50, 50));
		l2.setVisible(true);
		l3.setText("	Year:    ");
		l3.setForeground(new Color(50, 50, 50));
		l3.setVisible(true);
		l4.setText("	Edition:    ");
		l4.setForeground(new Color(50, 50, 50));
		l4.setVisible(true);
		l5.setText("	Author Names:    ");
		l5.setForeground(new Color(50, 50, 50));
		l5.setVisible(true);
		l6.setText("	Publisher:    ");
		l6.setForeground(new Color(50, 50, 50));
		l6.setVisible(true);
		l7.setText("	Lib:    ");
		l7.setForeground(new Color(50, 50, 50));
		l7.setVisible(true);
		l8.setText("	Category:    ");
		l8.setForeground(new Color(50, 50, 50));
		l8.setVisible(true);

		// panels
		heading.add(headingLabel);
		middle.setLayout(new GridLayout(15, 15));
		middle.add(l1);
		middle.add(isbn);
		middle.add(l2);
		middle.add(title);
		middle.add(l3);
		middle.add(year);
		// bottom.add(Edit);
		middle.add(l4);
		middle.add(edition);
		middle.add(l5);
		middle.add(authorNames);
		middle.add(l6);
		middle.add(publisher);
		middle.add(l7);
		middle.add(library);
		middle.add(l8);
		middle.add(category);

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
		StringBuffer buffer = new StringBuffer();
		for (Author name : book.getAuthorNames())
			buffer.append(name.getAuthorName() + ",");
		if (buffer.toString() != null && buffer.toString().length() > 1)
			authorNames.setText(buffer.toString().substring(0, buffer.toString().length() - 1));
		else
			authorNames.setText(buffer.toString());

		if(book.getEcopy().equalsIgnoreCase("1"))
			checkoutE_copy.setVisible(true);
		else
			checkoutE_copy.setVisible(false);
		
		isbn.setText(book.getISBN());
		title.setText(book.getTitle());
		year.setText(book.getYearOfPublication());
		edition.setText(book.getEdition());
		publisher.setText(book.getPublisher());
		library.setText("" + book.getLibrary());
		category.setText("" + book.getCategory());

		/* */
		isbn.setEditable(false);
		title.setEditable(false);
		year.setEditable(false);
		edition.setEditable(false);
		authorNames.setEditable(false);
		publisher.setEditable(false);
		library.setEditable(false);
		category.setEditable(false);

		try {

			int check = 0;
			boolean isReserved = false;
			connect = ConnectionManagerFactory.createConnection();
			PreparedStatement prepstat;
			if (studentinfo != null)
				prepstat = connect.prepareStatement(SqlQueries.BOOKALREADYISSUEDFORSTUDENT);
			else
				prepstat = connect.prepareStatement(SqlQueries.BOOKALREADYISSUEDFORFACULTY);

			String abc = Integer.toString(book.getResourceID());
			prepstat.setString(1, abc);
			if (studentinfo != null)
				prepstat.setString(2, studentinfo.getStudentId());
			else
				prepstat.setString(2, facultyInfo.getFacultyId());
			prepstat.setString(3, isbn.getText());
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
				BookTableModel.performEpublicationCheckout(book, studentinfo, facultyInfo);
				JOptionPane.showMessageDialog(null, "The E-Copy of the Book have been checked-out");
				BookDetailModule.this.setVisible(false);
				BookDetailModule.this.dispose();
				return;
			}
		});

		Renew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
					connect = ConnectionManagerFactory.createConnection();
					PreparedStatement prepstat1 = connect.prepareStatement(SqlQueries.ISBOOKRENEWABLE);
					String abc = Integer.toString(book.getResourceID());
					prepstat1.setString(1, abc);
					ResultSet rset1 = prepstat1.executeQuery();
					int check = 0;
					while (!rset1.next()) {
						check = 1;
						break;
					}

					if (check == 1) {
						// JOptionPane.showMessageDialog(null, "Renewable");

						DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
						Date date = new Date();
						String currentTime = dateFormat.format(date);
						String returnTime;
						final long SECONDS = 1000;
						final long MINUTES = 60 * SECONDS;
						final long HOURS = 60 * MINUTES;
						// System.out.println(isReserved);

						PreparedStatement prepstat2 = connect.prepareStatement(SqlQueries.GETCOURSEBOOK);
						prepstat2.setString(1, isbn.getText());
						ResultSet rset2 = prepstat2.executeQuery();

						boolean isReserved = true;
						while (!rset2.next()) {
							// System.out.println(rset.getString(1));
							isReserved = false;
							break;
						}

						if (isReserved) {
							date.setTime(date.getTime() + 4 * HOURS);
							returnTime = dateFormat.format(date);

						} else {
							Calendar c = Calendar.getInstance();
							c.setTime(new Date());
							c.add(Calendar.DATE, 14);
							returnTime = dateFormat.format(c.getTime());
						}
						// System.out.println(currentTime+" "+returnTime);

						rset2.close();
						prepstat2.close();

						PreparedStatement prepstat3;
						if (studentinfo != null)
							prepstat3 = connect.prepareStatement(SqlQueries.CHECKOUTUPDATEFORSTUDENT);
						else
						{
								
							prepstat3 = connect.prepareStatement(SqlQueries.CHECKOUTUPDATEFORFACULTY);

						}
						String xyz = Integer.toString(book.getResourceID());
						prepstat3.setString(1, currentTime);
						prepstat3.setString(2, returnTime);
						prepstat3.setString(3, xyz);

						if (studentinfo != null)
							prepstat3.setString(4, studentinfo.getStudentId());
						else
							prepstat3.setString(4, facultyInfo.getFacultyId());

						ResultSet rset3 = prepstat3.executeQuery();
						JOptionPane.showMessageDialog(null, "The respective Book have been renewed");
						rset3.close();
						prepstat3.close();
					} else {
						JOptionPane.showMessageDialog(null, "Cant be renewed as there is waiting on this item");
					}
					connect.close();
					rset1.close();
					prepstat1.close();
					BookDetailModule.this.setVisible(false);
					BookDetailModule.this.dispose();
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

					int check = 0;
					boolean isReserved = false;
					connect = ConnectionManagerFactory.createConnection();
					PreparedStatement prepstat = connect.prepareStatement(SqlQueries.GETCOURSEBOOK);
					prepstat.setString(1, isbn.getText());
					ResultSet rset = prepstat.executeQuery();

					int flag = 0;
					while (!rset.next()) {
						// System.out.println(rset.getString(1));
						flag = 1;
						break;
					}

					if (flag == 0 && studentinfo != null) {
						isReserved = true;
						String course = rset.getString(1);
						prepstat = connect.prepareStatement(SqlQueries.ISCOURSETAKEN);
						prepstat.setString(1, studentinfo.getStudentId());
						rset = prepstat.executeQuery();

						while (rset.next()) {

							if (rset.getString(1).equals(course)) {
								// System.out.println(rset.getString(1)+ "
								// "+studentinfo.getStudentId());
								check = 0;
								break;
							}
							check = 1;
						}
					}
					
					if(flag == 0 &&  studentinfo==null)
					{
							JOptionPane.showMessageDialog(null, "This book is reserved. Can only be checked out by a student.");
							return;
					
					}

					if (check == 1) {
						
						
						JOptionPane.showMessageDialog(null,
								"This book is reserved. Cant be booked by you as the course requirements does not meet");
						return;
					}

					if (check == 0) {
						prepstat = connect.prepareStatement(SqlQueries.BOOKAVAILABILITY);
						prepstat.setString(1, isbn.getText());
						rset = prepstat.executeQuery();
						int number = 0;
						while (rset.next()) {
							number = Integer.parseInt(rset.getString(1));
						}

						if (number > 0) {
							// System.out.println(isReserved);
							new BookConfirmation(studentinfo,facultyInfo,isbn.getText(), isReserved, book.getResourceID());
							BookDetailModule.this.setVisible(false);
							BookDetailModule.this.dispose();
						} else {
							//check if he is waitlist
							if(ConferenceDetailModule.CheckIfAlreadyInWaitList(book.getResourceID(),studentinfo,facultyInfo))
							{
								JOptionPane.showMessageDialog(null,
										" You are already in the waitlist table");
								return;
							}
							prepstat = connect.prepareStatement(SqlQueries.BOOKWAIT);
							String abc = Integer.toString(book.getResourceID());
							DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
							Date date = new Date();
							String currentTime = dateFormat.format(date);

							prepstat.setString(1, abc);
							prepstat.setString(2, currentTime);
							if (studentinfo != null) {
								prepstat.setString(3, studentinfo.getStudentId());
								prepstat.setString(4, null);
							} else {
								prepstat.setString(4, facultyInfo.getFacultyId());
								prepstat.setString(3, null);
							}
							

							dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
							date = new Date();
							currentTime = dateFormat.format(date);
							String returnTime;
							final long SECONDS = 1000;
							final long MINUTES = 60 * SECONDS;
							final long HOURS = 60 * MINUTES;
							// System.out.println(isReserved);
							PreparedStatement prepstat2 = connect.prepareStatement(SqlQueries.GETCOURSEBOOK);
							prepstat2.setString(1, isbn.getText());
							ResultSet rset2 = prepstat2.executeQuery();
							isReserved = true;
							while (!rset2.next()) {
								// System.out.println(rset.getString(1));
								isReserved = false;
								break;
							}
							if (isReserved) {
								date.setTime(date.getTime() + 4 * HOURS);
								returnTime = dateFormat.format(date);
							} else {
								Calendar c = Calendar.getInstance();
								c.setTime(new Date());
								c.add(Calendar.DATE, 14);
								returnTime = dateFormat.format(c.getTime());
							}
							// System.out.println(currentTime+" "+returnTime);
							prepstat.setString(5, returnTime);
							prepstat.setString(6, "Book");
							prepstat.setString(7, book.getISBN());
							rset2.close();
							prepstat2.close();
							rset = prepstat.executeQuery();
							JOptionPane.showMessageDialog(null,
									"This book is currently not available. You are currently added to the waitlist table");
						}
					}
					rset.close();
					prepstat.close();
					connect.close();
					BookDetailModule.this.setVisible(false);
					BookDetailModule.this.dispose();
				}
				catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
	}

}