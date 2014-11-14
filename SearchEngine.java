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
		HashMap<String, ArrayList<TermFrequency>> invertedIndex = new HashMap<String, ArrayList<TermFrequency>>();

		// String line;
		Scanner scan;	
		int docID = 0;
		int docLength = 0;
		ArrayList<TermFrequency> termfrequencies;
		// System.out.println("Initialized variables and in buildIndex");
		
		while(reader.ready()){
			String line = reader.readLine();
			scan = new Scanner(line);
			// System.out.println(line);
			while(scan.hasNext()){
				String term = scan.next();
				// System.out.println(term);
				if(term.equals("#")){
					term = scan.next();
					docID = Integer.parseInt(term);
					// System.out.println(docID);
					docLength = 0;
				} else if(invertedIndex.containsKey(term)){
					termfrequencies = invertedIndex.get(term);
					TermFrequency id = new TermFrequency(docID, 0);
					if(termfrequencies.contains(id)==false){
						TermFrequency tf = new TermFrequency(docID, 1);
						termfrequencies.add(tf);
					} else{
						// System.out.println("in the else");
						int index = termfrequencies.indexOf(id);	
						// System.out.println(index);
						termfrequencies.get(index).inrFreq();
					}
					// invertedIndex.put(term, termfrequencies);
					docLength = docLength+1;
				} else{
					termfrequencies = new ArrayList<TermFrequency>();
					TermFrequency tf = new TermFrequency(docID, 1);
					termfrequencies.add(tf);
					invertedIndex.put(term, termfrequencies);
					docLength = docLength+1;
				}
			}
		}

		// System.out.println("index has been built");

		Set<String> keys = invertedIndex.keySet();
		for(String key : keys){
			ArrayList pairs = invertedIndex.get(key);
			System.out.println(key + " - " + pairs.toString()+ " ");
		}
	}
}