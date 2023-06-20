package com.callor.iolist.service.impl;

import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;

import com.callor.iolist.config.DBConnection;
import com.callor.iolist.config.HelpMessage;
import com.callor.iolist.config.Line;
import com.callor.iolist.models.BuyerDto;
import com.callor.iolist.persistance.BuyerDao;

public class BuyerServiceImplV2 extends BuyerServiceImplV1 {

//	protected도 상속 받음, 상속 받을때는 final 삭제
//	BuyerDao, Scanner 객체를 v1에서 상속받아 선언한 상태
//	v2에서 객체를 초기화하는 생성자를 만들어야 한다
//	클래스를 상속 받을 때 public 또는 protected로 선언한 변수와 매서드는 상속을 받는다
//	하지만 v1클래스의 생성자는 상속을 받지 않는다.
//	v2에서 생성자를 만들어서 객체들을 초기화하는 코드를 사용한다.
	public BuyerServiceImplV2() {
		SqlSession session = DBConnection.getSessionFactory().openSession(true);
		buyerDao = session.getMapper(BuyerDao.class);
		scan = new Scanner(System.in);
	}

//	v1에서 만들어진 method를 재정의한다.
	public BuyerDto findByBuId() {
		while (true) {
			System.out.println("고객ID를 정수로 입력하세요 QUIT: 종료");
			System.out.println("고객 id>>");
			String strBuId = scan.nextLine();
			if (strBuId.isBlank()) {
				HelpMessage.ERROR("고객을 찾으려면 ID를 입력해 주세요");
				continue;
			}
			try {
//				입력받은 정수를 10자리의 고객 ID 형식으로 변환
				int intBuId = Integer.valueOf(strBuId);
				strBuId = String.format("%010d", intBuId);
			} catch (Exception e) {
				HelpMessage.ERROR(String.format("고객 id는 정수로만 입력해야 합니다.(%s)", strBuId));
				continue;
			}
			BuyerDto buyerDto = buyerDao.findById(strBuId);
			if (buyerDto == null) {
				HelpMessage.ERROR(String.format("입력한 ID의 고객 정보가 없습니다.(%s)", strBuId));
				continue;
			}
			return buyerDto;
		}
	} // findById end

	protected String inputBuId() {
		while (true) {
			System.out.println(Line.dLine(70));
			System.out.println("고객정보를 확인합니다");
			System.out.println("고객 ID를 입력해 주세요. QUIT:종료");
			System.out.print("고객 ID>>");
			String strBuId = scan.nextLine();
			if (strBuId.equals("QUIT"))
				return strBuId;
			if (strBuId.isBlank()) {
				HelpMessage.CONFIRM("고객 ID가 없습니다", "고객 ID를 새로 생성할까요?(Y:생성)");
				String yesNo = scan.nextLine();
				if (yesNo.equalsIgnoreCase("Y"))
					return this.newBuId();
				else {
					HelpMessage.ERROR("고객 아이디 생성 취소!");
					continue;
				}
			}
			try {
				int intBuId = Integer.valueOf(strBuId);
				strBuId = String.format("%010d", intBuId);
				return strBuId;
			} catch (Exception e) {
				HelpMessage.ERROR(String.format("고객 id는 정수로만 입력되야 합니다.(%s)", strBuId));
				continue;
			}
		}
	}

	@Override
	public void insert() {
//	buyerDto 선언만 함
		BuyerDto buyerDto = null;
		while (true) {
			String strBuId = this.inputBuId();
			if (strBuId.equals("QUIT"))
				return;
//		입력한 고객 ID로 고객 정보 조회
			buyerDto = buyerDao.findById(strBuId);
//		새로운 고객 입력 하기
			if (buyerDto == null) {
				buyerDto = new BuyerDto();
				buyerDto.buId = strBuId;
//		이미 저장된 고객이다.
			} else if (buyerDto != null) {
				String message = String.format("등록된 고객 ID입니다.\n\t 고객명 : (%s)" + "\n\t 전화번호 : (%s)", buyerDto.buName,
						buyerDto.buTel);
				String prompt = "고객정보를 수정 할까요? (Y:수정)";
				HelpMessage.CONFIRM(message, prompt);

				String yesNo = scan.nextLine();
				if (!yesNo.equals("Y"))
					continue;
			}
			break;
		} // while end
		HelpMessage.ALERT("고객 아이디 확인 : " + buyerDto.buId);
		while (true) {
			String prompt = buyerDto == null || buyerDto.buName == null ? "새로운 고객 이름"
					: String.format("고객명 수정(%s): 유지하려면 enter", buyerDto.buName);
			System.out.println(prompt);
			String strName = scan.nextLine();
			if (strName.equals("QUIT"))
				return;
			if (buyerDto.buName == null && strName.isEmpty()) {
				HelpMessage.ERROR("고객이름은 반드시 입력해야 합니다.");
				continue;
			} else if (!strName.isEmpty()) {
				buyerDto.buName = strName;
			}
		}
	}

}
