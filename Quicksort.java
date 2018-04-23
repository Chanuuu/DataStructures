public class Quicksort {
	
	private int arr[];
	
	Quicksort(int[] arr)
	{
		this.arr=arr;
	}
	
	private int partition(int p,int q)
	{
		int[] arr=this.arr;
		int pivot=p;
		int i=p;
		for(int j=i+1;j<=q;j++)
		{
			if(arr[j]<arr[pivot])
			{   
				i=i+1;
				swap(i,j);
			}
		 }
		swap(i,pivot);
		return i;
	}
	
	private void swap(int x,int y)
	{
		int[] arr=this.arr;
		int t=arr[x];
		arr[x]=arr[y];
		arr[y]=t;
	}
	public int[] quicksort( int p,int q)
	{
		int r;
		if(q>p)
		{
			r=partition(p,q);
			quicksort(p,r-1);
			quicksort(r+1,q);
		}
		
		return arr.clone();
	}
}
