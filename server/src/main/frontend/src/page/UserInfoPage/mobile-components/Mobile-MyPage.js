import React,{useState,useEffect} from 'react';
import {del} from "../../../services/AxiosService";
import Logo from "../../../assets/image/Logo.png";
import "../myPage.css";
import "../../../styles/font.css";
import {Link} from "react-router-dom";
import MyPic from './Mobile-MyPagePicToDetails';
import MyPageProfileDetails from '../components/MyPageProfileDetails';
import {useHistory} from "react-router";
import {useCookies} from "react-cookie";
import { motion } from "framer-motion";
import MobileTop from "../../../component/mobile-components/Mobile-Top";

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
        if(access_token === undefined || access_token === null){
            alert("로그인 후 이용 가능합니다.");
            history.push("/");
        }
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

    function Logout(e) {
        let url = null;
        e.preventDefault();
        if(provider === "LOCAL") {
            url = "/v1/api/user/logout";
        } else {
            url = "/v1/api/oauth2/logout";
        }
        del(url, {
            headers: {
                "X-AUTH-TOKEN": cookies.accessToken
            }
        }).then(() => {
            console.clear();
            localStorage.clear();
            removeCookie("accessToken", {path: "/"});
            history.push("/");
        }).catch((err) => {
            alert(err.response.data.msg);
        });
    }

    function deActive() {
        let url = null;
        let header = {};
        if(provider === "LOCAL") {
            url = "/v1/api/user/";
            header = {
                "X-AUTH-TOKEN": cookies.accessToken
            }
        } else {
            url = `/v1/api/oauth2/${provider}`;
            header = {
                "Authorization": cookies.accessToken
            }
        }
        del(url, {
            headers: header
        }).then((response) => {
            alert('회원 탈퇴가 정상적으로 이루어졌습니다.');
            localStorage.clear();
            console.clear();
            removeCookie("accessToken", {path: "/"});
            history.push("/");
        }).catch((err) => {
            console.error('err : ', JSON.stringify(err));
            alert(err.response.data.msg);
        });
    }

    return (
        <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            transition={{ ease: "easeOut", duration: 1 }}
        >
            <div className='MyPage-Content'>
                <MobileTop/>
                <div className='MyPage-Left'>


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

                </div>
                {conditionRender(mode)}
                <div className='MyPage-footer'>
                    <span onClick={Logout}>로그아웃</span>
                    <span>|</span>
                    <Link to="/terms" className='terms'>
                        <span>이용약관</span>
                    </Link>
                    <p className='Copyright'>Copyright 2022. PICTO*MAKER all rights reserved</p>
                </div>
            </div>
        </motion.div>
    );
}