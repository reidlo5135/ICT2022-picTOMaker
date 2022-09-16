import React from 'react';
import "../../css/Footer.css";
import "../../css/font.css"
import { Link } from "react-router-dom";

export default function Footer(){
	return (
		<div className="Footer">
			<div className="Footer-wrap">
				<div className="FooterMenu">
					<p><a href="frontend/src/component/contents/Footer#!">개인정보처리방침</a><span>ㅣ</span></p>
					<p><Link to="/terms">이용약관</Link><span>ㅣ</span></p>
					<p><a href="frontend/src/component/contents/Footer#!">공지사항</a><span>ㅣ</span></p>
					<p><Link to="/qna">문의하기</Link></p>
				</div>
				<div className="FooterText">
					PICTOMAKER<span>ㅣ</span>대표 : (주)I들<span>ㅣ</span>경기도 안양시 용안구 명곡로 29<span>ㅣ</span>
					전화번호 : 010-4030-7996<span>ㅣ</span>문자상담 : 010-4030-7996<span>ㅣ</span>메일 : ehdrh321@naver.com<span>ㅣ</span>
					사업자등록번호 : 123-45-67890<span>ㅣ</span>개인정보관리자 : PICKTOMAKER
				</div>
			</div>
		</div>
	);
}