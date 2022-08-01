
-- WEB 계정 생성
-- CREATE USER WEB IDENTIFIED BY WEB;
-- GRANT RESOURCE, CONNECT TO WEB;
-- 계정 삭제하기
-- drop user WEB cascade;


------------------------------------------------
--------------- USER 관련 테이블 ---------------
------------------------------------------------

DROP TABLE MEMBER CASCADE CONSTRAINTS;
CREATE TABLE MEMBER (
    NO NUMBER PRIMARY KEY,
    ID VARCHAR2(30) NOT NULL UNIQUE,
    PASSWORD VARCHAR2(100) NOT NULL,
    ROLE VARCHAR2(10) DEFAULT 'ROLE_USER',
    NAME VARCHAR2(15) NOT NULL,
    PHONE VARCHAR2(13),
    EMAIL VARCHAR2(100),
    ADDRESS VARCHAR2(100),
    HOBBY VARCHAR2(100),
    STATUS VARCHAR2(1) DEFAULT 'Y' CHECK(STATUS IN('Y', 'N')),
    ENROLL_DATE DATE DEFAULT SYSDATE,
    MODIFY_DATE DATE DEFAULT SYSDATE
);

DROP SEQUENCE SEQ_UNO;
CREATE SEQUENCE SEQ_UNO;

COMMENT ON COLUMN MEMBER.NO IS '회원번호';
COMMENT ON COLUMN MEMBER.ID IS '회원아이디';
COMMENT ON COLUMN MEMBER.PASSWORD IS '회원비밀번호';
COMMENT ON COLUMN MEMBER.ROLE IS '회원타입';
COMMENT ON COLUMN MEMBER.NAME IS '회원명';
COMMENT ON COLUMN MEMBER.PHONE IS '전화번호';
COMMENT ON COLUMN MEMBER.EMAIL IS '이메일';
COMMENT ON COLUMN MEMBER.ADDRESS IS '주소';
COMMENT ON COLUMN MEMBER.HOBBY IS '취미';
COMMENT ON COLUMN MEMBER.STATUS IS '상태값(Y/N)';
COMMENT ON COLUMN MEMBER.ENROLL_DATE IS '회원가입일';
COMMENT ON COLUMN MEMBER.MODIFY_DATE IS '정보수정일';


INSERT INTO MEMBER (
    NO, 
    ID, 
    PASSWORD, 
    ROLE,
    NAME, 
    PHONE, 
    EMAIL, 
    ADDRESS, 
    HOBBY, 
    STATUS, 
    ENROLL_DATE, 
    MODIFY_DATE
) VALUES(
    SEQ_UNO.NEXTVAL, 
    'admin', 
    '1234', 
    'ROLE_ADMIN', 
    '관리자', 
    '010-1234-4341', 
    'admin@iei.or.kr', 
    '서울시 강남구 역삼동',
    NULL,
    DEFAULT,
    DEFAULT,
    DEFAULT
);

COMMIT;

SELECT * FROM MEMBER;

------------------------------------------------
--------------- Board 관련 테이블 ---------------
------------------------------------------------


DROP TABLE BOARD CASCADE CONSTRAINTS;
CREATE TABLE BOARD (	
    NO NUMBER,
    WRITER_NO NUMBER, 
	TITLE VARCHAR2(50), 
	CONTENT VARCHAR2(2000), 
	TYPE VARCHAR2(100), 
	ORIGINAL_FILENAME VARCHAR2(100), 
	RENAMED_FILENAME VARCHAR2(100), 
	READCOUNT NUMBER DEFAULT 0, 
    STATUS VARCHAR2(1) DEFAULT 'Y' CHECK (STATUS IN('Y', 'N')),
    CREATE_DATE DATE DEFAULT SYSDATE, 
    MODIFY_DATE DATE DEFAULT SYSDATE,
    CONSTRAINT PK_BOARD_NO PRIMARY KEY(NO),
    CONSTRAINT FK_BOARD_WRITER FOREIGN KEY(WRITER_NO) REFERENCES MEMBER(NO) ON DELETE SET NULL
);

COMMENT ON COLUMN BOARD.NO IS '게시글번호';
COMMENT ON COLUMN BOARD.WRITER_NO IS '게시글작성자';
COMMENT ON COLUMN BOARD.TITLE IS '게시글제목';
COMMENT ON COLUMN BOARD.CONTENT IS '게시글내용';
COMMENT ON COLUMN BOARD.TYPE IS '게시글 타입';
COMMENT ON COLUMN BOARD.ORIGINAL_FILENAME IS '첨부파일원래이름';
COMMENT ON COLUMN BOARD.RENAMED_FILENAME IS '첨부파일변경이름';
COMMENT ON COLUMN BOARD.READCOUNT IS '조회수';
COMMENT ON COLUMN BOARD.STATUS IS '상태값(Y/N)';
COMMENT ON COLUMN BOARD.CREATE_DATE IS '게시글올린날짜';
COMMENT ON COLUMN BOARD.MODIFY_DATE IS '게시글수정날짜';

DROP SEQUENCE SEQ_BOARD_NO;
CREATE SEQUENCE SEQ_BOARD_NO;

BEGIN
    FOR N IN 1..52
    LOOP
        INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL, 1, '게시글 '||SEQ_BOARD_NO.CURRVAL , '게시글 테스트입니다.'||SEQ_BOARD_NO.CURRVAL, 'B'||SEQ_BOARD_NO.CURRVAL, null, null, DEFAULT, 'Y', SYSDATE, SYSDATE);
    END LOOP;
    
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN ROLLBACK;
END;
/


INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL, 1, '[판매] 애플 노트북 팔아요.',  '노트북 맥북 최신입니다. 게임하시면 안됩니다.', 'B1', '원본파일명.txt', '변경된파일명.txt', DEFAULT, 'Y', SYSDATE, SYSDATE);
INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL, 1, '[판매] 삼성 노트북 팔아요.',  '삼성 노트북 팝니다. 터치 됩니다.', 'B1', '원본파일명.txt', '변경된파일명.txt', DEFAULT, 'Y', SYSDATE, SYSDATE);
INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL, 1, '[판매] 아이폰 팔아요.',  '아이폰13 팝니다. ', 'B1', '원본파일명.txt', '변경된파일명.txt', DEFAULT, 'Y', SYSDATE, SYSDATE);
INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL, 1, '[판매] 갤럭시 팔아요.',  '갤럭시 플립3 팝니다. ', 'B1', '원본파일명.txt', '변경된파일명.txt', DEFAULT, 'Y', SYSDATE, SYSDATE);
INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL, 1, '[구매] 애플 노트북 삽니다.',  '맥북 게임용으로 삽니다. 이거 게임 잘돌아가나요?', 'B1', '원본파일명.txt', '변경된파일명.txt', DEFAULT, 'Y', SYSDATE, SYSDATE);
INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL, 1, '[구매] 삼성 노트북 삽니다.',  '삼성 노트북 삽니다. 아이폰 개발하려 삽니다.', 'B1', '원본파일명.txt', '변경된파일명.txt', DEFAULT, 'Y', SYSDATE, SYSDATE);
INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL, 1, '[구매] 아이폰 삽니다.',  '아이폰3사요. 30만원에 네고합니다. ', 'B1', '원본파일명.txt', '변경된파일명.txt', DEFAULT, 'Y', SYSDATE, SYSDATE);
INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL, 1, '[구매] 갤럭시 삽니다.',  '갤럭시 삽니다. 아무 기종이나 상관없어요. ', 'B1', '원본파일명.txt', '변경된파일명.txt', DEFAULT, 'Y', SYSDATE, SYSDATE);


COMMIT;
SELECT * FROM BOARD;

--------------------------------------------------------------------
------------------------- REPLY 관련 테이블 -------------------------
--------------------------------------------------------------------

DROP TABLE REPLY CASCADE CONSTRAINTS;

CREATE TABLE REPLY(
  NO NUMBER PRIMARY KEY,
  BOARD_NO NUMBER,
  WRITER_NO NUMBER,
  CONTENT VARCHAR2(400),
  STATUS VARCHAR2(1) DEFAULT 'Y' CHECK (STATUS IN ('Y', 'N')),
  CREATE_DATE DATE DEFAULT SYSDATE,
  MODIFY_DATE DATE DEFAULT SYSDATE,
  FOREIGN KEY (BOARD_NO) REFERENCES BOARD,
  FOREIGN KEY (WRITER_NO) REFERENCES MEMBER
);

DROP SEQUENCE SEQ_REPLY_NO;
CREATE SEQUENCE SEQ_REPLY_NO;

COMMENT ON COLUMN "REPLY"."NO" IS '댓글번호';
COMMENT ON COLUMN "REPLY"."BOARD_NO" IS '댓글이작성된게시글';
COMMENT ON COLUMN "REPLY"."WRITER_NO" IS '댓글작성자';
COMMENT ON COLUMN "REPLY"."CONTENT" IS '댓글내용';
COMMENT ON COLUMN "REPLY"."STATUS" IS '상태값(Y/N)';
COMMENT ON COLUMN "REPLY"."CREATE_DATE" IS '댓글올린날짜';
COMMENT ON COLUMN "REPLY"."MODIFY_DATE" IS '댓글수정날짜';

INSERT INTO REPLY VALUES(SEQ_REPLY_NO.NEXTVAL, 51, 22, '안녕하세요.', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO REPLY VALUES(SEQ_REPLY_NO.NEXTVAL, 50, 1, '반갑습니다.', DEFAULT, DEFAULT, DEFAULT);


BEGIN
    FOR N IN 1..52
    LOOP
        INSERT INTO REPLY VALUES(SEQ_REPLY_NO.NEXTVAL, N, 1, '안녕하세요.', DEFAULT, DEFAULT, DEFAULT);
        INSERT INTO REPLY VALUES(SEQ_REPLY_NO.NEXTVAL, N, 1, '안녕하세요.', DEFAULT, DEFAULT, DEFAULT);
        INSERT INTO REPLY VALUES(SEQ_REPLY_NO.NEXTVAL, N, 1, '안녕하세요.', DEFAULT, DEFAULT, DEFAULT);
    END LOOP;
    
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN ROLLBACK;
END;
/

COMMIT;

SELECT * FROM REPLY;



--------------------------------DDL 끝-------------------------------------------

-- MEMBER 

-- 멤버 조회
-- SELECT * FROM MEMBER WHERE ID=? AND STATUS='Y'

-- 멤버 추가
-- INSERT INTO MEMBER VALUES(SEQ_UNO.NEXTVAL,?,?,DEFAULT,?,?,?,?,?,DEFAULT,DEFAULT,DEFAULT)

-- 멤버 수정               
-- UPDATE MEMBER SET NAME=?,PHONE=?,EMAIL=?,ADDRESS=?,HOBBY=?,MODIFY_DATE=SYSDATE WHERE NO=?

-- 멤버 패스워드 변경
-- UPDATE MEMBER SET PASSWORD=? WHERE NO=?

-- 멤버 삭제
-- UPDATE MEMBER SET STATUS=? WHERE NO=?


-- BOARD DML

-- 총 게시글 갯수
-- SELECT COUNT(*) FROM BOARD WHERE STATUS='Y'

-- 조회수 증가
-- UPDATE BOARD SET READCOUNT=? WHERE NO=?

-- 게시글 작성
-- INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL,?,?,?,?,?,DEFAULT,DEFAULT,DEFAULT,DEFAULT)

-- 게시글 수정
-- UPDATE BOARD SET TITLE=?,CONTENT=?,ORIGINAL_FILENAME=?,RENAMED_FILENAME=?,MODIFY_DATE=SYSDATE WHERE NO=?

-- 게시글 삭제
-- UPDATE BOARD SET STATUS=? WHERE NO=?

-- 상세 조회
/*
SELECT  B.NO, B.TITLE, M.ID, B.READCOUNT, B.ORIGINAL_FILENAME, B.RENAMED_FILENAME, B.CONTENT, B.CREATE_DATE, B.MODIFY_DATE
FROM BOARD B
JOIN MEMBER M ON(B.WRITER_NO = M.NO)
WHERE B.STATUS = 'Y' AND B.NO=?
*/

/* 
목록 조회(페이징) 쿼리
SELECT RNUM, NO, TITLE, ID, CREATE_DATE, ORIGINAL_FILENAME, READCOUNT, STATUS  
FROM (
    SELECT ROWNUM AS RNUM, NO, TITLE, ID, CREATE_DATE, ORIGINAL_FILENAME, READCOUNT, STATUS 
    FROM (
        SELECT  B.NO, B.TITLE, M.ID, B.CREATE_DATE, B.ORIGINAL_FILENAME, B.READCOUNT, B.STATUS
        FROM BOARD B JOIN MEMBER M ON(B.WRITER_NO = M.NO) 
        WHERE B.STATUS = 'Y' ORDER BY B.NO DESC
    )
)
WHERE RNUM BETWEEN 2 and 3;
*/


-- insert query
-- INSERT INTO REPLY VALUES(SEQ_REPLY_NO.NEXTVAL, ?, ?, ?, DEFAULT, DEFAULT, DEFAULT)


-- 한 게시판에 해당하는 댓글 리스트 조회용 쿼리문
/*
SELECT R.NO, R.BOARD_NO, R.CONTENT, M.ID, R.CREATE_DATE, R.MODIFY_DATE
FROM REPLY R
JOIN MEMBER M ON(R.WRITER_NO = M.NO)
WHERE R.STATUS='Y' AND BOARD_NO= ? 
ORDER BY R.NO DESC;
*/


/*
select * FROM REPLY;
DELETE REPLY WHERE NO=5
*/


/*
SELECT RNUM, NO, TITLE, ID, CREATE_DATE, ORIGINAL_FILENAME, READCOUNT, STATUS  
FROM (
    SELECT ROWNUM AS RNUM, NO, TITLE, ID, CREATE_DATE, ORIGINAL_FILENAME, READCOUNT, STATUS 
    FROM (
      SELECT  B.NO, B.TITLE, M.ID, B.CREATE_DATE, B.ORIGINAL_FILENAME, B.READCOUNT, B.STATUS
        FROM BOARD B JOIN MEMBER M ON(B.WRITER_NO = M.NO) 
        WHERE 1 = 1 
        AND B.STATUS = 'Y'
        --	AND M.ID LIKE '%admin%' 
        --	AND B.TITLE LIKE '%구매%' 
        -- AND B.CONTENT LIKE '%아이폰%' 
        ORDER BY B.NO DESC
    )
)
WHERE RNUM BETWEEN 1 and 10;
*/



/*
SELECT  COUNT(*)
FROM BOARD B
JOIN MEMBER M ON(B.WRITER_NO = M.NO)
WHERE 1=1
    AND B.STATUS = 'Y'
--	AND M.ID LIKE '%admin%' 
--	AND B.TITLE LIKE '%구매%' 
	AND B.CONTENT LIKE '%아이폰%' 
*/