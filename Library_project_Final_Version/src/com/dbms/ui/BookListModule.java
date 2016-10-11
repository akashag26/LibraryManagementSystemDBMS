package com.dbms.ui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.dbms.entity.Book;
import com.dbms.entity.Faculty;
import com.dbms.entity.Student;
import com.dbms.models.BookTableModel;

public class BookListModule extends JFrame{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 8484120551363759234L;
	private List<Book> books;
	private Student studentinfo;
	private Faculty facultyInfo;
	private static JPanel middle;
	private BookTableModel dataModel;
	   
	   public BookListModule(List<Book> books,Student studentInfo,Faculty facultyInfo)
 {
		setTitle("BOOKS");
		this.setSize(1200, 600);
		middle = new JPanel();
		middle.setVisible(true);

		middle.setLayout(new GridLayout(1, 0));
		this.add(middle);

		this.books = books;
		this.studentinfo = studentInfo;
		this.facultyInfo = facultyInfo;

		this.getContentPane().add(BorderLayout.CENTER, middle);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);

		JTable table = new JTable(new BookTableModel(this.books));
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if (!e.getValueIsAdjusting()) {
					int selectIdx = table.getSelectedRow();
					// JOptionPane.showMessageDialog(BookListModule.this,
					// BookListModule.this.books.get(selectIdx).getTitle());
					// System.out.println(abc.getResourceID());
					new BookDetailModule(BookListModule.this.books.get(selectIdx), studentinfo, facultyInfo);

				}
			}

		});
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);

		// Add the scroll pane to this panel.
		middle.add(scrollPane);
	}
}
