package project;

import java.awt.Component;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class App {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
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
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Color lightblue = new Color(204, 245, 255);
		Color lighterblue = new Color(230, 250, 255);
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(lightblue);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(1, 1, 210, 99);
		textArea.setBackground(lighterblue);
		frame.getContentPane().add(textArea);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(26, 34, 212, 123);
		frame.getContentPane().add(scrollPane);

		
		JFileChooser fileChooser = new JFileChooser();
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showOpenDialog((Component)e.getSource());
				if (result==JFileChooser.APPROVE_OPTION){
					textArea.setText("Sample ");
					List<String> lines;
					try {
						lines = Files.readAllLines(fileChooser.getSelectedFile().toPath(),Charset.defaultCharset());
						
						//passa os valores do csv para a forma de Sample
						Sample sample = new Sample();
						int[] valueint;
						String[] valuestring;
						
						for(int i=0;i<lines.size(); i++) {
						
								valuestring = lines.get(i).split(",");
								valueint = new int[valuestring.length];
								
								for(int j = 0; j < valuestring.length; j++) {
									valueint[j] = Integer.parseInt(valuestring[j]);
									
						}
								sample.add(valueint);
						}
						
						//criaçao do grafo pesado
						int Vsize= sample.element(1).length-1;
						int Asize= sample.length();
						
						WGraph wg = new WGraph(Vsize);

						int Maxj;
						int Maxi;
						double Pc1;
						
						int [] v= new int[1];
						int [] l= new int[1];
						
						v[0]=Vsize;
						l[0]=0;
						
						Pc1 = (double)sample.count(v, l);

						v= new int[2];
						l= new int[2];
						
						int [] vh= new int[3];
						int [] lh= new int[3];
						
						double Pxc;
						double Pyc;
						double Pxyc;
						double PT;
						double Pc;

						v[1]=Vsize;

						//varia os nós Xi
						for(int i=0;i<Vsize-1;i++) {
							Maxi=sample.domain(i);
							
							//varia os nós Xj
							for(int j=i+1;j<Vsize;j++) {
								Maxj = sample.domain(j);
								PT=0;
								
								//3 ciclos para o cálculo do somatório
								for (int c=0;c<=1;c++){
									Pc=((1-c)*Pc1+c*(Asize-Pc1));
								
									for (int x=0;x<=Maxi;x++){
										v[0]=i;
										l[0]=x; l[1]=c;
										Pxc=(double)sample.count(v,l);
										for (int y=0;y<=Maxj;y++){
											v[0]=j;
											l[0]=y;
											Pyc=(double)sample.count(v,l);
										
											vh[0]=i;vh[1]=j;vh[2]=Vsize;
											lh[0]=x;lh[1]=y;lh[2]=c;
											Pxyc=(double)sample.count(vh, lh);
											
											if(Pxc!=0 && Pyc!=0 && Pc!=0 && Pxyc!=0) {
												PT= PT + (Pxyc/(double)Asize)*(java.lang.Math.log((double)(Pxyc*Pc)/((double)(Pyc*Pxc))));
											};
											
											
										};
									};
									
							};
							
							//adiciona a aresta ao grafo pesado
							wg.add_edge(i, j, PT);
								
								
							}
						}
						
						
						//corre o MST ao grafo pesado
						DGraph dg = wg.MST(0);
						
						//adiciona a classe ao grafo dirigido
						dg.add_node();
						for(int j=0;j<dg.dim-1;j++)
							dg.add_edge(dg.dim-1,j);
						
						//gera a rede de Bayes								
						BN bayesresult = new BN( dg, sample, 0.5);
						
						//grava a rede no disco
						if(sample.element(1).length==11){
							FileOutputStream fos = new FileOutputStream("bcancer.ser");
							ObjectOutputStream oos = new ObjectOutputStream(fos);
							oos.writeObject(bayesresult);
							oos.close();
							textArea.setText(textArea.getText() + "of breast cancer ");
							}
						else if(sample.element(1).length==21) {
							FileOutputStream fos = new FileOutputStream("thyroid.ser");
							ObjectOutputStream oos = new ObjectOutputStream(fos);
							oos.writeObject(bayesresult);
							oos.close();
							textArea.setText(textArea.getText() + "of thyroid ");
						}
						else if(sample.element(1).length==20) {
							FileOutputStream fos = new FileOutputStream("hepatitis.ser");
							ObjectOutputStream oos = new ObjectOutputStream(fos);
							oos.writeObject(bayesresult);
							oos.close();
							textArea.setText(textArea.getText() + "of hepatitis ");
						}
						else if(sample.element(1).length==9) {
							FileOutputStream fos = new FileOutputStream("diabetes.ser");
							ObjectOutputStream oos = new ObjectOutputStream(fos);
							oos.writeObject(bayesresult);
							oos.close();
							textArea.setText(textArea.getText() + "of diabetes ");
						}
						
						textArea.setText(textArea.getText() + "received with success!");
						textArea.setText(textArea.getText() + "\n \nReady to do the diagnosis. ");
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}

			}
		});
		

		
		
		
		btnBrowse.setBounds(279, 88, 115, 29);
		frame.getContentPane().add(btnBrowse);
		
		
		JLabel lblSearchForThe = new JLabel("Search for the sample");
		lblSearchForThe.setBounds(279, 53, 176, 20);
		frame.getContentPane().add(lblSearchForThe);
		
	
	}	
}
