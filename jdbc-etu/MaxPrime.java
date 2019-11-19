import java.sql.*;
import java.io.*;


/**
 *  NOM, Prenom 1 :
 *  NOM, Prenom 2 :
 *  Binome        :
 *  Groupe        :
 *
 * La classe MaxPrime
 **/
public class MaxPrime {

    String server = "db-oracle.ufr-info-p6.jussieu.fr";
    String port = "1521";
    String database = "oracle";

   // remplacer 1234567 par votre numéro
    String user = "E1234567";

    // remplacer 1234567 par votre numéro
    String password = "E1234567";

    Connection connexion = null;
    public static PrintStream out = System.out;    // affichage des résulats à l'ecran

    /**
     * methode main: point d'entrée
     **/
    public static void main(String a[]) {
        MaxPrime c = new MaxPrime();
	c.traiteRequete();
    }
 
   
    /**
     * Constructeur : initialisation
     **/
    private MaxPrime(){
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

	    /* Commentaire: */
	    String a1 = Outil.lireValeur("Saisir une annee");
	    String requete = "select nom, max(prime) from Gain g, Joueur j"
		+ " where g.nujoueur = j.nujoueur and annee = " + a1 + " group by nom";
            
	    /* Commentaire: */
	    out.println("statement...");
            Statement lecture =  connexion.createStatement();
            
	    out.println("execute la requete : " + requete);
            ResultSet resultat = lecture.executeQuery(requete);
            
	    out.println("resultat...");
            while (resultat.next()) {
                String tuple = resultat.getString(1) + "\t" + resultat.getString(2);
                out.println(tuple);
            }
	    resultat.close();
	    lecture.close();
	    connexion.close();
        }

        /* getion des exceptions */
        catch(Exception e){ Outil.gestionDesErreurs(connexion, e);}
    }
}
