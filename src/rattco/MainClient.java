package rattco;

import rattco.client.JFrameConnexion;

/**
 * 
 * @author Chaperon Vincent, Droxler Arnaud, Perez Joaquim
 * 
 * RATTCO
 * Ramasse des Armes par Terre et Tire sur tes Copains Online
 * Projet développé au sein de la HE-Arc Ingénierie en août et septembre 2016
 * Il sagit d'un jeu de tir à la première personne en ligne. La logique du jeu est en 
 * deux dimension mais le rendu en 3D grâce à la technique du ray-casting
 * 
 * Si vous reprenez le projet, faites attention à inclure kryonet-2.21-all.jar dans votre
 * ClassPath et à avoir à la racine de votre projet les dossier "sprite" et "maps"
 *
 */
public class MainClient {

	/**
	 * Point d'entrée du client. Une boîte de dialogue est ouverte. Le client doit
	 * y choisir un serveur hôte, un pseudo et une résolution d'image
	 */
	public static void main(String[] args) {
		new JFrameConnexion();
	}

}
