import React from 'react';
import Human from "../../../assets/image/Human.png";
import Things from "../../../assets/image/Things.png";
import Edit from "../../../assets/image/Edit.png";
import { Link } from "react-router-dom";
import "../menuSelect.css";
import "../../../styles/font.css";


export default function MenuSelectDetails(){
    return(
        <div className='Select-Content'>
            <div className='HT-Content'>
                <Link to="/pose/hand">
                    <div className='Human-content cont'>
                        <span></span><span></span><span></span><span></span>
                        <div className='cont-wrap'>
                            <div className='img-wrap'>
                                <div className='Human-Image'>
                                    <img src={Human} alt="Human-Img" style={{width:"30%",height:"30%"}}/>
                                </div>
                            </div>
                            <div className='desc-wrap'>
                                <div className='Human-mdes'>
                                    <p>인체인식(모바일)</p>
                                </div>
                                <div className='Human-des'>
                                    인체를 인식하여 <br />
                                    사람 형태의 그래픽을 생성해줍니다.<br />
                                    카메라에 신체를 맞춰 포즈를 취해보세요!
                                </div>
                            </div>
                        </div>
                    </div>
                </Link>

                <Link to="/edit">
                    <div className='Edit-Content cont'>
                        <span></span><span></span><span></span><span></span>
                        <div className='cont-wrap'>
                            <div className='img-wrap'>
                                <div className='Edit-Image'>
                                    <img src={Edit} alt="Edit-Img" style={{width:"30%",height:"30%"}}/>
                                </div>
                            </div>
                            <div className='desc-wrap'>
                                <div className='Edit-mdes'>
                                    <p>픽토그램 편집</p>
                                </div>
                                <div className='Edit-des'>
                                    본인이 만든 픽토그램을 <br />
                                    취향에 맞게 자유롭게 수정해 보세요! <br />
                                </div>
                            </div>
                        </div>
                    </div>
                </Link>


                <Link to="/things">
                    <div className='Things-Content cont'>
                        <span></span><span></span><span></span><span></span>
                        <div className='cont-wrap'>
                            <div className='img-wrap'>
                                <div className='Things-Image'>
                                    <img src={Things} alt="Edit-Img" style={{width:"30%",height:"30%"}}/>
                                </div>
                            </div>

                            <div className='desc-wrap'>
                                <div className='Things-mdes'>
                                    <p>사물인식</p>
                                </div>
                                <div className='Things-des'>
                                    사물을 인식하여 <br />
                                    사물 형태의 그래픽을 생성해줍니다.<br />
                                    카메라에 사물에 맞춰 <br/>
                                    그래픽을 생성해 보세요!
                                </div>
                            </div>
                        </div>
                    </div>
                </Link>
            </div>
        </div>
    );
}