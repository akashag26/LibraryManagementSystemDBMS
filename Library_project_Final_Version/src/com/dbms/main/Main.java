package com.dbms.main;
import java.util.List;

import javax.swing.JFrame;

import com.dbms.entity.Book;
import com.dbms.ui.Login;

public class Main {
	
	public static void main(String[] args) {
		new Login().start();
	}
	
	   /***** Code below is For Testing *****/
	
	   /**
	   * Create the GUI and show it. For thread safety, this method should be
	   * invoked from the event-dispatching thread.
	   */
	  private static void createAndShowGUI(List items) {
	    //Make sure we have nice window decorations.
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    //Create and set up the window.
	    JFrame frame = new JFrame("Show Books List");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(1200, 600);
	    frame.setVisible(true);
	  }
	
	public static void populateBooks(List<Book> books)
	{
			Book book = new Book();
			//book.setAuthorNames(new String[]{"Arjun","Akash","Balaji"});
			book.setEdition("100");
			book.setLibId(1);
			book.setPublisher("Mc-graw");
			book.setTitle("Yahoo");
			book.setYearOfPublication("2015");
			books.add(book);
			
			book = new Book();
			//book.setAuthorNames(new String[]{"Ankit","Akash","Balaji"});
			book.setEdition("101");
			book.setLibId(1);
			book.setPublisher("Mc-graw");
			book.setTitle("Yahoo-2");
			book.setYearOfPublication("2016");
			books.add(book);	
			
			book = new Book();
			//book.setAuthorNames(new String[]{"Paritosh","Akash","Balaji"});
			book.setEdition("102");
			book.setLibId(1);
			book.setPublisher("Mc-graw");
			book.setTitle("Yahoo-3");
			book.setYearOfPublication("2017");
			books.add(book);	
		}
	
	}


