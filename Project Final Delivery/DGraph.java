package project;

import java.util.LinkedList;
import java.io.Serializable;
import java.util.ArrayList;

public class DGraph implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public int dim;
	public ArrayList<LinkedList<Integer>> dgraph;
	
	public DGraph(int dim){
		ArrayList<LinkedList<Integer>> r = new ArrayList<LinkedList<Integer>>(dim);
		for (int i = 0; i < dim; i++) {
			r.add(new LinkedList<Integer>());
		}
		this.dim = dim;
		dgraph = r;
	}
	
	//Escrever no relatório
	
	public void add_node() {
		dim++;
		LinkedList<Integer> l = new LinkedList<Integer>();
		dgraph.add(l);
	}
	
	public void add_edge(int o, int d) {
		if(!dgraph.get(o).contains(d) && o < dim && d < dim) {
			dgraph.get(o).add(d);
		}
	}
	
	public void remove_edge(int o, int d) {
		if(o < dim && d < dim) {
			dgraph.get(o).removeFirstOccurrence(d);
		}
	}
	
	public LinkedList<Integer> parents(int n){
		int i = 0;
		LinkedList<Integer> r = new LinkedList<Integer>();
		while (i < dim) {
			if (i != n && dgraph.get(i).contains(n)) {
				r.add(i);
			}
			i++;
		}
		return r;
	}

	@Override
	public String toString() {
		return "DGraph [dim=" + dim + ", dgraph=" + dgraph + "]";
	}
	
	}
	
	
