import java.sql.*;
import java.io.*;


/**
 *  NOM, Prenom 1 :
 *  NOM, Prenom 2 :
 *  Binome        :
 *  Groupe        :
 *
 * La classe Generique
 **/
public class GeneriqueHTML {
    
    /* les attributs */
    
    String server = "db-oracle.ufr-info-p6.jussieu.fr";
    String port = "1521";
    String database = "oracle";

    // remplacer 1234567 par votre numéro
    String user = "E3874034";

    // remplacer 1234567 par votre numéro
    String password = "E3874034";
    
    Connection connexion = null;
    static PrintStream out = System.out;    // affichage des résulats à l'ecran
    
    /**
     * methode main: point d'entrée
     **/
    public static void main(String param[]) {

    	
	if (param.length == 0) {
	    throw new RuntimeException("Pas de  requete, arret immediat");
	}


	/* requete */
	String requete = param[0];

        /* initialisation */
        GeneriqueHTML c = new GeneriqueHTML();
        
	/* requete */
	c.traiteRequete(requete);
    }
    
    /**
     * Constructeur : initialisation
     **/
    private GeneriqueHTML(){
        try {

            /* Chargement du pilote JDBC */
	    /* driver Oracle */
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
    public void traiteRequete(String requete) {
        try {

	    /* à compléter */
        	String url = "jdbc:oracle:thin:@" + server + ":" + port + ":" + database;
        	connexion = DriverManager.getConnection(url,user,password);
        	//String requeteSQL = "select * from Joueur";
        	Statement stmt =  connexion.createStatement();
        	ResultSet rs = stmt.executeQuery(requete);
        	ResultSetMetaData rsmd = rs.getMetaData();
            
        	StringBuffer html = new StringBuffer();
        	
        	html.append("<?xml version=\"1.0\" encoding=\"ISO-\n" + 
        			"8859-\n" + 
        			"1\"   ?> \n" + 
        			"<!DOCTYPE html PUBLIC \"-\n" + 
        			"//W3C//DTD XHTML 1.0 Transitional//EN\" \n" + 
        			"\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-\n" + 
        			"transitional.dtd\"> \n" + 
        			"<html> \n" + 
        			"  <head >\n" + 
        			"<title>Résultat\n" + 
        			"</title>\n" + 
        			"</head>\n" + 
        			"<body> ");
        	html.append("<h3>La requete est : </h3> "+requete);
        	html.append("<h3>le resultat est : </h3>");
        	html.append("  <" + 
        			"table" + 
        			" border=\"2\"> ");
        	html.append("<tr>");
        	for(int i=1; i<=rsmd.getColumnCount(); i++) {
        		html.append("<th>"+rsmd.getColumnName(i)+"</th>");
        	}
        	html.append("</tr>");
        	
        	while(rs.next()) {
        		html.append("<tr>");
        		for(int i=1; i<=rsmd.getColumnCount(); i++) {
        			html.append("<td>"+rs.getString(i)+"</td>");
        		}
        		html.append("</tr>");
        	}
            
        	html.append("  </table> \n" + 
        			"</body> \n" + 
        			"</html> ");
        	
        	out.println(html.toString());
        	
        }

        /* getion des exceptions */
        catch(Exception e){ Outil.gestionDesErreurs(connexion, e);}
    }
}
