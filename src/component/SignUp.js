import React, { Component } from 'react';
import Logo from "../image/Logo.png";
import "../css/SignUp.css"
import "../css/font.css"

class SignUp extends Component{
    render(){
    return (
        <div className='SignUp'>
            <div className='SU-Content'>
                <div className='Logo'>
                    <img src={Logo} alt="PictoMaker-Logo" style={{width:"278px",height:"142px"}}/>
                </div>
                <div className='SU-Input'>
                    <form action="" method="get">
                        <div className='SU-Form'>
                            <div className='Label-txt'>아이디</div>
                            <input type={'email'} placeholder="아이디"/>
                        </div>
                        <div className='SU-Form'>
                            <div className='Label-txt'>비밀번호</div>
                            <input type={'password'} placeholder="비밀번호"/>
                        </div>
                        <div className='SU-Form'>
                            <div className='Label-txt'>비밀번호 확인</div>
                            <input type={'password'} placeholder="비밀번호 확인"/>
                        </div>
                        <div className='SU-Form'>
                            <div className='Label-txt'>이름</div>
                            <input type={'text'} placeholder="이름"/>
                        </div>

                            <button className='SU-Button'>
                                가입완료
                            </button>
                    </form>
                    </div>
                    <div className='Caution'>
                        <p>가입시에 <span>이용약관</span>과 <span>개인정보취급방침</span>에 동의한 것으로 간주합니다.</p>
                    </div>
                

            </div>
        </div>
    );
    }
}

  export default SignUp;