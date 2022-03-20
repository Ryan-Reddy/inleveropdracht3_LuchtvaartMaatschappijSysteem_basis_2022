package main.domeinLaag;
import java.util.*;

public class Vlucht
{
   private static HashSet<Vlucht> alleVluchten = new HashSet<Vlucht>();
   private static int hoogsteVluchtNummer = 1;
   private int vluchtNummer;
   private Vliegtuig vliegtuig;
   private Luchthaven bestemming;
   private Luchthaven vertrekpunt;
   private Calendar vertrekTijd;
   private Calendar aankomstTijd;
   private Calendar duur;


   public static TreeMap<Integer, Vlucht> geefAlle() {
	   TreeMap<Integer, Vlucht> alleVluchten = new TreeMap<Integer, Vlucht>();
	   for (Vlucht vlucht : Vlucht.alleVluchten) {
	   		int vluchtNummer = vlucht.getVluchtNummer();
	   		alleVluchten.put(vluchtNummer, vlucht);
	   }
	   return alleVluchten;
   }

   /** Controleert of het vliegtuig op het meegegeven tijdstip al een vlucht heeft.
    * @return True, als vliegtuig bezet. Anders false. */
   private static boolean isBezet(Vliegtuig vliegtuig, Calendar d) {
	   boolean b = false;
	   for (Vlucht v : alleVluchten) {
		   if (v.vliegtuig.equals(vliegtuig)) {
			   if (v.getVertrekTijd().before(d) && v.getAankomstTijd().after(d))
				   b = true;
		   }
	   }
	   return b;
   }


   public Vlucht() {
	   zetVluchtNummer();
   }

   public Vlucht(Vliegtuig vt, Luchthaven vertrekp, Luchthaven best, Calendar vertrekTijd, Calendar aankomstTijd) {
	   if (vt != null && vertrekp != null && best != null && vertrekTijd != null && aankomstTijd != null){

	   Calendar aTijd = vertrekTijd;
	   Calendar vTijd = aankomstTijd;
	   aTijd.setLenient(false);
	   vTijd.setLenient(false);
	   // Ter controle of het een juiste datum is. Gebeurt niet bij het zetten, maar bij het getten.
	   try {
		   @SuppressWarnings("unused")
		   Date adatum = aTijd.getTime();
		   Date vdatum = vTijd.getTime();
	   } catch (IllegalArgumentException e) {
		   throw new VluchtException("Geen geldig tijdstip!");
	   }
// check moment vertrek < moment aankomst:
	   if (vertrekTijd.toInstant().isBefore(aankomstTijd.toInstant())) {

		   zetVluchtNummer();
		   this.vliegtuig = vt;
		   this.vertrekpunt = vertrekp;
		   this.bestemming = best;
		   this.vertrekTijd = (Calendar) vertrekTijd.clone();
		   this.aankomstTijd = (Calendar) aankomstTijd.clone();
		   alleVluchten.add(this);
	   }
	   else if (aankomstTijd.toInstant().isBefore(vertrekTijd.toInstant())) {
		   throw new VluchtException("Aankomstdatum kan niet gebeuren voor vertrekdatum!");
	   }


	   /*
	   if aankomstTijd.before(Calendar.getInstance()){
		   throw new VluchtException("Aankomsttijd ligt in het verleden");
	   }
	   if (aankomstTijd.before(getVertrekTijd())) {
		   throw new VluchtException("Aankomsttijd ligt voor vertrektijd");
	   }
	   if (vertrekTijd.before(Calendar.getInstance())) {
		   System.out.println(Calendar.getInstance());
		   throw new VluchtException("Vertrektijd ligt in het verleden");}*/

	   } else {
		   throw new VluchtException("Missende gegevens voor een nieuwe vlucht.");
	   }

   }

   public void zetVliegtuig(Vliegtuig vt) {
	   this.vliegtuig = vt;
   }

	public void zetVertrekpunt(Luchthaven vertrekpunt) {
		this.vertrekpunt = vertrekpunt;
	}

   /**
    * Controleer dat bestemming <> vertrekpunt.
    */
   public void zetBestemming(Luchthaven best) {
		if (best == null) {
			this.bestemming = best;
		} else {
			if (best != this.vertrekpunt)
				this.bestemming = best;
			else
				throw new IllegalArgumentException("bestemming en vertrek zijn gelijk");
		}
   }
   
   /**
    * Controleer dat de vertrektijd niet overlapt met een andere vlucht van het toestel.
    * @param tijd
    */
   public void zetVertrekTijd(Calendar tijd) throws VluchtException {
	   if (tijd == null) {
		   vertrekTijd = null;
	   } else {if (tijd.before(Calendar.getInstance())) {
		   throw new VluchtException("Vertrektijd ligt in het verleden");
	   }
	   Calendar vTijd = tijd;
		   vTijd.setLenient(false);
		   // Ter controle of het een juiste datum is. Gebeurt niet bij het zetten, maar bij het getten.
		   try {
			   @SuppressWarnings("unused")
			   Date datum = vTijd.getTime();
		   } catch (IllegalArgumentException e) {
			   throw new VluchtException("Geen geldig tijdstip!");
		   }
		   if (!Vlucht.isBezet(vliegtuig, vTijd)) {
			   vertrekTijd = (Calendar) vTijd.clone();
		   } else
			   throw new VluchtException("Vliegtuig reeds bezet op " + tijd.getTime());
	   }
   }
   
   public Calendar getVertrekTijd() {
   		return vertrekTijd;
   }
   
   /**
    * Controleer dat aankomstTijd > vertrekTijd.
    */
   public void zetAankomstTijd(Calendar tijd) throws VluchtException {
   		if (tijd == null) {
			aankomstTijd = null;
		} else {
			Calendar aTijd = tijd;
			aTijd.setLenient(false);
			// Ter controle of het een juiste datum is. Gebeurt niet bij het zetten, maar bij het getten.
			try {
				@SuppressWarnings("unused")
				Date datum = aTijd.getTime();
			} catch (IllegalArgumentException e) {
				throw new VluchtException("Geen geldig tijdstip!");
			}
			if (aTijd.after(vertrekTijd))
				aankomstTijd = (Calendar) aTijd.clone();
			else
				throw new VluchtException("Aankomsttijd voor vertrektijd");
		}
   }
   
	public Vliegtuig getVliegtuig() {
		return vliegtuig;
	}
	public Calendar getAankomstTijd() {
	   return aankomstTijd;
	  }
   
   /**
    * Controleer of alle gegevens gezet zijn. Zo ja, bewaar de vluchtgegevens.
    */
   public void bewaar() throws VluchtException {
	   if(vliegtuig == null)
		   throw new VluchtException("Geen geldige vliegtuig.");
   		else if(bestemming == null)
   			throw new VluchtException("Geen geldige bestemming.");
		else if(aankomstTijd == null)
			throw new VluchtException("Geen geldige aankomsttijd.");
		else if(vertrekTijd == null)
			throw new VluchtException("Geen geldige vertrektijd.");			
   		else 
			alleVluchten.add(this);
   }

	public Luchthaven getBestemming() {
		return bestemming;
	}

	public Luchthaven getVertrekPunt() {
		return vertrekpunt;
	}


	   
	private void zetVluchtNummer() {
		vluchtNummer = hoogsteVluchtNummer;
		hoogsteVluchtNummer ++;
	}

	public int getVluchtNummer() {
   		return vluchtNummer;
	}
	
	@Override
	public String toString() {
		return "Vlucht [vluchtNummer=" + vluchtNummer + ", vt=" + vliegtuig
				+ ", bestemming=" + bestemming + ", vertrekpunt=" + vertrekpunt
				+ ", vertrekTijd=" + vertrekTijd + ", aankomstTijd="
				+ aankomstTijd + ", duur=" + duur + "]";
	}
	
}
