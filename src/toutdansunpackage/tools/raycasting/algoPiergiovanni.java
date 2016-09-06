package toutdansunpackage.tools.raycasting;

import toutdansunpackage.tools.map.LvlMap;

public class algoPiergiovanni {

	public static double algoRaycasting(Vector2D pos, Vector2D dir, LvlMap map) {

		double rayPosX = pos.getdX();
		double rayPosY = pos.getdY();
		double rayDirX = dir.getdX();
		double rayDirY = dir.getdY();

		int mapX = (int) rayPosX;
		int mapY = (int) rayPosY;

		double sideDistX;
		double sideDistY;

		double deltaDistX = Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
		double deltaDistY = Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));
		double perpWallDist;

		int stepX;
		int stepY;

		int hit = 0;
		int side = 0;

		if (rayDirX < 0) {
			stepX = -1;
			sideDistX = (rayPosX - mapX) * deltaDistX;
		} else {
			stepX = 1;
			sideDistX = (mapX + 1.0 - rayPosX) * deltaDistX;
		}
		if (rayDirY < 0) {
			stepY = -1;
			sideDistY = (rayPosY - mapY) * deltaDistY;
		} else {
			stepY = 1;
			sideDistY = (mapY + 1.0 - rayPosY) * deltaDistY;
		}
		
		while (hit == 0) {
			if (sideDistX < sideDistY) {
				sideDistX += deltaDistX;
				mapX += stepX;
				side = 0;
			} else {
				sideDistY += deltaDistY;
				mapY += stepY;
				side = 1;
			}
			if (map.inWall(mapX, mapY)) {
				hit = 1;
			}
		}
		if (side == 0) {
			perpWallDist = (mapX - rayPosX + (1 - stepX) / 2) / rayDirX;
		} else {
			perpWallDist = (mapY - rayPosY + (1 - stepY) / 2) / rayDirY;
		}
		return perpWallDist;
	}
}
