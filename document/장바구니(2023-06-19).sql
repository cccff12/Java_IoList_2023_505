-- iolist USER 화면

-- 현재 설치된 Oracle DBMS에 포함된 모든 Table을 보여달라
SELECT * FROM all_tables;

-- iolist user가 접근 할수 있는 table을 보여달라
SELECT * FROM all_tables WHERE OWNER = 'IOLIST';


-- tbl_product TABLE 의 속성을 보여달라
-- DBMS에서 Domain : table 의 속성의 데이터 type
DESCRIBE tbl_product;

INSERT INTO tbl_buyer (buid, buname, butel, buaddr)
VALUES('0000000001','홍길동','010-1111-1111','서울특별시');

INSERT INTO tbl_buyer (buid, buname, butel, buaddr)
VALUES('0000000002','성춘향','010-2222-2222','전라북도 남원시');

INSERT INTO tbl_buyer (buid, buname, butel, buaddr)
VALUES('0000000003','이몽룡','010-3333-3333','한양시특별시');

INSERT INTO tbl_buyer (buid, buname, butel, buaddr)
VALUES('0000000004','김춘향','010-4444-4444','함경도 함흥');

INSERT INTO tbl_buyer (buid, buname, butel, buaddr)
VALUES('0000000005','이길용','010-5555-5555','광주광역시');

COMMIT;
SELECT * FROM tbl_buyer;

-- pk를 기준으로 조회(SELECT) 하면 1개 이거나 null이다.
SELECT * FROM tbl_buyer WHERE buid='0000000001';
SELECT * FROM tbl_buyer WHERE buid='0000000007';

-- pk 이외의 칼럼을 기준으로 조회 비록 1개의 결과만 보일지라도 
-- 이 결과는 1개 이상인 것으로 본다.
-- 이 결과는 empty 이거나 1개 이상이다.
SELECT * FROM tbl_buyer WHERE buName='홍길동';



/*
중간문자열 검색
Full Text Search : 칼럼의 데이터의 문자열을 모두 검색하여 일치하는 데이터 찾기
SELECT 조회 명령 실행 과정에서 가장 느린 Query

칼럼의 데이터 중에 일부라도 일치하는 문자열이 있는 데이터 조회하기
*/
-- 이름 중간 문자열 검색 
-- 첫글자가 홍으로 시작되는 이름
SELECT * FROM tbl_buyer WHERE buName LIKE '홍%';

-- 홍이라는 문자열로 끝나는 모든 이름
SELECT * FROM tbl_buyer WHERE buName LIKE '%향';

-- 길이라는 문자열이 포함된 모든 이름
-- 시작글자, 끝글자 , 중간글자 상관없이 길 이라는 문자열이 포함된 모든 이름
SELECT * FROM tbl_buyer WHERE buname LIKE '%길%';


-- 연결된 문자열 생성하기
SELECT '강' || '가' || '딘' FROM dual;


/*
표준 SQL에서는 아주 단순한 연산을 수행하는 SELECT 문을 지원한다.
숫자의 4칙연산 , 문자열 연결하기 등 간단히 SELECT 명령을 통하여
결과를 확인할수 있다.

그런데 ORACLE에서는 FROM(절) 키워드가  생략된 SELECT 명령을 허용하지 않는다
그래서 의미없는 DUMMY TABLE을 하나 마련해 두었다.
ORACLE 에서 단순 연산을 위한 SELECT에서는 dual table을 사용하여 수행한다.
*/


SELECT 30+40;-- 표준 SQL문법
SELECT * FROM dual;
SELECT 30+40 FROM dual;

SELECT 30 + 40 FROM tbl_buyer;

-- 문자열 연결 : 이때 '||'을 사용하여 연결
SELECT '강' || '가' || '딘' FROM dual; 

--사칙연산
SELECT 30 + 40 FROM dual;
SELECT 30 - 40 FROM dual;
SELECT 30 * 40 FROM dual;
SELECT 30 / 40 FROM dual;

--연산
SELECT SUM(30+40+50+60) FROM dual;

-- 함수를 사용한 연산
--테이블에 데이터가 몇 개 있느냐?
SELECT COUNT(*) FROM tbl_buyer;

-- 이름에 길 문자열이 포함된 고객이 몇 명이냐
SELECT COUNT(*) FROM tbl_buyer WHERE buname LIKE '%길%';

-- 현재 저장된 데이터 중에 가장 큰 buid값은 얼마냐
SELECT MAX(buid) FROM tbl_buyer;
--현재 저장된 데이터 중에 가장 작은 buid값은 얼마냐
SELECT MIN(buid) from tbl_buyer;

--DROP TABLE tbl_product;

CREATE TABLE tbl_product(
pCode	VARCHAR2(13)		PRIMARY KEY,
pName	nVARCHAR2(50)	NOT NULL,	
pItem	nVARCHAR2(10)	NOT NULL,	
pIPrice	NUMBER,		
pOPrice	NUMBER		
);

