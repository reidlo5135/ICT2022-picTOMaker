import React, { useEffect, useState } from 'react';
import {useHistory} from "react-router";
import Logo from "../image/Logo.png";
import "../css/SignUp.css"
import "../css/font.css"
import axios from "axios";

export default function SignUp(){

    const [inputValue, setInputValue] = useState({
        email: '',
        name: '',
        nickName: '',
        password: '',
    });

    const {email, name, nickName, password } = inputValue;

    const isValidEmail = email.includes('@') && email.includes('.');
    const specialLetter = password.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
    const isValidPassword = password.length >= 8 && specialLetter >= 1;

    const handleInput = event => {
        const { name, value } = event.target;
        setInputValue({
            ...inputValue,
            [name]: value,
        });
    };

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
            });
        } catch (err) {
            console.error(err);
        }
    }

    const [checkBoxActive, setCheckBoxActive] = useState(false);
    const isCheckBoxClicked = () => {
        setCheckBoxActive(!checkBoxActive);
    };

    const isValidInput = email.length >= 1 && name.length >= 1 && nickName.length >= 1;

    const getIsActive =
        isValidEmail && isValidPassword && isValidInput && checkBoxActive === true;

    const handleButtonValid = () => {
        if (
            !isValidInput ||
            !isValidEmail ||
            !isValidPassword ||
            !isCheckBoxClicked()
        ) {
            alert('please fill in the blanks');
        } else {
            localSignUp();
        }
    };

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
                            <input type={'email'} onChange={handleInput} placeholder="exmaple@picTOMaker.com"/>
                        </div>
                        <div className='SU-Form'>
                            <div className='Label-txt'>이름</div>
                            <input type={'text'} onChange={handleInput} placeholder="EX) 김픽토"/>
                        </div>
                        <div className='SU-Form'>
                            <div className='Label-txt'>닉네임</div>
                            <input type={'text'} onChange={handleInput} placeholder="EX) 픽토메이커"/>
                        </div>
                        <div className='SU-Form'>
                            <div className='Label-txt'>비밀번호</div>
                            <input type={'password'} onChange={handleInput} placeholder="*******"/>
                        </div>
                        <div className='SU-Form'>
                            <div className='Label-txt'>비밀번호 확인</div>
                            <input type={'password'} onChange={handleInput} placeholder="*******"/>
                        </div>

                        <button className={getIsActive ? 'signUpButtonAction' : 'signUpButtonInaction'} onClick={handleButtonValid}>
                            가입완료
                        </button>
                    </form>
                </div>
                <div className='SU-Caution'>
                    <p>
                        <input type={'checkbox'} className={'checkbox'} onClick={isCheckBoxClicked} />
                        가입시에 <span>이용약관</span>과 <span>개인정보취급방침</span>에 동의한 것으로 간주합니다.
                    </p>
                </div>
            </div>
        </div>
    );
}