package project;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JButton;

public class App2 {

	private JFrame frame;
	private boolean b1;
	private boolean b2;
	private JTextField textField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App2 window = new App2();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Color lightgreen = new Color(204, 255, 238);
		Color lightergreen = new Color(230, 255, 247);
		
		Color breast = new Color(255, 230, 204);
		Color diabetes = new Color(242, 217, 217);
		Color hepatitis = new Color(255, 204, 255);
		Color tyrhoid = new Color(204, 204, 255);
		
		frame.getContentPane().setBackground(lightgreen);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Breast Cancer");
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b1=true;
				b2=true;
			}
		});
		rdbtnNewRadioButton.setBounds(286, 35, 109, 23);
		frame.getContentPane().add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBackground(breast);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Diabetes");
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b1=true;
				b2=false;
			}
		});
		rdbtnNewRadioButton_1.setBounds(286, 75, 109, 23);
		frame.getContentPane().add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setBackground(diabetes);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Hepatitis");
		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b1=false;
				b2=true;
			}
		});
		rdbtnNewRadioButton_2.setBackground(hepatitis);
		rdbtnNewRadioButton_2.setBounds(286, 115, 109, 23);
		frame.getContentPane().add(rdbtnNewRadioButton_2);
		
		JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("Thyroid");
		rdbtnNewRadioButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b1=false;
				b2=false;
			}
		});
		rdbtnNewRadioButton_3.setBackground(tyrhoid);
		rdbtnNewRadioButton_3.setBounds(286, 155, 109, 23);
		frame.getContentPane().add(rdbtnNewRadioButton_3);
		
		ButtonGroup group=new ButtonGroup();
		group.add(rdbtnNewRadioButton);
		group.add(rdbtnNewRadioButton_1);
		group.add(rdbtnNewRadioButton_2);
		group.add(rdbtnNewRadioButton_3);
		
		textField = new JTextField();
		textField.setBounds(44, 51, 177, 23);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		textField.setBackground(lightergreen);
		
		JLabel lblInput = new JLabel("Input");
		lblInput.setBounds(47, 26, 46, 25);
		frame.getContentPane().add(lblInput);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(44, 134, 177, 53);
		frame.getContentPane().add(textArea);
		textArea.setBackground(lightergreen);
		
		JLabel lblDiagnosis = new JLabel("Diagnosis");
		lblDiagnosis.setBounds(47, 111, 71, 14);
		frame.getContentPane().add(lblDiagnosis);
		
		JButton btnRun_Diagnosis = new JButton("Run Diagnosis");
		btnRun_Diagnosis.addActionListener(new ActionListener() {
			String r;
			public void actionPerformed(ActionEvent e) {
				try {
					String input_text = textField.getText();
					String [] valuestring = input_text.split(",");
					int size = valuestring.length;
					int [] values_0 = new int[size + 1];
					int [] values_1 = new int[size + 1];
					
					for (int i = 0; i < size; i++) {
						values_0[i] = Integer.parseInt(valuestring[i]);
						values_1[i] = Integer.parseInt(valuestring[i]);
					}
					values_0[size] = 0;
					values_1[size] = 1;					
					
					int nvariables = values_0.length;
					
					if(b1 && b2){
						
						if (nvariables == 11) {

							FileInputStream file = new FileInputStream("bcancer.ser");
							ObjectInputStream object = new ObjectInputStream(file);
							BN bn = (BN) object.readObject();
							object.close();
						
							double r1 = bn.prob(values_0);
							double r2 = bn.prob(values_1);

						
							if (r1 > r2) {
								r = "The patient doesn't have \n breast cancer";
							}
							else {
								r = "The patient has breast cancer";
							}
						}
						else {
							
							r = "The input doesn't have the \nnumber of variables needed \nfor the selected disease";
						}
					}
					else if(b1 && !b2){
						
						if (nvariables == 9) {
							
							FileInputStream file = new FileInputStream("diabetes.ser");
							ObjectInputStream object = new ObjectInputStream(file);
							BN bn = (BN) object.readObject();
							object.close();
						
							double r1 = bn.prob(values_0);
							double r2 = bn.prob(values_1);
						
							if (r1 > r2) {
								r = "The patient doesn't have \n diabetes";
							}
							else {
								r = "The patient has diabetes";
							}
						}
						else {
							
							r = "The number of variables \n doesn't match the input of \n the selected disease";
						}
					}
					else if(!b1 && b2) {
						
						if (nvariables == 20) {
							
							FileInputStream file = new FileInputStream("hepatitis.ser");
							ObjectInputStream object = new ObjectInputStream(file);
							BN bn = (BN) object.readObject();
							object.close();
						
							double r1 = bn.prob(values_0);
							double r2 = bn.prob(values_1);
						
							if (r1 > r2) {
								r = "The patient doesn't have \n hepatitis";
							}
							else {
								r = "The patient has hepatitis";
							}
						}
						else {
							
							r = "The number of variables \n doesn't match the input of \n the selected disease";
						}
					}
					else if (!b1 && !b2) {
						
						if (nvariables == 21) {
							FileInputStream file = new FileInputStream("thyroid.ser");
							ObjectInputStream object = new ObjectInputStream(file);
							BN bn = (BN) object.readObject();
							object.close();
						
							double r1 = bn.prob(values_0);
							double r2 = bn.prob(values_1);
						
							if (r1 > r2) {
								r = "The patient doesn't have \n thyroid";
							}
							else {
								r = "The patient has thyroid";
							}
						}
						else {
							
							r = "The number of variables \n doesn't match the input of \n the selected disease";
						}
					}
				}
				catch (FileNotFoundException f) {
					f.printStackTrace();
				} 
				catch (IOException f) {
					f.printStackTrace();
				} 
				catch (ClassNotFoundException f) {
					f.printStackTrace();
				}
				textArea.setText(" "+r);
			}
		});
		btnRun_Diagnosis.setBounds(262, 197, 133, 23);
		frame.getContentPane().add(btnRun_Diagnosis);
		
		JLabel lblInputExample = new JLabel("Input Example: a,b,c");
		lblInputExample.setBounds(47, 79, 168, 14);
		frame.getContentPane().add(lblInputExample);
	}
}
