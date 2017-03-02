package Krams;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

public class Orakel {
	
	public static void main(String[] args) {

		try {
			getRandom();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void getRandom() throws InterruptedException{

		System.out.println("+++++++++++++++++++++++++++++");
		System.out.println();
		String frage = readString("Stelle deine Frage: ");
		
		System.out.println("Wie viele Antwortmöglichkeiten gibt es?:");
		
		int anzahl = readInt("Anzahl der Möglichkeiten: ");

		
		String[] optionen = new String[anzahl];
		
		for(int i = 1; i<=anzahl; i++){
			
			String option = readString("Option " + i + ":");
			
			Scanner s = new Scanner(System.in);
			optionen[i-1] = option;
		}
		

		double time = 6;
		System.out.println("Uuuuuund der Gewinner ist: ");
		int count = 0;
		String antwort = "keine Ahung!";
		System.out.print("[ ");
		while(true){
			
			int r = (int) (Math.random()*anzahl);
			
			
			System.out.print(optionen[r] + " ]");
			
			Thread.sleep((long) (time*10));
			
			System.out.print("\b\b");
			System.out.print("  ");
			System.out.print("\b\b");
			
			for(int i = 0; i < optionen[r].length() ; i++){
				System.out.print("\b");
			}			
			for(int i = 0; i < optionen[r].length() ; i++){
				System.out.print(" ");
			}
			for(int i = 0; i < optionen[r].length() ; i++){
				System.out.print("\b");
			}
			
			count++;
			
			Random ran = new Random();
			
			if(count > 20 + ran.nextInt(60)){
				time = time * 1.3;
			}
			
			if(time > 150){
				r = (int) (Math.random()*anzahl);
				antwort = optionen[r];
				System.out.print(antwort + " ]");
				Thread.sleep((long) (time*12));
				break;
			}
			
		}
		
		System.out.println();
		System.out.println(" +++ Antwort gefunden! +++ ");
		System.out.println();
		System.out.println("Frage: " + frage);
		Thread.sleep(300);
		System.out.println();
		System.out.println("Antwort: " + antwort);
		Thread.sleep(300);
		System.out.println();
		System.out.println("Fertig!");
		System.out.println();
		
		getRandom();
	}

	private static String readString(String msg) {
		System.out.print(msg+" ");
		Scanner s = new Scanner(System.in);
		while(true){
			try {
				return s.nextLine();
			}
			catch(Exception e){
				System.out.println("Error!");
				return readString(msg);
			}
		}
	}

	private static int readInt(String msg) {
		System.out.print(msg+" ");
		Scanner s = new Scanner(System.in);
		int anzahl;
		while(true){
			try {

				anzahl = s.nextInt();
					
				if(anzahl > 0 && anzahl < 100){
					return anzahl;
				}else{
					throw new Exception();
				}
			}
			catch(Exception e){
						System.out.println("Error!");
						return readInt(msg);
			}
		}
	}
}
		
	

