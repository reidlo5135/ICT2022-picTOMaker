import React, { Component } from 'react';
import Logo from "../image/Logo.png";
import "../css/MyPage.css"
import "../css/font.css"
import { Link } from "react-router-dom";

class MyPageContent extends Component{
    render(){
        
    return (
       <div className='MyPage-Content'>
           <div className='MyPage-Left'>
               <Link to="/">
                <div className='MyPage-Logo'>
                    <img src={Logo} alt="Logo" style={{width:"40%",height:"40%"}}/>
                </div>
                </Link>

                <div className='Profile'>
                    <div className='Pro-Img'>
                    </div>

                    <div className='Pro-Txt'>
                        <div className='Pro-Title'>
                            홍길동님, 반갑습니다!
                        </div>
                        <div className='Pro-Email'>
                            ehdrh321@naver.com
                        </div>
                    </div>
                </div>

                <div className='MyPage-Menu'>
                    <p>내 프로필</p>
                    <p>My 픽토그램</p>
                    <p>공유한 픽토그램</p>
                    <p>문의하기</p>
                    <p>회원탈퇴</p>
                </div>

                <div className='MyPage-footer'>
                    <span>로그아웃</span>
                    <span>|</span>
                    <span>개인정보처리방침</span>
                    <span>|</span>
                    <span>이용약관</span>
                    <p className='Copyright'>Copyright 2022. PICTO*MAKER all rights reserved</p>
                </div>
            </div>

           <div className='MyPage-Right'>
               <div className='Right-Contents'>
                    <div className='Menu-Name'>
                        내 프로필
                    </div>
                    <div className='MenuBox'>
                        <div className='Name'>
                            이름
                        </div>
                        <div className='MenuBox-props'>
                            홍길동
                        </div>
                    </div>
                    <div className='MenuBox'>
                        <div className='Name'>
                            이름
                        </div>
                        <div className='MenuBox-props'>
                            홍길동
                        </div>
                    </div>
                    <div className='MenuBox'>
                        <div className='Name'>
                            이름
                        </div>
                        <div className='MenuBox-props'>
                            홍길동
                        </div>
                    </div>
                    <div className='MenuBox'>
                        <div className='Name'>
                            이름
                        </div>
                        <div className='MenuBox-props'>
                            홍길동
                        </div>
                    </div>
                </div>
           </div>
       </div>
    );
    }
}

  export default MyPageContent;