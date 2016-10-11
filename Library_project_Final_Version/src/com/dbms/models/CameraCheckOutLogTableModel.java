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
import com.dbms.entity.CameraCheckoutLog;
import com.dbms.entity.Faculty;
import com.dbms.entity.Student;
import com.dbms.queries.SqlQueries;

public class CameraCheckOutLogTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9012968124952674277L;
	/**
	 * 
	 */
	private List<CameraCheckoutLog> checkedOutLogCameras;

	public CameraCheckOutLogTableModel(List<CameraCheckoutLog> checkedOutLogCameras) {
		this.checkedOutLogCameras = checkedOutLogCameras;
	}

	private final String[] columnNames = new String[] { "ReservationId", "CameraId", "CheckinTime", "CheckoutTime", "ReservedUpto", 
			"Fine in $", "Manufacturer", "Model", "Configuration"};
	private final Class[] columnClass = new Class[] { Integer.class, String.class ,Date.class, Date.class, Date.class,
			Integer.class, String.class, String.class,String.class};

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.checkedOutLogCameras.size();
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
		CameraCheckoutLog row = this.checkedOutLogCameras.get(rowIndex);
		if (0 == columnIndex) {
			return row.getReservationId();
		} else if (1 == columnIndex) {
			return row.getCameraId();
		} else if (2 == columnIndex) {
			return row.getCheckInTime();
		} else if (3 == columnIndex) {
			return row.getCheckOutTime();
		} else if (4 == columnIndex) {
			return row.getEndTime();
		} else if (5 == columnIndex) {
			return row.getFine();
		} else if (6 == columnIndex) {
			return row.getManufacturer();
		} else if (7 == columnIndex) {
			return row.getModel();
		} else if (8 == columnIndex) {
			return row.getConfiguration();
		}
		return null;
	}
	
	@Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
	
	
	public static List<CameraCheckoutLog> getAllFineForCameras(Student studentInfo,Faculty facultyInfo)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			if(studentInfo != null){
				stat = conn.prepareStatement(SqlQueries.GETALLCAMERAFINEFROMCHECKOUTLOGSTUDENT);
				stat.setString(1, studentInfo.getStudentId());
			}
			else{
				stat = conn.prepareStatement(SqlQueries.GETALLCAMERAFINEFROMCHECKOUTLOGFACULTY);
				stat.setString(1, facultyInfo.getFacultyId());
			}
			List<CameraCheckoutLog> cameras = new ArrayList<CameraCheckoutLog>();
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					CameraCheckoutLog camera = new CameraCheckoutLog();
					camera.setManufacturer(rs.getString("MANUFACTURER"));
					camera.setModel(rs.getString("MODEL"));
					camera.setConfiguration(rs.getString("CONFIGURATION"));
					camera.setCameraId(rs.getString("CAMERAID"));
					camera.setCheckInTime(rs.getDate("CHECKINTIME"));
					camera.setCheckOutTime(rs.getDate("CHECKOUTTIME"));
					camera.setEndTime(rs.getDate("ENDTIME"));
					camera.setStudentNumber(rs.getString("STUDENTNUMBER"));
					camera.setFacultyNumber(rs.getString("FACULTYNUMBER"));
					camera.setFine(rs.getInt("FINE"));
					camera.setReservationId(rs.getInt("RESERVATIONID"));
					cameras.add(camera);
				}
				ConnectionManagerFactory.CloseConnection(conn, stat,rs);
			}
			return cameras;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error getting All Cameras from Camera checkout Log\n");
		}
		return new ArrayList<CameraCheckoutLog>();
	}
	
	public static void updateFineOfCamerasForStudents(Student studentInfo,Faculty facultyInfo)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			if(studentInfo != null){
				stat = conn.prepareStatement(SqlQueries.UPDATEALLCAMERAFINEFROMCHECKOUTLOGSTUDENT);
				stat.setString(1,studentInfo.getStudentId());
			}else
			{
				stat = conn.prepareStatement(SqlQueries.UPDATEALLCAMERAFINEFROMCHECKOUTLOGFACULTY);
				stat.setString(1,facultyInfo.getFacultyId());
			}
			System.out.println("Execute update fine of cameras \n"+stat.executeUpdate());
			ConnectionManagerFactory.CloseConnection(conn, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error clearing fine for cameras\n");
		}
	}

}
