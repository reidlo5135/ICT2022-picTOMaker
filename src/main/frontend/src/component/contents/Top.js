import React from 'react';
import {useHistory} from "react-router";
import { Link } from "react-router-dom";
import Profile from "../user/Profile";
import Logo from "../../image/Logo.png";
import Sidebar from "../../component/contents/Sidebar";
import "../../css/Top.css";
import "../../css/font.css";


export default function Top(){
    const history = useHistory();
    const access_token = localStorage.getItem('access_token');

    function start() {
        try {
            if(access_token !== null) {
                history.push("/select");
            } else {
                alert('로그인 후 사용 가능합니다.');
            }
        } catch (err) {
            console.error(err);
        }
    }

    function moveToComm() {
        try {
            if(access_token !== null) {
                history.push("/community");
            } else {
                alert('로그인 후 사용 가능합니다.');
            }
        } catch (err) {
            console.error(err);
        }
    }

    function moveToQna() {
        try {
            if(access_token !== null) {
                history.push("/qna");
            } else {
                alert('로그인 후 사용 가능합니다.');
            }
        } catch (err) {
            console.error(err);
        }
    }

    return (
        <div className='top' id="1">
            <div className='topMenu'>
                <Link to='/'>
                    <img src={Logo} className='img_logo' alt="PictoMaker-Logo" style={{width:"100px",height:"50px"}}/>
                </Link>
                {/* <div className='GnbMenu'>
                    <Link to ='/introduce'>
                        <div>소개</div>
                    </Link>
                    <div onClick={start} style={{cursor:"pointer"}}>시작하기</div>
                    <div onClick={moveToQna}>문의사항</div>
                    <div onClick={moveToComm}>커뮤니티</div>              
                </div> */}
                {access_token === null ? <div></div> : <Profile />}
                {access_token === null ? <div></div> : <Sidebar/>}
            </div>
        </div>
    );
}