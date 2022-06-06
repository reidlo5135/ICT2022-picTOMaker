import React,{useState,useEffect} from 'react';
import Logo from "../../image/Logo.png";
import "../../css/MyPage.css";
import "../../css/font.css";
import {Link} from "react-router-dom";
import MyPic from './MyPage-Mypic';
import MyPageProfile from './MyPage-profile';
import GetProfile from "../user/GetProfile";
import {useHistory} from "react-router";
import axios from "axios";
import { motion } from "framer-motion";

export default function MyPageContent(){
    const [mode, setMode] = useState('profile');
    const history = useHistory();
    const access_token = localStorage.getItem("access_token").toString();

    function confirmMode(Paramode) {
        setMode(Paramode);
    }

    useEffect(()=> {
        console.log('mode : ', mode);
    },[mode]);

    function conditionRender(conditionMode) {
        if (conditionMode === "profile") {
            return <MyPageProfile/>
        } else if (conditionMode === "mypic") {
            return <MyPic/>
        } else if (conditionMode === "sharepic") {
            return <h1>SharePic</h1>
        }
    }

    function Logout() {
        try {
            axios.delete(`/oauth2/token/invalid/${access_token}`,{
                baseURL: 'http://localhost:8080',
                withCredentials: true
            }).then((response) => {
                console.log('res data : ', response.data);
                console.log('res data.data : ', response.data.data);

                if(response.data.code === 0) {
                    console.clear();
                    localStorage.clear();
                    alert('성공적으로 로그아웃 되었습니다!!');
                    history.push("/");
                }
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
                    <GetProfile />
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
                        <p>회원탈퇴</p>
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