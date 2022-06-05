import React,{useRef,useEffect,useState} from 'react';
import * as cam from '@mediapipe/camera_utils'
import {DeviceCheck, getStream} from './util/DevicesCheck'
import TestPose from './module/test/TestPose'
import CamPose from './module/CamPose';
import Button from '@mui/material/Button';
import Top from "../../contents/Top";
import "../../../css/stuido/topbar.css";
import "../../../css/stuido/posewebstudio.css";

export default function PoseWebStudio() {
    const childRef = useRef();
    const [selectMode, setSelectMode] = useState('image');
    const [result, setResult] = useState(null);
    const webcamRef = useRef(null);
    const canvasRef = useRef(null);

    function capture() {
        console.log("capture");
        childRef.current.capture();
    }

    function onResults(props) {
        console.log(props);
    }
    
    // 초기설정 Hook
    useEffect(()=> {
        console.log("PoseWebStudio Mounting Start")

        // window.localStorage.setItem('thick',50);
        // window.localStorage.setItem('lineColor',"FF03030");
        // window.localStorage.setItem('backgroundColor',"FFFFFF");
        /*
        if (DeviceCheck()) {
            console.log("디바이스 인식 성공")
            const deviceStream = getStream();
        } */
    },[])

    return (
        <>
            <Top />
            <div className ="studio_container">
                <div id="topbar"></div>
                <div id="left-content">
                    <CamPose ref={childRef}/>
                </div>
                <div id="right-content">
                    <Button onClick={()=> {
                        capture();
                    }} variant="text">촬영</Button>
                </div>
            </div>
        </>
    );
}