import React , {useState}from 'react';
import emailjs from 'emailjs-com';
import Logo from "../../image/Logo.png";
import "../../App.css"
import "../../css/QnA.css"
import { Link } from "react-router-dom";
import { motion } from "framer-motion";


export default function QnA() {
    const [isOpen, setIsOpen] = useState(false)
    const [inputValue, setInputValue] = useState({
        name: '',
        email: '',
        qna: ''
    });

    const { name, email, qna} = inputValue;

    const isValidName = name.length >= 1;
    const isValidEmail = email.includes('@') && email.includes('.');
    const isValidQna = qna.length >= 1;

    const handleInput = e => {
        const { name, value } = e.target;
        setInputValue({
            ...inputValue,
            [name]: value,
        });
    };

    const isValidInput = name.length >= 1 && email.length >= 1 &&  qna.length >= 1;

    const getIsActive = isValidEmail && isValidName && isValidQna === true;

    const handleButtonValid = (e) => {
        e.preventDefault();
        if (!isValidInput) {
            alert('성함을 입력해 주십시오');
        } else if(!isValidEmail) {
            alert('이메일을 입력해 주십시오');
        } else if(!isValidQna) {
            alert('문의 내용을 입력해 주십시오');
        }
        };
    

    /* const onChangeIsopen = () =>{
        setIsOpen(!isOpen)
    } */
    function onChangeIsopen() {
        setIsOpen(!isOpen);
        alert("문의가 전송 되었습니다.");
        
        setTimeout(() => {
            window.location.reload();
        }, 3000);
        
        
    }

    function sendEmail(e){
        e.preventDefault();

    /*  emailjs.sendForm(
            'service_kdh',
            'template_xw0dnqc',
            e.target,
            '_jS58pRqqhKudZIRC'
            ).then(res=>{
                console.log(res);
                alert('문의내용이 전송 되었습니다.');
                console.log(isOpen);
            }).catch(err => console.log(err));  */
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
                            <input type="text" name="name" placeholder="성함을 입력하세요" onChange={handleInput}/><br/>

                            <label>이메일</label><br/>
                            <input type="email" name="email" placeholder="이메일을 입력하세요" onChange={handleInput}/>
                        </div>
                        </div>

                        <div className='qna-right'>
                            <div className='right-input'>
                            <label>문의사항</label><br/>
                            <textarea name='qna' rows='50' className='textareat' placeholder="문의사항을 입력하세요."  onChange={handleInput}/>
                        </div>
                    </div>
                    <input type='submit' value='문의하기' className={getIsActive ? 'sendbutton' : 'sendinbutton'} disabled={getIsActive ?  false : true} onClick={onChangeIsopen} onBlur={handleButtonValid}
                   
                    />
                </div>
                </div>
            </div>
                
            </motion.form>

            <motion.div className='mail-ani'
            onSubmit={sendEmail}
             animate={isOpen ? "open" : "closed"}
             variants={{
             open: {  opacity: 1, x: 1000 },
             closed: {  opacity: 0, x: 0 }
             }} ></motion.div>
        </div>
        </div>
    );
}