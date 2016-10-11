package com.dbms.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.dbms.entity.Camera;
import com.dbms.entity.Faculty;
import com.dbms.entity.Student;

public class CameraDetailModule extends JFrame {

	/**
	 * 
	 */
	private Student studentinfo;
	private Faculty facultyInfo;
	private static final long serialVersionUID = 4458798644409226665L;
	private static JPanel heading;
	private static JPanel middle;
	private static JPanel bottom;

	private static JLabel headingLabel;
	private static JLabel l1;// cameraId
	private static JLabel l2;// libId
	private static JLabel l3;// categoryId
	private static JLabel l4;// Manufacturer
	private static JLabel l5;// Model
	private static JLabel l6;// Configuration
	private static JLabel l7;// Memory Available

	private static JTextField cameraId;
	private static JTextField libId;
	private static JTextField categoryId;
	private static JTextField manufacturer;
	private static JTextField model;
	private static JTextField configuration;
	private static JTextField memoryAvailable;

	// private static JButton Edit;
	private static JButton Request;
	private Camera camera;

	public CameraDetailModule(Camera camera, Student student, Faculty faculty) {
		this.studentinfo = student;
		this.facultyInfo = faculty;
		this.camera = camera;
		heading = new JPanel();
		middle = new JPanel();
		bottom = new JPanel();

		headingLabel = new JLabel();
		l1 = new JLabel();
		l2 = new JLabel();
		l3 = new JLabel();
		l4 = new JLabel();
		l5 = new JLabel();
		l6 = new JLabel();
		l7 = new JLabel();

		headingLabel.setText("Camera Information");
		headingLabel.setForeground(new Color(0, 0, 0));
		headingLabel.setVisible(true);

		cameraId = new JTextField();
		libId = new JTextField();
		categoryId = new JTextField();
		manufacturer = new JTextField();
		model = new JTextField();
		configuration = new JTextField();
		memoryAvailable = new JTextField();

		Request = new JButton();

		Request.setText("REQUEST");
		Request.setVisible(true);

		// Labels
		l1.setText("	Camera ID:   ");
		l1.setForeground(new Color(50, 50, 50));
		l1.setVisible(true);
		l2.setText("	Lib ID:    ");
		l2.setForeground(new Color(50, 50, 50));
		l2.setVisible(true);
		l3.setText("	Category ID:    ");
		l3.setForeground(new Color(50, 50, 50));
		l3.setVisible(true);
		l4.setText("	Manufacturer:    ");
		l4.setForeground(new Color(50, 50, 50));
		l4.setVisible(true);
		l5.setText("	Model:    ");
		l5.setForeground(new Color(50, 50, 50));
		l5.setVisible(true);
		l6.setText("	Configuration:    ");
		l6.setForeground(new Color(50, 50, 50));
		l6.setVisible(true);
		l7.setText("	Memory Available:    ");
		l7.setForeground(new Color(50, 50, 50));
		l7.setVisible(true);

		// panels
		heading.add(headingLabel);
		middle.setLayout(new GridLayout(15, 15));
		middle.add(l1);
		middle.add(cameraId);
		middle.add(l2);
		middle.add(libId);
		middle.add(l3);
		middle.add(categoryId);
		// bottom.add(Edit);
		middle.add(l4);
		middle.add(manufacturer);
		middle.add(l5);
		middle.add(model);
		middle.add(l6);
		middle.add(configuration);
		middle.add(l7);
		middle.add(memoryAvailable);
		bottom.add(Request);

		this.getContentPane().add(BorderLayout.NORTH, heading);
		this.getContentPane().add(BorderLayout.CENTER, middle);
		this.getContentPane().add(BorderLayout.SOUTH, bottom);
		this.setSize(400, 600);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.display();
	}

	public void display() {
		cameraId.setText("" + camera.getCameraId());
		libId.setText("" + camera.getLibraryId());
		categoryId.setText("" + camera.getCategoryId());
		manufacturer.setText(camera.getManufacturer());
		model.setText(camera.getModel());
		configuration.setText(camera.getConfiguration());
		memoryAvailable.setText(camera.getMemoryAvailable());

		cameraId.setEditable(false);
		libId.setEditable(false);
		categoryId.setEditable(false);
		manufacturer.setEditable(false);
		model.setEditable(false);
		configuration.setEditable(false);
		memoryAvailable.setEditable(false);

		Request.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new CameraConfirmation(studentinfo, facultyInfo, camera);
				CameraDetailModule.this.setVisible(false);
				CameraDetailModule.this.dispose();
				
			}
		});
	}

}