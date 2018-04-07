package com.humble.gongxi;

public class PubLogger {
	public static void log(String formatStr, Object... args){
		if (args.length != 0){
			System.out.println(String.format(formatStr, args));
		} else {
			System.out.println(formatStr);
		}
	}
	public static void info(String formatStr, Object... args){
		log("info__"+formatStr, args);
	}
	public static void warn(String formatStr, Object... args){
		log("warn__"+formatStr, args);
	}
	public static void error(String formatStr, Object... args){
		log("error__"+formatStr, args);
	}
}
