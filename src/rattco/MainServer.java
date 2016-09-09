package rattco;

import rattco.server.JFrameConfiguration;
import rattco.server.PcServer;
/**
 * 
 * @author Chaperon Vincent, Droxler Arnaud, Perez Joaquim
 *  	   ___   ___  ______ ______ _____ ____ 
 *  	  / _ \ / _ |/_  __//_  __// ___// __ \
 *  	 / , _// __ | / /    / /  / /__ / /_/ /
 *  	/_/|_|/_/ |_|/_/    /_/   \___/ \____/ 
 *                                     
 * Ramasse des Armes par Terre et Tire sur tes Copains Online
 * Projet d�velopp� au sein de la HE-Arc Ing�nierie en ao�t et septembre 2016
 * Il sagit d'un jeu de tir � la premi�re personne en ligne. La logique du jeu est en 
 * deux dimension mais le rendu en 3D gr�ce � la technique du ray-casting
 * 
 * Si vous reprenez le projet, faites attention � inclure kryonet-2.21-all.jar dans votre
 * ClassPath et � avoir � la racine de votre projet les dossier "sprite" et "maps"
 *
 */
public class MainServer {

	/**
	 * Point d'entr�e du serveur. On peut lancer les serveur avec des param�tres
	 * en ligne de commande. Si il n'y a pas de param�tres, une bo�te de dialogue
	 * s'ouvre.
	 * Les param�tres sont le nom de la map, le nombres de joueur maximum et le
	 * temps d'une partie en minute. A la fin de la partie. Le serveur en recr�e
	 * une et y reconnecte les pr�c�dents clients
	 * exemple : java -cp .:./*:./ext/* rattco.MainServer maps/dust 8 5
	 */
	public static void main(String[] args) {
		if(args.length == 0){
			new JFrameConfiguration();
		}else{
			new PcServer(args);
		}
	}

}
