package com.dbms.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.dbms.connection.ConnectionManagerFactory;
import com.dbms.entity.Faculty;
import com.dbms.entity.Room;
import com.dbms.entity.RoomReservation;
import com.dbms.entity.Student;
import com.dbms.queries.SqlQueries;

public class RoomTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8801654651359610902L;
	private List<Room> rooms;

	public RoomTableModel(List<Room> rooms) {
		this.rooms = rooms;
	}

	private final String[] columnNames = new String[] { "RoomId", "Room Number", "Library Name", "Caetgory Name",
			"Floor Number", "Capacity", "Type" };
	private final Class[] columnClass = new Class[] { Integer.class, Integer.class, String.class, String.class,
			Integer.class, Integer.class, String.class };

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.rooms.size();
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
		Room row = rooms.get(rowIndex);
		if (0 == columnIndex) {
			return row.getRoomId();
		} else if (1 == columnIndex) {
			return row.getRoomNumber();
		} else if (2 == columnIndex) {
			return row.getLibraryName();
		} else if (3 == columnIndex) {
			return row.getCategoryName();
		} else if (4 == columnIndex) {
			return row.getFloorNo();
		} else if (5 == columnIndex) {
			return row.getCapacity();
		} else if (6 == columnIndex) {
			return row.getType();
		}
		return null;
	}
	
	@Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
	
	public static List<Room> getALLRooms()
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.GETALLROOMS);
			List<Room> rooms = new ArrayList<Room>();
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					Room room = new Room();
					room.setRoomId(rs.getInt("ROOMID"));
					room.setRoomNumber(rs.getString("ROOMNUMBER"));
					room.setLibraryId(rs.getInt("LIBRARYID"));
					room.setCategoryId(rs.getInt("CATEGORYID"));
					room.setLibraryName(rs.getString("LIBRARYNAME"));
					room.setCategoryName(rs.getString("CATEGORY"));
					room.setFloorNo(rs.getInt("FLOORNO"));
					room.setCapacity(rs.getInt("CAPACITY"));
					room.setType(rs.getString("TYPE"));
					rooms.add(room);
				}
				ConnectionManagerFactory.CloseConnection(conn, stat,rs);
			}
			return rooms;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error getting All Rooms\n");
		}
		return new ArrayList<Room>();
	}
	
	public static List<Room> queryRooms(Integer capacity,String libName, Date startTime, Date endTime)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.SEARCHROOMS);
			stat.setInt(1, capacity);
			stat.setString(2, libName);
			stat.setTimestamp(3, new java.sql.Timestamp(startTime.getTime()));
			System.out.println("Print sql STartDate "+new java.sql.Timestamp(startTime.getTime()));
			System.out.println("Print sql EndDate "+new java.sql.Timestamp(endTime.getTime()));
			stat.setTimestamp(4, new java.sql.Timestamp(startTime.getTime()));
			stat.setTimestamp(5, new java.sql.Timestamp(endTime.getTime()));
			stat.setTimestamp(6, new java.sql.Timestamp(endTime.getTime()));
			stat.setTimestamp(7, new java.sql.Timestamp(startTime.getTime()));
			stat.setTimestamp(8, new java.sql.Timestamp(endTime.getTime()));
			//System.out.println(" The prepared statement is "+stat.to+"\n");
			List<Room> rooms = new ArrayList<Room>();
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					Room room = new Room();
					room.setRoomId(rs.getInt("ROOMID"));
					room.setRoomNumber(rs.getString("ROOMNUMBER"));
					room.setLibraryId(rs.getInt("LIBRARYID"));
					room.setCategoryId(rs.getInt("CATEGORYID"));
					room.setLibraryName(rs.getString("LIBRARYNAME"));
					room.setCategoryName(rs.getString("CATEGORY"));
					room.setFloorNo(rs.getInt("FLOORNO"));
					room.setCapacity(rs.getInt("CAPACITY"));
					room.setType(rs.getString("TYPE"));
					rooms.add(room);
				}
				ConnectionManagerFactory.CloseConnection(conn, stat,rs);
			}
			return rooms;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error Searching All Rooms\n");
		}
		return new ArrayList<Room>();
	}
	
	public static void MakeReservation(RoomReservation reservation)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.MAKEROOMRESERVATION);
			stat.setInt(1,getNewRoomReservationId());
			stat.setInt(2, reservation.getRoomId());
			stat.setTimestamp(3, new java.sql.Timestamp(reservation.getStartTime().getTime()));
			System.out.println("Print sql StartDate "+new java.sql.Timestamp(reservation.getStartTime().getTime()));
			System.out.println("Print sql EndDate "+new java.sql.Timestamp(reservation.getEndTime().getTime()));
			stat.setTimestamp(4, new java.sql.Timestamp(reservation.getEndTime().getTime()));
			stat.setString(5,reservation.getStudentNumber());
			stat.setString(6,reservation.getFacultyNumber());
			//System.out.println(" The prepared statement is "+stat.to+"\n");
			System.out.println("Execute Insert \n"+stat.executeUpdate());
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error Inserting Reservation\n");
		}
	}
	
	public static int getNewRoomReservationId()
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		int reservationID = -1;
		try {
			stat = conn.prepareStatement(SqlQueries.GETROOMRESERVATIONID);
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					reservationID = rs.getInt("NEWID");
				}
				ConnectionManagerFactory.CloseConnection(conn, stat,rs);
			}
			return reservationID;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error Inserting Reservation\n");
		}
		return reservationID;
	}

	public static List<Room> getReservedRooms(Student studentInfo,Faculty facultyInfo)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			if(studentInfo != null){
				stat = conn.prepareStatement(SqlQueries.GETRESERVEDROOMSBYSTUDENT);
				stat.setString(1,studentInfo.getStudentId());
			}
			else{
				stat = conn.prepareStatement(SqlQueries.GETRESERVEDROOMSBYFACULTY);
				stat.setString(1,facultyInfo.getFacultyId());
			}
			
			List<Room> rooms = new ArrayList<Room>();
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					Room room = new Room();
					room.setRoomId(rs.getInt("ROOMID"));
					room.setRoomNumber(rs.getString("ROOMNUMBER"));
					room.setLibraryId(rs.getInt("LIBRARYID"));
					room.setCategoryId(rs.getInt("CATEGORYID"));
					room.setLibraryName(rs.getString("LIBRARYNAME"));
					room.setCategoryName(rs.getString("CATEGORY"));
					room.setFloorNo(rs.getInt("FLOORNO"));
					room.setCapacity(rs.getInt("CAPACITY"));
					room.setType(rs.getString("TYPE"));
					room.setReservationId(rs.getInt("RESERVATIONID"));
					room.setReservationStartTime(rs.getTimestamp("STARTTIME"));
					rooms.add(room);
				}
				ConnectionManagerFactory.CloseConnection(conn, stat,rs);
			}
			return rooms;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error getting All Rooms\n");
		}
		return new ArrayList<Room>();
	}
	
	public static void PerformCheckout(Room room)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.CHECKOUTROOM);
			stat.setInt(1, room.getReservationId());
			stat.setTimestamp(2, null);
			stat.setTimestamp(3, new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
			System.out.println("Execute Insert \n"+stat.executeUpdate());
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error checkingout Reservation\n");
		}
	}
	
	public static void PerformCheckin(Room room)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.CHECKINROOM);
			stat.setTimestamp(1, new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
			stat.setInt(2, room.getReservationId());
			System.out.println("Execute Insert \n"+stat.executeUpdate());
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error performing check-in\n");
		}
	}
	
	public static List<Room> getAllCheckedoutRooms(Student studentInfo,Faculty facultyInfo)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			if(studentInfo != null)
			{
				stat = conn.prepareStatement(SqlQueries.GETALLCHECKOUTROOMSBYSTUDENTID);
				stat.setString(1,studentInfo.getStudentId());
			}else
			{
				stat = conn.prepareStatement(SqlQueries.GETALLCHECKOUTROOMSBYFACULTYID);
				stat.setString(1,facultyInfo.getFacultyId());
			}
			List<Room> rooms = new ArrayList<Room>();
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					Room room = new Room();
					room.setRoomId(rs.getInt("ROOMID"));
					room.setRoomNumber(rs.getString("ROOMNUMBER"));
					room.setLibraryId(rs.getInt("LIBRARYID"));
					room.setCategoryId(rs.getInt("CATEGORYID"));
					room.setLibraryName(rs.getString("LIBRARYNAME"));
					room.setCategoryName(rs.getString("CATEGORY"));
					room.setFloorNo(rs.getInt("FLOORNO"));
					room.setCapacity(rs.getInt("CAPACITY"));
					room.setType(rs.getString("TYPE"));
					room.setReservationId(rs.getInt("RESERVATIONID"));
					rooms.add(room);
				}
				ConnectionManagerFactory.CloseConnection(conn, stat,rs);
			}
			return rooms;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error getting All Rooms\n");
		}
		return new ArrayList<Room>();
	}
	
	public static void deleteFromRoomCheckoutAndReservation(Room room,Student studentInfo,Faculty facultyInfo)
	{

		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.DELETEFROMROOMCHECKOUT);
			stat.setInt(1,room.getReservationId());
			System.out.println("Delete entry from Room-Checkout after room is checked-in \n"+stat.executeUpdate());
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error performing check-in and deleting from Room-Checkout\n");
		}
		
		conn  = ConnectionManagerFactory.createConnection();
		try {
			if(studentInfo != null){
				stat = conn.prepareStatement(SqlQueries.DELETEFROMROOMRESERVESTUDENT);
				stat.setString(1,studentInfo.getStudentId());
			}else
			{
				stat = conn.prepareStatement(SqlQueries.DELETEFROMROOMRESERVEFACULTY);
				stat.setString(1,facultyInfo.getFacultyId());
			}
			stat.setInt(2,room.getReservationId());
			System.out.println("Delete entry from Room-Checkout after room is checked-in \n"+stat.executeUpdate());
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error performing check-in and deleting from Room-reservation\n");
		}
	}
			
}