package project;

public class Edge {
	  public int dvertex;
	  public double weight;
	  
	
	public Edge(int dvertex, double weight) {
		this.dvertex = dvertex;
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Edge [dvertex=" + dvertex + ", weight=" + weight + "]";
	}
	  
}