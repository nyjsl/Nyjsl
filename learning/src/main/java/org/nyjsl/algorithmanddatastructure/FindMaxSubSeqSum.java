package org.nyjsl.algorithmanddatastructure;

public class FindMaxSubSeqSum {
	
	
	public static int maxSubSeqSum(int [] arr,int start,int end){
		int length = arr.length;
		if(length == 1){
			return arr[0];
		}
		
		if(start == end){
			if (arr[start]>0){
				return arr[start];
			}else{
				return 0;
			}
		}
		
		int mid = (start + end) / 2;
		
		int maxSumLeft = maxSubSeqSum(arr, 0, mid);
		int maxSumRight = maxSubSeqSum(arr, mid+1, end);
		
		return 0;
	}

}
