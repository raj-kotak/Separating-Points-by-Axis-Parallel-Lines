package PointsSeparationByParallelLines;

import java.io.*;
import java.util.*;

/**
 * This program implements an algorithm for separating points by axis-parallel lines. 
 * The algorithm first divides the points by vertical lines with a multiple of 3. 
 * Then taking each vertical partition, searching for non-separated points and separating them by horizontal lines.
 * Each time checking that further non-separated points are separated by the current horizontal lines.  
 * @author Raj
 * @version 1.0
 * @since 11/28/2016
 *
 */

public class PointsSeparationMain {
	public static void main(String args[]) throws IOException{
		List<String> list = new ArrayList<String>();		
		
		/**
		 * Taking the input files one by one to implement the algorithm
		 */
		File path = new File("input/");
		for (File file : path.listFiles()){
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file));

				String filename = file.getName();
				String strdig = filename.replaceAll("\\D+","");
				
				String strLine;
				while((strLine = br.readLine()) != null){
					List<String> coordlist = Arrays.asList(strLine.split("\n"));
	                list.addAll(coordlist);
				}
				int n = Integer.parseInt(list.get(0));
				int[] X = new int[n+1];
				int[] Y = new int[n+1];
				List<Double> H = new ArrayList<Double>();
				List<Double> V = new ArrayList<Double>();
				
				
				int k = 0, j = 0;
				for(int i = 1; i< list.size(); i++){
					String[] temp =  list.get(i).split(" ");

					X[k] = Integer.parseInt(temp[0]);
					k++;
					Y[j] = Integer.parseInt(temp[1]);
					j++;
				}
				
				Map<Integer, Integer> coordinates = new LinkedHashMap<Integer, Integer>();
				for(int i = 1; i< list.size(); i++){
					String[] temp =  list.get(i).split(" ");
					coordinates.put(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
				}
				
				if(n > 3){
					
					/**
					 * Using vertical lines to divide the points
					 */
					for(int i = 3; i < n; i+=3){
						V.add((double) ((X[i-1] + X[i])/2) + 0.5);
					}
					
					
					/**
					 * Using horizontal lines to divide the points in an optimum way
					 */
					List<Integer> firstpart = new ArrayList<Integer>();
					firstpart.add(coordinates.get(X[0]));
					firstpart.add(coordinates.get(X[1]));
					firstpart.add(coordinates.get(X[2]));
					
					Collections.sort(firstpart);
					Collections.reverse(firstpart);
					
					double midptfirst =  (firstpart.get(0) + firstpart.get(1))/2 + 0.5;
					H.add(midptfirst);
					
					if(H.get(0) > firstpart.get(1) || H.get(0) < firstpart.get(2)){
						double midptsecond =  (firstpart.get(1) + firstpart.get(2))/2 + 0.5;
						if(!H.contains(midptsecond)){
							H.add(midptsecond);
						}
					}
					
					for(int i = 3; i < n; i+=3){
							List<Integer> temp = new ArrayList<Integer>();
									
							if((i+2) < n){
								temp.add(coordinates.get(X[i]));
								temp.add(coordinates.get(X[i+1]));
								temp.add(coordinates.get(X[i+2]));
								
								Collections.sort(temp);
								Collections.reverse(temp);
							
														
								double midpt;
								for(int l = 0; l < temp.size()-1; l++){
									boolean flag = false;
									for(int h = 0; h < H.size(); h++){
										if(H.get(h) < temp.get(l) && H.get(h) > temp.get(l+1)){	
											flag = true;
										}
									}	
									
									if(flag == false){
										midpt =  (temp.get(l) + temp.get(l+1))/2 + 0.5;
										if(!H.contains(midpt)){
											H.add(midpt);										
										}
									}									
								}
							}else if((i+1) < n){
								boolean flag = false;
								for(int h = 0; h < H.size(); h++){
									if(H.get(h) < coordinates.get(X[i]) && H.get(h) > coordinates.get(X[i+1])){		
										flag = true;
									}
								}	
								double lastmidpt;
								if(flag == false){
									lastmidpt =  (coordinates.get(X[i]) + coordinates.get(X[i+1]))/2 + 0.5;
									if(!H.contains(lastmidpt)){
										H.add(lastmidpt);										
									}
								}
							}
					}
				}else if(n <= 3){
					for(int i = 0; i < n-1; i++){
						V.add((X[i-1] + X[i])/2 + 0.5);
					}
				}
				
				
				/**
				 *Printing the output for the number of lines and their respective coordinates in the file.
				 */
				File outputfile = new File("output_greedy/greedy_solution"+strdig+".txt");
				PrintStream out = new PrintStream(new FileOutputStream(outputfile));	
				System.setOut(out);
				System.out.println(V.size()+H.size());
				
				for(int v = 0; v < V.size(); v++){
					System.out.println("v "+V.get(v));
				}
				for(int h = 0; h < H.size(); h++){
					System.out.println("h "+H.get(h));
				}
				
				
				list.clear();
				H.clear();
				V.clear();
				
				br.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		}
	}
}
