import React, {useState, useEffect} from 'react';
import emailjs from 'emailjs-com';
import { Link } from "react-router-dom";
import {useHistory} from "react-router";
import axios from "axios";
import { motion } from "framer-motion";
import Postani1 from "../../image/postcar.png";
import Postani2 from "../../image/email.png";
import Logo from "../../image/Logo.png";
import "../../App.css";
import "../../css/QnA.css";

export default function QnA() {
    const history = useHistory();
    const [isOpen, setIsOpen] = useState(false);
    const [profEmail, setProfEmail] = useState(null);
    const [profNickName, setProfNickName] = useState(null);
    const getProfile = localStorage.getItem('profile');
    const provider = localStorage.getItem('provider');

    const [inputValue, setInputValue] = useState({
        name: '',
        email: '',
        qna: ''
    });

    const { name, email, qna } = inputValue;

    const getProf = () => {
        try {
            const jsonProf = JSON.parse(getProfile);
            console.log('QNA jProf : ', jsonProf);

            setProfEmail(jsonProf.email);
            setProfNickName(jsonProf.nickname);
        } catch (err) {
            console.error(err);
        }
    }

    useEffect(() => {
        getProf();
    }, []);

    const handleInput = e => {
        const { name, value } = e.target;
        setInputValue({
            ...inputValue,
            [name]: value,
        });
    };

    const isValidQna = qna.length >= 1;
    const getIsActive = isValidQna === true;
    const getIsOpen = isOpen === true;

    function onChangeIsOpen() {
        try {
            axios.post(`/v1/api/qna/register/${provider}`, {
                email: profEmail,
                name: profNickName,
                qna: qna
            }).then((response) => {
                console.log('response : ', response.data);
                console.log('response : ', response.data.data);

                if(response.data.code === 0) {
                    alert('문의사항이 접수되었습니다.');
                    setIsOpen(!isOpen);
                    history.push("/");
                } else {
                    alert('An Error Occurred code : ' + response.data.code);
                }
            });
        } catch (err) {
            console.error(err);
        }
        
        /* setTimeout(() => {
            window.location.reload();
        }, 2500); */
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
                     closed: {  opacity: 1, x: 0 }
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
                                    <input type="text" name="name" placeholder="성함을 입력하세요" value={profNickName} onChange={handleInput} /><br/>

                                    <label>이메일</label><br/>
                                    <input type="email" name="email" placeholder="이메일을 입력하세요" value={profEmail} onChange={handleInput}/>
                                </div>
                                </div>

                                <div className='qna-right'>
                                    <div className='right-input'>
                                    <label>문의사항</label><br/>
                                    <textarea name='qna' rows='50' className='textareat' placeholder="문의사항을 입력하세요." onChange={handleInput}/>
                                </div>
                            </div>
                            <input type='submit' value='문의하기' className={getIsActive ? 'sendbutton' : 'sendinbutton'} disabled={getIsActive ?  false : true} onClick={onChangeIsOpen} onSubmit={sendEmail}/>
                        </div>
                        </div>
                    </div>
                </motion.form>
{/*                 <motion.div className='mail-ani' 
                     animate={isOpen ? true : false}
                     variants={{
                     open: {  opacity: 1,x: 1500},
                     closed: {  opacity: 0, x:0}
                     }} >
                     <img src={Postani1} alt="Post-animation" className={getIsOpen ? 'postcar' : 'postcar2'}/>
                     <img src={Postani2} alt="Post-animation" className={getIsOpen ? 'postmail' : 'postmail2'} style={{width:"150px",height:"70px"}}/>
                 </motion.div> */}
            </div>
        </div>
    );
}