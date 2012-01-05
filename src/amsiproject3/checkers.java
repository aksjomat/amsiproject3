
package amsiproject3;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class checkers {
  
    public static void main(String[] args) {
       okno OknoGlowne = new okno("Warcaby",640,480);
    }
}
class okno extends Frame implements ActionListener
{
    	Button bNowa;
        Button bZasad;
        Plansza plansza;
        Label lTekst;
        Ruchy ruchy;
	Bicia bicia;
        
	public okno(String Nazwa, int szer, int wys)
	{
                //wyświetlenie "Warcaby" w LG rogu
                super(Nazwa);
                //aby buttony itp. miały takie wartości jak zapisaliśmy...
		setLayout(null);
		
                plansza = new Plansza();
                ruchy = new Ruchy();
		bicia = new Bicia();
                
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
                bNowa.addActionListener(this);	
		//wyświetlenie przycisku
                add(bNowa);
                
		bZasad = new Button("Zasady gry");
		bZasad.setSize(100,25);
		bZasad.setLocation(450,145);	
                bZasad.addActionListener(this);	
	        add(bZasad);	  
                
                lTekst = new Label("Gracz:");
		lTekst.setSize(60,25);
		lTekst.setLocation(450,200);
		add(lTekst);
                
       //bez tego nie wyświetlimy okna - służy do wyświetlania okna
       show();
	}
        public void actionPerformed(ActionEvent ev)
	{
		Object 	cel = ev.getSource();
		if (cel == bZasad)
		{
 
                   TextArea pole1 = new TextArea();// nowy obiekt klasy TextField
                   pole1.setBounds(10,370,620,125); // ustawienie położenia komponentu w oknie aplikacji
                   String s = "Zasady gry\n"+
                              "1. Wykonywanie posunięć:\n"+
                              "# Pierwszy ruch wykonuje zawodnik grający białymi.\n"+
                              "# Gracze wykonują posunięcia po jednym ruchu, \nna przemian, własnymi warcabami.\n"+
                              "# Wykonanie ruchu jest obowiązkowe.\n"+
                              "# Kamień porusza się po przekątnej tylko do przodu\n na wolne pole następnej linii.\n"+
                              "# W sytuacji, gdy kamień staje na pole przemiany \n(dla białych to pola: b8, d8, f8, h8; dla czarnych: \na1, c1, e1, g1) staje się damką.\n"+
                              "# Damka porusza się po przekątnych we wszystkich \nkierunkach na dowolne wolne pole.\n"+
                              "\n"+
                              "2. Bicie:\n"+
                              "# Jeżeli kamień znajduje się w sąsiedztwie po \nprzekątnej przeciwnika, za którym jest wolne pole, \nto może on przeskoczyć ten kamień i zająć to wolne \npole. Kamień, który został przeskoczony, zostaje\n usunięty.\n"+
                              "# Bicie wykonuje się do przodu lub do tyłu.\n"+
                              "# Bicie jest obowiązkowe.\n"+
                              "# W wypadku, gdy istnieje wybór pomiędzy zbiciem \nróżnych ilości warcab przeciwnika, \nto obowiązkowe jest bicie większej ilości warcab.\n"+
                              "# Jeżeli zwykły kamień w czasie bicia przechodzi \nprzez jedno z pól przemiany i kontynuuje bicie,\n to nie zamienia się w damkę i nadal \npozostaje kamieniem.\n"+
                              "\n"+
                              "3. Cel i rezultat gry\n"+
                              "# Celem gry jest uniemożliwienie przeciwnikowi \nwykonania posunięcia poprzez zbicie lub\n zablokowanie jego warcab. \nW pozostałych przypadkach partię uznaje się \nza remisową.\n"+                              
                              "\n"+
                              "źródło: http://pl.wikipedia.org/wiki/Warcaby_klasyczne";
                              
                   pole1.setText(s);
                   add(pole1);
                   repaint();
		}
                else if (cel == bNowa)
		{
			plansza.rozpoczecie();
                        ruchy.zerowanie();
			ruchy.set_gracz(1);
			bicia.zerowanie();
			repaint();
		} 
	}
        public void paint(Graphics g)
	{
		RysujPlansze(g);
	}	
	
	public void RysujPlansze(Graphics g)
	{	
		Image img = createImage(getSize().width,getSize().height);
		
		Graphics2D g2 = (Graphics2D) img.getGraphics();
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
                //czarne ramki (obramowanie kwadratów)
		g2.setColor(Color.black);
		g2.fillRect(18,38,322,322);	
				
		for (int j = 0; j < 8; j++)
		{
			for (int i = 0; i < 8; i++)	
			{
                            //Wyświetlenie szachownicy....................................................................
				if (plansza.pole[i][j] == 0) 
					g2.setColor(new Color(0,0,0)); //jasny 220,205,145
				else 
					g2.setColor(new Color(255,255,255)); //ciemny 95, 145, 95			
				g2.fillRect(20 + 40*i, 40 + 40*j,38,38);	
                            //............................................................................................
			    //wyświetlenie pionków........................................................................
				if (plansza.pole[i][j] > 1) 
				{
					g2.setColor(Color.black);
					g2.fillOval(21 + 40*i, 41 + 40*j,36,36);
					
					if (plansza.pole[i][j] == 2 || plansza.pole[i][j] == 4) 
						g2.setColor(new Color(255,0,0)); //245, 240, 240
					if (plansza.pole[i][j] == 3 || plansza.pole[i][j] == 5) 
						g2.setColor(new Color(0,255,0)); //215, 95, 95
						
					g2.fillOval(23 + 40*i, 43 + 40*j,32,32);
					
					if (plansza.pole[i][j] == 4 || plansza.pole[i][j] == 5) 
					{
						g2.setColor(Color.black);
						g2.fillOval(26 + 40*i, 46 + 40*j,26,26);	
					}
					
				}
			     //............................................................................................			
			}
		}
		// Gracz: aktualne kółko...
		if (ruchy.get_gracz() != 0 )
		{
			g2.setColor(Color.black);
			g2.fillOval(525,200,36,36);
					
			if (ruchy.get_gracz() == 1)
			{
				g2.setColor(new Color(255,0,0));
			}	
			else
			{
				g2.setColor(new Color(0,255,0));
			}
			g2.fillOval(527,202,32,32);
		}			
	
		g.drawImage(img,0,0,this);
		
	}
	
	public void update(Graphics g)
	{
   		paint(g);
 	}
}        
class Tablica
{
	public int pole[][];
	
	public Tablica()
	{
		pole = new int[8][8];
		this.zerowanie();
	}
	
	public void zerowanie()
	{		
		for (int j = 0; j < 8; j++)
		{
			for (int i = 0; i < 8; i++)			
			{
				pole[i][j] = 0;	
			}
		}
	}
        public void wyswietlanie()
	{
		for (int j = 0; j < 8; j++)	
		{
			for (int i = 0; i < 8; i++)
			{
				System.out.print(pole[i][j] + " ");
			}
			System.out.println();
		}
	}
}

class Plansza extends Tablica
{
	public Plansza(){}	
//k=1 ciemne, k=0 jasne (pola szachownicy)
	public void zerowanie()
	{
		int k = 1;
		for (int j = 0; j < 8; j++)	
		{
			k = (k + 1) % 2;
			for (int i = 0; i < 8; i++)
			{	
				pole[i][j] = k;
				k = (k + 1) % 2;
			}
		}		
	}
        
	//ustawienie pionków na szachownicy
	public void rozpoczecie()
	{
		zerowanie();
	//	gracz 2 - gora planszy
		for (int j = 0; j < 3; j++)	
			for (int i = 0; i < 8; i++)
				if (pole[i][j] == 1) pole[i][j] = 3;
	
	//	gracz 1 - dol planszy		
		for (int j = 5; j < 8; j++)	
			for (int i = 0; i < 8; i++)
				if (pole[i][j] == 1) pole[i][j] = 2;
	}
}

class Ruchy extends Tablica
{
	private int gracz;
	
	public Ruchy()
	{
		this.set_gracz(0);
	}

	public void klikniecie(int x,int y,Plansza p, Bicia b)
	{
            //c.d. nastąpi......................................................
        }
	
	public void set_gracz(int gracz)
	{
		this.gracz = gracz;
		System.out.println("Gracz " + this.gracz);
	}
	
	public int get_gracz()
	{
		return this.gracz;	
	}
	
	public void zmiana_gracza()
	{
		if (this.gracz == 1)
		{
			this.set_gracz(2);
		}
		else if (this.gracz == 2)
		{
			this.set_gracz(1);
		}
	}
	
	public void zamien_pionki_na_damki(Plansza p)
	{
		if (gracz == 1)
		{
			for (int i = 0; i < 8; i++)
				if (p.pole[i][0] == 2 ) p.pole[i][0] = 4;
		}
		else if (gracz == 2)
		{
			for (int i = 0; i < 8; i++)
				if (p.pole[i][7] == 3 ) p.pole[i][7] = 5;
		}	
		p.wyswietlanie();	
	}
}

class Bicia extends Tablica
{
  public boolean przymus;
    
    public Bicia()
    {
        przymus = false;
    }
        
    private int get_bicia(int x,int y,Plansza p)
    {
        int wynik = 0;
        
        if (p.pole[x][y] == 2)
        {
            if (x > 1 && y > 1 && (p.pole[x - 1][y - 1] == 3
            || p.pole[x - 1][y - 1] == 5) && p.pole[x - 2][y - 2] == 1)
                wynik = wynik + 1;
                    
            if (x < 6 && y > 1 && (p.pole[x + 1][y - 1] == 3
            || p.pole[x + 1][y - 1] == 5) && p.pole[x + 2][y - 2] == 1)
                wynik = wynik + 2;
                                            
            if (x > 1 && y < 6 && (p.pole[x - 1][y + 1] == 3
            || p.pole[x - 1][y + 1] == 5) && p.pole[x - 2][y + 2] == 1)
                wynik = wynik + 4;    
                        
            if (x < 6 && y < 6 && (p.pole[x + 1][y + 1] == 3
            || p.pole[x + 1][y + 1] == 5) && p.pole[x + 2][y + 2] == 1)
                wynik = wynik + 8;    
        }
        else if (p.pole[x][y] == 3)
        {
            if (x > 1 && y < 6 && (p.pole[x - 1][y + 1] == 2
            || p.pole[x - 1][y + 1] == 4) && p.pole[x - 2][y + 2] == 1)
                wynik = wynik + 1;    
                        
            if (x < 6 && y < 6 && (p.pole[x + 1][y + 1] == 2
            || p.pole[x + 1][y + 1] == 4) && p.pole[x + 2][y + 2] == 1)
                wynik = wynik + 2;
                    
            if (x > 1 && y > 1 && (p.pole[x - 1][y - 1] == 2
            || p.pole[x - 1][y - 1] == 4) && p.pole[x - 2][y - 2] == 1)
                wynik = wynik + 4;
                    
            if (x < 6 && y > 1 && (p.pole[x + 1][y - 1] == 2
            || p.pole[x + 1][y - 1] == 4) && p.pole[x + 2][y - 2] == 1)
                wynik = wynik + 8;
        }
        else if (p.pole[x][y] == 4)
        {
            int i = 0; int j = 0;
            while (x+i > 1 && y+j > 1)
            {
                if ((p.pole[x+i - 1][y+j - 1] == 3 || p.pole[x+i - 1][y+j - 1] == 5) 
                    && p.pole[x+i - 2][y+j - 2] == 1)
                {
                    wynik = wynik + 1;    
                    break;    
                }
                else if (p.pole[x+i - 1][y+j - 1] != 1)
                {
                    break;    
                }
                else 
                {
                    --i;
                    --j;                    
                }
            }
            
            i = 0; j = 0;
            
            while (x+i < 6 && y+j > 1)
            {        
                if ((p.pole[x+i + 1][y+j - 1] == 3 || p.pole[x+i + 1][y+j - 1] == 5) 
                    && p.pole[x+i + 2][y+j - 2] == 1)
                {
                    wynik = wynik + 2;
                    break;    
                }
                else if (p.pole[x+i + 1][y+j - 1] != 1)
                {
                    break;    
                }
                else
                {
                    ++i;
                    --j;    
                }
            }    
            
            i = 0; j = 0;
            
            while (x+i > 1 && y+j < 6)
            {
                if ((p.pole[x+i - 1][y+j + 1] == 3 || p.pole[x+i - 1][y+j + 1] == 5) 
                    && p.pole[x+i - 2][y+j + 2] == 1)    
                {
                    wynik = wynik + 4;            
                    break;    
                }
                else if (p.pole[x+i - 1][y+j + 1] != 1)
                {
                    break;    
                }
                else
                {
                    --i;
                    ++j;    
                }                
            }
            
            i = 0; j = 0;        
                                        
            while (x+i < 6 && y+j < 6)    
            {
                if ((p.pole[x+i + 1][y+i + 1] == 3 || p.pole[x+i + 1][y+j + 1] == 5) 
                    && p.pole[x+j + 2][y+j + 2] == 1)
                {
                    wynik = wynik + 8;        
                    break;    
                }
                else if (p.pole[x+i + 1][y+j + 1] != 1)
                {
                    break;    
                }
                else
                {
                    ++i;
                    ++j;    
                }
            }            
            
        } 
        else if (p.pole[x][y] == 5)
        {
            int i = 0; int j = 0;
            while (x+i > 1 && y+j > 1)
            {
                if ((p.pole[x+i - 1][y+j - 1] == 2 || p.pole[x+i - 1][y+j - 1] == 4) 
                    && p.pole[x+i - 2][y+j - 2] == 1)
                {
                    wynik = wynik + 4;    
                    break;    
                }
                else if (p.pole[x+i - 1][y+j - 1] != 1)
                {
                    break;    
                }
                else 
                {
                    --i;
                    --j;                    
                }
            }
            
            i = 0; j = 0;
            
            while (x+i < 6 && y+j > 1)
            {        
                if ((p.pole[x+i + 1][y+j - 1] == 2 || p.pole[x+i + 1][y+j - 1] == 4) 
                    && p.pole[x+i + 2][y+j - 2] == 1)
                {
                    wynik = wynik + 8;
                    break;    
                }
                else if (p.pole[x+i + 1][y+j - 1] != 1)
                {
                    break;    
                }
                else
                {
                    ++i;
                    --j;    
                }
            }    
            
            i = 0; j = 0;
            
            while (x+i > 1 && y+j < 6)
            {
                if ((p.pole[x+i - 1][y+j + 1] == 2 || p.pole[x+i - 1][y+j + 1] == 4) 
                    && p.pole[x+i - 2][y+j + 2] == 1)    
                {
                    wynik = wynik + 1;            
                    break;    
                }
                else if (p.pole[x+i - 1][y+j + 1] != 1)
                {
                    break;    
                }
                else
                {
                    --i;
                    ++j;    
                }                
            }
            
            i = 0; j = 0;        
                                        
            while (x+i < 6 && y+j < 6)    
            {
                if ((p.pole[x+i + 1][y+i + 1] == 2 || p.pole[x+i + 1][y+j + 1] == 4) 
                    && p.pole[x+j + 2][y+j + 2] == 1)
                {
                    wynik = wynik + 2;        
                    break;    
                }
                else if (p.pole[x+i + 1][y+j + 1] != 1)
                {
                    break;    
                }
                else
                {
                    ++i;
                    ++j;    
                }
            }            
            
        }
        
        if (wynik != 0) przymus = true;
        
        return wynik;
    }
    
    public void sprawdz_bicia(int gracz, Plansza p)
    {
        zerowanie();
        przymus = false;
        
        for (int j = 0; j < 8; j++)    
            for (int i = 0; i < 8; i++)
                if ((gracz + 1) == p.pole[i][j] || (gracz + 3) == p.pole[i][j])
                {
                    this.pole[i][j] = get_bicia(i,j,p);
                }
                else
                {
                    this.pole[i][j] = 0;
                }                
//        this.wyswietlanie();
    }
    
    public void sprawdz_bicia(int gracz, Plansza p, int x, int y)
    {
        zerowanie();
        przymus = false;
        
        this.pole[x][y] = get_bicia(x,y,p);
    }
}
