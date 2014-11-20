/**
*	Faisal Hoda and Paul Nathan
*	Homework 5
*	11/21/14
*/


/** Class for storing and managing document id and bm25 scores objects */
public class BM25 implements Comparable<BM25>{
	private int docID;
	private double score;



	/**
	*	Constructor
	*	@param docID int for document ID
	*	@param score double for storing and tallying the total term score of a document
	*/
	public BM25(int docID, double score){
		this.docID = docID;
		this.score = score;
	}


	/**
	*	Accessor for docID
	*	@return docID
	*/
	public int getDocID(){
		return docID;
	}


	/**
	*	Accessor for score
	*	@return score
	*/
	public double getScore(){
		return score;
	}



	/**
	*	Equals method to allow determining whether TermnFrequency object has the same docID
	*	For use by ArrayList contains() method.
	*	@param obj should be TermFrequency object to compare with
	*	@return boolean on whether it is equal or not
	*/
	public boolean equals(Object obj){
		BM25 bm = (BM25) obj;
		if(docID==bm.getDocID()){
			return true;
		}
		else {
			return false;
		}
	}

	/**
	*	overriden becaue equals method is overriden
	*/
	public int hashCode() {
    	return this.docID;
	}

	@Override
	public int compareTo(BM25 obj){
		BM25 bm = obj;
		
		if(this.getScore()>bm.getScore()){
			return 1;
		} else if (this.getScore()<bm.getScore()){
			return -1;
		} else {
			return 0;
		}

	}



	public String toString(){
		return docID+"\t"+score;
	}
}