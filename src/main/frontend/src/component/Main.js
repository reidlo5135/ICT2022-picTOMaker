import React, { Component } from 'react';
import {Modal} from "./Modal";
import "../css/Main-Contents.css";
import "../css/font.css";
import "../css/Modal.css";
import "../css/Login.css";
import axios from "axios";
import Logo from "../image/Logo.png";
import {GOOGLE_AUTH_URL, KAKAO_AUTH_URL, NAVER_AUTH_URL} from "./oauth2/env";
import kakaotalk from "../image/kakaotalk.png";
import naver from "../image/naver.png";
import google from "../image/google.png";
import {Link} from "react-router-dom";


class Main extends Component{

    state = {
        modalOpen: false,
    }

    openModal = () => {
        this.setState({ modalOpen: true })
    }
    closeModal = () => {
        this.setState({ modalOpen: false })
    }

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
            <div className='explanation'>
                <div className='Title'>
                    <div className='MainTitle'>
                        쉽고 재밌는 픽토그램 제작 플랫폼
                    </div>
                    <div className='SubTitle'>
                        PC와 Mobile을 통해 간단하고 재밌게
                        <div>
                            픽토그램을 제작해 보세요.
                        </div>
                    </div>
                    <button className='MainButton' onClick={this.openModal}>
                        무료로 시작
                    </button>
                    <div className={'loginModal'}>
                        <Modal open={ this.state.modalOpen } close={ this.closeModal } title="Login">
                            <div className='SignIn'>
                                <div className='SI-Content'>
                                    <div className='SI-Logo'>
                                        <img src={Logo} alt="PictoMaker-Logo" style={{width:"278px",height:"142px"}}/>
                                    </div>
                                    <div className='SI-Input'>
                                        <form>
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
                                        <button className='SI-Button'>
                                            회원가입
                                        </button>
                                    </Link>
                                </div>
                            </div>
                        </Modal>
                    </div>
                </div>
            </div>
        );
    }
}

export default Main;