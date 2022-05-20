import React, { useEffect, useState } from 'react';
import {useHistory} from "react-router";
import Logo from "../image/Logo.png";
import "../css/SignUp.css"
import "../css/font.css"
import axios from "axios";

export default function SignUp(){

    const history = useHistory();
    const [email, setEmail] = useState("");
    const [name, setName] = useState("");
    const [nickName, setNickName] = useState("");
    const [password, setPassword] = useState("");
    const [checkPassword, setCheckPassword] = useState("");

    const emailInputCheck = (event) => {
        setEmail(event.target.value);
    }

    const nameInputCheck = (event) => {
        setName(event.target.value);
    }

    const nickNameInputCheck = (event) => {
        setNickName(event.target.value);
    }

    const pwInputCheck = (event) => {
        setPassword(event.target.value);
    }

    const checkPwInputCheck = (event) => {
        setCheckPassword(event.target.value);
    }

    const pwCheck = (event) => {
        const password = pwCheck(event);
        const checkPassword = checkPwInputCheck(event);

        if(password.length < 1 || checkPassword.length < 1) {
            this.setState({
                checkPassword: '패스워드 입력',
            });
        } else if(password === checkPassword) {
            this.setState({
                checkPassword: '일치',
            });
        } else {
            this.setState({
                checkPassword: '불일치',
            });
        }
    }

    const localSignUp = () => {
        try {
            axios.post('/v1/user/signUp', {
                email,
                name,
                nickName,
                password
            },{
                baseURL: 'http://localhost:8080',
                withCredentials: true
            }).then((response) => {
                console.log('res data ', response.data);
                console.log('res data.data ', response.data.data);

                history.push("/");
            });
        } catch (err) {
            console.error(err);
        }
    }

    return (
        <div className='SignUp'>
            <div className='SU-Content'>
                <div className='SU-Logo'>
                    <img src={Logo} alt="PictoMaker-Logo" style={{width:"278px",height:"142px"}}/>
                </div>
                <div className='SU-Input'>
                    <form>
                        <div className='SU-Form'>
                            <div className='Label-txt'>이메일</div>
                            <input type={'email'} onChange={emailInputCheck} placeholder="exmaple@picTOMaker.com"/>
                        </div>
                        <div className='SU-Form'>
                            <div className='Label-txt'>이름</div>
                            <input type={'text'} onChange={nickNameInputCheck} placeholder="EX) 김픽토"/>
                        </div>
                        <div className='SU-Form'>
                            <div className='Label-txt'>닉네임</div>
                            <input type={'text'} onChange={nameInputCheck} placeholder="EX) 픽토메이커"/>
                        </div>
                        <div className='SU-Form'>
                            <div className='Label-txt'>비밀번호</div>
                            <input type={'password'} onChange={pwInputCheck} placeholder="*******"/>
                        </div>
                        <div className='SU-Form'>
                            <div className='Label-txt'>비밀번호 확인</div>
                            <input type={'password'} onChange={pwCheck} placeholder="*******"/>
                        </div>

                        <button className='SU-Button' onClick={localSignUp}>
                            가입완료
                        </button>
                    </form>
                </div>
                <div className='SU-Caution'>
                    <p>가입시에 <span>이용약관</span>과 <span>개인정보취급방침</span>에 동의한 것으로 간주합니다.</p>
                </div>
            </div>
        </div>
    );
}