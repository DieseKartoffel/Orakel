package Krams;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class OrakelGUI extends JFrame{
	
	//cheats are empty spcaes used to reposition elements in the gui
	public JLabel cheat;

	public JLabel task;
	public JTextField input;
	public JButton buttonAsk;
	public JButton buttonAdd;
	public JButton buttonRoll;
	public static JTextArea output;
	public JButton buttonFake;
	public JButton update;
	
	Thread thread = new Thread(new myRunner());
	

	static OrakelGUI orak;
	public static void main(String[] args) {
		orak = new OrakelGUI("Das Orakel");
		orak.setVisible(true);
		orak.setResizable(false);
		orak.setSize(300, 180);
		orak.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//Constrctor stes up GUI:
	public OrakelGUI(String s){
		super(s);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((dim.width/2-this.getSize().width/2)-150, (dim.height/2-this.getSize().height/2)-90);
		
		//setting up wooden background image from ressource folder
	    setLayout(new BorderLayout());
	    URL url = OrakelGUI.class.getResource("/ressource/bild2.jpg");
	    ImageIcon backgroundImage = new ImageIcon(url);
	    JLabel background = new JLabel(backgroundImage);
	    setContentPane(background);
	    setLayout(new FlowLayout());
		
	    //used to listen to button actions
	    Listener listen = new Listener();
		
	    //used for space in the top of gui (dont even ask)
	    cheat = new JLabel("                                                                      ");

	    //adding buttons for first frame:
		task = new JLabel("Stelle deine Frage: ");
		task.setForeground(Color.white);

		input = new JTextField(20);
		input.setEditable(true);

		buttonAsk = new JButton("Frage stellen");
		buttonAsk.addActionListener(listen);
		getRootPane().setDefaultButton(buttonAsk); //Enter key allows firing Askbutton

		add(cheat);
		add(task);
		add(input);
		add(buttonAsk);

		//buttons that will be added in later frames:
		buttonAdd = new JButton("Hinzufügen");
		buttonAdd.addActionListener(listen);

		buttonRoll = new JButton("Roll the Dice");
		buttonRoll.addActionListener(listen);

		output = new JTextArea(1,20);
		output.setEditable(false);

		update = new JButton("Nächste Frage");
		update.addActionListener(listen);
		//update button is set visible after final answer is displayed
		update.setVisible(false);
	}
	
	//new Thread is needed to update JTextField that displays random answers fast:
	
	static String frage = "";
	//ArrayList is filled in Actionlistener methods
	static ArrayList<String> antworten = new ArrayList<String>();
	static String antwort = "";
	
	public class myRunner implements Runnable{
		int waitTime = 10;
		public void run(){
			int count = 0;
			//loop that goes thru added answers and displays them in TextArea
			while(waitTime < 1500){
				int max = antworten.size();
				Random r = new Random();
				antwort = antworten.get(r.nextInt(max));
				output.setText("  "+antwort+"  ");
				try {
					Thread.sleep(waitTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				count++;
				if(count > 200){
					waitTime *= 1.15;
				}
			}
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "Das Orakel sagt: "+antwort);
			orak.update.setVisible(true);
			getRootPane().setDefaultButton(update); //Enter key allows firing reset button
		}	
	}
	
	
	class Listener implements ActionListener{
		
		//anzahl der antwortmöglichkeiten
		int count = 1;
		
		public void actionPerformed(ActionEvent event) {
			//when question is entered and buttonask fires, set up new frame to add answers
			if(event.getSource() == buttonAsk){
				//if there is no input:
				if(input.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Gib deine Frage ein.");
					return;
				}
				//used to center question text when answer is chosen:
				String cheat = "                                                      ";
				frage = cheat+input.getText()+cheat;
				input.setText(null);
				task.setText("Antwortmöglichkeit " +count+": ");
				//disable ask button and let user add answers with Add button
				buttonAsk.setVisible(false);;
				add(buttonAdd);
				getRootPane().setDefaultButton(buttonAdd); //Enter key allows firing Add answer button
			}
			if(event.getSource() == buttonAdd){
				if(input.getText().equals("") && count <= 2){
					JOptionPane.showMessageDialog(null, "Gib mindestens zwei Antwortmöglichkeiten ein.");
					return;
				}
				
				//allow user to answer his question
				if(count == 2){
					add(buttonRoll);
				}
				
				//user can start finding the answer by pressing enter key if there is no input and there are at least 2 answer options
				if(input.getText().equals("") && count > 2){
					buttonRoll.doClick();
					return;
				}

				antworten.add(input.getText());
				input.setText(null);
				count++;
				task.setText("Antwortmöglichkeit " +count+": ");	
			}
			
			//start rolling thru answers / "roll the dice"
			if(event.getSource() == buttonRoll){
				input.setVisible(false);
				buttonAdd.setVisible(false);
				buttonRoll.setVisible(false);
				task.setText(frage);
				add(output);
				thread.start();
				add(update);	
			}
			
			//reset programm:
			if(event.getSource() == update){
				orak.dispose();
				orak.antworten.clear();
				orak.main(null);
				
			}	
		}	
	}
}