package amsiproject3;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class checkers {
  
    public static void main(String[] args) {
       okno OknoGlowne = new okno("Warcaby",640,480);
    }
}
class okno extends Frame 
{
    	Button bNowa;
        Button bZasad;
		
	public okno(String Nazwa, int szer, int wys)
	{
                //wyświetlenie "Warcaby" w LG rogu
                super(Nazwa);
                //aby buttony itp. miały takie wartości jak zapisaliśmy...
		setLayout(null);
		
		setSize(szer,wys);
		setLocation(10,10);
                //np. czcionka tekstu na buttonach
		setFont(new Font("Arial",0,16));
                //jeśli true - to okna można skalować przy pomocy myszki (rozciągać)
		setResizable(false);
		setBackground(new Color(220,220,220));
                
		//aby nasze okno można było zamknąć...
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing (WindowEvent e)
			{
				System.exit(0);
			}
		});
   
                bNowa = new Button("Nowa gra");
		bNowa.setSize(100,25);
		bNowa.setLocation(450,90);	
		//wyświetlenie przycisku
                add(bNowa);
                
		bZasad = new Button("Zasady gry");
		bZasad.setSize(100,25);
		bZasad.setLocation(450,145);	
	        add(bZasad);	    
       //bez tego nie wyświetlimy okna - służy do wyświetlania okna
       show();
	}
}