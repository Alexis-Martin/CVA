package utils;

import java.util.LinkedList;

public class MemoryStack<T> {
	private LinkedList<T> ll;
	private int max_size = -1;
	private int iterator = -1;
	public MemoryStack(){
		this.ll = new LinkedList<T>();
	}
	public MemoryStack(int max_size){
		this();
		this.max_size = max_size;
	}
	public T get(int index){

		return this.ll.get(index);
	}
	public T get_previous(){
		iterator--;
		return this.ll.get(this.iterator+1);
	}
	public T get_next(){
		iterator++;
		return this.ll.get(this.iterator);		
	}
	public void cut(int index){
		for(int i = this.ll.size()-1; i>index;i--){
			this.ll.removeLast();
		}
	}
	public void push(T e){

		if(this.ll.size() == max_size && iterator == this.ll.size()-1){
			this.ll.removeFirst();
			iterator--;
		}
		else if(iterator != this.ll.size()-1 ){
			this.cut(iterator);
		}
		iterator++;	
		this.ll.add(e);

	
	}
	public int size(){
		return this.ll.size();
	}
	public int getIteration() {
		return iterator;
	}
	public boolean has_next(){
		return this.ll.size()>this.iterator+1;
	}
	public boolean has_previous(){
		return this.iterator>=0;
	}
}
