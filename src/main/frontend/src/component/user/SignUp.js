import React, { useEffect, useState } from 'react';
import { Link, useHistory } from "react-router-dom";
import Logo from "../../image/Logo.png";
import "../../css/SignUp.css"
import "../../css/font.css"
import axios from "axios";

export default function SignUp(){

    const history = useHistory();

    const [inputValue, setInputValue] = useState({
        email: '',
        name: '',
        nickName: '',
        password: '',
        confirmPassword: '',
    });

    const {email, name, nickName, password, confirmPassword } = inputValue;

    const isValidEmail = email.includes('@') && email.includes('.');
    const specialLetter = password.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
    const isValidPassword = password.length >= 8 && specialLetter >= 1;
    const isEqualsPassword = password === confirmPassword;

    const handleInput = event => {
        const { name, value } = event.target;
        setInputValue({
            ...inputValue,
            [name]: value,
        });
    };

    const isValidInput = email.length >= 1 && name.length >= 1 && nickName.length >= 1;

    const getIsActive = isValidEmail && isValidPassword && isValidInput && isEqualsPassword === true;

    const handleButtonValid = () => {
        console.log('isInput : ', isValidInput);
        console.log('isEmail : ', isValidEmail);
        console.log('isPassword : ', isValidPassword);
        console.log('isEqualsPassword : ', isEqualsPassword);

        if (!isValidInput) {
            alert('빈 칸을 모두 채워주십시오.');
        } else if(!isValidEmail) {
            alert('유호하지 않은 이메일입니다.');
        } else if(!isValidPassword) {
            alert('비밀번호는 특수문자를 포함하여 8자 이상이어야합니다.');
        }else if(!isEqualsPassword) {
            alert('비밀번호가 일치하지 않습니다.');
        } else {
            try {
                axios.post('/v1/user/signUp', {
                    email: email,
                    name: name,
                    nickName: nickName,
                    password: password
                },{
                    baseURL: 'http://localhost:8080',
                    withCredentials: true
                }).then((response) => {
                    alert(nickName + ' 픽토메이커님 환영합니다!');
                    history.push("/");
                });
            } catch (err) {
                console.error(err);
            }
        }
    };

    return (
        <div className='SignUp'>
            <div className='SU-Content'>
                <Link to='/'>
                    <div className='SU-Logo'>
                        <img src={Logo} alt="PictoMaker-Logo" style={{width:"278px",height:"142px"}}/>
                    </div>
                </Link>
                <div className='SU-Input'>
                    <form>
                        <div className='SU-Form'>
                            <div className='Label-txt'>이메일</div>
                            <input type={'email'} name={'email'} onChange={handleInput} placeholder="exmaple@picTOMaker.com"/>
                        </div>
                        <div className='SU-Form'>
                            <div className='Label-txt'>이름</div>
                            <input type={'text'} name={'name'} onChange={handleInput} placeholder="EX) 김픽토"/>
                        </div>
                        <div className='SU-Form'>
                            <div className='Label-txt'>닉네임</div>
                            <input type={'text'} name={'nickName'} onChange={handleInput} placeholder="EX) 픽토메이커"/>
                        </div>
                        <div className='SU-Form'>
                            <div className='Label-txt'>비밀번호</div>
                            <input type={'password'} name={'password'} onChange={handleInput} placeholder="*******"/>
                        </div>
                        <div className='SU-Form'>
                            <div className='Label-txt'>비밀번호 확인</div>
                            <input type={'password'} name={'confirmPassword'} onChange={handleInput} placeholder="*******"/>
                        </div>

                        <button className={getIsActive ? 'signUpButtonAction' : 'signUpButtonInAction'} onClick={handleButtonValid}>
                            가입완료
                        </button>
                    </form>
                </div>
                <div className='SU-Caution'>
                    <p>
                        가입시에 <span>이용약관</span>과 <span>개인정보취급방침</span>에 동의한 것으로 간주합니다.
                    </p>
                </div>
            </div>
        </div>
    );
}