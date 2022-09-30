import React from "react";
import "../menuSelect.css";
import "../../../styles/font.css";
import {Link} from "react-router-dom";
import Logo from "../../../assets/image/Logo.png";
import Mobile from "../../../assets/image/Mobile.png";
import Browser from "../../../assets/image/Browser.png";

export default function SelectMobileOrBrowser() {
    const isMobile = () => {
        localStorage.setItem("isFromMobile", "true");
        document.location.href = "/edit";
    }

    const isBrowser = () => {
        localStorage.setItem("isFromMobile", "false");
        document.location.href = "/edit";
    }
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
                    <div className='Edit-Content cont' onClick={isMobile}>
                        <span></span><span></span><span></span><span></span>
                        <div className='Edit-Image'>
                            <img src={Mobile} alt="Edit-Img" style={{width:"30%",height:"30%"}}/>
                        </div>
                        <div className='Edit-mdes'>
                            모바일로 촬영한 픽토그램 편집
                        </div>
                    </div>
                    <div className='Edit-Content cont' onClick={isBrowser}>
                        <span></span><span></span><span></span><span></span>
                        <div className='Edit-Image'>
                            <img src={Browser} alt="Edit-Img" style={{width:"30%",height:"30%"}}/>
                        </div>
                        <div className='Edit-mdes'>
                            PC로 촬영한 픽토그램 편집
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}