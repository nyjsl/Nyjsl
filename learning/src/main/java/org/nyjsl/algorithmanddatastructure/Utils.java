package org.nyjsl.algorithmanddatastructure;

public class Utils {
	
	public static int max3(int first,int second,int third){
		return Math.max(Math.max(first, second), third);
	}
	
	public static int maxInArray(int [] src){
		int max = src[0];
		for(int a:src){
			if(a > max) max = a;
		}
		return max;
	}

}
