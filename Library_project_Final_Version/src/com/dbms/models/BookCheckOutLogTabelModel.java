package com.dbms.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.dbms.connection.ConnectionManagerFactory;
import com.dbms.entity.CheckoutLog;
import com.dbms.entity.Faculty;
import com.dbms.entity.Student;
import com.dbms.queries.SqlQueries;

public class BookCheckOutLogTabelModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 828702133377368683L;
	
	private List<CheckoutLog> checkedOutBooks;

	public BookCheckOutLogTabelModel(List<CheckoutLog> checkedOutBooks) {
		this.checkedOutBooks = checkedOutBooks;
	}

	private final String[] columnNames = new String[] { "ResourceId", "CheckoutTime", "ReservedUpto", "CheckinTime",
			"Fine in $", "ResourceType", "ResourceTitle"};
	private final Class[] columnClass = new Class[] { Integer.class, Date.class, Date.class, Date.class,
			Integer.class, String.class, String.class};

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.checkedOutBooks.size();
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
		CheckoutLog row = this.checkedOutBooks.get(rowIndex);
		if (0 == columnIndex) {
			return row.getResourceId();
		} else if (1 == columnIndex) {
			return row.getCheckOutTime();
		} else if (2 == columnIndex) {
			return row.getReservedUpto();
		} else if (3 == columnIndex) {
			return row.getCheckInTime();
		} else if (4 == columnIndex) {
			return row.getFine();
		} else if (5 == columnIndex) {
			return row.getType();
		} else if (6 == columnIndex) {
			return row.getTitle();
		}
		return null;
	}
	
	@Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

	public static void updateFineOfBooksForStudents(Student studentInfo,Faculty facultyInfo)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			if(studentInfo != null){
				stat = conn.prepareStatement(SqlQueries.UPDATEALLBOOKFINEFROMCHECKOUTLOGSTUDENT);
				stat.setString(1,studentInfo.getStudentId());
				PreparedStatement stat1=conn.prepareStatement(SqlQueries.UPDATESTUDENTHOLD);
				stat1.setString(1,studentInfo.getStudentId());
				stat1.executeUpdate();
			}else
			{
				stat = conn.prepareStatement(SqlQueries.UPDATEALLBOOKFINEFROMCHECKOUTLOGFACUTY);
				stat.setString(1,facultyInfo.getFacultyId());
			}
			System.out.println("Execute update fine of books \n"+stat.executeUpdate());
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error clearing fine of Books\n");
		}
	}
	
	
	public static List<CheckoutLog> getAllFineForBooks(Student studentInfo,Faculty facultyInfo)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			if(studentInfo != null){
				stat = conn.prepareStatement(SqlQueries.GETALLBOOKFINEFROMCHECKOUTLOGSTUDENT);
				stat.setString(1, studentInfo.getStudentId());
			}else
			{
				stat = conn.prepareStatement(SqlQueries.GETALLBOOKFINEFROMCHECKOUTLOGSFACULTY);
				stat.setString(1, facultyInfo.getFacultyId());
			}
			List<CheckoutLog> books = new ArrayList<CheckoutLog>();
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					CheckoutLog book = new CheckoutLog();
					book.setResourceId(rs.getInt("RESOURCEID"));
					book.setCheckOutTime(rs.getDate("CHECKOUTTIME"));
					book.setReservedUpto(rs.getDate("RESERVEDUPTO"));
					book.setCheckInTime(rs.getDate("CHECKINTIME"));
					book.setStudentNumber(rs.getString("STUDENTNUMBER"));
					book.setFacultyNumber(rs.getString("FACULTYNUMBER"));
					book.setFine(rs.getInt("FINE"));
					getTypeAndTitle(book);
					books.add(book);
				}
				ConnectionManagerFactory.CloseConnection(conn, stat,rs);
			}
			return books;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error getting All books\n");
		}
		return new ArrayList<CheckoutLog>();
	}
	
	public static void getTypeAndTitle(CheckoutLog book)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.GETTILEANDTYPEFROMBOOKBYRESOURCEID);
			stat.setInt(1,book.getResourceId());
			System.out.println("Find title and type form book \n");
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					book.setType("BOOK");
					book.setTitle(rs.getString("TITLE"));
					return;
				}
			}
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error clearing fine of Books\n");
		}
	
		conn  = ConnectionManagerFactory.createConnection();
		try {
			stat = conn.prepareStatement(SqlQueries.GETTILEANDTYPEFROMCONFERENCEBYRESOURCEID);
			stat.setInt(1,book.getResourceId());
			System.out.println("Find title and type form Conference \n");
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					book.setType("CONFERENCE");
					book.setTitle(rs.getString("TITLE"));
					return;
				}
			}
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error clearing fine of Books\n");
		}
		
		conn  = ConnectionManagerFactory.createConnection();
		try {
			stat = conn.prepareStatement(SqlQueries.GETTILEANDTYPEFROMJOURNALBYRESOURCEID);
			stat.setInt(1,book.getResourceId());
			System.out.println("Find title and type form Journal \n");
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					book.setType("JOURNAL");
					book.setTitle(rs.getString("TITLE"));
					return;
				}
			}
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error finding title/type of Books for checkoutlog\n");
		}
		
	}
	
	
}
