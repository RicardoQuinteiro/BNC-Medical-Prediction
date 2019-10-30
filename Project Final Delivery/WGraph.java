package project;

import java.util.LinkedList;
import java.util.ArrayList;

public class WGraph{
			
	public int dim;
	public ArrayList<LinkedList<Edge>> wgraph;
	
	
	public WGraph(int n) {
		dim=n;
		wgraph= new ArrayList<LinkedList<Edge>>();
		for (int i = 0; i < n; i++) {
			wgraph.add(new LinkedList<Edge>());
		}
	}

	public void add_edge(int vertex1, int vertex2, double weight) {
		Edge e1 = new Edge(vertex2, weight);
		Edge e2 = new Edge(vertex1, weight);
		wgraph.get(vertex1).add(e1);
		wgraph.get(vertex2).add(e2);
	}
	
	public void remove_edge(int v1, int v2) {
		int i=0;
		boolean b1 = true;
		boolean b2 = true;
		int size1 = wgraph.get(v1).size();
		int size2 = wgraph.get(v2).size();
		while((i < size1 || i < size2) && (b1 || b2)) {
			if(i < size1 && b1) {
				if(wgraph.get(v1).get(i).dvertex == v2) {
					wgraph.get(v1).remove(i);
					b1 = false;
				}
			}
			if(i < size2 && b2) {
				if(wgraph.get(v2).get(i).dvertex == v1) {
					wgraph.get(v2).remove(i);
					b2 = false;
				}
			}
			i++;
		}
	}
	
	public DGraph MST(int n) {
		
		int dim = this.dim;
		DGraph maxspantree = new DGraph(dim);
		LinkedList<Integer> visited = new LinkedList<Integer>();
		ArrayList<Double> costs = new ArrayList<Double>(dim);
		ArrayList<Integer> cnodes = new ArrayList<Integer>(dim);
		visited.add(n);
		costs.add(n, -Double.MAX_VALUE);
		cnodes.add(n, n);
		
		for (int i = 0; i < dim; i++) {
			if (i != n) {
				costs.add(i, -Double.MAX_VALUE);
				cnodes.add(i, i);
			}	
		}
		
		int currentnode = n;
		
		while (visited.size() != dim) {
			
			LinkedList<Edge> currentedges = wgraph.get(currentnode);
			
			for(int i = 0; i < currentedges.size(); i++) {
				
				int currentv = currentedges.get(i).dvertex;
				double currentw = currentedges.get(i).weight;
				
				if (costs.get(currentv) < currentw) {
					costs.set(currentv, currentw);
					cnodes.set(currentv, currentnode);
				}				
			}
			
			int i = 0;
			int[] highestcedge = new int[2];
			double highestcost = -Double.MAX_VALUE;
			
			while (i < dim) {
				if ((!visited.contains(i)) && (costs.get(i) > highestcost)) {
					highestcost = costs.get(i);
					highestcedge[0] = cnodes.get(i);
					highestcedge[1] = i;
				}
				i++;
			}

			maxspantree.add_edge(highestcedge[0], highestcedge[1]);
			visited.add(highestcedge[1]);
			currentnode = highestcedge[1];
		}
		return maxspantree;
	}

	@Override
	public String toString() {
		return "WGraph [dim=" + dim + ", wgraph=" + wgraph + "]";
	}

	
}
	
