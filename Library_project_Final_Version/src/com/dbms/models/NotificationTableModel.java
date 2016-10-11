package com.dbms.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.dbms.connection.ConnectionManagerFactory;
import com.dbms.entity.Faculty;
import com.dbms.entity.Notification;
import com.dbms.entity.Student;
import com.dbms.queries.SqlQueries;

public class NotificationTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4928783421623475233L;
	private List<Notification> notifications;

	public NotificationTableModel(List<Notification> notifications) {
		this.notifications = notifications;
	}

	private final String[] columnNames = new String[] { "REMINDERID", "REMINDERRESOURCETYPE", "REMINDERMESSAGE"};
	private final Class[] columnClass = new Class[] { Integer.class, String.class, String.class, String.class};

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.notifications.size();
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
		Notification row = notifications.get(rowIndex);
		if (0 == columnIndex) {
			return row.getReminderId();
		} else if (1 == columnIndex) {
			return row.getResourceType();
		} else if (2 == columnIndex) {
			return row.getReminderMessage();
		}
		return null;
	}
	
	@Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
	
	public static List<Notification> getAllNotifications(Student studentInfo,Faculty facultyInfo)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			if(facultyInfo != null){
				return getAllFacultyNotifications(facultyInfo);
			}
			stat = conn.prepareStatement(SqlQueries.GETALLREMINDERSFORSTUDENTS);
			stat.setString(1, studentInfo.getStudentId());
			List<Notification> notifications = new ArrayList<Notification>();
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					Notification notification = new Notification();
					notification.setReminderId(rs.getInt("REMINDERID"));
					notification.setStudentNumber(rs.getString("STUDENTNUMBER"));
					notification.setFacultyNumber(rs.getString("FACULTYNUMBER"));
					notification.setResourceType(rs.getString("REMINDERRESOURCETYPE"));
					notification.setReminderMessage(rs.getString("REMINDERMESSAGE"));
					notification.setTimeStamp(rs.getTimestamp("TIMESTAMP"));
					notifications.add(notification);
				}
				ConnectionManagerFactory.CloseConnection(conn, stat,rs);
			}
			return notifications;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error getting All Student Notifications\n");
		}
		return new ArrayList<Notification>();
	}
	
	public static List<Notification> getAllFacultyNotifications(Faculty faculty)
	{
		Connection conn  = ConnectionManagerFactory.createConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(SqlQueries.GETALLREMINDERSFORFACULTY);
			List<Notification> notifications = new ArrayList<Notification>();
			stat.setString(1, faculty.getFacultyId());
			if(stat.execute())
			{
				ResultSet rs = stat.getResultSet();
				while(rs.next())
				{
					Notification notification = new Notification();
					notification.setReminderId(rs.getInt("REMINDERID"));
					notification.setStudentNumber(rs.getString("STUDENTNUMBER"));
					notification.setFacultyNumber(rs.getString("FACULTYNUMBER"));
					notification.setResourceType(rs.getString("REMINDERRESOURCETYPE"));
					notification.setReminderMessage(rs.getString("REMINDERMESSAGE"));
					notification.setTimeStamp(rs.getTimestamp("TIMESTAMP"));
					notifications.add(notification);
				}
				ConnectionManagerFactory.CloseConnection(conn, stat,rs);
			}
			return notifications;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error getting All Faculty Notifications\n");
		}
		return new ArrayList<Notification>();
	}
	
}