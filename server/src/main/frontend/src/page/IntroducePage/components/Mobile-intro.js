import React, {useEffect} from 'react';
import "aos/dist/aos.css";
import AOS from "aos";
import "../introduce.css"
import "../../../App.css"
import Logo from "../../../assets/image/Logo.png"
import Step1 from "../../../assets/image/step1.svg"
import Step2 from "../../../assets/image/step2.svg"
import Step3 from "../../../assets/image/step3.svg"
import { motion } from "framer-motion";
import { Link } from 'react-router-dom';
import Circle from "../../../assets/image/circle.png"
import Top from '../../../component/Top';

export default function M_Intro(){
    useEffect(() => {
        AOS.init({
            duration : 1000
        });
    });

    return(
        <motion.div 
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            transition={{ ease: "easeOut", duration: 1 }}
        >
        <Top/>
            <div className='Introduce' >
                <div className="col-md-7 intro-content intro-content1" data-aos="fade-up" data-aos-duration="300">
                    <div className='intro-qeustion' ><img src={Logo} alt={"픽토메이커"} style={{width:"70px",height:"35px"}}/>란?</div>
                    <div className='intro-title' data-aos="fade-up" data-aos-duration="1000" data-aos-delay="300">
                        <div>카메라 촬영만으로</div>
                        <div className='br-title'>저작권 없는 픽토그램을 제공합니다.</div>
                    </div>
                    <div className='intro-subtitle' data-aos="fade-up" data-aos-duration="2000" data-aos-delay="500">
                        <div className='intro-answer' >
                            <p className='answer-title'>Pictogram + Maker</p>
                            <p className='answer-subtit'>언어를 초월해 같은 의미로<br/>통할 수 있는 그림(Pictogram)을 만들어 <br/>사용자에게 제공합니다.</p>
                        </div>
                        <div className='circle-img'><img className="intro-circle" src={Circle} alt={"픽토메이커 설명"}/></div>
                    </div>
                </div>

                <div className="col-md-7 intro-content intro-content2">
                    <div className='intro-title' data-aos="fade-up" data-aos-duration="1000">
                        <div >사용법이 아주 간단해요</div>
                        <div className='br-title'>3가지 단계만 따라해 보세요!</div>
                    </div>
                    <div className='intro-manual'>
                    <div className='intro-imgmanual'>
                        <div className='step1-wrap step-wrap'>
                            <div className='step step1' data-aos="fade-left" data-aos-duration="1000" data-aos-delay="300">
                                <img src={Step1} alt="1단계"/> 
                            </div>
                            <div className='step step1-1' data-aos="fade-left" data-aos-duration="1000" data-aos-delay="500"> 
                                <div className='step-desc'>하나,</div>
                                    <div className='step-title step1-title'>
                                    <div>캠 화면에 맞춰<br/>포즈를 취해보세요!</div>
                                </div>
                            </div>
                        </div>

                        <div className='step2-wrap step-wrap'>
                            <div className='step step2' data-aos="fade-left" data-aos-duration="1000" data-aos-delay="300">
                                <img src={Step2} alt="2단계"/>
                            </div>
                            <div className='step step2-1' data-aos="fade-left" data-aos-duration="1000" data-aos-delay="500">
                                <div className='step-desc'>두울,</div>
                                <div className='step-title step2-title'>
                                    <div>생성된 픽토그램을 <br/>편집해 보세요!</div>
                                </div>
                            </div>
                        </div>

                        <div className='step3-wrap step-wrap'>
                            <div className='step step3' data-aos="fade-left" data-aos-duration="1000" data-aos-delay="300">
                                <img src={Step3} alt="3단계"/>
                            </div>
                            <div className='step step3-1' data-aos="fade-left" data-aos-duration="1000" data-aos-delay="500">
                                <div className='step-desc'>세엣,</div>
                                <div className='step-title step3-title'>
                                    <div>완성된 픽토그램을<br/> 다운받으세요!</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    </div>
                </div>

                <div className="col-md-7 intro-content intro-content3">
                    <div className='col-md-7 intro-title3'>
                        <div className='intro-titlediv'>
                            <div className='intro-title-content'>
                                <div className='tit-wrap'>
                                    <div className="intro-subtit" data-aos="fade-right" data-aos-duration="1000" data-aos-delay="300">나만의 픽토그램이 필요하신가요?</div>
                                    <div  data-aos="fade-right" data-aos-duration="1000" data-aos-delay="700">지금 바로 시작해 보세요!</div>
                                </div>
                                <Link to="/">
                                    <button className='MainButton'  data-aos="fade-right" data-aos-duration="1000" data-aos-delay="1000">
                                        처음으로
                                    </button>
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>


                {/* <div class="intro-content intro-content4"  data-aos="fade-down" data-aos-easing="linear" data-aos-duration="1500">
                    
                </div> */}
            </div>
        </motion.div>
    );
}