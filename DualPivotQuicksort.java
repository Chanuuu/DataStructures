import java.util.Arrays;

/* This class implements the Two Pivot Quicksort algorithm by Vladimir Yaroslavskiy, Jon Bentley, and Josh Bloch.
 * The algorithm offers O(n log(n)) performance on many data sets that cause the traditional One Pivot Quicksort to 
 * degrade to quadratic time 
 */

public class DualPivotQuicksort {
	private int[] arr;
	public DualPivotQuicksort(int[] arr) {
		this.arr = arr;
	}
	final static class Pair{
		final int leftPivot ;
		final int rightPivot ;
		Pair(int leftPivot,int rightPivot)
		{
			this.leftPivot=leftPivot;
			this.rightPivot=rightPivot;
		}
		
	}
	

	private Pair partition(int low, int high)
	{   //if left pivot is greater than right pivot them swap 
		if(arr[low] > arr[high])
				swap(low,high);
		int p=arr[low];
		int q=arr[high];
	    
		int k=low+1;
		int j=low+1;
		int g=high-1;
		while(k<=g)
		{
			// if this element is less than left pivot
			if(arr[k]<p)
				{
				swap(k,j) ; 
				j++;
				}
			// if element is greater than equal to the right pivot 
			else if(arr[k]>=q)
			{
				while(arr[g]>q && k<g)
				   g--;
				swap(k,g);
				g--;
				//additionally check if the new element in kth place is less than p
				if(arr[k]<p)
					{
					swap(j,k);
					j++;
					}
				   
			}
			k++;
			
		}
		j--;
		g++;
		swap(j,low);
		swap(g,high);
			return new Pair(j,g);	
	}
	
	private void swap(int a, int b)
	{
		int t=arr[b];
		arr[b]=arr[a];
		arr[a]=t;
	}
	
	
	
	 private void dualPivotQuickSort(int low, int high)
	{
	    if (low < high) {
	        // lp means left pivot, and rp means right pivot.
	        int lp, rp; 
	        Pair p = partition(low, high);
	        lp=p.leftPivot;
	        rp=p.rightPivot;
	        dualPivotQuickSort(low, lp - 1);
	        dualPivotQuickSort(lp + 1, rp - 1);
	        dualPivotQuickSort(rp + 1, high);
	    }
	}
	 public int[] sort()
		{
		 dualPivotQuickSort(0,arr.length-1); 
		 return arr;
		}
	 public static void main(String[] args)
	 {   
		 int[] arr= {100,90,80,70,60,50,40,30,20,10};
		 DualPivotQuicksort dpqs=new DualPivotQuicksort(arr);
		 System.out.println(Arrays.toString(dpqs.sort()));
	 }

}
