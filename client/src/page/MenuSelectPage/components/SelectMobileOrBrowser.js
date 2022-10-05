import React, {useState} from 'react';
import {post} from "../../../services/AxiosService";
import "../menuSelect.css";
import "../../../styles/font.css";
import "../menuSelectModal.css";
import {Link} from "react-router-dom";
import Logo from "../../../assets/image/Logo.png";
import Mobile from "../../../assets/image/Mobile.png";
import Browser from "../../../assets/image/Browser.png";
import Modal from "./MenuModal";
import {useCookies} from "react-cookie";
import {useHistory} from "react-router";
import MyPic from "./MenuSelecPicToDetails"

export default function SelectMobileOrBrowser() {
    const [cookies, setCookie] = useCookies(["accessToken"]);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [modalOpen, setModalOpen] = useState(false);
    const [isLogged, setIsLogged] = useState(false);
    const history = useHistory();
    
    const openModal = () => {
        const token = cookies.accessToken;
        if(token !== null) {
            setModalOpen(true);
        } else {
            history.push("/edit");
        }
    }
    const closeModal = () => {
        setModalOpen(false);
    }

    const isMobile = () => {
        localStorage.setItem("isFromMobile", "true");
    }

    const isBrowser = () => {
        localStorage.setItem("isFromMobile", "false");
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
                <div className='HT-Content'>
                    <Link to='/edit'>
                        <div className='Human-content cont' onClick={isMobile}>
                            <span></span><span></span><span></span><span></span>
                            <div className='Human-Image'>
                                <img src={Mobile} alt="Mobile-Img" style={{width:"30%",height:"30%"}}/>
                            </div>
                            <div className='Human-mdes'>
                                모바일로 촬영한<br/>픽토그램 편집
                            </div>
                        </div>
                    </Link>
                    <a>
                        <div className='Edit-Content cont' onClick={openModal}>
                            <span></span><span></span><span></span><span></span>
                            <div className='Edit-Image'>
                                <img src={Browser} alt="Edit-Img" style={{width:"30%",height:"30%"}}/>
                            </div>
                            <div className='Edit-mdes'>
                                PC로 촬영한<br/>픽토그램 편집
                            </div>
                        </div>
                    </a> 
                </div>
                                    
                <div className={'loginModal'}>
                    <Modal open={modalOpen} close={closeModal}>
                        <MyPic/>
                    </Modal>
                </div>
            </div>
        </div>
    );
}