package multi.tools.raycasting;

/**
 * ======================================================== Vector2D.java:
 * Source code for two-dimensional vectors
 *
 * Written by: Mark Austin November, 2005
 * ========================================================
 */

public class Vector2D {

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public Vector2D() {
		dX = dY = 0.0;
	}

	public Vector2D(double dX, double dY) {
		this.dX = dX;
		this.dY = dY;
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/


	// Convert vector to a string ...
	@Override
	public String toString() {
		return "Vector2D(" + dX + ", " + dY + ")";
	}

	// Compute magnitude of vector ....

	public double length() {
		return Math.sqrt(dX * dX + dY * dY);
	}

	// Sum of two vectors ....

	public Vector2D add(Vector2D v1) {
		Vector2D v2 = new Vector2D(this.dX + v1.dX, this.dY + v1.dY);
		return v2;
	}

	// Subtract vector v1 from v .....

	public Vector2D sub(Vector2D v1) {
		Vector2D v2 = new Vector2D(this.dX - v1.dX, this.dY - v1.dY);
		return v2;
	}

	// Scale vector by a constant ...

	public Vector2D scale(double scaleFactor) {
		Vector2D v2 = new Vector2D(this.dX * scaleFactor, this.dY * scaleFactor);
		return v2;
	}

	// Normalize a vectors length....

	public Vector2D normalize() {
		Vector2D v2 = new Vector2D();

		double length = norm();
		if (length != 0) {
			v2.dX = this.dX / length;
			v2.dY = this.dY / length;
		}

		return v2;
	}
	
	public double norm(){
		return Math.sqrt(this.dX * this.dX + this.dY * this.dY);
	}

	// Dot product of two vectors .....

	public double dotProduct(Vector2D v1) {
		return this.dX * v1.dX + this.dY * v1.dY;
	}

	/**
	 * ======================================================== Code ajouté par
	 * le groupe dans le cadre du Projet p2
	 *
	 * Written by: Chaperon Vincent, Maxime Piergiovanni
	 * ========================================================
	 */

	/**
	 * 
	 * Rotation par la matrice de rotation {cos[alpha] -sin[alpha]} {sin[alpha]
	 * cos[alpha]}
	 * 
	 * @param :
	 *            angle de rotation en radians.
	 */
	public Vector2D rotate(double alpha) {
		Vector2D v2 = new Vector2D();
		v2.dX = (this.dX * Math.cos(alpha)) - (dY * Math.sin(alpha));
		v2.dY = (this.dX * Math.sin(alpha)) + (dY * Math.cos(alpha));

		return v2;
	}

	/**
	 * Multiplication d'un vecteur par une matrice
	 * 
	 * @param double[][]
	 *            matrice
	 */
	public Vector2D multiplyByMatrice(double[][] matrice) {
		Vector2D v2 = new Vector2D();
		v2.dX = (this.dX * matrice[0][0]) + (dY * matrice[0][1]);
		v2.dY = (this.dX * matrice[1][0]) + (dY * matrice[1][1]);

		return v2;
	}

	/**
	 * Calcule l'angle par rapport à l'horizontale.
	 * 
	 * @return theta entre 0 et 2PI
	 */
	public double getTheta() {
		double theta = Math.atan2(dY, dX);
		return theta < 0 ? theta + (2 * Math.PI) : theta;
	}
	
	public Vector2D mult(double d) {
		return new Vector2D(d*dX,d*dY);
	}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setdX(double x) {
		dX = x;
	}

	public void setdY(double y) {
		dY = y;
	}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public double getdX() {
		return dX;
	}

	public double getdY() {
		return dY;
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	protected double dX;
	protected double dY;

}
