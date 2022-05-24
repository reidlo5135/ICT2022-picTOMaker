import React, {useEffect} from 'react';
import "aos/dist/aos.css";
import AOS from "aos";
import "../../css/Introduce.css"
import "../../App.css"

import Intimg from "../../image/picto.png";
import Intboximg1 from "../../image/Intboximg1.png";
import Intboximg2 from "../../image/Intboximg2.png";
import Intboximg3 from "../../image/Intboximg3.png";
import Intboximg4 from "../../image/Edit.png";
import Arrow from "../../image/arrow.png";
import { motion } from "framer-motion";



export default function Introduce(){
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
        <div className='Introduce'>

                <div className='Int-MainTitle'>
                    나만의 픽토그램을 만들어 보세요!
                </div>
               <div className='intro-select'>
                    <div className='Int-box'>
                        <div className='IntBox-Content'>
                            <div className='Box-Main Box-Main1'>
                                <div className='IntBox-img'>
                                    <img src={Intboximg1} alt="PictoMaker-Logo" style={{width:"100px",height:"100px"}}/>
                                </div>
                            </div>
                        </div>
                    <div className='Box-desc'>
                        1. 카메라 인식
                        <p>PC의 웹캠 및 스마트폰의 카메라로<br/>사물 및 인체 인식</p>
                    </div>
                </div>

                    <div className='Int-box Int-box2'>
                        <div className='IntBox-Content'>
                            <div className='Box-Main Box-Main2'>
                                <div className='IntBox-img'>
                                    <img src={Intboximg2} alt="PictoMaker-Logo" style={{width:"100px",height:"100px"}}/>
                                </div>
                            </div>
                        </div>
                        <div className='Box-desc'>
                            2. 데이터 추출
                            <p>인체,사물을 인식하여<br/>사물의 정보나 인체 위치 데이터 추출</p>
                        </div>
                    </div>

                    <div className='Int-box Int-box3'>
                        <div className='IntBox-Content'>
                            <div className='Box-Main Box-Main3'>
                                <div className='IntBox-img'>
                                    <img src={Intboximg3} alt="PictoMaker-Logo" style={{width:"100px",height:"100px"}}/>
                                </div>
                            </div>
                        </div>
                        <div className='Box-desc'>
                            3. 그래픽 변환
                            <p>추출된 데이터를 바탕으로<br/>그래픽을 덮어 씌워<br/>픽토그램 제공</p>
                        </div>
                    </div>

                    <div className='Int-box Int-box4'>
                        <div className='IntBox-Content'>
                            <div className='Box-Main Box-Main4'>
                                <div className='IntBox-img'>
                                    <img src={Intboximg4} alt="PictoMaker-Logo" style={{width:"100px",height:"100px"}}/>
                                </div>
                            </div>
                        </div>
                        <div className='Box-desc'>
                            4. 커스텀 디자인/편집
                            <p>제공되는 편집 툴을 사용하여<br/>픽토그램 수정 및 배포</p>
                        </div>
                    </div>
                </div>

            </div>


           
        </motion.div>
    );
}