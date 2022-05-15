import React, {Component} from 'react';
import "../css/Login.css"
import "../css/font.css"
import Logo from "../image/Logo.png";
import kakaotalk from "../image/kakaotalk.png";
import naver from "../image/naver.png";
import google from "../image/google.png";
import {Link} from "react-router-dom";
import {KAKAO_AUTH_URL, GOOGLE_AUTH_URL, NAVER_AUTH_URL} from "./oauth2/env";
import axios from "axios";

class Login extends Component{
    constructor(props) {
        super(props);
        this.state = {
            email: "",
            password: ""
        };
        console.log('state : ', this.state);
    }

    goToMain = () => {
        console.log(this.props);
        this.props.history.push("/");
    }

    idInputCheck = (event) => {
        this.setState({email: event.target.value});
    }

    pwInputCheck = (event) => {
        this.setState({password: event.target.value});
    }

    localLogin = () => {
        try {
            axios.post('/v1/user/login', {
                email: this.state.email,
                password: this.state.password
            },{
                baseURL: 'http://localhost:8080',
                withCredentials: true
            }).then((response) => {
                console.log('res data ', response.data);
                console.log('res data.data ', response.data.data);
                this.props.history.push("/");
            });
        } catch (err) {
            console.error(err);
        }
    }

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
                                <input type={'email'} onChange={this.idInputCheck} placeholder="아이디"/>
                            </div>
                            <div className='SI-Form'>
                                <div className='Label-txt'>비밀번호</div>
                                <input type={'password'} onChange={this.pwInputCheck} placeholder="비밀번호"/>
                            </div>
                            <div className='Sns-Si'>
                                <a href={KAKAO_AUTH_URL}><img src={kakaotalk} alt="kakaotalk-icon" style={{width:"70px",height:"70px"}} /></a>
                                <a href={NAVER_AUTH_URL}><img src={naver} alt="naver-icon" style={{width:"70px",height:"70px"}}/></a>
                                <a href={GOOGLE_AUTH_URL}><img src={google} alt="google-icon" style={{width:"70px",height:"70px"}}/></a>
                            </div>
                            <button className='SI-Button' onClick={this.localLogin}>
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