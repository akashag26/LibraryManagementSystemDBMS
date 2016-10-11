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
import com.dbms.entity.Student;
import com.dbms.queries.SqlQueries;

public class BookTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7514199055761133227L;
	private List<Book> books;

	public BookTableModel(List<Book> books) {
		this.books = books;
	}

	private final String[] columnNames = new String[]
			{"ISBN","Title","Edition","Author Names","Year Of Publication",
					"Publisher","Category","Library","ResourceId"};
	
	
	
	private final Class[] columnClass = new Class[]
			{ String.class, String.class, String.class, 
			  String.class,//List of Authors
			  String.class, String.class,
			  String.class,String.class,String.class};

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.books.size();
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
		StringBuffer buffer = new StringBuffer();
		Book row = books.get(rowIndex);
		if (0 == columnIndex) {
			return row.getISBN();
		}else if (1 == columnIndex) {
			return row.getTitle();
		} else if (2 == columnIndex) {
			return row.getEdition();
		} else if (3 == columnIndex) {
			for (Author name : row.getAuthorNames())
				buffer.append(name.getAuthorName() + ",");
			if(buffer.toString() != null && buffer.toString().length() > 1)
				return buffer.toString().substring(0, buffer.toString().length()-1);
			else
				return buffer.toString();
		} else if (4 == columnIndex) {
			return row.getYearOfPublication();
		} else if (5 == columnIndex) {
			return row.getPublisher();
		} else if (7 == columnIndex) {
			return row.getLibrary();
		} else if (6 == columnIndex) {
			return row.getCategory();
		}
		else if (8 == columnIndex) {
			return row.getResourceID();
		}
		return null;
	}
	
	@Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
	
	public static List<Book> getAllBooks()
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.GETALLBOOKS);
			List<Book> books = new ArrayList<Book>();
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					
					Book book = new Book();
					book.setISBN(rs.getString("ISBN"));
					book.setTitle(rs.getString("TITLE"));
					book.setEdition(rs.getString("EDITION"));
					book.setYearOfPublication(rs.getString("YEAR"));
					book.setPublisher(rs.getString("PUBLISHER"));
					book.setLibId(rs.getInt("LIBRARYID"));
					book.setCategoryId(rs.getInt("CATEGORYID"));
					book.setLibrary(rs.getString("LIBRARYNAME"));
					book.setCategory(rs.getString("CATEGORY"));
					book.setAuthorNames(getBookAuthorsbyISBN(rs.getString("ISBN")));
					book.setResourceID(Integer.parseInt(rs.getString("RESOURCEID")));
					book.setEcopy(rs.getString("ECOPY"));
				    //System.out.println("ResourceID"+rs.getString(10));	
					
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
		return new ArrayList<Book>();
	}
	
	public static List<Author> getBookAuthorsbyISBN(String ISBN)
	{
		List<Author> authors = new ArrayList<Author>();
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.GETBOOKAUTHORS);
			stat.setString(1, ISBN);
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
			System.out.println("Error getting All books\n");
		}
		return new ArrayList<Author>();
	}
	
	public static void PerformCheckin(Book book,Student studenInfo,Faculty facultyInfo)
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
			stat.setInt(2, book.getResourceID());
			System.out.println("Execute Insert \n"+stat.executeUpdate());
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error performing check-in\n");
		}
	}
	
	public static void updateNumberOfCopies(Book book)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.UPDATENUMBEROFBOOKCOPIES);
			stat.setString(1,book.getISBN());
			System.out.println("Execute update count of book available copies \n"+stat.executeUpdate());
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error performing check-in and increment count\n");
		}
	}
	
	public static List<Book> getAllCheckoutBooks(Student studentInfo,Faculty facultyInfo)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			if(studentInfo != null){
				stat = conn.prepareStatement(SqlQueries.GETALLCHECKOUTBOOKSBYSTUDENTID);
				stat.setString(1, studentInfo.getStudentId());
			}else
			{
				stat = conn.prepareStatement(SqlQueries.GETALLCHECKOUTBOOKSBYFACULTYID);
				stat.setString(1, facultyInfo.getFacultyId());
			}
			
			List<Book> books = new ArrayList<Book>();
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					Book book = new Book();
					book.setISBN(rs.getString("ISBN"));
					book.setTitle(rs.getString("TITLE"));
					book.setEdition(rs.getString("EDITION"));
					book.setYearOfPublication(rs.getString("YEAR"));
					book.setPublisher(rs.getString("PUBLISHER"));
					book.setLibId(rs.getInt("LIBRARYID"));
					book.setCategoryId(rs.getInt("CATEGORYID"));
					book.setLibrary(rs.getString("LIBRARYNAME"));
					book.setCategory(rs.getString("CATEGORY"));
					book.setAuthorNames(getBookAuthorsbyISBN(rs.getString("ISBN")));
					book.setResourceID(Integer.parseInt(rs.getString("RESOURCEID")));
				    //System.out.println("ResourceID"+rs.getString(10));	
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
		return new ArrayList<Book>();
	}

	public static void deleteFromBookCheckout(Book book,Student studenInfo,Faculty facultyInfo)
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
			stat.setInt(2,book.getResourceID());
			System.out.println("Delete entry from checkout after book is checked-in \n"+stat.executeUpdate());
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error performing check-in and deleting from checkout\n");
		}
	}
	
	public static void performEpublicationCheckout(Book book,Student studentInfo,Faculty facultyInfo)
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
			stat.setInt(1,book.getResourceID());
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
