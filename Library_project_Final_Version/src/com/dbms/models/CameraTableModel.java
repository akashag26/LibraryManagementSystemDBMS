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
import com.dbms.entity.Camera;
import com.dbms.entity.Faculty;
import com.dbms.entity.Student;
import com.dbms.queries.SqlQueries;

public class CameraTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8657294170903557077L;
	private List<Camera> cameras;

	public CameraTableModel(List<Camera> cameras) {
		this.cameras = cameras;
	}

	private final String[] columnNames = new String[] { "CameraId", "LibraryId", "CategoryId", "Manufacturer",
			"Model", "Configuration", "MemoryAvailable" };
	private final Class[] columnClass = new Class[] { Integer.class, Integer.class, Integer.class, String.class,
			String.class, String.class, String.class };

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.cameras.size();
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
		Camera row = cameras.get(rowIndex);
		if (0 == columnIndex) {
			return row.getCameraId();
		} else if (1 == columnIndex) {
			return row.getLibraryId();
		} else if (2 == columnIndex) {
			return row.getCategoryId();
		} else if (3 == columnIndex) {
			return row.getManufacturer();
		} else if (4 == columnIndex) {
			return row.getModel();
		} else if (5 == columnIndex) {
			return row.getConfiguration();
		} else if (6 == columnIndex) {
			return row.getMemoryAvailable();
		}
		return null;
	}
	
	@Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
	
	public static List<Camera> getALLCameras()
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.GETCAMERAS);
			List<Camera> cameras = new ArrayList<Camera>();
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					Camera camera = new Camera();
					camera.setCameraId(rs.getString("CAMERAID"));
					camera.setLibraryId(rs.getInt("LIBRARYID"));
					camera.setCategoryId(rs.getInt("CATEGORYID"));
					camera.setManufacturer(rs.getString("MANUFACTURER"));
					camera.setModel(rs.getString("MODEL"));
					camera.setConfiguration(rs.getString("CONFIGURATION"));
					camera.setMemoryAvailable(rs.getString("MEMORYAVAILABLE"));
					cameras.add(camera);
				}
				ConnectionManagerFactory.CloseConnection(conn, stat,rs);
			}
			return cameras;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error getting All Cameras\n");
		}
		return new ArrayList<Camera>();
	}
	
	public static List<Camera> getCamerasReserved(Student studentInfo,Faculty facultyInfo)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			if(studentInfo != null){
				stat = conn.prepareStatement(SqlQueries.GETCAMERASRESERVEDBYSTUDENT);
				stat.setString(1, studentInfo.getStudentId());
			}
			else{
				stat = conn.prepareStatement(SqlQueries.GETCAMERASRESERVEDBYFACULTY);
				stat.setString(1, facultyInfo.getFacultyId());
			}
			
			List<Camera> cameras = new ArrayList<Camera>();
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					Camera camera = new Camera();
					camera.setCameraId(rs.getString("CAMERAID"));
					camera.setLibraryId(rs.getInt("LIBRARYID"));
					camera.setCategoryId(rs.getInt("CATEGORYID"));
					camera.setManufacturer(rs.getString("MANUFACTURER"));
					camera.setModel(rs.getString("MODEL"));
					camera.setConfiguration(rs.getString("CONFIGURATION"));
					camera.setMemoryAvailable(rs.getString("MEMORYAVAILABLE"));
					camera.setReservationId(rs.getInt("RESERVATIONID"));
					camera.setReservationStartTime(rs.getTimestamp("STARTTIME"));
					cameras.add(camera);
				}
				ConnectionManagerFactory.CloseConnection(conn, stat,rs);
			}
			return cameras;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error getting All Cameras\n");
		}
		return new ArrayList<Camera>();
	}
	
	public static void PerformCheckout(Camera camera)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.CHECKOUTCAMERA);
			stat.setInt(1, camera.getReservationId());
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
	
	public static void PerformCheckin(Camera camera)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.CHECKINCAMERA);
			stat.setTimestamp(1, new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
			stat.setInt(2, camera.getReservationId());
			System.out.println("Execute Insert \n"+stat.executeUpdate());
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error performing check-in\n");
		}
	}
	
	public static List<Camera> getAllCamerasCheckedOut(Student studentInfo,Faculty facultyInfo)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			if(studentInfo != null){
				stat = conn.prepareStatement(SqlQueries.GETALLCHECKOUTCAMERASBYSTUDENTID);
				stat.setString(1, studentInfo.getStudentId());
			}else
			{
				stat = conn.prepareStatement(SqlQueries.GETALLCHECKOUTCAMERASBYFACULTYID);
				stat.setString(1, facultyInfo.getFacultyId());
			}
			List<Camera> cameras = new ArrayList<Camera>();
			
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					Camera camera = new Camera();
					camera.setCameraId(rs.getString("CAMERAID"));
					camera.setLibraryId(rs.getInt("LIBRARYID"));
					camera.setCategoryId(rs.getInt("CATEGORYID"));
					camera.setManufacturer(rs.getString("MANUFACTURER"));
					camera.setModel(rs.getString("MODEL"));
					camera.setConfiguration(rs.getString("CONFIGURATION"));
					camera.setMemoryAvailable(rs.getString("MEMORYAVAILABLE"));
					camera.setReservationId(rs.getInt("RESERVATIONID"));
					cameras.add(camera);
				}
				ConnectionManagerFactory.CloseConnection(conn, stat,rs);
			}
			return cameras;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error getting All Cameras\n");
		}
		return new ArrayList<Camera>();
	}
	
	public static void deleteFromCameraCheckoutAndReservation(Camera camera,Student studenInfo,Faculty facultyInfo)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.DELETEFROMCAMERACHECKOUT);
			stat.setInt(1,camera.getReservationId());
			System.out.println("Delete entry from Camera-Checkout after camera is checked-in \n"+stat.executeUpdate());
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error performing check-in and deleting from Camera-Checkout\n");
		}
		
		conn  = ConnectionManagerFactory.createConnection();
		try {
			if(studenInfo != null){
				stat = conn.prepareStatement(SqlQueries.DELETEFROMCAMERARESERVESTUDENT);
				stat.setString(1,studenInfo.getStudentId());
				stat.setInt(2,camera.getReservationId());
			}else
			{
				stat = conn.prepareStatement(SqlQueries.DELETEFROMCAMERARESERVEFACULTY);
				stat.setString(1,facultyInfo.getFacultyId());
				stat.setInt(2,camera.getReservationId());
			}
			System.out.println("Delete entry from Camera-Checkout after camera is checked-in \n"+stat.executeUpdate());
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error performing check-in and deleting from Camera-Reservation\n");
		}
	}

	public static int getNewCameraReservationId()
	{
		Connection conn = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		int reservationID = -1;
		try {
			stat = conn.prepareStatement(SqlQueries.GETCAMERARESERVATIONID);
			if (stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while (rs.next())
				{
					reservationID = rs.getInt("NEWID");
				}
				ConnectionManagerFactory.CloseConnection(conn, stat, rs);
			}
			return reservationID;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error Inserting Reservation\n");
		}
	return reservationID;
	}
}
