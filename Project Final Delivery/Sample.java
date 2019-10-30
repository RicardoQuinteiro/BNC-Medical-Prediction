package project;

import java.util.LinkedList;

public class Sample{
	
	public LinkedList<int[]> list;
  
	public Sample(){
		list = new LinkedList<int[]>();
	}

	@Override
	public String toString() {
		return "Sample [list=" + list + "]";
	}

	public int varnumber() {
			return list.get(0).length;
	}
	public void add(int[] v){
		list.add(v);
	}
  

	public int length(){
		return list.size();
	}
  


	public int[] element(int n){
		return list.get(n);
	}

	
	public int domain(int n){
		int max = Integer.MIN_VALUE;
		for(int i=0; i<list.size();i++){
			if(max<list.get(i)[n]) {
				max=list.get(i)[n];
			}
		}
		return max;
	}

	public int count(int[] v, int[] i){
		int n = 0;
		int jl = v.length;
		int sl = list.size();
		boolean b;
    
		for (int s = 0; s < sl; s++){
			b = true;
			for (int j = 0; j < jl && b; j++){
				if ((list.get(s))[((int)v[j])] != i[j]) {
					b=false;
				}
			}
			if(b==true) {
				n++;
			}
		}
		return n;
	}
	
}