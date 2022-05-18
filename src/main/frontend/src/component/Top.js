import React, {useState} from 'react';
import Logo from "../image/Logo.png";
import "../css/Top.css"
import { Link } from "react-router-dom";
import "../css/font.css"
import axios from "axios";

export default function Top(){

    const getProf = localStorage.getItem('profile');
    const [isLogged, setIsLogged] = useState(false);

    console.log('get profile : ', getProf);

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
                    {getProf != null ? <div className={'p-img'}>
                        <img src={getProf.profile_image_url} alt={'p-image'} style={{width:'40px', height:'40px'}} />
                    </div> : <div></div>}
                </div>
            </div>
        </div>
    );
}