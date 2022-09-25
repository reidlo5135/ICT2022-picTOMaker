import React, {useState} from 'react';
import SockJS from "sockjs-client";
import Stomp from 'stompjs';
import {post} from "../../services/AxiosService";
import {useCookies} from "react-cookie";
import {useHistory} from "react-router";
import Modal from "../../component/Modal";
import "./main.css";
import "../../styles/font.css";
import "../../styles/modal.css";
import "../../styles/login.css";
import Logo from "../../assets/image/Logo.png";
import {GOOGLE_AUTH_URL, KAKAO_AUTH_URL, NAVER_AUTH_URL} from "../../component/SocialUserConfig";
import kakaotalk from "../../assets/image/kakaotalk.png";
import naver from "../../assets/image/naver.png";
import google from "../../assets/image/google.png";
import {Link} from "react-router-dom";
import Top from "../../component/Top";
import { motion } from "framer-motion";
import AOS from "aos";
import 'swiper/swiper-bundle.min.css'
import 'swiper/swiper.min.css'
import 'swiper/components/navigation/navigation.min.css'
import 'swiper/components/pagination/pagination.min.css'
import { Navigation, Pagination, Scrollbar, A11y } from 'swiper';
import { Swiper, SwiperSlide } from 'swiper/react';

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
            const sock = new SockJS("http://localhost:8080/ws/");
            const ws = Stomp.over(sock);
            let UserLoginDto = {
                email,
                password
            }
            try {
                ws.connect({}, () => {
                    ws.send('/pub/login', {}, JSON.stringify(UserLoginDto));
                    ws.subscribe(
                        '/sub/login',
                        (data) => {
                            const response = JSON.parse(data.body);
                            console.log("Stomp ws msg : ", response);
                            console.log("")
                            if(response.body.code === 0) {
                                setIsLogged(true);
                                setCookie("accessToken", response.body.data.accessToken, {path: "/"});
                                localStorage.setItem("refresh_token", response.body.data.refreshToken);
                                localStorage.setItem("provider", "LOCAL");
                                closeModal();
                                history.push("/");
                            }
                        }
                    )
                });
            } catch (e) {
                console.error(e);
            }
            // post('/v1/api/user/login', {
            //     email,
            //     password
            // }).then((response) => {
            //     console.log('res data ', response.data);
            //     if(response.data.code === 0){
            //         setIsLogged(true);
            //         setCookie("accessToken", response.data.data.accessToken, {path: "/"});
            //         localStorage.setItem("refresh_token", response.data.data.refreshToken);
            //         localStorage.setItem("provider", "LOCAL");
            //         closeModal();
            //         history.push('/');
            //     }
            // }).catch((err) => {
            //     console.error('err : ', JSON.stringify(err));
            //     alert(err.response.data.msg);
            // });
        }
    }

    return(
        <React.Fragment>
        <div className='mobile-main'>
            <Top/>
            <div className='explanation'>
                <div className='Title'>
                    <div className='tit-desc'>
                        <div className='MainTitle'>
                            쉽고 재밌는 픽토그램 제작 플랫폼
                        </div>
                        <div className='SubTitle'>
                            PC와 Mobile을 통해 간단하고 재밌게
                            <div>
                                픽토그램을 제작해 보세요.
                            </div>
                        </div>
                    </div>
                    <div className='btn-wrap'>
                        <button className='MainButton' onClick={openModal}>
                            시작하기
                        </button>
                        <Link to='/introduce'>
                            <button className='MainButton'>
                                이용방법
                            </button>
                        </Link>
                        <Link to='/qna'>
                            <button className='MainButton'>
                                문의하기
                            </button>
                        </Link>
                        <Link to='community'>
                            <button className='MainButton'>
                                커뮤니티
                            </button>
                        </Link>
                    </div>
                </div>
                {/* <div className='btn-wrap'>
                    <button className='MainButton' onClick={openModal}>
                        <div className='btn-start'>시작하기</div>
                    </button>
                    <button className='MainButton' >
                        이용방법
                    </button>
                    <button className='MainButton' >
                        문의하기
                    </button>
                    <button className='MainButton'>
                        커뮤니티
                    </button>
                </div> */}
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
                        <div className="col-md-7 mobile-intro">
                            <div className='mobile-intro-div'></div>
                        </div>
                </div>
            </div>
        </React.Fragment>
    );
}