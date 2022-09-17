import React, {useState} from 'react';
import {useCookies} from "react-cookie";
import {useHistory} from "react-router";
import Modal from "./Modal";
import "../css/Main-Contents.css";
import "../css/font.css";
import "../css/Modal.css";
import "../css/Login.css";
import axios from "axios";
import Logo from "../assets/image/Logo.png";
import {GOOGLE_AUTH_URL, KAKAO_AUTH_URL, NAVER_AUTH_URL} from "./oauth2/env";
import kakaotalk from "../assets/image/kakaotalk.png";
import naver from "../assets/image/naver.png";
import google from "../assets/image/google.png";
import {Link} from "react-router-dom";
import Top from "./contents/Top";
import Footer from "./contents/Footer";
import Introduce from "./contents/Introduce";

export default function Main(){
    const [cookies, setCookie] = useCookies(["accessToken"]);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [modalOpen, setModalOpen] = useState(false);
    const [isLogged, setIsLogged] = useState(false);
    const history = useHistory();

    const openModal = () => {
        const token = cookies.accessToken;
        if((token === null || token === undefined) && isLogged === false) {
            console.log('Main isLogged : ', isLogged);
            setModalOpen(true);
        } else {
            console.log("token : ", token);
            console.log('Main isLogged : ', isLogged);
            history.push("/select");
        }
    }
    const closeModal = () => {
        setModalOpen(false);
    }

    const emailInputCheck = (event) => {
        setEmail(event.target.value);
    }

    const pwInputCheck = (event) => {
        setPassword(event.target.value);
    }

    const localLogin = (e) => {
        e.preventDefault();
        if(email === ""){
            alert('아이디를 입력해주세요.');
        } else if(password === "") {
            alert('비밀번호를 입력해주세요.');
        }else if(email === "" && password === "") {
            alert('아이디와 비밀번호를 입력해주세요.');
        } else {
            try {
                axios.post('/v1/api/user/login', {
                    email,
                    password
                }).then((response) => {
                    console.log('res data ', response.data);
                    if(response.data.code === 0){
                        setIsLogged(true);
                        setCookie("accessToken", response.data.data.accessToken, {path: "/"});
                        localStorage.setItem("refresh_token", response.data.data.refreshToken);
                        localStorage.setItem("provider", "LOCAL");
                        closeModal();
                        history.push('/');
                    }
                })
                .catch((err) => {
                    console.error('err : ', JSON.stringify(err));
                    alert(err.response.data.msg);
                });
            } catch (err) {
                console.error(err);
            }
        }
    }

    return(
        <React.Fragment>
            <Top />
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
                    <button className='MainButton' onClick={openModal}>
                        무료로 시작
                    </button>
                    <div className={'loginModal'}>
                        <Modal open={modalOpen} close={closeModal}>
                            <div className='SignIn'>
                                <div className='SI-Content'>
                                        <span className={'logo_modal'}>
                                            <img src={Logo} alt="PictoMaker-Logo" />
                                        </span>
                                    <div className='SI-Input'>
                                        <form>
                                            <div className='SI-Form'>
                                                <input type={'email'} className='form-input' onChange={emailInputCheck} placeholder="이메일"/>
                                                <input type={'password'} className='form-input' onChange={pwInputCheck} placeholder="비밀번호"/>
                                            </div>
                                            <button className='SI-Button' onClick={localLogin}>
                                                로그인
                                            </button>
                                        </form>
                                    </div>
                                    <p>
                                        <Link to='/v1/user/'>
                                            <p className={'p_pw'}>비밀번호 찾기</p>
                                        </Link>
                                        <Link to='/signUp'>
                                            <p className={'p_signUp'}>| 회원가입</p>
                                        </Link>
                                    </p>
                                    <div className='Sns-Si'>
                                        <hr className='.social-inline'></hr>
                                        <span>간편 로그인</span>
                                        <div className='Si-social-div'>
                                            <a href={KAKAO_AUTH_URL}><img src={kakaotalk} alt="kakaotalk-icon" /></a>
                                            <a href={NAVER_AUTH_URL}><img src={naver} alt="naver-icon" /></a>
                                            <a href={GOOGLE_AUTH_URL}><img src={google} alt="google-icon" /></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </Modal>
                    </div>
                </div>
            </div>

            <Introduce/>

            <Footer />
        </React.Fragment>
    );
}