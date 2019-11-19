import java.sql.*;
import java.io.*;


/**
 *  NOM, Prenom 1 :
 *  NOM, Prenom 2 :
 *  Binome        :
 *  Groupe        :
 *
 * La classe MaxPrime2
 **/

public class MaxPrimeParPays {

    String server = "db-oracle.ufr-info-p6.jussieu.fr";
    String port = "1521";
    String database = "oracle";

   // remplacer 1234567 par votre numéro
    String user = "E3874034";

    // remplacer 1234567 par votre numéro
    String password = "E3874034";

    Connection connexion = null;
    public static PrintStream out = System.out;    // affichage des résulats à l'ecran

    /**
     * methode main: point d'entrée
     **/
    
    public static void main(String a[]) {
        MaxPrimeParPays c = new MaxPrimeParPays();
        c.traiteRequete();
    }
 
   
    /**
     * Constructeur : initialisation
     **/
    
    private MaxPrimeParPays(){
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
     *  
     */
    
    public void traiteRequete() {
    	
    	try {
        	
		    String url = "jdbc:oracle:thin:@" + server + ":" + port + ":" + database;
		    out.println("Connexion avec l'URL: " + url);
		    out.println("utilisateur: " + user + ", mot de passe: " + password);
	        connexion = DriverManager.getConnection(url, user, password);
	
		    String requete = "select nom, nationalite, max(prime) from Gain g, Joueur j"
			+ " where g.nujoueur = j.nujoueur " + " group by nom,nationalite " + "order by nationalite" ;
	            
		    /* Commentaire: */
		    out.println("statement...");
	            Statement lecture =  connexion.createStatement();
	            
		    out.println("execute la requete : " + requete);
	            ResultSet resultat = lecture.executeQuery(requete);
	            
		    out.println("resultat...");
	            while (resultat.next()) {
	            	System.out.println("Nationalite = "+resultat.getString(2));
	            	System.out.println("JOUEUR | MAX PRIME");
	                String tuple = resultat.getString(1) + "\t" + resultat.getString(3);
	                out.println(tuple);
	            }
	            
		    resultat.close();
		    lecture.close();
		    connexion.close();
	    
        }

        /* gestion des exceptions */
        catch(Exception e){ Outil.gestionDesErreurs(connexion, e);}

    }
}
