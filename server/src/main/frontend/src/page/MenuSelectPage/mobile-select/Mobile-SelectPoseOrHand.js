import React from "react";
import {Link} from "react-router-dom";
import Logo from "../../../assets/image/Logo.png";
import Human from "../../../assets/image/Human.png";
import Hand from "../../../assets/image/Hand.png";
import "../menuSelect.css";
import "../../../styles/font.css";

export default function MobileSelectPoseOrHand() {
    return (
        <div className="selectpage">
            <div className="star-ani">
                <div className="star"></div>
                <div className="star"></div>
                <div className="star"></div>
                <div className="star"></div>
                <div className="star"></div>
                <div className="star"></div>
                <div className="star"></div>
            </div>
            <Link to='/'>
                <div className='qna-logo'>
                    <img src={Logo} alt="PictoMaker-Logo" style={{width:"150px",height:"70px"}}/>
                </div>
            </Link>
            <div className='Select-Content'>
                <span className='start'></span>
                <span className='start'></span>
                <span className='start'></span>
                <span className='start'></span>
                <span className='start'></span>
                <span className='start'></span>
                <span className='start'></span>
                <span className='start'></span>
                <span className='start'></span>
                <span className='start'></span>
                <div className='HT-Content'>
                    <Link to="/studio/pose/mobile">
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
                                        <p>전신(Pose) 촬영</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </Link>
                    <Link to="/studio/hand/mobile">
                        <div className='Edit-Content cont'>
                            <span></span><span></span><span></span><span></span>
                            <div className='cont-wrap'>
                                <div className='img-wrap'>
                                    <div className='Edit-Image'>
                                        <img src={Hand} alt="Edit-Img" style={{width:"30%",height:"30%"}}/>
                                    </div>
                                </div>
                                <div className='desc-wrap'>
                                    <div className='Edit-mdes'>
                                        <p>손(Hand) 촬영</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </Link>
                </div>
            </div>
        </div>
    );
}