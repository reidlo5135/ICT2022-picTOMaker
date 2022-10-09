import React, {useState, useEffect} from 'react';
import {post} from "../../services/AxiosService";
import { Link } from "react-router-dom";
import {useHistory} from "react-router";
import { motion } from "framer-motion";
import Logo from "../../assets/image/Logo.png";
import "../../App.css";
import "./qna.css";

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
            setProfEmail(jsonProf.email);
            setProfNickName(jsonProf.nickName);
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
        post(`/v1/api/qna/${provider}`, {
            email: profEmail,
            name: profNickName,
            qna
        }).then((response) => {
            if(response.data.code === 0) {
                alert('문의사항이 접수되었습니다.');
                setIsOpen(!isOpen);
                history.push("/");
            }
        }).catch((err) => {
            console.error('err : ', JSON.stringify(err));
            alert(err.response.data.msg);
        });
    }

    function sendEmail(e){
        e.preventDefault();
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