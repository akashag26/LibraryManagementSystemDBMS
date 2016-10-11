package com.dbms.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.dbms.entity.Room;
import com.dbms.entity.RoomReservation;
import com.dbms.models.RoomTableModel;

public class RoomDetailModule extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4908667275199240251L;
	private static JPanel heading;
	private static JPanel middle;
	private static JPanel bottom;
	
	private static JLabel headingLabel;
	private static JLabel l1;//RoomID
	private static JLabel l2;//roomNumber
	private static JLabel l3;//LibrarName
	private static JLabel l4;//CategoryName
	private static JLabel l5;//Floor Number
	private static JLabel l6;//Capacity
	private static JLabel l7;//Type
	
	/*
	private static JLabel l8;
	private static JLabel l9;
	private static JLabel l10;
	private static JLabel l11;
	private static JLabel l12;
	private static JLabel l13;
	private static JLabel l14;
	private static JLabel l15;
	*/	
	
	private static JTextField roomID;
	private static JTextField roomNumber;
	private static JTextField libraryName;
	private static JTextField categoryName;
	private static JTextField floorNumber;
	private static JTextField capcity;
	private static JTextField type;

	/*
	private static JTextField categoryId;
	private static JTextField DOB;
	private static JTextField Sex;
	private static JTextField Nat;
	private static JTextField Dept;
	private static JTextField Class;
	private static JTextField Degree;
	private static JTextField Year;
	*/
	
	private static JButton bookRoom;
	private Room room;
	private RoomReservation reservation;
	
	public RoomDetailModule(RoomReservation reservation,Room room)
	{
		this.room = room;
		this.reservation = reservation;
	    heading = new JPanel();
        middle=new JPanel();
		bottom=new JPanel();
		 
		headingLabel = new JLabel();
		l1 = new JLabel();
		l2= new JLabel();
		l3= new JLabel();
		l4= new JLabel();
		l5= new JLabel();
		l6= new JLabel();
		l7= new JLabel();

		/*
		l8= new JLabel();
		l9= new JLabel();
		l10= new JLabel();
		l11= new JLabel();
		l12= new JLabel();
		l13= new JLabel();
		l14= new JLabel();
		l15= new JLabel();
		l12= new JLabel();*/
		 
		headingLabel.setText("Room Information");
		headingLabel.setForeground(new Color(0,0,0));
		headingLabel.setVisible(true);
	  
		roomID = new JTextField();
		roomNumber = new JTextField() ;
		libraryName= new JTextField();
		categoryName= new JTextField();
		floorNumber= new JTextField();
		capcity= new JTextField();
		type= new JTextField();
		
		/*DOB= new JTextField();
		Sex= new JTextField();
		Nat= new JTextField();
		Dept= new JTextField();
		Class= new JTextField();
		Degree= new JTextField();
		Year= new JTextField();*/
		 
		bookRoom=new JButton();
		 
		// Labels
		l1.setText("	Room ID:   ");
		l1.setForeground(new Color(50, 50, 50));
		l1.setVisible(true);
		l2.setText("	Room Number:    ");
		l2.setForeground(new Color(50, 50, 50));
		l2.setVisible(true);
		l3.setText("	Library Name:    ");
		l3.setForeground(new Color(50, 50, 50));
		l3.setVisible(true);
		l4.setText("	Category Name:    ");
		l4.setForeground(new Color(50, 50, 50));
		l4.setVisible(true);
		l5.setText("	Floor Number:    ");
		l5.setForeground(new Color(50, 50, 50));
		l5.setVisible(true);
		l6.setText("	Capcity:    ");
		l6.setForeground(new Color(50, 50, 50));
		l6.setVisible(true);
		l7.setText("	Type:    ");
		l7.setForeground(new Color(50, 50, 50));
		l7.setVisible(true);
		
		/*
		l8.setText("	Category ID:    ");
		l8.setForeground(new Color(50, 50, 50));
		l8.setVisible(true);
  		l9.setText("	Date of Birth:    ");
		l9.setForeground(new Color(50, 50, 50));
		l9.setVisible(true);
		l10.setText("	Sex:    ");
		l10.setForeground(new Color(50, 50, 50));
		l10.setVisible(true);
		l11.setText("	Nationality:    ");
		l11.setForeground(new Color(50, 50, 50));
		l11.setVisible(true);
		l12.setText("	Department:    ");
		l12.setForeground(new Color(50, 50, 50));
		l12.setVisible(true);
		l13.setText("	Classification:    ");
		l13.setForeground(new Color(50, 50, 50));
		l13.setVisible(true);
		l14.setText("	Degree:    ");
		l14.setForeground(new Color(50, 50, 50));
		l14.setVisible(true);
		l15.setText("	Year:    ");
		l15.setForeground(new Color(50, 50, 50));
		l15.setVisible(true);*/
		 
		//panels
		heading.add(headingLabel);
		middle.setLayout(new GridLayout(15, 15));
		middle.add(l1);
		middle.add(roomID);
		middle.add(l2);
		middle.add(roomNumber);
		middle.add(l3);
		middle.add(libraryName);
		bottom.add(bookRoom);
		middle.add(l4);
		middle.add(categoryName);
		middle.add(l5);
		middle.add(floorNumber);
		middle.add(l6);
		middle.add(capcity);
		middle.add(l7);
		middle.add(type);

		/*
		middle.add(l8);
		middle.add(categoryId);
  		middle.add(l9);
		middle.add(DOB);
		middle.add(l10);
		middle.add(Sex);
		middle.add(l11);
		middle.add(Nat);
		middle.add(l12);
		middle.add(Dept);
		middle.add(l13);
		middle.add(Class);
		middle.add(l14);
		middle.add(Degree);
		middle.add(l15);
		middle.add(Year);*/
		 
		 //Buttons
		bookRoom.setText("Book Room");
		bookRoom.setVisible(true);
		
		bookRoom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Book Room");
				reservation.setRoomId(room.getRoomId());
				RoomTableModel.MakeReservation(reservation);
				JOptionPane.showMessageDialog(null, "Room "+room.getRoomNumber()+" on floor "+ room.getFloorNo()+" in Libarary "+room.getLibraryName()+" has been reserved from "+reservation.getStartTime()+ " to "+reservation.getEndTime()+"\n ");
				RoomDetailModule.this.setVisible(false);
				RoomDetailModule.this.dispose();
			}
		});
		
		this.getContentPane().add(BorderLayout.NORTH, heading);
		this.getContentPane().add(BorderLayout.CENTER, middle);
		this.getContentPane().add(BorderLayout.SOUTH, bottom);
		this.setSize(400, 600);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.display();
	}
	
	public void display()
	{
		roomID.setText(""+room.getRoomId());
		roomNumber.setText(""+room.getRoomNumber());
		libraryName.setText(""+room.getLibraryName());
		categoryName.setText(room.getCategoryName());
		floorNumber.setText(""+room.getFloorNo());
		capcity.setText(""+room.getCapacity());
		type.setText(room.getType());
		
		roomID.setEditable(false);
		roomNumber.setEditable(false);
		libraryName.setEditable(false);
		categoryName.setEditable(false);
		floorNumber.setEditable(false);
		capcity.setEditable(false);
		type.setEditable(false);
		
	}
	
}