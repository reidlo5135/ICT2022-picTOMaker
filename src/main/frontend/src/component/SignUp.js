import React, { Component } from 'react';
import emailjs from '@emailjs/browser';
import Logo from "../image/Logo.png";
import "../css/SignUp.css"
import "../css/font.css"
import axios from "axios";

class SignUp extends Component{
    constructor(props) {
        super(props);
        this.state = {
            email: "",
            name: "",
            nickName:"",
            password: "",
            checkPassword:""
        };
        console.log('state :' , this.state);
    }

    emailInputCheck = (event) => {
        this.setState({email: event.target.value});
    }

    nameInputCheck = (event) => {
        this.setState({name: event.target.value});
    }

    nickNameInputCheck = (event) => {
        this.setState({nickName: event.target.value});
    }

    pwInputCheck = (event) => {
        this.setState({password: event.target.value});
    }

    checkPwInputCheck = (event) => {
        this.setState({checkPassword: event.target.value});
    }

    pwCheck = (event) => {
        const password = this.pwInputCheck(event);
        const checkPassword = this.checkPwInputCheck(event);

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

    sendEmail = (event) => {
        event.preventDefault();

        emailjs.sendForm('service_nz8k095', 'template_xh1rx6r', this.state.email)
            .then((response) => {
                console.log(response);
            }, (error) => {
                console.error(error.text);
            });
    }

    localSignUp = () => {
        try {
            axios.post('/v1/user/signUp', {
                email: this.state.email,
                name: this.state.name,
                nickName: this.state.nickName,
                password: this.state.password
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

    render(){
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
                                <input type={'email'} onChange={this.emailInputCheck} placeholder="exmaple@picTOMaker.com"/>
                            </div>
                            <div className='SU-Form'>
                                <div className='Label-txt'>이름</div>
                                <input type={'text'} onChange={this.nickNameInputCheck} placeholder="EX) 김픽토"/>
                            </div>
                            <div className='SU-Form'>
                                <div className='Label-txt'>닉네임</div>
                                <input type={'text'} onChange={this.nameInputCheck} placeholder="EX) 픽토메이커"/>
                            </div>
                            <div className='SU-Form'>
                                <div className='Label-txt'>비밀번호</div>
                                <input type={'password'} onChange={this.pwInputCheck} placeholder="*******"/>
                            </div>
                            <div className='SU-Form'>
                                <div className='Label-txt'>비밀번호 확인</div>
                                <input type={'password'} onChange={this.pwCheck} placeholder="*******"/>
                            </div>

                            <button className='SU-Button' onClick={this.localSignUp}>
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
}

export default SignUp;