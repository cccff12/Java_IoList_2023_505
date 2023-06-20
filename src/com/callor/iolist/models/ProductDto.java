package com.callor.iolist.models;

public class ProductDto {

	public String pCode;// VARCHAR2(13)
	public String pName; // nVARCHAR2(50)
	public String pItem; // nVARCHAR2(10)
	public int pIPrice; // NUMBER
	public int pOPrice; // NUMBER

	public ProductDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductDto(String pCode, String pName, String pItem, int plPrice, int pOPrice) {
		super();
		this.pCode = pCode;
		this.pName = pName;
		this.pItem = pItem;
		this.pIPrice = plPrice;
		this.pOPrice = pOPrice;
	}

	/*
	 * 매입단가를 seting하면 매출단가를 자동으로 계산하여 저장하기 매입단가를 DTO에 저장할때는 pIPrice= 얼마를 사용하지 않고
	 * 
	 * 
	 */

	public void setIPrice(int iprice) {

//		iprice + iprice * 0.22
		float oprice = iprice * 1.22F;

//		(int)(oprice/10): 소수점 이하 자르기
		oprice = Math.round(oprice / 10) * 10; // 소수점 첫자리 반올림
		this.pOPrice = (int) oprice;
	}
// pIprice는 변수에 직접 접근 모샇게 private로 선언
	public int getIPrice() {
		return this.pIPrice;
	}

	@Override
	public String toString() {
		return "ProductDto [pCode=" + pCode + ", pName=" + pName + ", pItem=" + pItem + ", plPrice=" + pIPrice
				+ ", pOPrice=" + pOPrice + "]";
	}

}
