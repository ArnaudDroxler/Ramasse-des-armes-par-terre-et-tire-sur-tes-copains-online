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
 * Projet développé au sein de la HE-Arc Ingénierie en août et septembre 2016
 * Il sagit d'un jeu de tir à la première personne en ligne. La logique du jeu est en 
 * deux dimension mais le rendu en 3D grâce à la technique du ray-casting
 * 
 * Si vous reprenez le projet, faites attention à inclure kryonet-2.21-all.jar dans votre
 * ClassPath et à avoir à la racine de votre projet les dossier "sprite" et "maps"
 *
 */
public class MainServer {

	/**
	 * Point d'entrée du serveur. On peut lancer les serveur avec des paramètres
	 * en ligne de commande. Si il n'y a pas de paramètres, une boîte de dialogue
	 * s'ouvre.
	 * Les paramètres sont le nom de la map, le nombres de joueur maximum et le
	 * temps d'une partie en minute. A la fin de la partie. Le serveur en recrée
	 * une et y reconnecte les précédents clients
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
