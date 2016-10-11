package com.dbms.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.dbms.entity.Camera;
import com.dbms.entity.Faculty;
import com.dbms.entity.Room;
import com.dbms.entity.Student;
import com.dbms.models.CameraTableModel;
import com.dbms.models.RoomTableModel;

public class ReserevedResourcesListModule extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7245855641575353925L;
	private List<Camera> cameras;
	private List<Room> rooms;
	private Student studentInfo;
	private Faculty facultyInfo;
	private static JPanel middle2;
	private static JPanel middle3;
	private static JLabel l2;
	private static JLabel l3;
	private static JButton checkoutRoom;
	private static JButton checkoutCamera;
	private static JPanel bottom;
	   
	   public ReserevedResourcesListModule(List<Room> rooms,List<Camera> cameras,Student studentInfo,Faculty facultyInfo)
	   {
		   setTitle("RESERVED RESOURCES");
		   this.setSize(1200, 600);

		   bottom=new JPanel();
		   checkoutRoom=new JButton();
		   checkoutRoom.setText("Checkout Room");
		   checkoutCamera=new JButton();
		   checkoutCamera.setText("Checkout Camera");
		   
		   middle2=new JPanel();
		   middle2.setVisible(true);
		   middle2.setLayout(new GridLayout(2,0));
		   
		
		   middle3=new JPanel();
		   middle3.setVisible(true);
		   middle3.setLayout(new GridLayout(2,0));
		
		   this.studentInfo = studentInfo;
		   this.facultyInfo = facultyInfo;
		   this.rooms = rooms;
		   this.cameras = cameras;
		   
		   checkoutRoom.setVisible(true);
		   checkoutCamera.setVisible(true);
		   
		   bottom.add(checkoutRoom);
		   bottom.add(checkoutCamera);
		   
		   //this.getContentPane().add(BorderLayout.NORTH,middle1);
		   this.getContentPane().add(BorderLayout.NORTH,middle2);
		   this.getContentPane().add(BorderLayout.CENTER,bottom);
		   this.getContentPane().add(BorderLayout.SOUTH,middle3);
		   this.setVisible(true);
		   this.setLocationRelativeTo(null); 
		   
		   JTable roomTable = new JTable(new RoomTableModel(this.rooms));
		   roomTable.setPreferredScrollableViewportSize(new Dimension(500, 100));
		   roomTable.setColumnSelectionAllowed(false);
		   roomTable.setRowSelectionAllowed(true);
		   checkoutRoom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectIdx = roomTable.getSelectedRow();
				if(selectIdx < 0) return;
				//JOptionPane.showMessageDialog(BookListModule.this, BookListModule.this.books.get(selectIdx).getTitle());
				Room tempRoom =ReserevedResourcesListModule.this.rooms.get(selectIdx);
				System.out.println(tempRoom.getRoomId()+" "+tempRoom.getReservationId() + " reservation StartTime "+tempRoom.getReservationStartTime());
				if(Calendar.getInstance().getTime().before(tempRoom.getReservationStartTime()))
				{
					JOptionPane.showMessageDialog(ReserevedResourcesListModule.this,"Cannot be checked-out before Reservation Start Time "+tempRoom.getReservationStartTime()+"\n");
					return;
				}
				RoomTableModel.PerformCheckout(tempRoom);
				JOptionPane.showMessageDialog(ReserevedResourcesListModule.this,"Room checkout Completed,you can find it in your checkedout resources\n");
				ReserevedResourcesListModule.this.setVisible(false);
				ReserevedResourcesListModule.this.dispose();
			}
		   });
		   
		   JTable cameraTable = new JTable(new CameraTableModel(this.cameras));
		   cameraTable.setPreferredScrollableViewportSize(new Dimension(500, 100));
		   cameraTable.setColumnSelectionAllowed(false);
		   cameraTable.setRowSelectionAllowed(true);
		   checkoutCamera.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectIdx = cameraTable.getSelectedRow();
				if(selectIdx < 0) return;
				//JOptionPane.showMessageDialog(BookListModule.this, BookListModule.this.books.get(selectIdx).getTitle());
				Camera tempCamera =ReserevedResourcesListModule.this.cameras.get(selectIdx);
				System.out.println(tempCamera.getCameraId()+" "+tempCamera.getReservationId() + " reservation StartTime "+tempCamera.getReservationStartTime());
				if(Calendar.getInstance().getTime().before(tempCamera.getReservationStartTime()))
				{
					JOptionPane.showMessageDialog(ReserevedResourcesListModule.this,"Cannot be checked-out before Reservation Start Time "+tempCamera.getReservationStartTime()+"\n");
					return;
				}
				CameraTableModel.PerformCheckout(tempCamera);
				JOptionPane.showMessageDialog(ReserevedResourcesListModule.this,"Camera checkout Completed,you can find it in your checkedout resources\n");
				ReserevedResourcesListModule.this.setVisible(false);
				ReserevedResourcesListModule.this.dispose();
				//System.out.println(abc.getResourceID());
			}
		   });
		   
		   l2 = new JLabel("RESERVED ROOMS");
		   middle2.add(l2);
		   l3 = new JLabel("RESERVED CAMERAS");
		   middle3.add(l3);  
		   
		   //Create the scroll pane and add the table to it.
		   //JScrollPane scrollPane1 = new JScrollPane(bookTable);
		   JScrollPane scrollPane2 = new JScrollPane(roomTable);
		   JScrollPane scrollPane3 = new JScrollPane(cameraTable);
		  
		   //Add the scroll pane to this panel.
		   //middle1.add(scrollPane1);
		   middle2.add(scrollPane2);
		   //middle2.add(checkoutRoom);
		   middle3.add(scrollPane3);
		   //middle2.add(checkoutCamera);
	   }
}