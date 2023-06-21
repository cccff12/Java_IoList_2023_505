package com.callor.iolist.exec;

import java.util.Scanner;

public class IsDate {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("날짜>>");
		String strDate = scan.nextLine();
		
		
//		SimpleDateFormat check = new SimpleDateFormat("yyyy-MM-dd");
////	Date date= check.parse(strDate); 이걸 try catch로 묶어줘야 함
//		try {
//			
//			
//			
//			/*
//			 * SimpleDateFormat 에 설정한 형식에 벗어나면
//			 * exception 을 발생하고
//			 * 실제 상용되는 날짜가 아니면 엉뚱한 날짜를 생성한다
//			 * 1. exception 처리에서 날짜 format(형식)dmf rjatkfkrh
//			 * 2. equals()사용해 정상적인 상용날짜인지 검사
//			 */
//			
//			Date date = check.parse(strDate);
//			String checkDate = check.format(date);
//			if (!strDate.equals(checkDate)) {
//				System.out.println("날짜를 정확히 입력 하세요");
//			}
//			System.out.println(date.toString());
//		} catch (Exception e) {
//
//		}
//
	}
}
