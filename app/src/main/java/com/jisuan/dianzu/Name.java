package com.jisuan.dianzu;

public class Name
{
	public static String NAME;
	public static String DESCRIBE;
	public static int min;
	public static int max;
	public static float value;
	private static final int[][] area = {
		{-7,7},
		{-4,10}
	};
	public static void setArea(int type){
		min=area[type][0];
		max=area[type][1];
	}
}
