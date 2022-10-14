import React, {useState, useEffect} from 'react';
import {wsConnect, wsCommunication} from "../../../services/StompService";
import {useCookies} from "react-cookie";
import {useHistory} from "react-router";
import Modal from "../../../component/Modal";
import "../main.css";
import "../../../styles/font.css";
import "../../../styles/modal.css";
import "../../../styles/login.css";
import Logo from "../../../assets/image/Logo.png";
import {GOOGLE_AUTH_URL, KAKAO_AUTH_URL, NAVER_AUTH_URL} from "../../../component/SocialUserConfig";
import kakaotalk from "../../../assets/image/kakaotalk.png";
import naver from "../../../assets/image/naver.png";
import google from "../../../assets/image/google.png";
import {Link} from "react-router-dom";
import MobileTop from "../../../component/mobile-components/Mobile-Top"
import StartBtn from "../../../assets/image/start-btn.png"
import IntroBtn from "../../../assets/image/intro-btn.png"
import QnaBtn from "../../../assets/image/qna-btn.png"
import MyPageBtn from "../../../assets/image/mypage-btn.png"

export default function M_Main(){
    const history = useHistory();
    const [cookies, setCookie] = useCookies(["accessToken"]);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [modalOpen, setModalOpen] = useState(false);
    const [isLogged, setIsLogged] = useState(false);

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

    const login = () => {

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
            let data = {
                email,
                password
            }
            const action = (response) => {
                if(response.body.code === 0) {
                    setIsLogged(true);
                    setCookie("accessToken", response.body.data.accessToken, {path: "/"});
                    localStorage.setItem("refresh_token", response.body.data.refreshToken);
                    localStorage.setItem("provider", "LOCAL");
                    closeModal();
                    history.push("/user/redirect");
                }
                /* else {
                    console.log("asdfafd");
                    alert("존재하지 않는 회원입니다. 회원가입을 진행해 주세요.");
                } */
                
            }
            wsCommunication('/pub/user/login', {}, data, '/sub/login', action);
        }
    };

    useEffect(() => {
        wsConnect();
    }, []);

    return(
        <React.Fragment>
            <MobileTop/>
            <div className='mobile-main'>
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
                            <div className='MainButton' >
                                <img src={StartBtn} alt="시작하기 버튼" onClick={openModal}/>
                            </div>
                            <Link to='/introduce'>
                                <div className='MainButton'>
                                    <img src={IntroBtn} alt="이용방법 버튼"/>
                                </div>
                            </Link>
                            <Link to='/qna'>
                                <button className='MainButton'>
                                    <img src={QnaBtn} alt="문의하기 버튼"/>
                                </button>
                            </Link>
{/*                             <Link to='/community'>
                                <button className='MainButton'>
                                    커뮤니티
                                </button>
                            </Link> */}
                            {
                                cookies.accessToken === null || cookies.accessToken === undefined ?
                                    <div></div> :
                                    <Link to='/myPage'>
                                        <button className='MainButton'>
                                            <img src={MyPageBtn} alt="마이페이지 버튼"/>
                                        </button>
                                    </Link>
                            }
                        </div>
                    </div>
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
                                        {/* <Link to='/v1/user/'>
                                            <p className={'p_pw'}>비밀번호 찾기</p>
                                        </Link> */}
                                        <Link to='/signUp'>
                                            <p className={'p_signUp'}>회원가입</p>
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