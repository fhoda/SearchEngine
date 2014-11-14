public class TermFrequency{
	private int docID;
	private int frequency;

	// public TermFrequency(){
	// 	docID = 0;
	// 	frequency = 0;
	// }

	public TermFrequency(int docID, int frequency){
		this.docID = docID;
		this.frequency = frequency;
	}

	public int getDocID(){
		return docID;
	}

	public int getFrequency(){
		return frequency;
	}

	public void setFequency(int f){
		frequency = f;
	}

	public void inrFreq(){
		frequency = frequency + 1;
	}

	public boolean equals(Object obj){
		TermFrequency tf = (TermFrequency) obj;
		if(docID==tf.getDocID()){
			return true;
		}
		else {
			return false;
		}
	}


	public int hashCode() {
    	return this.docID;
	}

	public String toString(){
		return docID+":"+frequency;
	}
}