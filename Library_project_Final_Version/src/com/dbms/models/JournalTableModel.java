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
import com.dbms.entity.Book;
import com.dbms.entity.Faculty;
import com.dbms.entity.Journal;
import com.dbms.entity.Student;
import com.dbms.queries.SqlQueries;

public class JournalTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4928783421623475233L;
	private List<Journal> journals;

	public JournalTableModel(List<Journal> journals) {
		this.journals = journals;
	}

	private final String[] columnNames = new String[] { "ISSN", "Title", "Year", "LibraryName",
			"CategoryName", "Ecopy" };
	private final Class[] columnClass = new Class[] { String.class, String.class, String.class, String.class,
			String.class, String.class};

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.journals.size();
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
		// TODO Auto-generated method stub
		Journal row = journals.get(rowIndex);
		if (0 == columnIndex) {
			return row.getISSN();
		} else if (1 == columnIndex) {
			return row.getTitle();
		} else if (2 == columnIndex) {
			return row.getYear();
		} else if (3 == columnIndex) {
			return row.getLibraryName();
		} else if (4 == columnIndex) {
			return row.getCategoryName();
		} else if (5 == columnIndex) {
			return row.getEcopy();
		}
		/*else if (6 == columnIndex) {
			return row.getResourceId();
		}*/
		return null;
	}
	
	@Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
	
	public static List<Journal> getALLJournals()
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.GETALLJOURNALS);
			List<Journal> journals = new ArrayList<Journal>();
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					Journal journal = new Journal();
					journal.setISSN(rs.getString("ISSN"));
					journal.setTitle(rs.getString("TITLE"));
					journal.setYear(rs.getString("YEAR"));
					journal.setLibraryName(rs.getString("LIBRARYNAME"));
					journal.setCategoryName(rs.getString("CATEGORY"));
					journal.setEcopy(rs.getString("ECOPY"));
					journal.setResourceId(Integer.parseInt(rs.getString("RESOURCEID")));
					journal.setAuthorNames(JournalTableModel.getBookAuthorsbyISSN(rs.getString("ISSN")));
					journals.add(journal);
				}
				ConnectionManagerFactory.CloseConnection(conn, stat,rs);
			}
			return journals;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error getting All books\n");
		}
		return new ArrayList<Journal>();
	}
	
	public static List<Author> getBookAuthorsbyISSN(String ISSN)
	{
		List<Author> authors = new ArrayList<Author>();
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.GETJOURNALAUTHORS);
			stat.setString(1, ISSN);
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
			System.out.println("Error getting All Journals\n");
		}
		return new ArrayList<Author>();
	}

	public static List<Journal> getAllCheckedoutJournals(Student studentInfo,Faculty facultyInfo)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			if(studentInfo != null){
				stat = conn.prepareStatement(SqlQueries.GETALLJOURNALSCHECKEDOUTFORSTUDENT);
				stat.setString(1, studentInfo.getStudentId());
			}else
			{
				stat = conn.prepareStatement(SqlQueries.GETALLJOURNALSCHECKEDOUTFORFACULTY);
				stat.setString(1, facultyInfo.getFacultyId());
			}
			
			List<Journal> journals = new ArrayList<Journal>();
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					Journal journal = new Journal();
					journal.setISSN(rs.getString("ISSN"));
					journal.setTitle(rs.getString("TITLE"));
					journal.setYear(rs.getString("YEAR"));
					journal.setLibraryName(rs.getString("LIBRARYNAME"));
					journal.setCategoryName(rs.getString("CATEGORY"));
					journal.setEcopy(rs.getString("ECOPY"));
					journal.setResourceId(Integer.parseInt(rs.getString("RESOURCEID")));
					journal.setAuthorNames(JournalTableModel.getBookAuthorsbyISSN(rs.getString("ISSN")));
					journal.setResourceId(Integer.parseInt(rs.getString("RESOURCEID")));
					journals.add(journal);
					
				}
				ConnectionManagerFactory.CloseConnection(conn, stat,rs);
			}
			return journals;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error getting All Journals for checkout\n");
		}
		return new ArrayList<Journal>();
	}
	
	public static void PerformCheckin(Journal journal,Student studenInfo,Faculty facultyInfo)
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
			stat.setInt(2, journal.getResourceId());
			System.out.println("Execute Insert \n"+stat.executeUpdate());
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error performing check-in\n");
		}
	}

	public static void updateNumberOfCopies(Journal journal)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.UPDATENUMBEROFJOURNALCOPIES);
			stat.setString(1,journal.getISSN());
			System.out.println("Execute update count of journal available copies \n"+stat.executeUpdate());
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error performing check-in and increment count\n");
		}
	}

	public static void deleteFromJournalCheckout(Journal journal,Student studenInfo,Faculty facultyInfo)
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
			stat.setInt(2,journal.getResourceId());
			System.out.println("Delete entry from checkout after book is checked-in \n"+stat.executeUpdate());
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error performing check-in and deleting from checkout\n");
		}
	}
	
	public static void performEpublicationCheckout(Journal journal,Student studentInfo,Faculty facultyInfo)
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
			stat.setInt(1,journal.getResourceId());
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
