package utils;

public class Couple<T,U> {
	T l;
	U r;
	public Couple(T l, U r){
		this.l = l;
		this.r = r;
	}
	public T getL() {
		return l;
	}
	public void setL(T l) {
		this.l = l;
	}
	public U getR() {
		return r;
	}
	public void setR(U r) {
		this.r = r;
	}
	
}
