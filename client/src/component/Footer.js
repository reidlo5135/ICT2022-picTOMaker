import React from 'react';
import "../styles/footer.css";
import "../styles/font.css"
import { Link } from "react-router-dom";

export default function Footer(){
	return (
		<div className="Footer">
			<div className="Footer-wrap">
				<div className="FooterText">
					PICTOMAKER<span>ㅣ</span>대표 : I들<span>ㅣ</span>경기도 안양시 용안구 명곡로 29<span>ㅣ</span>메일 : ehdrh321@naver.com<span>ㅣ</span>
					개인정보관리자 : PICKTOMAKER
				</div>
				<div className="FooterMenu">
					<p><Link to="/terms">이용약관</Link><span className='footer-bt'>ㅣ</span></p>
					<p><Link to="/qna">문의하기</Link></p>
				</div>
				
			</div>
		</div>
	);
}