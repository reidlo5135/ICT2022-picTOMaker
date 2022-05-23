import React from 'react';
import emailjs from 'emailjs-com';
import Logo from "../../image/Logo.png";
import "../../App.css"
import "../../css/QnA.css"
import { Link } from "react-router-dom";


export default function QnA() {

    function sendEmail(e){
        e.preventDefault();

        emailjs.sendForm(
            'service_kdh',
            'template_xw0dnqc',
            e.target,
            '_jS58pRqqhKudZIRC'
            ).then(res=>{
                console.log(res);
                alert('문의내용이 전송 되었습니다.');
                window.location.reload();
            }).catch(err => console.log(err));
    }

    return(
        <div className='qnapage'>
        <div className='qna-content'>
        <div class="firefly"></div>
        <div class="firefly"></div>
        <div class="firefly"></div>
        <div class="firefly"></div>
        <div class="firefly"></div>
        <div class="firefly"></div>
        <div class="firefly"></div>
        <div class="firefly"></div>
        <div class="firefly"></div>
        <div class="firefly"></div>
        <div class="firefly"></div>
        <div class="firefly"></div>
        <div class="firefly"></div>
        <div class="firefly"></div>
        <div class="firefly"></div>
            <Link to='/'>
                <div className='qna-logo'>
                    <img src={Logo} alt="PictoMaker-Logo" style={{width:"150px",height:"70px"}}/>
                </div>
            </Link>

            <form onSubmit={sendEmail}>
                <div className='qna-form'>
                    <div className='qna-left'>
                        <div className='left-input'>
                            <label>성함</label><br/>
                            <input type="text" name="user_name" placeholder="성함을 입력하세요"/><br/>

                            <label>이메일</label><br/>
                            <input type="email" name="user_email" placeholder="이메일을 입력하세요"/>
                        </div>
                        </div>

                        <div className='qna-right'>
                            <div className='right-input'>
                            <label>문의사항</label><br/>
                            <textarea name='message' rows='50' className='textareat' placeholder="문의사항을 입력하세요."/>
                        </div>
                    </div>
                    <input type='submit' value='문의하기' className='sendbutton'/>
                </div>
            </form>
        </div>
        </div>
    );
}