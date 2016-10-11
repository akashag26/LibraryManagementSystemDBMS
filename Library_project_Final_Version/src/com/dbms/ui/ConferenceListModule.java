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

import com.dbms.entity.Conference;
import com.dbms.entity.Faculty;
import com.dbms.entity.Student;
import com.dbms.models.ConferencesTableModel;

public class ConferenceListModule extends JFrame {

	/**
	* 
	*/
	private static final long serialVersionUID = -490998287611072118L;
	private List<Conference> conferences;
	private Student StudentInfo;
	private Faculty facultyInfo;
	private ConferencesTableModel dataModel;
	private static JPanel middle;

	public ConferenceListModule(List<Conference> conferences, Student studentInfo, Faculty facultyInfo) {

		System.out.println("123");
		setTitle("CONFERENCES");
		this.setSize(1200, 600);
		middle = new JPanel();
		middle.setVisible(true);

		middle.setLayout(new GridLayout(1, 0));
		this.add(middle);

		this.getContentPane().add(BorderLayout.CENTER, middle);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.conferences = conferences;
		this.StudentInfo = studentInfo;
		this.facultyInfo = facultyInfo;

		JTable table = new JTable(new ConferencesTableModel(this.conferences));
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if (!e.getValueIsAdjusting()) {
					int selectIdx = table.getSelectedRow();
					// JOptionPane.showMessageDialog(ConferenceListModule.this,
					// ConferenceListModule.this.conferences.get(selectIdx).getTitle());
					new ConferenceDetailModule(ConferenceListModule.this.conferences.get(selectIdx), studentInfo,facultyInfo);
				}
			}

		});
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);

		// Add the scroll pane to this panel.
		middle.add(scrollPane);
	}
}