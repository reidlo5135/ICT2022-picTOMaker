import React, { Component } from 'react';
import "../css/Footer.css";

class Footer extends Component{
    render(){
    return (
        <div className="Footer">
		<div className="Footer-wrap">
		<div className="FooterMenu">
			<p><a href="#!">회사소개</a><span>ㅣ</span></p>
			<p><a href="#!">개인정보처리방침</a><span>ㅣ</span></p>
			<p><a href="#!">이용약관</a><span>ㅣ</span></p>
			<p><a href="#!">공지사항</a><span>ㅣ</span></p>
			<p><a href="#!">고객센터</a><span>ㅣ</span></p>
			<p><a href="#!">FAQ</a></p>
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
}

  export default Footer;