package com.dbms.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.dbms.entity.Book;
import com.dbms.entity.Camera;
import com.dbms.entity.Conference;
import com.dbms.entity.Faculty;
import com.dbms.entity.Journal;
import com.dbms.entity.Room;
import com.dbms.entity.Student;
import com.dbms.models.BookTableModel;
import com.dbms.models.CameraTableModel;
import com.dbms.models.ConferencesTableModel;
import com.dbms.models.JournalTableModel;
import com.dbms.models.RoomTableModel;

public class CheckedOutResourcesListModule extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7245855641575353925L;
	private List<Book> books;
	private List<Camera> cameras;
	private List<Room> rooms;
	private List<Journal> journals;
	private List<Conference> conferences;
	private Student studentinfo;
	private Faculty facultyInfo;
	private static JPanel middle1;
	private static JPanel middle2;
	private static JPanel middle3;
	private static JPanel middle4;
	private static JPanel middle5;
	private static JLabel l1;
	private static JLabel l2;
	private static JLabel l3;
	private static JLabel l4;
	private static JLabel l5;
	private static JButton checkinRoom;
	private static JButton checkinCamera;
	private static JButton checkinBook;
	private static JButton checkinJournals;
	private static JButton checkinConfernces;
	private static JPanel bottom;

	public CheckedOutResourcesListModule(List<Book> books, List<Room> rooms,List<Camera> cameras,
			List<Journal> journals, List<Conference> conferences, Student studentInfo,Faculty facultyInfo)
	{
		   setTitle("CHECKED-OUT RESOURCES");
		   this.setSize(1700, 600);

		   bottom=new JPanel();
		   checkinRoom=new JButton();
		   checkinRoom.setText("Check-in Room");
		   checkinCamera=new JButton();
		   checkinCamera.setText("Check-in Camera");
		   checkinBook=new JButton();
		   checkinBook.setText("Check-in Book");
		   checkinJournals=new JButton();
		   checkinJournals.setText("Check-in Journal");
		   checkinConfernces=new JButton();
		   checkinConfernces.setText("Check-in Conference-Paper");
		   
		   middle1=new JPanel();
		   middle1.setVisible(true);
		   middle1.setLayout(new GridLayout(2,0));
		   
		   middle2=new JPanel();
		   middle2.setVisible(true);
		   middle2.setLayout(new GridLayout(2,0));
		   
		
		   middle3=new JPanel();
		   middle3.setVisible(true);
		   middle3.setLayout(new GridLayout(2,0));
		   
		   middle4=new JPanel();
		   middle4.setVisible(true);
		   middle4.setLayout(new GridLayout(2,0));
		   
		   middle5=new JPanel();
		   middle5.setVisible(true);
		   middle5.setLayout(new GridLayout(2,0));
		
		   this.books = books;
		   this.studentinfo = studentInfo;
		   this.facultyInfo = facultyInfo;
		   this.rooms = rooms;
		   this.cameras = cameras;
		   this.journals = journals;
		   this.conferences = conferences;
		   
		   checkinRoom.setVisible(true);
		   checkinCamera.setVisible(true);
		   checkinBook.setVisible(true);
		   checkinJournals.setVisible(true);
		   checkinConfernces.setVisible(true);
		   
		   bottom.add(checkinBook);
		   bottom.add(checkinRoom);
		   bottom.add(checkinCamera);
		   bottom.add(checkinJournals);
		   bottom.add(checkinConfernces);
		   
		   
		   JTable bookTable = new JTable(new BookTableModel(this.books));
		   bookTable.setPreferredScrollableViewportSize(new Dimension(500, 100));
		   bookTable.setColumnSelectionAllowed(false);
		   bookTable.setRowSelectionAllowed(true);
		   
		   checkinBook.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectIdx = bookTable.getSelectedRow();
				if (selectIdx < 0)
					return;
				Book tempBook = CheckedOutResourcesListModule.this.books.get(selectIdx);
				System.out.println(tempBook.getISBN());
				BookTableModel.PerformCheckin(tempBook,CheckedOutResourcesListModule.this.studentinfo,CheckedOutResourcesListModule.this.facultyInfo);
				BookTableModel.updateNumberOfCopies(tempBook);
				BookTableModel.deleteFromBookCheckout(tempBook,studentinfo,facultyInfo);
				JOptionPane.showMessageDialog(CheckedOutResourcesListModule.this, "Book check-in Completed\n");
				CheckedOutResourcesListModule.this.setVisible(false);
				CheckedOutResourcesListModule.this.dispose();
			}

		   });
		   
		   JTable roomTable = new JTable(new RoomTableModel(this.rooms));
		   roomTable.setPreferredScrollableViewportSize(new Dimension(500, 100));
		   roomTable.setColumnSelectionAllowed(false);
		   roomTable.setRowSelectionAllowed(true);
		   checkinRoom.addActionListener(new ActionListener() {

		   @Override
		   public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectIdx = roomTable.getSelectedRow();
				if(selectIdx < 0) return;
				//JOptionPane.showMessageDialog(BookListModule.this, BookListModule.this.books.get(selectIdx).getTitle());
				Room tempRoom =CheckedOutResourcesListModule.this.rooms.get(selectIdx);
				System.out.println(tempRoom.getRoomId()+" "+tempRoom.getReservationId());
				RoomTableModel.PerformCheckin(tempRoom);
				RoomTableModel.deleteFromRoomCheckoutAndReservation(tempRoom,studentinfo,facultyInfo);
				JOptionPane.showMessageDialog(CheckedOutResourcesListModule.this,"Room check-in Completed\n");
				CheckedOutResourcesListModule.this.setVisible(false);
				CheckedOutResourcesListModule.this.dispose();
			}
		   });
		   
		   JTable cameraTable = new JTable(new CameraTableModel(this.cameras));
		   cameraTable.setPreferredScrollableViewportSize(new Dimension(500, 100));
		   cameraTable.setColumnSelectionAllowed(false);
		   cameraTable.setRowSelectionAllowed(true);
		   checkinCamera.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectIdx = cameraTable.getSelectedRow();
				if(selectIdx < 0) return;
				//JOptionPane.showMessageDialog(BookListModule.this, BookListModule.this.books.get(selectIdx).getTitle());
				Camera tempCamera =CheckedOutResourcesListModule.this.cameras.get(selectIdx);
				System.out.println(tempCamera.getCameraId()+" "+tempCamera.getReservationId());
				CameraTableModel.PerformCheckin(tempCamera);
				CameraTableModel.deleteFromCameraCheckoutAndReservation(tempCamera,studentinfo,facultyInfo);
				JOptionPane.showMessageDialog(CheckedOutResourcesListModule.this,"Camera check-in Completed\n");
				CheckedOutResourcesListModule.this.setVisible(false);
				CheckedOutResourcesListModule.this.dispose();
			}
		   });
		   
		   
		   JTable journalTable = new JTable(new JournalTableModel(this.journals));
		   journalTable.setPreferredScrollableViewportSize(new Dimension(500, 100));
		   journalTable.setColumnSelectionAllowed(false);
		   journalTable.setRowSelectionAllowed(true);
		   
		   checkinJournals.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectIdx = journalTable.getSelectedRow();
				if (selectIdx < 0)
					return;
				Journal tempJournal = CheckedOutResourcesListModule.this.journals.get(selectIdx);
				System.out.println(tempJournal.getISSN());
				JournalTableModel.PerformCheckin(tempJournal,CheckedOutResourcesListModule.this.studentinfo,CheckedOutResourcesListModule.this.facultyInfo);
				JournalTableModel.updateNumberOfCopies(tempJournal);
				JournalTableModel.deleteFromJournalCheckout(tempJournal,studentinfo,facultyInfo);
				JOptionPane.showMessageDialog(CheckedOutResourcesListModule.this, "Journal check-in Completed\n");
				CheckedOutResourcesListModule.this.setVisible(false);
				CheckedOutResourcesListModule.this.dispose();
			}

		   });
		   
		   
		   
		   JTable confernceTable = new JTable(new ConferencesTableModel(this.conferences));
		   confernceTable.setPreferredScrollableViewportSize(new Dimension(500, 100));
		   confernceTable.setColumnSelectionAllowed(false);
		   confernceTable.setRowSelectionAllowed(true);
		   
		   checkinConfernces.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectIdx = confernceTable.getSelectedRow();
				if (selectIdx < 0)
					return;
				Conference tempConf = CheckedOutResourcesListModule.this.conferences.get(selectIdx);
				System.out.println(tempConf.getConferenceNumber());
				ConferencesTableModel.PerformCheckin(tempConf,CheckedOutResourcesListModule.this.studentinfo,CheckedOutResourcesListModule.this.facultyInfo);
				ConferencesTableModel.updateNumberOfCopies(tempConf);
				ConferencesTableModel.deleteFromConferenceCheckout(tempConf,studentinfo,facultyInfo);
				JOptionPane.showMessageDialog(CheckedOutResourcesListModule.this, "Conference check-in Completed\n");
				CheckedOutResourcesListModule.this.setVisible(false);
				CheckedOutResourcesListModule.this.dispose();
			}

		   });
		   
		   
		   l1 = new JLabel("CHECKED-OUT BOOKS");
		   middle1.add(l1);
		   l2 = new JLabel("CHECKED-OUT ROOMS");
		   middle2.add(l2);
		   l3 = new JLabel("CHECKED-OUT CAMERAS");
		   middle3.add(l3);  
		   l4 = new JLabel("CHECKED-OUT JOURNALS");
		   middle4.add(l4);  
		   l5 = new JLabel("CHECKED-OUT CONFERENCES");
		   middle5.add(l5);  
		   
		   
		   //Create the scroll pane and add the table to it.
		   JScrollPane scrollPane1 = new JScrollPane(bookTable);
		   JScrollPane scrollPane2 = new JScrollPane(roomTable);
		   JScrollPane scrollPane3 = new JScrollPane(cameraTable);
		   JScrollPane scrollPane4 = new JScrollPane(journalTable);
		   JScrollPane scrollPane5 = new JScrollPane(confernceTable);
		  
		   //Add the scroll pane to this panel.
		   middle1.add(scrollPane1);
		   middle2.add(scrollPane2);
		   middle3.add(scrollPane3);
		   middle4.add(scrollPane4);
		   middle5.add(scrollPane5);
		   
		   this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		   this.getContentPane().add(middle1);
		   this.getContentPane().add(middle2);
		   this.getContentPane().add(middle3);
		   this.getContentPane().add(middle4);
		   this.getContentPane().add(middle5);
		   this.getContentPane().add(bottom);
		   this.setVisible(true);
		   this.setLocationRelativeTo(null); 
	   }

}