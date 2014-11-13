/**
*	Faisal Hoda and Paul Nathan
*	Homework 5
*	11/21/14
*/

import java.util.*;
import java.io.*;

public class SearchEngine{

	public static void main(String[] args)throws FileNotFoundException, IOException{
		buildIndex(args[0]);
	}

	/**
	*	Method to read file and create an inverted index of terms
	*	@param fileName String name of file to index
	*/
	public static void buildIndex(String fileName)throws FileNotFoundException, IOException{
		FileReader file = new FileReader(fileName);
		BufferedReader reader = new BufferedReader(file);
		HashMap<String, ArrayList<Integer>> invertedIndex = new HashMap<String, ArrayList<Integer>>();

		String line = "";
		Scanner scan = new Scanner(line);
		int docID = 0;
		while(reader.ready()){
			line = reader.readLine();
			while(scan.hasNext()){
				String term = scan.next();
				if(term.equals("#")){
					term = scan.next();
					docID = Integer.parseInt(term);
				} else if(invertedIndex.containsKey(term)){
					ArrayList termfrequencies = invertedIndex.get(term);
					int frequency;
					if(termfrequencies.get(docID)==null){
						frequency = 0;
					} else{
						frequency = (Integer) termfrequencies.get(docID);
					}
					frequency += 1;
					termfrequencies.add(docID, frequency);
					invertedIndex.put(term, termfrequencies);
				} else{
					ArrayList<Integer> tf = new ArrayList<Integer>();
					tf.add(docID, 1);
					invertedIndex.put(term, tf);
				}

				}
			}
	}

}