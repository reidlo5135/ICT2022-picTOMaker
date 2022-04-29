import React, { Component } from 'react';
import "../css/Login.css"
import "../css/font.css"
import Logo from "../image/Logo.png";
import kakaotalk from "../image/kakaotalk.png";
import naver from "../image/naver.png";
import google from "../image/google.png";
import { Link } from "react-router-dom";
import {KAKAO_AUTH_URL, GOOGLE_AUTH_URL, NAVER_AUTH_URL} from "./oauth2/env";

/**ㅁㄴㅇㅁㄴㅇㅁㄴㅇ */
class Login extends Component{
    render(){
        return(
            <div className='SignIn'>
                <div className='SI-Content'>
                    <div className='SI-Logo'>
                        <img src={Logo} alt="PictoMaker-Logo" style={{width:"278px",height:"142px"}}/>
                    </div>
                    <div className='SI-Input'>
                        <form action="" method="get">
                            <div className='SI-Form'>
                                <div className='Label-txt'>아이디</div>
                                <input type={'email'} placeholder="아이디"/>
                            </div>
                            <div className='SI-Form'>
                                <div className='Label-txt'>비밀번호</div>
                                <input type={'password'} placeholder="비밀번호"/>
                            </div>
                            <div className='Sns-Si'>
                                <a href={KAKAO_AUTH_URL}><img src={kakaotalk} alt="kakaotalk-icon" style={{width:"70px",height:"70px"}} /></a>
                                <a href={NAVER_AUTH_URL}><img src={naver} alt="naver-icon" style={{width:"70px",height:"70px"}}/></a>
                                <a href={GOOGLE_AUTH_URL}><img src={google} alt="google-icon" style={{width:"70px",height:"70px"}}/></a>
                            </div>
                            <button className='SI-Button'>
                                로그인
                            </button>
                        </form>
                    </div>
                    <Link to='/SignUp'>
                        <div className='SI-Caution'>
                            <p>회원가입</p>
                        </div>
                    </Link>


                </div>
            </div>
        );
    }
}

export default Login;