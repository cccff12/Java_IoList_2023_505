package com.callor.iolist.exec;

public class isEmptyAndIsBlank {

	public static void main(String[] args) {

		String nation1 = "";
//		space만 있는 문자열
		String nation2 = "      ";

		System.out.println(nation1.equals(""));
		System.out.println(nation1.isEmpty());
		System.err.println(nation1.isBlank());

		System.out.println(nation2.isEmpty());
//		java11에서는 isBlank를 지원하지만
//		이전버전에서는 isBlank를 사용할 수 없다
//		이때는 문자열을 trim()method를 한 번 통과시켜
//		white space등을 제거한다.
		System.out.println(nation2.isBlank()); // JDK 11이후
		System.out.println(nation2.trim().isEmpty());// JDK 11 이전

	}

}
