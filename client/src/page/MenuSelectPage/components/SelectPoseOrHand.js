import React from "react";
import {Link} from "react-router-dom";
import Logo from "../../../assets/image/Logo.png";
import Human from "../../../assets/image/Human.png";
import Hand from "../../../assets/image/Hand.png";

export default function SelectPoseOrHand() {
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
                <div className='HT-Content'>
                    <Link to="/studio/pose/web">
                        <div className='Human-content cont'>
                            <span></span><span></span><span></span><span></span>
                            <div className='Human-Image'>
                                <img src={Human} alt="Human-Img" style={{width:"30%",height:"30%"}}/>
                            </div>
                            <div className='Human-mdes'>
                                전신(Pose) 촬영
                            </div>
                        </div>
                    </Link>
                    <Link to="/studio/hand/web">
                        <div className='Edit-Content cont'>
                            <span></span><span></span><span></span><span></span>
                            <div className='Edit-Image'>
                                <img src={Hand} alt="Edit-Img" style={{width:"30%",height:"30%"}}/>
                            </div>
                            <div className='Edit-mdes'>
                                손(Hand) 촬영
                            </div>
                        </div>
                    </Link>
                </div>
            </div>
        </div>
    );
}