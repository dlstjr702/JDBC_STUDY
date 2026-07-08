# JDBC_STUDY
JDBC수업

-------------------------------------------------------------------------------------------------------------------
1. jdbc폴더 생성
2. 이클립스로 jdbc폴더경로 설정
3. 프로젝트생성
<img width="688" height="584" alt="순서1" src="https://github.com/user-attachments/assets/48055769-2d12-4757-aa4e-541b9c4f3048" />
<img width="701" height="636" alt="순서2" src="https://github.com/user-attachments/assets/e2ee4473-434f-4569-b434-2bbe732ca5b7" />
<img width="532" height="506" alt="순서3" src="https://github.com/user-attachments/assets/d0558f30-95fd-4022-b419-7198e95d0a37" />
<img width="749" height="800" alt="순서4" src="https://github.com/user-attachments/assets/2b1ac824-65a1-4c1e-9f18-262753c8e181" />
<img width="1837" height="914" alt="순서5" src="https://github.com/user-attachments/assets/a2d97615-384b-4b47-a16f-3a20090edee8" />
<img width="703" height="747" alt="순서6" src="https://github.com/user-attachments/assets/58ff76a7-a215-466a-b2b9-b7c4821ae42d" />
<img width="724" height="776" alt="순서7" src="https://github.com/user-attachments/assets/7cf1c97d-e3f1-4311-9704-d3573748ec01" />


이클립스 db연결
<img width="1911" height="973" alt="순서20" src="https://github.com/user-attachments/assets/c9095f8c-4e9f-4afa-99d8-fbd504e45f37" />
<img width="953" height="573" alt="순서21" src="https://github.com/user-attachments/assets/362d421c-355e-4c2d-9695-8c8d48519726" />
<img width="979" height="586" alt="순서22" src="https://github.com/user-attachments/assets/681561f8-a08e-4b34-abd4-a5fe435fdf4e" />


-------------------------------------------------------------------------------------------------------------------
2026-07-06 (월요일)
- DB연결후 조회후 출력작업






-------------------------------------------------------------------------------------------------------------------
2026-07-07 (화요일)
- DB연결후 조회후 출력작업
-- 회원가입
-- 아이디 : [ ] [중복체크] empno

CREATE OR REPLACE PROCEDURE up_idcheck
(
    pid IN emp.empno%TYPE
)
IS
    vcheck NUMBER(1);
    ex_duplicate_id EXCEPTION;
BEGIN
    SELECT COUNT(*) INTO vcheck
    FROM emp
    WHERE empno = pid;
    
    
    IF vcheck > 0 THEN
        RAISE ex_duplicate_id;
    END IF;
EXCEPTION
 WHEN ex_duplicate_id THEN
    RAISE_APPLICATION_ERROR(
            -20001,
            '이미 사용 중인 ID(사원번호)입니다..'
        );
-- WHEN OTHERS THEN
WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(
            -20002,
            '예상하지 못한 오류가 발생했습니다. (' || SQLCODE || ') ' || SQLERRM
        );
END;


DECLARE
vcheck NUMBER(1);
BEGIN
    UP_IDCHECK(9999,vcheck);
    DBMS_OUTPUT.PUT_LINE(vcheck);
END;

-- 두번째 예시
-- 로그인 처리/                   emp테이블
-- 아이디 : [         ]          empno
-- 비밀번호 : [         ]        ename
-- [로그인][회원가입]

-----------------------------------
-- 로그인 구현
--CREATE OR REPLACE PROCEDURE up_login
--(
--    pid    IN  emp.empno%TYPE,
--    ppwd   IN  emp.ename%TYPE,
--    pcheck OUT NUMBER
--)
--IS
--    vcnt NUMBER;
--BEGIN
--    -- 1. 아이디 존재 여부 확인
--    SELECT COUNT(*)
--    INTO vcnt
--    FROM emp
--    WHERE empno = pid;
--
--    IF vcnt = 0 THEN
--        pcheck := 1;       -- 아이디 없음 , 로그인실패
--    ELSE
--        -- 2. 비밀번호 확인
--        SELECT COUNT(*)
--        INTO vcnt
--        FROM emp
--        WHERE empno = pid
--          AND ename = ppwd;
--
--        IF vcnt = 1 THEN
--            pcheck := 0;   -- 로그인 성공
--        ELSE


--            pcheck := -1;  -- 아이디는 존재하지만 비밀번호 틀림
--        END IF;
--    END IF;
--END;

-----------------------------------
-- 로그인 구현(예외처리)

CREATE OR REPLACE PROCEDURE up_login
(
    pid    IN  emp.empno%TYPE,
    ppwd   IN  emp.ename%TYPE
)
IS
    vcnt NUMBER;
    ex_invalid_id EXCEPTION;
    ex_invalid_pwd EXCEPTION;
    
BEGIN
    -- 1. 아이디 존재 여부 확인
    SELECT COUNT(*)
    INTO vcnt
    FROM emp
    WHERE empno = pid;

    IF vcnt = 0 THEN
        RAISE ex_invalid_id;
    ELSE
        -- 2. 비밀번호 확인
        SELECT COUNT(*)
        INTO vcnt
        FROM emp
        WHERE empno = pid
          AND ename = ppwd;

        IF vcnt = 0 THEN
            RAISE ex_invalid_pwd;
        END IF;
    END IF;
EXCEPTION
    WHEN ex_invalid_id THEN
     RAISE_APPLICATION_ERROR(
            -20001,
            '존재하지 않는 아이디입니다.'
        );
    WHEN ex_invalid_pwd THEN
     RAISE_APPLICATION_ERROR(
            -20002,
            '비밀번호가 일치 하지 않습니다.'
        );
    WHEN OTHERS THEN
        RAISE;
    
END;


-----------------------------------
-- 저장프로시저

CREATE OR REPLACE PROCEDURE up_selectdept
(
    pdeptcursor OUT SYS_REFCURSOR
)
IS    
BEGIN
    OPEN pdeptcursor FOR
        SELECT *
        FROM dept
        WHERE deptno > 0;
--EXCEPTION
END;







-------------------------------------------------------------------------------------------------------------------
2026-07-08 (수요일)
-- 수업 자료 사이트
http://taeyo.net/Columns/View.aspx?SEQ=110&PSEQ=10&IDX=1


-- 게시판 만들기 수업 (DB)
-- 시퀀스
CREATE SEQUENCE SEQ_tblcstVSBoard
NOCACHE;

--DDL문(내 코드) /선생님코드랑 비교해보기
CREATE TABLE tbl_cstVSBoard (
  seq NUMBER  NOT NULL PRIMARY KEY, -- 글번호 컬럼
  writer VARCHAR2(20) NOT NULL, -- 작성자 컬럼
  pwd VARCHAR2(20) NOT NULL, -- 비밀번호 컬럼
  email VARCHAR2(100), -- 메일주소 컬럼
  title VARCHAR2(200) NOT NULL, -- 제목 컬럼
  writedate DATE NOT NULL DEFAULT SYSDATE, -- 글쓴일시
  readed NUMBER NOT NULL DEFAULT 0, -- 조회수
  tag NUMBER(1) NOT NULL , -- 글의 형식 0이면 일반텍스트 , 1이면 HTML형식
  content CLOB -- 글의 내용
)


--DDL문(선생님 코드)
CREATE TABLE tbl_cstVSBoard (
  seq NUMBER  NOT NULL PRIMARY KEY,
  writer VARCHAR2(20) NOT NULL ,
  pwd VARCHAR2(20)  NOT NULL ,
  email VARCHAR2(100)  ,
  title VARCHAR2(200)  NOT NULL ,
  writedate DATE  DEFAULT  SYSDATE ,
  readed NUMBER DEFAULT  0 ,
  tag NUMBER(1) NOT NULL ,
  content CLOB
);



-- 페이징 처리 X  + 목록보기
SELECT seq, title, writer,email,writedate,readed
FROM tbl_cstvsboard
ORDER BY seq DESC;


-- 게시글 150개정도 더미 INSERT
BEGIN
  FOR i IN 1..150 LOOP
      INSERT INTO tbl_cstvsboard ( seq, writer, pwd, email, title, tag, content )
      VALUES ( seq_tblcstvsboard.NEXTVAL, '홍길동' || MOD(i, 10), '1234'
      , '홍길동' || MOD(i, 10) || '@naver.com'
      , '더미...' || MOD(i, 10), 0, '더미...' || MOD(i, 10) );
  END LOOP;
  COMMIT;
END;
--

BEGIN
    UPDATE tbl_cstvsboard
    SET writer = '조지훈'
    WHERE MOD(seq,16) IN(3,5,8);
END;


--
BEGIN
    UPDATE tbl_cstvsboard
    SET title = '게시글 구현'
    WHERE MOD(seq,16) IN(1,7,10);
END;

