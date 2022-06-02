import React , {useState}from 'react';
import emailjs from 'emailjs-com';
import Logo from "../../image/Logo.png";
import "../../App.css"
import "../../css/QnA.css"
import { Link } from "react-router-dom";
import { motion } from "framer-motion";


export default function QnA() {
    const [isOpen, setIsOpen] = useState(false)

    function sendEmail(e){
        e.preventDefault();

/*         emailjs.sendForm(
            'service_kdh',
            'template_xw0dnqc',
            e.target,
            '_jS58pRqqhKudZIRC'
            ).then(res=>{
                console.log(res);
                alert('문의내용이 전송 되었습니다.');
                console.log(isOpen);
            }).catch(err => console.log(err)); */
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

            <motion.form onSubmit={sendEmail}
             animate={isOpen ? "open" : "closed"}
             variants={{
             open: {  opacity: 0, x: 0 },
             closed: {  opacity: 1, x: 100 }
             }} >
            <div class="center">
                <div class="phone">
                    <div class="notch">
                        <div class="mic" />
                        <div class="camera" />
                    </div>

                    <div className='qna-form'>
                    <div className='qna-left'>
                        <div className='left-input'>
                            <label>성함</label><br/>
                            <input type="text" name="user_name" placeholder="성함을 입력하세요" required/><br/>

                            <label>이메일</label><br/>
                            <input type="email" name="user_email" placeholder="이메일을 입력하세요" required/>
                        </div>
                        </div>

                        <div className='qna-right'>
                            <div className='right-input'>
                            <label>문의사항</label><br/>
                            <textarea name='message' rows='50' className='textareat' placeholder="문의사항을 입력하세요." required/>
                        </div>
                    </div>
                    <motion.input type='submit' value='문의하기' className='sendbutton' onClick={() => setIsOpen(!isOpen)}
                    animate={isOpen ? "open" : "closed"}
                    variants={{
                        open: { opacity: 1, x: 0 },
                        closed: { opacity: 1, x: 0},
                    }}/>
                </div>
                </div>
            </div>
                
            </motion.form>
        </div>
        </div>
    );
}