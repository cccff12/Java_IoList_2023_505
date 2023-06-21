package com.callor.iolist.persistance;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.callor.iolist.config.DBContract;
import com.callor.iolist.models.IolistDto;

/*
 *  1. 판매내역 등록(장바구니 담기)
 *  2. 장바구니 전체 리스트
 *  3. 기간별 리스트
 *  4. 고객별 리스트
 *  5. 상품별 리스트
 *  6. 고객 + 거래기간별 리스트
 */
public interface IolistDao {

	@Select("SELECT * FROM " 
			+ DBContract.TABLE.IOLIST 
			+ " ORDER BY ioDate, ioTime")
	public List<IolistDto> selectAll();
	
	
	
	/*
	 * mybatis 의 query method값을 전달할 때
	 * 가능하면 Dto에 데이터를 담아서 전달하라
	 * 만약 불가피하게 일반변수를 (int string)을 사용해 
	 * 전달할때 1개는 문제가 없으나 2개 이상부터는 반드시 @Param() Annotation을
	 * 사용해 변수 이름도 함꼐 전달해 주어야 한다.
	 */
	// 기간별 리스트
	@Select("SELECT * FROM " 
			+ DBContract.TABLE.IOLIST
			+ " WHERE iodate BETWEEN #{sDate} AND #{eDate} "
			+ " ORDER BY ioDate, ioTime")
	public List<IolistDto> selectBetwenDate(
			@Param("sDate") String sDate,
			@Param("eDate") String eDate);

	// 고객별 리스트
	@Select("SELECT * FROM " 
			+ DBContract.TABLE.IOLIST
			+ " WHERE ioBuid = #{buid} "
			+ " ORDER BY ioDate, ioTime")
	public List<IolistDto> selectByBuyer(String buId);
	
	// 상품별 리스트
	@Select("SELECT * FROM " 
			+ DBContract.TABLE.IOLIST
			+ " WHERE ioPcode = #{code} "
			+ " ORDER BY ioDate, ioTime")
	public List<IolistDto> selectByProduct(String pCode);
	
	// 고객 + 기간별 리스트
	@Select("SELECT * FROM " 
			+ DBContract.TABLE.IOLIST
			+ " WHERE ioBuid = #{buId} "
			+ " AND ioDate BETWEEN #{sDate} AND #{eDate} "
			+ " ORDER BY ioDate, ioTime")
	public List<IolistDto> selectByBuyerBetweenDate(
			@Param("buId") String buId,
			@Param("sDate")String sDate,
			@Param("eDate")String eDate);
	
	// 상품 + 기간별 리스트
	@Select("SELECT * FROM " 
			+ DBContract.TABLE.IOLIST
			+ " WHERE ioPcode = #{pCode} "
			+ " AND ioDate BETWEEN #{sDate} AND #{eDate} "
			+ " ORDER BY ioDate, ioTime")
	public List<IolistDto> selectByProductBetweenDate(String pCode,String sDate, String eDate);
	
	@Insert("INSERT INTO " 
			+ DBContract.TABLE.IOLIST 
			+ " ( ioDate, ioTime, ioBuId,"
			+ " ioPCode, ioQuan, ioPrice, ioTotal)" 
			
			+ " VALUES ( #{ioDate}, #{ioTime}, #{ioBuId},"
			+ " #{ioPCode}, #{ioQuan}, #{ioPrice}, #{ioTotal})" 
			)
	public int insert(IolistDto iolistDto);
	
}