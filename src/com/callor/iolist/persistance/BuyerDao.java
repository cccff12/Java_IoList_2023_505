package com.callor.iolist.persistance;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.callor.iolist.config.DBContract;
import com.callor.iolist.models.BuyerDto;
import com.callor.iolist.persistance.sql.BuyerSQL;

// DBMS에 Query 를 보내고, 데이터를 받을 method 선언
// 인터페이스에 Query method를 선언만 해 두면
// Mybatis 의 SessionFactory 가
// 실제 사용될 코드를 자동으로 작성한다
public interface BuyerDao {

	@Select(" SELECT * FROM " + DBContract.TABLE.BUYER)
	public List<BuyerDto> selectAll();

//	pk를 기준으로 조회
	@Select(" SELECT * FROM " + DBContract.TABLE.BUYER + " WHERE buId = #{id} ")
	public BuyerDto findById(String id);

	@Insert(BuyerSQL.INSERT)
	public int insert(BuyerDto dto);
	
	@Update(BuyerSQL.INSERT)
	public int update(BuyerDto dto);

	@Delete(" DELETE FROM " + DBContract.TABLE.BUYER + " WHERE buid= #{id} ")
	public int delete(String id);

	/*
	 * 고객정보를 관리하기 위해 추가할 기능 
	 * 1. 고객 이름으로 조회하기
	 *  2. 고객 전화번호로 조회하기
	 * 
	 */
	@Select(" SELECT * FROM " 
	 + DBContract.TABLE.BUYER 
	 + " WHERE buName LIKE '%' || #{name} || '%' ")
	public List<BuyerDto> findByName(String name);
	
	@Select(" SELECT * FROM " 
			 + DBContract.TABLE.BUYER 
			 + " WHERE buTel  LIKE buName '%' || #{name} || '%' ")
	public List<BuyerDto> findByTel(String tel);
	
	
	@Select( " SELECT max(buid) FROM " + DBContract.TABLE.BUYER)
	public String getMaxId();
	
	
	
	/*
	 * select( 조회) method를 만들때 주의 사항
	 * 조회대상(where 절에서 사용)이 PK일때는 
	 * 조회되는 데이터가 없거나(null), 1개 뿐이다.
	 * 이때 method의 return type은 Dto로 설정
	 * ex) SELECT _ FROM tbl_buyer WHERE buid= '000001'
	 * 
	 * 조회대상이 PK가 아닌경우
	 * 조회되는 데이터는 없거나 (empty), 1개 이상이다.
	 * 이떄는 method의 return type은 List<Dto>로 설정
	 * 
	 */

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
