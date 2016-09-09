package rattco;

import rattco.client.JFrameConnexion;

/**
 * 
 * @author Chaperon Vincent, Droxler Arnaud, Perez Joaquim
 * 
 * RATTCO
 * Ramasse des Armes par Terre et Tire sur tes Copains Online
 * Projet d�velopp� au sein de la HE-Arc Ing�nierie en ao�t et septembre 2016
 * Il sagit d'un jeu de tir � la premi�re personne en ligne. La logique du jeu est en 
 * deux dimension mais le rendu en 3D gr�ce � la technique du ray-casting
 * 
 * Si vous reprenez le projet, faites attention � inclure kryonet-2.21-all.jar dans votre
 * ClassPath et � avoir � la racine de votre projet les dossier "sprite" et "maps"
 *
 */
public class MainClient {

	/**
	 * Point d'entr�e du client. Une bo�te de dialogue est ouverte. Le client doit
	 * y choisir un serveur h�te, un pseudo et une r�solution d'image
	 */
	public static void main(String[] args) {
		new JFrameConnexion();
	}

}
