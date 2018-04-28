/*
A segment tree implementation to answer range queries.  

@author : Debapriya Biswas
*/

public class SegmentTree {

	/*Builds a conceptual minimum segment tree backed by array  from an input array.
	 * 
	 * @return : Array representing the conceptual tree   
	 * 
	 * */
	
	public int[] buildSegmentTree(int[] input)
	{
		int length=input.length;
		
		/*if length is a power of two ? else find the next power of two . the result size
		 * of the segment tree will be 2*length-1
		*/
		int size=((length)&(length-1))==0?2*length-1:2*nextPow2(length)-1;
		int[] segmentTree= new int[size];
		buildMinSegmentTree(segmentTree,input,0,length-1,0);
		return segmentTree;
	}
	private void buildMinSegmentTree(int[] segmentTree,int[] input,int lo,int hi,int pos)
	{
		if(lo==hi)
		{
			segmentTree[pos]=input[lo];
			return;
		}
		
		int mid=(lo+hi)>>>1;
		buildMinSegmentTree(segmentTree,input, lo, mid, 2*pos+1) ; // construct left subtree
		buildMinSegmentTree(segmentTree, input, mid+1, hi, 2*pos+2); // construct right subtree
		segmentTree[pos]=Math.min(segmentTree[2*pos+1],segmentTree[2*pos+2]) ;
		
		
	}
	/*
	 * This API finds the minimum element in a range set identified by qlo and qhi   
	 * */
	public int rangeMinQuery(int[] segmentTree,int qlo,int qhi,int lo,int hi,int pos)
	{
		if(qlo<=lo&&qhi>=hi)
			return segmentTree[pos];
		if(qlo>hi||qhi<lo)
			return Integer.MAX_VALUE;
		int mid=(lo+hi)>>>1;
		return Math.min(rangeMinQuery(segmentTree, qlo,qhi,lo,mid,2*pos+1),
				rangeMinQuery(segmentTree, qlo, qhi, mid+1, hi, 2*pos+2));
	}
	
	private static int nextPow2(int x)
	{   int result=1;
		while(result<x)
			result=result<<1;
		return result;
	}
	 public static void main(String[] args)
	 {
		 SegmentTree st= new SegmentTree();
		 int[]input={-1,2,4,0};
		 System.out.println(st.rangeMinQuery(st.buildSegmentTree(input), 0, 2, 0, 3, 0));
	 }
}
