package com.dbms.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.dbms.entity.Faculty;
import com.dbms.entity.Room;
import com.dbms.entity.RoomReservation;
import com.dbms.entity.Student;
import com.dbms.models.RoomTableModel;

public class RoomListModule extends JFrame{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 5617087489020572327L;
	private List<Room> rooms;
	private Student studentInfo;
	private Faculty facultyInfo;
	private RoomReservation reservation;
	private static JPanel middle;
	private RoomTableModel dataModel;
	   
	   public RoomListModule(List<Room> rooms,Student studentInfo,Faculty facultyInfo,RoomReservation reservation)
	   {
		   setTitle("ROOMS");
		   this.setSize(1200, 600);
		   middle=new JPanel();
		   middle.setVisible(true);
			
		   middle.setLayout(new GridLayout(1,0));
		   this.add(middle);
		   
		   this.getContentPane().add(BorderLayout.CENTER,middle);
		   this.setVisible(true);
		   this.setLocationRelativeTo(null); 
		   
		   this.studentInfo=studentInfo;
		   this.facultyInfo=facultyInfo;
		   this.reservation=reservation;
		   this.rooms = rooms;
		   JTable table = new JTable(new RoomTableModel(this.rooms));
		   table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		   table.setColumnSelectionAllowed(false);
		   table.setRowSelectionAllowed(true);
		   table.getSelectionModel().addListSelectionListener(
				   new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						// TODO Auto-generated method stub
						if (!e.getValueIsAdjusting()) {
							int selectIdx = table.getSelectedRow();
							Room tempRoom = RoomListModule.this.rooms.get(selectIdx);
							if(tempRoom.getType().contains("Conference") && studentInfo != null)
							{
								JOptionPane.showMessageDialog(null, "Students Cannot Book Conference Rooms");
								return;
							}
							new RoomDetailModule(reservation,RoomListModule.this.rooms.get(selectIdx));
							RoomListModule.this.setVisible(false);
							RoomListModule.this.dispose();
		                }
					}

		   });
		   //Create the scroll pane and add the table to it.
		   JScrollPane scrollPane = new JScrollPane(table);
		   this.setSize(1200, 600);
		   //Add the scroll pane to this panel.
		   middle.add(scrollPane);
		   
	   }
}