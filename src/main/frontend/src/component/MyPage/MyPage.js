import React,{useState,useEffect} from 'react';
import Logo from "../../image/Logo.png";
import "../../css/MyPage.css"
import "../../css/font.css"
import { Link } from "react-router-dom";
import MyPic from './MyPage-Mypic';
import MyPageProfile from './MyPage-profile';
import SetSocialImg from "../oauth2/SetSocialImg";
import Logout from "../Logout";

export default function MyPageContent(){
    const [mode, setMode] = useState('profile');

    const getProfile = localStorage.getItem('profile');

    const [email, setEmail] = useState();
    const [nickName, setNickName] = useState();
    const [profileImage, setProfileImage] = useState();

    function confirmMode(Paramode) {
        setMode(Paramode);
    }

    const getProf = () => {
        try {
            const jsonProf = JSON.parse(getProfile);
            console.log('jProf : ', jsonProf);

            setEmail(jsonProf.email);
            setNickName(jsonProf.nickname);
            setProfileImage(jsonProf.profile_image_url);
        } catch (err) {
            console.error(err);
        }
    }

    useEffect(()=> {
        console.log('mode : ', mode);
    },[mode]);

    useEffect(() => {
        getProf();
    }, []);

    function conditionRender(conditionMode) {
        if (conditionMode === "profile") {
            return <MyPageProfile/>
        } else if (conditionMode === "mypic") {
            return <MyPic/>
        } else if (conditionMode === "sharepic") {
            return <h1>SharePic</h1>
        }

    }

    return (
        <div className='MyPage-Content'>
            <div className='MyPage-Left'>
                <Link to="/">
                    <div className='MyPage-Logo'>
                        <img src={Logo} alt="Logo" style={{width:"40%",height:"40%"}}/>
                    </div>
                </Link>

                <div className='Profile'>
                    <div className='Pro-Img'>
                        <img className={'p-img'} src={profileImage} alt='p-image' />
                        <SetSocialImg />
                    </div>

                    <div className='Pro-Txt'>
                        <div className='Pro-Title'>
                            {nickName}님, 반갑습니다!
                        </div>
                        <div className='Pro-Email'>
                            {email}
                        </div>
                    </div>
                </div>

                <div className='MyPage-Menu'>
                    <p onClick={()=> {
                        confirmMode("profile")
                    }}>내 프로필</p>

                    <p onClick={()=> {
                        confirmMode("mypic")
                    }}>My 픽토그램</p>

                    <p onClick={()=> {
                        confirmMode("sharepic")
                    }}>공유한 픽토그램</p>
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

    );
}