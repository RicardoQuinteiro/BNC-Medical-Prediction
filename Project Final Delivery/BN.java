package project;

import java.io.Serializable;
import java.util.ArrayList;

public class BN implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public DGraph G; 
	public double[] Cprob;
	public double[][] Rprob;
	//em R prob 1º é a classe, 2º são os elementos da raiz
	//O primeiro nó é a raiz o ultimo nó é a classe (convenção para o grafo dirigido)
	public ArrayList<double[][][]> Prob;

	public BN(DGraph graph, Sample A, double S){
		
		int Asize = A.length();
		int Vsize = A.element(0).length-1;
		int rmax=A.domain(0);
		G = graph;
		Cprob =  new double[2];
		int [] v = new int[1];
		int [] l = new int[1];
		v[0]=Vsize;
		l[0]=0;
		
		double cc0=A.count(v,l);
		double cc1=Asize-cc0;
		Cprob[0]=cc0/(cc1+cc0);
		Cprob[1]=1-Cprob[0];
		
		v = new int[2];
		l = new int[2];
		
		Rprob = new double[rmax+1][2];
		int i0 = 0;
		v[1]=Vsize;
		v[0]=0;
	
		for(int j0 = 0;j0<=1;j0++){
			
			while(i0<=rmax){
				l[0]=i0;
				l[1]=j0;
				// vetor das variaveis v=[0="raiz",Vsize="posição final, classe"]
				// vetor dos valores da contagem l=[i0= de 0 ao máximo dos valores da raiz,j0= 0 ou 1, valores da classe]
				//vetor hv=[0="raiz"] e lv=[i0=valor a contar] usados para a segunda contagem T(di)
				Rprob[i0][j0]=(double)(A.count(v, l)+S)/((cc0*(1-j0)+cc1*j0)+S*(rmax+1));
				i0++;
			}
			i0=0;
		}
		
		//Cálculo das probabilidades da raíz.
		
		Prob= new ArrayList<double[][][]>();
		
		int rmaxp;
		int pi;
		int i1=0;
		double [][][] M;
		v = new int[3];
		l = new int[3];
		int j0;
		int [] vp= new int[2];
		int [] lp= new int[2];
		v[2]=Vsize;
		vp[1]= Vsize;
		
		
		for(int j1 = 1;j1<Vsize;j1++){
			v[0]=j1;
			j0=0;
			rmax=A.domain(j1);
			if(G.parents(j1).get(0)==Vsize)
				{pi=G.parents(j1).get(1);}
			else 
				{pi=G.parents(j1).get(0);}
			rmaxp=A.domain(pi);
			v[1]=pi;
			vp[0]=pi;
			M= new double [rmax+1][rmaxp+1][2];
			while (j0<=1){
				i0=0;
				l[2]=j0;
				lp[1]=j0;
				while (i0<=rmaxp){
					l[1]=i0;
					lp[0]=i0;
					i1=0;
					while(i1<=rmax){
						l[0]=i1;
						M[i1][i0][j0]=(double)(A.count(v,l)+S)/(A.count(vp, lp)+S*(rmax+1));
						i1++;
						}
					i0++;
				}
				j0++;
			}
			Prob.add(M);
		}
		
	}
	
	public double prob(int[] v){
		
		int Vsize = v.length-1;
		double p;
		int c = v[Vsize];
		p = Cprob[c]*Rprob[v[0]][c];
		int i = 1;
		int pi;
		
		while(i < Vsize){
			
			if(G.parents(i).get(0) == (Vsize)){
				pi = G.parents(i).get(1);
				}
			else {
				pi = G.parents(i).get(0);
				}
			
			p = p*Prob.get(i-1)[v[i]][v[pi]][c];
			i++;
		}
		return p;	
	}
	
	@Override
	public String toString() {
		return "BN [G=" + G + ", Cprob=" + Cprob +", Rprob=" + Rprob + ", Prob=" + Prob + "]";
	}
	
}