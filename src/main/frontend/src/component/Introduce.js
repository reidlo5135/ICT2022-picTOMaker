import React from 'react';
import "aos/dist/aos.css";
import AOS from "aos";
import "../css/Introduce.css"
import "../App.css"

import Intimg from "../image/picto.png";
import Intboximg1 from "../image/Intboximg1.png";
import Intboximg2 from "../image/Intboximg2.png";
import Intboximg3 from "../image/Intboximg3.png";
import Intboximg4 from "../image/Intboximg4.png";
import Arrow from "../image/arrow.png";




export default function Introduce(){
    AOS.init = function (param) {

    }
    AOS.init({
        easing: 'ease-out-back',
        duration: 1000
    });

    return(
        <div className='Introduce'>
            <div class="wave">
                <div className='Int-MainTitle'>
                    나만의 픽토그램을 만들어 보세요!
                </div>
                <svg width="100%" height="200px" fill="none" version="1.1" xmlns="http://www.w3.org/2000/svg">
                    <path
                        fill="white"
                        d=" M0 67C 273,183 822,-41 1920.01,106 V 359 H 0 V 67Z"
                    >
                    </path>
                </svg>
                <div className='Int-Main'>
                    <div className='Intimg'>
                        <img src={Intimg} alt="PictoMaker-Logo" style={{width:"440px",height:"500px"}}/>
                    </div>
                    <div className='Int-Subtitle'>
                        <span className='Top'>캠화면에 맞춰 포즈를 취해보세요.</span><br/>
                        <span className="Top">아이콘을 생성해 줍니다.</span><br/>
                        <span className='Bot'>맞춤형 아이콘 생성</span>
                    </div>
                </div>
            </div>


            <div class="col-md-7" data-aos="fade-right">
                <div className='Int-box'>
                    <div className='IntBox-Content'>
                        <div className='Box-Tit'>
                            1. 인체 및 사물 인식
                        </div>
                        <div className='Box-Main'>
                            <div className='IntBox-img'>
                                <img src={Intboximg1} alt="PictoMaker-Logo" style={{width:"400px",height:"300px"}}/>
                            </div>
                            <div className='Box-Subtit'>
                                <p>PC의 웹캠 및 스마트폰의 카메라를 통해</p>
                                <p>사물 및 인체를 인식한다.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <div className='Arrow'>
                <img src={Arrow} alt="PictoMaker-Logo" style={{width:"300px",height:"100px"}}/>
            </div>

            <div class="col-md-7" data-aos="fade-left">
                <div className='Int-box Int-box2'>
                    <div className='IntBox-Content'>
                        <div className='Box-Tit'>
                            2. 데이터 추출
                        </div>
                        <div className='Box-Main'>
                            <div className='IntBox-img'>
                                <img src={Intboximg2} alt="PictoMaker-Logo" style={{width:"400px",height:"300px"}}/>
                            </div>
                            <div className='Box-Subtit'>
                                <p>MediaPipe와 Tensorflow를 이용해</p>
                                <p>사용자가 직접 모델(인체,사물)을 인식하여</p>
                                <p>사물의 정보나 인체 위치 데이터 추출</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div className='Arrow'>
                <img src={Arrow} alt="PictoMaker-Logo" style={{width:"300px",height:"100px"}}/>
            </div>

            <div class="col-md-7" data-aos="fade-right">
                <div className='Int-box Int-box3'>
                    <div className='IntBox-Content'>
                        <div className='Box-Tit'>
                            3. 실시간 그래픽 변환
                        </div>
                        <div className='Box-Main'>
                            <div className='IntBox-img'>
                                <img src={Intboximg3} alt="PictoMaker-Logo" style={{width:"400px",height:"300px"}}/>
                            </div>
                            <div className='Box-Subtit'>
                                <p>추출된 데이터를 바탕으로 실시간으로</p>
                                <p>그래픽을 덮어씌워 사용자의 움직임에 따른</p>
                                <p>픽토그램을 제공한다.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div className='Arrow'>
                <img src={Arrow} alt="PictoMaker-Logo" style={{width:"300px",height:"100px"}}/>
            </div>


            <div class="col-md-7" data-aos="fade-left">
                <div className='Int-box Int-box4'>
                    <div className='IntBox-Content'>
                        <div className='Box-Tit'>
                            4. 커스텀 디자인/편집
                        </div>
                        <div className='Box-Main'>
                            <div className='IntBox-img'>
                                <img src={Intboximg4} alt="PictoMaker-Logo" style={{width:"400px",height:"300px"}}/>
                            </div>
                            <div className='Box-Subtit'>
                                <p>픽스타그램에서 제공하는 툴을</p>
                                <p>활용하여 사용자가 원하는 디자인의</p>
                                <p>픽토그램 제작 및 수정, 배포기능 지원</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    );
}