import React from 'react';
import Logo from "../image/Logo.png";
import "../css/Top.css"
import { Link } from "react-router-dom";
import "../css/font.css"
import Profile from "./Profile";

export default function Top(){

    return (
        <div className='top'>
            <div className='topMenu'>
                <Link to='/'>
                    <img src={Logo} alt="PictoMaker-Logo" style={{width:"100px",height:"50px"}}/>
                </Link>
                <div className='GnbMenu'>
                    <Link to ='/introduce'>
                        <div>소개</div>
                    </Link>
                    <Link to ='/select'>
                        <div>시작하기</div>
                    </Link>
                    <Link to ='/qna'>
                        <div>문의사항</div>
                    </Link>
                    <div>커뮤니티</div>
                    <Profile />
                </div>
                
            </div>
        </div>
    );
}