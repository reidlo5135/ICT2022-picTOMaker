import React,{useRef,useEffect,useState} from 'react';
import * as cam from '@mediapipe/camera_utils';
// import {DeviceCheck, getStream} from './util/DevicesCheck';
import CamHand from './module/CamHand';
import MobileCamHand from "./module/mobile/MobileCamHand";
import Button from '@mui/material/Button';
import MobileTop from "../../mobile-components/Mobile-Top";
import "../../../styles/stuido/topbar.css";
import "../../../styles/stuido/posewebstudio.css";
import Gear from "../../../assets/image/studio_image/gear-solid.svg";
import Time from "./resource/time.svg";
import Camera from "../../../assets/image/studio_image/camera.png";
import Modal from "../../Modal";
import StudioSettingComponent from '../StudioSettingComponent';
import StudioCaptureDelay from '../StudioCaptureDelay';

export default function HandWebStudio() {
    const [timeModalOpen, setTimeModalOpen] = useState(false);
    const [modalOpen, setModalOpen] = useState(false);

    const timeOpenModal = () => {
        setTimeModalOpen(true);
    }

    const timeCloseModal = () => {
        setTimeModalOpen(false);
    }

    const openModal = () => {
        setModalOpen(true);
    }

    const closeModal = () => {
        setModalOpen(false);
    }

    const childRef = useRef();
    const [selectMode, setSelectMode] = useState('image');
    const [result, setResult] = useState(null);
    const webcamRef = useRef(null);
    const canvasRef = useRef(null);

    function capture() {
        const delay = parseInt(localStorage.getItem("delay"));
        setTimeout(() => {
            childRef.current.capture();
        }, delay)
    }

    // 초기설정 Hook
    useEffect(()=> {
        /*
        if (DeviceCheck()) {
            console.log("디바이스 인식 성공")
            const deviceStream = getStream();
        } */
    },[]);

    return (
        <>
            <MobileTop />
            <div id="topbar"></div>
            <div className ="studio_container">
                <MobileCamHand ref={childRef} />
                <div className = 'button-group'>
                    <div className='camera-btn'>
                        <div>
                            <img className="setting" src={Time} alt="asdf" onClick={timeOpenModal}/>
                            <div className={'loginModal'}>
                                <Modal open={timeModalOpen} close={timeCloseModal}>
                                    <StudioCaptureDelay/>
                                </Modal>
                            </div>
                        </div>
                        <Button onClick={()=> {
                            capture();
                        }} variant="text"><img className='camera-img' src={Camera} alt="촬영이미지"/></Button>
                        <div>
                            <img className="setting" src={Gear} alt="asdf" onClick={openModal}/>
                            <div className={'loginModal'}>
                                <Modal open={modalOpen} close={closeModal}>
                                    <StudioSettingComponent/>
                                </Modal>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
};