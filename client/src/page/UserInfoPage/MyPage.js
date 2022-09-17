import React,{useState,useEffect} from 'react';
import Logo from "../../assets/image/Logo.png";
import "./myPage.css";
import "../../styles/font.css";
import {Link} from "react-router-dom";
import MyPic from './components/MyPagePicToDetails';
import MyPageProfileDetails from './components/MyPageProfileDetails';
import MyPageProfile from "./components/MyPageProfile";
import {useHistory} from "react-router";
import {useCookies} from "react-cookie";
import axios from "axios";
import { motion } from "framer-motion";

export default function MyPageContent(){
    const [mode, setMode] = useState('profile');
    const history = useHistory();
    const [cookies, setCookie, removeCookie] = useCookies(["accessToken"]);
    const access_token = cookies.accessToken;
    const provider = localStorage.getItem("provider");

    function confirmMode(Paramode) {
        setMode(Paramode);
    }

    useEffect(()=> {
        console.log('mode : ', mode);
    },[mode]);

    function conditionRender(conditionMode) {
        if (conditionMode === "profile") {
            return <MyPageProfileDetails/>
        } else if (conditionMode === "mypic") {
            return <MyPic/>
        } else if (conditionMode === "sharepic") {
            return <h1>SharePic</h1>
        }
    }

    function Logout() {
        try {
            axios.delete('/v1/api/user/logout', {
                headers: {
                    "X-AUTH-TOKEN": cookies.accessToken
                }
            }).then((response) => {
                if(response.status === 200) {
                    console.clear();
                    localStorage.clear();
                    removeCookie("accessToken", {path: "/"});
                    history.push("/");
                }
            }).catch((err) => {
                alert(err.response.data.msg);
            });
        } catch (err) {
            console.error(err);
        }
    }

    function deActive() {
        try {
            axios.delete('/v1/api/user/', {
                headers: {
                    "X-AUTH-TOKEN": access_token
                }
            })
                .then((response) => {
                    if(response.status === 200) {
                        alert('회원 탈퇴가 정상적으로 이루어졌습니다.');
                        localStorage.clear();
                        console.clear();
                        history.push("/");
                    }
                }).catch((err) => {
                    console.error('err : ', JSON.stringify(err));
                    alert(err.response.data.msg);
                });
        } catch (err) {
            console.error(err);
        }
    }

    return (
        <motion.div 
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            transition={{ ease: "easeOut", duration: 1 }}
        >
            <div className='MyPage-Content'>   
                <div className='MyPage-Left'>
                    <Link to="/">
                        <div className='MyPage-Logo'>
                            <img src={Logo} alt="Logo" style={{width:"40%",height:"40%"}}/>
                        </div>
                    </Link>
                    <MyPageProfile />
                    <div className='MyPage-Menu'>
                        <p onClick={()=> {
                            confirmMode("profile")
                        }}>내 프로필</p>

                        <p onClick={()=> {
                            confirmMode("mypic")
                        }}>My 픽토그램</p>

                        {/* <p onClick={()=> {
                            confirmMode("sharepic")
                        }}>공유한 픽토그램</p> */}
                        <Link to="/qna" className='qna-link'>
                            <p>문의하기</p>
                        </Link>
                        <p onClick={() => {
                            deActive();
                        }}>회원탈퇴</p>
                    </div>

                    <div className='MyPage-footer'>
                        <span onClick={Logout}>로그아웃</span>
                        <span>|</span>
                        <span>개인정보처리방침</span>
                        <span>|</span>
                        <span>이용약관</span>
                        <p className='Copyright'>Copyright 2022. PICTO*MAKER all rights reserved</p>
                    </div>
                </div>      
                {conditionRender(mode)}
            </div>
        </motion.div>
    );
}