import java.sql.*;
import java.io.*;


/**
 *  NOM, Prenom 1 :
 *  NOM, Prenom 2 :
 *  Binome        :
 *  Groupe        :
 *
 * La classe Sponsor
 **/
public class Sponsor {
    
    /* parametres de connexion */

    String url1 = "jdbc:oracle:thin:@db-oracle.ufr-info-p6.jussieu.fr:1521:oracle"; // base tennis

    // remplacer 1234567 par votre numéro
    String user = "E3874034";

    // remplacer 1234567 par votre numéro
    String password = "E3874034";
    
    Connection connexionTennis = null;

    String url2 = "jdbc:oracle:thin:@oracle.ufr-info-p6.jussieu.fr:1521:ora10"; // base des sponsors
    String user2 ="anonyme";             // acces anonyme
    String password2 ="anonyme";
    Connection connexionSponsor = null;

    PrintStream out = System.out;    // affichage des resulats a l'ecran
    
    /**
     * methode main: point d'entrée
     **/
    public static void main(String a[]) {
        
        /* initialisation */
        Sponsor c = new Sponsor();
        
        /* test des méthodes que vous avez écrites */
        c.traiteRequete();
    }
    
    /**
     * Constructeur : initialisation
     **/
    private Sponsor(){
        try {
            /* Chargement du pilote JDBC */
	    Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch(Exception e) {
            Outil.erreurInit(e);
        }
    }
    
    
    /**
     *  La methode traiteRequete
     *  affiche le resultat d'une requete
     */
    public void traiteRequete() {
        try {

	    /* à compléter */
        	
        	try {
            	connexionSponsor = DriverManager.getConnection(url2, user2, password2);
            	out.println("Successfully connected to the SponsorDB");
            	}catch (Exception e ){
            		out.println("Could not find the db1");
            		
            	}
            	try {
            	connexionTennis = DriverManager.getConnection(url1, user, password);
            	out.println("Successfully connected to the TennisDB");
            	}catch (Exception e ){
            		out.println("Could not find the db2");
            		
    }
        	
        	String r1 = "Select nom,nationalite,sponsor"
        			  + "From Joueur j, Gain g"
        			  + "Where j.nujoueur = g.nujoueur"
        			  + "Order By nom";
        	String r2 = "Select nom,nationalite from Sponsor";
        	Statement lectureT =  connexionTennis.createStatement();
        	Statement lectureS = connexionSponsor.createStatement();
        	ResultSet resultatT = lectureT.executeQuery(r1);
        	ResultSet resultatS = lectureS.executeQuery(r2);
        	System.out.println("here");
        	while(resultatT.next()) {
        		System.out.println("here again");
        		while(resultatS.next()) {
        			if(resultatT.getString(3).equals(resultatS.getString(1))) {
        				out.println("Nom Joueur : "+resultatT.getString(1)+" Nat joueur : "+resultatT.getString(2));
        				out.println("Nom Sponsor : "+resultatS.getString(1)+" Nat sponsor : "+resultatS.getString(1));
        			}
        		}
        	}
        	
        	resultatT.close();
    	    lectureT.close();
    	    connexionTennis.close();
    	    
    	    resultatS.close();
    	    lectureS.close();
    	    connexionSponsor.close();
	}
	/* getion des exceptions */
	catch(Exception e){ gestionDesErreurs(e);}
    }
    
    //---------------------------------------------------------------------
    
    /* méthodes pour la gestion des erreurs */
    
    protected void gestionDesErreurs(Exception e) {
	out.println("Probleme d'acces a la base: " + e);
	
	/* pour facilier le débogage,
	   afficher la ligne ou l'erreur s'est produite*/
	e.printStackTrace();
	
	/* En cas de pb d'acces, on ferme la connexion */
	try {
	    if (connexionTennis != null)
		connexionTennis.close();
	    if (connexionSponsor != null)
		connexionSponsor.close();
	}
	catch(Exception se) {
	    out.println("Tout autre probleme: " + se);
	}
        throw new RuntimeException("Arret immediat");
    }
    
}
