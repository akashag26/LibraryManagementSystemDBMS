package com.dbms.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.dbms.entity.CameraCheckoutLog;
import com.dbms.entity.CheckoutLog;
import com.dbms.entity.Faculty;
import com.dbms.entity.Student;
import com.dbms.models.BookCheckOutLogTabelModel;
import com.dbms.models.CameraCheckOutLogTableModel;

public class DueListModule extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2106611717204897001L;

	private List<CheckoutLog> books;
	private List<CameraCheckoutLog> cameras;
	private Student studentInfo;
	private Faculty facultyInfo;

	private static JPanel middle2;
	private static JPanel middle3;

	private static JLabel l2;
	private static JLabel l3;
	private static JButton clearBookFine;
	private static JButton clearCameraFine;
	private static JPanel bottom;
	private int bookFine;
	private int cameraFine;
	   
	   public DueListModule(List<CheckoutLog> books,List<CameraCheckoutLog> cameras,Student studentInfo,Faculty facultyInfo)
	   {
		   setTitle("DUE ON RESOURCES CHECKED-OUT");
		   this.setSize(1200, 600);
		   this.bookFine = 0;
		   this.cameraFine = 0;
		   bottom=new JPanel();
		   clearBookFine=new JButton();
		   clearBookFine.setText("Clear Book Fine");
		   clearCameraFine=new JButton();
		   clearCameraFine.setText("Clear Camera Fine");
		   
		   middle2=new JPanel();
		   middle2.setVisible(true);
		   middle2.setLayout(new GridLayout(2,0));
		   
		
		   middle3=new JPanel();
		   middle3.setVisible(true);
		   middle3.setLayout(new GridLayout(2,0));
		
		   this.studentInfo = studentInfo;
		   this.facultyInfo =  facultyInfo;
		   this.cameras = cameras;
		   this.books = books;

		   this.calculateFineOnBooksAndCameras(books,cameras);
		   
		   clearBookFine.setVisible(true);
		   clearCameraFine.setVisible(true);
		   
		   bottom.add(clearBookFine);
		   bottom.add(clearCameraFine);
		   
		   this.getContentPane().add(BorderLayout.NORTH,middle2);
		   this.getContentPane().add(BorderLayout.CENTER,bottom);
		   this.getContentPane().add(BorderLayout.SOUTH,middle3);
		   this.setVisible(true);
		   this.setLocationRelativeTo(null); 
		   
		   JTable roomTable = new JTable(new BookCheckOutLogTabelModel(this.books));
		   roomTable.setPreferredScrollableViewportSize(new Dimension(500, 100));
		   roomTable.setColumnSelectionAllowed(false);
		   roomTable.setRowSelectionAllowed(true);
		   clearBookFine.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectIdx = roomTable.getSelectedRow();
				if(selectIdx < 0) return;
				CheckoutLog tempBook =DueListModule.this.books.get(selectIdx);
				BookCheckOutLogTabelModel.updateFineOfBooksForStudents(studentInfo,facultyInfo);
				JOptionPane.showMessageDialog(DueListModule.this,"Fine on the Books has been cleared\n");
				DueListModule.this.setVisible(false);
				DueListModule.this.dispose();
			}
		   });
		   
		   JTable cameraTable = new JTable(new CameraCheckOutLogTableModel(this.cameras));
		   cameraTable.setPreferredScrollableViewportSize(new Dimension(500, 100));
		   cameraTable.setColumnSelectionAllowed(false);
		   cameraTable.setRowSelectionAllowed(true);
		   clearCameraFine.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectIdx = cameraTable.getSelectedRow();
				if(selectIdx < 0) return;
				CameraCheckoutLog tempCamera =DueListModule.this.cameras.get(selectIdx);
				CameraCheckOutLogTableModel.updateFineOfCamerasForStudents(studentInfo,facultyInfo);
				JOptionPane.showMessageDialog(DueListModule.this,"Fine on the Cameras has been cleared\n");
				DueListModule.this.setVisible(false);
				DueListModule.this.dispose();
			}
		   });
		   
		   l2 = new JLabel("TOTAL FINE ON BOOKS : $"+this.bookFine);
		   middle2.add(l2);
		   l3 = new JLabel("TOTAL FINE ON CAMERAS : $"+this.cameraFine);
		   middle3.add(l3);  
		   
		   JScrollPane scrollPane2 = new JScrollPane(roomTable);
		   JScrollPane scrollPane3 = new JScrollPane(cameraTable);
		  
		   middle2.add(scrollPane2);
		   middle3.add(scrollPane3);
	   }
	   
	   public void calculateFineOnBooksAndCameras(List<CheckoutLog> books,List<CameraCheckoutLog> cameras)
	   {
		   Iterator<CheckoutLog> i1 = this.books.iterator();
		   while(i1.hasNext())
		   {
			   this.bookFine = this.bookFine + ((CheckoutLog)i1.next()).getFine();
		   }
		   
		   Iterator<CameraCheckoutLog> i2 = this.cameras.iterator();
		   while(i2.hasNext())
		   {
			   this.cameraFine = this.cameraFine + ((CameraCheckoutLog)i2.next()).getFine();
		   }
	   }
	   
}
