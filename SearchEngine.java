/**
*	Faisal Hoda and Paul Nathan
*	Homework 5
*	11/21/14
*/

import java.util.*;
import java.io.*;

public class SearchEngine{

	public static void main(String[] args)throws FileNotFoundException, IOException{

		HashMap<String, ArrayList<TermFrequency>> invertedIndex = new HashMap<String, ArrayList<TermFrequency>>();
		ArrayList<Integer> docLengths = new ArrayList<Integer>(); // index number the docID and value is the document length
		
		buildIndex(args[0], invertedIndex, docLengths);







		// Set<String> keys = invertedIndex.keySet();
		// for(String key : keys){
		// 	ArrayList pairs = invertedIndex.get(key);
		// 	System.out.println(key + " - " + pairs.toString()+ " ");
		// }

		// System.out.println("\n");
		// System.out.println("Document Lengths:");
		// 	for(int i=1; i<docLengths.size(); i++){
		// 		System.out.println(i+":"+docLengths.get(i));
		// 	}
	}

	/**
	*	Method to read file and create an inverted index of terms and document length table
	*	@param fileName String name of file to index
	*	@param invertedIndex Hashtable for storing inverted index
	*	@param docLengths ArrayList for storing document lenghts
	*/
	public static void buildIndex(String fileName, HashMap<String, ArrayList<TermFrequency>> invertedIndex, ArrayList docLengths)throws FileNotFoundException, IOException{
		FileReader file = new FileReader(fileName);
		BufferedReader reader = new BufferedReader(file);
		// HashMap<String, ArrayList<TermFrequency>> invertedIndex = new HashMap<String, ArrayList<TermFrequency>>();

		Scanner scan;	
		int docID = 0; 		// For storing document ID throughout the loop.
		int docLength = 0; 	//	For tallying document length throughout the loop.
		ArrayList<TermFrequency> termfrequencies; // Store docID-tf pairs for each term in the hashtable invertedIndex	
		// ArrayList<Integer> docLengths = new ArrayList<Integer>();


		// System.out.println("Initialized variables and now building Index...");
		
		

		/* 	
		*	Loop to create invertedIndex.
		*	Read each line of the file. If it has a '#' in it then the next token is the docID
		*	Else, see if the term is in the index already
		*		If it is then see if the docID is in the corresponding list (array).
		*			If it is not in the list then add it as an object containing docID-tf pair
		*			Else, increment the frequency of that document at that term
		*			Finally inrement the docLength to tally the processed term
		*		Else, add the term and docID-tf(1) pair object to the inverted index and incement docLength
		*/
		while(reader.ready()){
			String line = reader.readLine();
			scan = new Scanner(line);
			// System.out.println(line);
			while(scan.hasNext()){
				String term = scan.next();
				// System.out.println(term);
				if(term.equals("#")){
					docLengths.add(docID, docLength);
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

		// Set<String> keys = invertedIndex.keySet();
		// for(String key : keys){
		// 	ArrayList pairs = invertedIndex.get(key);
		// 	System.out.println(key + " - " + pairs.toString()+ " ");
		// }

		// System.out.println("\n");
		// System.out.println("Document Lengths:");
		// 	for(int i=1; i<docLengths.size(); i++){
		// 		System.out.println(i+":"+docLengths.get(i));
		// 	}
	}


}