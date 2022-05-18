import React, {useState} from 'react';
import Logo from "../image/Logo.png";
import "../css/Top.css"
import { Link } from "react-router-dom";
import "../css/font.css"
import axios from "axios";

export default function Top(){

    const access_token = localStorage.getItem('access_token');
    const provider = localStorage.getItem('provider');
    const [profile, setProfile] = useState(null);
    const [isLogged, setIsLogged] = useState(false);
    const [isLoading, setIsLoading] = useState(false);

    if(access_token) {
        axios.post(`/oauth2/profile/${provider}`, {
            access_token
        },{
            baseURL: 'http://localhost:8080',
            withCredentials: true
        }).then((response) => {
            console.log('profile res data.data : ', response.data.data);

            const profile = response.data.data;

            console.log('profile res profile : ', profile);

            if(profile) {
                setProfile(profile);
                setIsLogged(true);
            } else {
                alert("An Error Occurred");
            }
        });
    }

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
                    {isLogged ? <div className={'p-img'}>
                        <img src={profile.profile_image_url} alt={'p_image'} style={{width:'40px', height:'40px'}} />
                    </div> : <div></div>}
                </div>
            </div>
        </div>
    );
}