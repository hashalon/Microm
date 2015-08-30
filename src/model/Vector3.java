package model;

public class Vector3 {

	public int x;
	public int y;
	public int z;
	
	public Vector3(){
		super();
		x = 0;
		y = 0;
		z = 0;
	}
	public Vector3( int x, int y, int z ){
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Vector3( Vector3 vector ){
		super();
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	
	public void invert(){
		this.x = -this.x;
		this.y = -this.y;
		this.z = -this.z;
	}
	
	public void add( Vector3 vector ){
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;
	}
	
	public void substract( Vector3 vector ){
		this.x -= vector.x;
		this.y -= vector.y;
		this.z -= vector.z;
	}
	
	public void multiply( int factor ){
		this.x *= factor;
		this.y *= factor;
		this.z *= factor;
	}
	
	public void divide( int factor ){
		this.x /= factor;
		this.y /= factor;
		this.z /= factor;
	}
	
	public void modulo( int factor ){
		this.x %= factor;
		this.y %= factor;
		this.z %= factor;
	}
	
	public boolean isEqual( Vector3 vector ){
		return(
			( this.x == vector.x ) &&
			( this.y == vector.y ) &&
			( this.z == vector.z )
		);
	}
	
	public boolean isSmaller( Vector3 vector ){
		return(
			( this.x < vector.x ) &&
			( this.y < vector.y ) &&
			( this.z < vector.z )
		);
	}
	public boolean isSmallerOrEqual( Vector3 vector ){
		return(
			( this.x <= vector.x ) &&
			( this.y <= vector.y ) &&
			( this.z <= vector.z )
		);
	}
	
	public boolean isBigger( Vector3 vector ){
		return(
			( this.x > vector.x ) &&
			( this.y > vector.y ) &&
			( this.z > vector.z )
		);
	}
	public boolean isBiggerOrEqual( Vector3 vector ){
		return(
			( this.x >= vector.x ) &&
			( this.y >= vector.y ) &&
			( this.z >= vector.z )
		);
	}
}
