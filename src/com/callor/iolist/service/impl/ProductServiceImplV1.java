package com.callor.iolist.service.impl;

import java.util.List;
import java.util.Scanner;

import com.callor.iolist.config.DBConnection;
import com.callor.iolist.config.HelpMessage;
import com.callor.iolist.config.Line;
import com.callor.iolist.models.ProductDto;
import com.callor.iolist.persistance.ProductDao;
import com.callor.iolist.service.ProductService;

public class ProductServiceImplV1 implements ProductService {
	protected final Scanner scan;
	protected final ProductDao proDao;

	public ProductServiceImplV1() {
		proDao = DBConnection.getSessionFactory().openSession(true).getMapper(ProductDao.class);
		scan = new Scanner(System.in);
	}

	@Override
	public void insert() {
		ProductDto productDto = null;
		while (true) {
			HelpMessage.CONFIRM("상품정보를 확인합니다 \n" + "상품코드를 입력해 주세요 QUIT: 종료", "상품코드>>");
			String strPCode = scan.nextLine();
			if (strPCode.isBlank()) {
				HelpMessage.ERROR("상품코드는 반드시 상품코드를 입력해 주세요");
				continue;
			}

			if (strPCode.equals("QUIT"))
				return;
			productDto = proDao.findById(strPCode);
			if (productDto == null) {
				productDto = new ProductDto();
				productDto.pCode = strPCode;

			} else if (productDto != null) {
				String message = String.format("이미 등록된 상품 입니다.\n" + "\t상품코드: %s\n" + "\t매인단가 : %d", productDto.pName,
						productDto.getIPrice());
				String prompt = "상품정보를 수정할까요? (Y: 수정)>>";
				HelpMessage.CONFIRM(message, prompt);
				String yesNo = scan.nextLine();
				if (!yesNo.equalsIgnoreCase("Y"))
					continue;
			}
			break;
		} // 상품코드 입력 while end
		HelpMessage.ALERT("입력한 상품코드 : " + productDto.pCode);

//		상품명 입력받기
		while (true) {
			String strname = this.inputValue("상품명", productDto.pName);
			if (strname.equals("QUIT"))
				return;
			else if (strname.equals("RE"))
				continue;
			else if (productDto.pName == null && strname.isEmpty()) {
				HelpMessage.ERROR("상품명은 반드시 입력해야 한다.");
				continue;
			}
			productDto.pName = strname;
			break;
		}
		HelpMessage.ALERT("입력받은 상품명 :" + productDto.pName);

//		품목명 입력받기
		while (true) {
			String strname = this.inputValue("품목명", productDto.pItem);
			if (strname.equals("QUIT"))
				return;
			else if (strname.equals("RE"))
				continue;
			else if (productDto.pItem == null && strname.isEmpty()) {
				HelpMessage.ERROR("품목명을 입력 해주세요");
				continue;
			}
			productDto.pItem = strname;
			break;
		}
		HelpMessage.ALERT("입력받은 품목명 :" + productDto.pItem);

//		매입단가 입력하기
		while (true) {
			String strIPrice = productDto.getIPrice() == 0 ? null : productDto.getIPrice() + "";
			strIPrice = this.inputValue("매입단가", strIPrice);
			if (strIPrice.equals("QUIT"))
				return;
			if (strIPrice.equals("RE"))
				continue;
			try {
				int iPrice = Integer.valueOf(strIPrice);
				productDto.setIPrice(iPrice);
				break;
			} catch (Exception e) {
				HelpMessage.ERROR("매입단가는 정수로 입력해야 합니다.");
				continue;
			}

		} // 매입단가 while end

		HelpMessage.ALERT("입력한 매입단가 : " + productDto.getIPrice());
		HelpMessage.ALERT("계산한 매입단가 : " + productDto.pOPrice);

		int result = 0;
		try {
			result = proDao.insert(productDto);
			if (result > 0)
				HelpMessage.OK("상품정보 추가 ok");
		} catch (Exception e) {
			result = proDao.update(productDto);
			if (result > 0)
				HelpMessage.OK("상품정보 수정 ok");
		}

		if (result < 1) {
			HelpMessage.ERROR("상품정보 추가 수정 문제 발생", "상품정보 추가, 수정 실패");
		}

	}

	protected String inputValue(String title, String value) {
		String prompt = value == null ? String.format("새로운 %s >>", title)
				: String.format("%s 수정(%s) :  유지하려면 Enter >>", title, value);
		System.out.println(prompt);
		String strValue = scan.nextLine();
		if (strValue.equals("QUIT"))
			return strValue;
		if (value == null && strValue.isEmpty()) {
			HelpMessage.ERROR(title + "는(은) 반드시 입력해야 한다.");
			return "RE";
		} else if (value != null && strValue.isEmpty())
			return value;
		return strValue;
	}

	@Override
	public void printList() {
		List<ProductDto> productList = proDao.selecAll();
		this.printList(productList);
	}

	@Override
	public void printList(List<ProductDto> productList) {
		System.out.println(Line.sLine(70));
		System.out.println("상품코드\t상품명\t품목명\t매입단가\t매출단가");
		System.out.println(Line.sLine(70));
		for (ProductDto dto : productList) {
			this.printProduct(dto);
		}
		System.out.println(Line.dLine(70));
	}

	@Override
	public void printProduct(ProductDto productDto) {
		System.out.printf("%s\t", productDto.pCode);
		System.out.printf("%s\t", productDto.pName);
		System.out.printf("%s\t", productDto.pItem);
		System.out.printf("%s\t", productDto.getIPrice());
		System.out.printf("%s\n", productDto.pOPrice);
	}

	@Override
	public ProductDto findByPCode() {
		while (true) {
			System.out.println(Line.dLine(70));
			System.out.println("상품정보를 확인합니다");
			System.out.println("상품코드를 입력해 주세요 QUIT:종료");
			System.out.println("상품코드 >>");
			String strPcode = scan.nextLine();
			if (strPcode.isBlank()) {
				HelpMessage.ERROR("상품코드을 입력해 주세요");
				continue;
			} else if (strPcode.equals("QUIT")) {
				return null;
			}

			ProductDto productDto = proDao.findById(strPcode);
			if (productDto == null) {
				HelpMessage.ERROR(String.format("상품코드를 찾을 수 없다(%s)", strPcode));
			} else if (productDto != null) {
				printProduct(productDto);
				return productDto;
			}

		}

	}

	@Override
	public ProductDto findByPName() {
		while (true) {

			System.out.println(Line.dLine(70));
			System.out.println("상품정보를 확인한다");
			System.out.println("상품이름을 입력해 주세여 QUIT:종료");
			System.out.println("상품이름>>");
			String strPName = scan.nextLine();
			if (strPName.isBlank()) {
				HelpMessage.ERROR("상품이름을 입력해 주세요");
				continue;
			} else if (strPName.equals("QUIT"))
				return null;

			List<ProductDto> productList = proDao.findByPName(strPName);
			if (productList.isEmpty()) {
				HelpMessage.ERROR(String.format("상품이름을 찾을 수 없습니다 %s", strPName));

			} else if (productList.size() < 2) {
				return productList.get(0);
			}
			this.printList(productList);
			ProductDto productDto = this.findByPCode();
			if (productDto != null)
				return productDto;
		}

	}

}
