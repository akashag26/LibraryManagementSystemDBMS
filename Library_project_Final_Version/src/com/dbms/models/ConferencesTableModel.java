package com.dbms.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.dbms.connection.ConnectionManagerFactory;
import com.dbms.entity.Author;
import com.dbms.entity.Conference;
import com.dbms.entity.Faculty;
import com.dbms.entity.Journal;
import com.dbms.entity.Student;
import com.dbms.queries.SqlQueries;

public class ConferencesTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3530099292807965490L;
	private List<Conference> Conferences;

	public ConferencesTableModel(List<Conference> Conferences) {
		this.Conferences = Conferences;
	}

	private final String[] columnNames = new String[] { "ConferenceNumber", "Title", "Year", "LibraryId",
			"CategoryId", "ResourceId","ECopy", "ConferenceName","LibraryName","CategoryName", "AuthorNames"};
	private final Class[] columnClass = new Class[] { Integer.class, String.class, String.class, Integer.class,
			Integer.class, Integer.class,String.class, String.class, String.class, String.class, String.class};

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.Conferences.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columnClass[columnIndex];
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		StringBuffer buffer = new StringBuffer();
		Conference row = Conferences.get(rowIndex);
		if (0 == columnIndex) {
			return row.getConferenceNumber();
		} else if (1 == columnIndex) {
			return row.getTitle();
		} else if (2 == columnIndex) {
			return row.getYear();
		} else if (3 == columnIndex) {
			return row.getLibraryId();
		} else if (4 == columnIndex) {
			return row.getCategoryId();
		} else if (5 == columnIndex) {
			return row.getResourceId();
		}else if (6 == columnIndex) {
			return row.getEcopy();
		}else if (7 == columnIndex) {
			return row.getConferenceName();
		}else if (8 == columnIndex) {
			return row.getLibraryName();
		}else if (9 == columnIndex) {
			return row.getCaetgoryName();
		}else if (10 == columnIndex) {
			for (Author name : row.getAuthorNames())
				buffer.append(name.getAuthorName() + ",");
			if(buffer.toString() != null && buffer.toString().length() > 1)
				return buffer.toString().substring(0, buffer.toString().length()-1);
			else
				return buffer.toString();
		}
		return null;
	}
	
	@Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
	
	public static List<Conference> getALLConferencePapers()
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.GETCONFERENCEPAPERS);
			List<Conference> conferences = new ArrayList<Conference>();
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					Conference conference = new Conference();
					conference.setConferenceNumber(rs.getString("CONFERENCENUMBER"));
					conference.setTitle(rs.getString("TITLE"));
					conference.setYear(rs.getString("YEAR"));
					conference.setLibraryId(rs.getInt("LIBRARYID"));
					conference.setCategoryId(rs.getInt("CATEGORYID"));
					conference.setResourceId(rs.getInt("RESOURCEID"));
					conference.setCaetgoryName(rs.getString("CATEGORY"));
					conference.setLibraryName(rs.getString("LIBRARYNAME"));
					conference.setConferenceName(rs.getString("CONFERENCENAME"));
					conference.setEcopy(rs.getString("ECOPY"));
					conference.setAuthorNames(getConferenceAuthorsbyConfID(rs.getString("CONFERENCENUMBER")));
					conferences.add(conference);
				}
				ConnectionManagerFactory.CloseConnection(conn, stat,rs);
			}
			return conferences;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error getting DATA\n");
		}
		return new ArrayList<Conference>();
	}

	public static List<Author> getConferenceAuthorsbyConfID(String confID)
	{
		List<Author> authors = new ArrayList<Author>();
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.GETCONFERENCEAUTHORS);
			stat.setString(1, confID);
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					Author author = new Author();
					author.setAuthorId(rs.getInt("AUTHORID"));
					author.setAuthorName(rs.getString("AUTHORNAME"));
					authors.add(author);
				}
				return authors;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error getting All Conferences\n");
		}
		return new ArrayList<Author>();
	}
	
	public static List<Conference> getAllCheckoutedConferences(Student studentInfo,Faculty facultyInfo)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			if(studentInfo != null){
				stat = conn.prepareStatement(SqlQueries.GETALLCONFERENCESCHECKEDOUTFORSTUDENT);
				stat.setString(1, studentInfo.getStudentId());
			}else
			{
				stat = conn.prepareStatement(SqlQueries.GETALLCONFERENCESCHECKEDOUTFORFACULTY);
				stat.setString(1, facultyInfo.getFacultyId());
			}
			
			List<Conference> conferences = new ArrayList<Conference>();
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					Conference conference = new Conference();
					conference.setConferenceNumber(rs.getString("CONFERENCENUMBER"));
					conference.setTitle(rs.getString("TITLE"));
					conference.setYear(rs.getString("YEAR"));
					conference.setLibraryId(rs.getInt("LIBRARYID"));
					conference.setCategoryId(rs.getInt("CATEGORYID"));
					conference.setResourceId(rs.getInt("RESOURCEID"));
					conference.setCaetgoryName(rs.getString("CATEGORY"));
					conference.setLibraryName(rs.getString("LIBRARYNAME"));
					conference.setConferenceName(rs.getString("CONFERENCENAME"));
					conference.setEcopy(rs.getString("ECOPY"));
					conference.setAuthorNames(getConferenceAuthorsbyConfID(rs.getString("CONFERENCENUMBER")));
					conference.setResourceId(Integer.parseInt(rs.getString("RESOURCEID")));
					conferences.add(conference);
				}
				ConnectionManagerFactory.CloseConnection(conn, stat,rs);
			}
			return conferences;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error getting All Journals for checkout\n");
		}
		return new ArrayList<Conference>();
	}
	
	public static void PerformCheckin(Conference conference,Student studenInfo,Faculty facultyInfo)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			if(studenInfo != null)
			{
				stat = conn.prepareStatement(SqlQueries.CHECKINPUBLICATIONSFORSTUDENT);
				stat.setString(3, studenInfo.getStudentId());
			
			}else
			{
				stat = conn.prepareStatement(SqlQueries.CHECKINPUBLICATIONSFORFACULTY);
				stat.setString(3, facultyInfo.getFacultyId());
			}
			
			stat.setTimestamp(1, new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
			stat.setInt(2, conference.getResourceId());
			System.out.println("Execute Insert \n"+stat.executeUpdate());
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error performing check-in\n");
		}
	}

	public static void updateNumberOfCopies(Conference conference)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.UPDATENUMBEROFCONFERENCECOPIES);
			stat.setString(1,conference.getConferenceNumber());
			System.out.println("Execute update count of conference available copies \n"+stat.executeUpdate());
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error performing check-in and increment count\n");
		}
	}

	public static void deleteFromConferenceCheckout(Conference conference,Student studenInfo,Faculty facultyInfo)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			if(studenInfo != null){
				stat = conn.prepareStatement(SqlQueries.DELETEFROMPUBLICATIONCHECKOUTSTUDENT);
				stat.setString(1,studenInfo.getStudentId());
			}else
			{
				stat = conn.prepareStatement(SqlQueries.DELETEFROMPUBLICATIONCHECKOUTFACULTY);
				stat.setString(1,facultyInfo.getFacultyId());
			}
			stat.setInt(2,conference.getResourceId());
			System.out.println("Delete entry from checkout after book is checked-in \n"+stat.executeUpdate());
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error performing check-in and deleting from checkout\n");
		}
	}
	
	public static void performEpublicationCheckout(Conference conference,Student studentInfo,Faculty facultyInfo)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.CHECKOUT_E_PUBLICATION);
			if(studentInfo != null){
				stat.setString(3,studentInfo.getStudentId());
				stat.setString(4,null);
			}else
			{
				stat.setString(3,null);
				stat.setString(4,facultyInfo.getFacultyId());
			}
			stat.setInt(1,conference.getResourceId());
			stat.setTimestamp(2,new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
			System.out.println("Insert into the E-publicaion\n"+stat.executeUpdate());
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error Inserting into the E-publicaion\n");
		}
	}
}
