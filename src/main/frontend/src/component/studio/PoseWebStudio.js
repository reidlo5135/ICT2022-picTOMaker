
import "../../css/stuido/topbar.css"


import {useRef,useEffect,useState} from 'react';
import * as cam from '@mediapipe/camera_utils'
import {DeviceCheck, getStream} from './util/DevicesCheck'
import TestPose from './module/test/TestPose'

export default function PoseWebStudio() {
    const [selectMode, setSelectMode] = useState('image');
    const [result, setResult] = useState(null);
    const webcamRef = useRef(null);
    const canvasRef = useRef(null);

    function onResults(props) {
        console.log(props);

    }
    
    // 초기설정 Hook
    useEffect(()=> {
        // window.localStorage.setItem('thick',50);
        // window.localStorage.setItem('lineColor',"FF03030");
        // window.localStorage.setItem('backgroundColor',"FFFFFF");

        const userVideoElement = document.getElementById("user-video");
        const testImageElement = document.getElementById("testVid");
        console.log(testImageElement)
        const drawingCanvasElement = document.getElementById("drawing-canvas");

        /*
        if (DeviceCheck()) {
            console.log("디바이스 인식 성공")
            const deviceStream = getStream();
        } */
       

        /*const camera = new cam.Camera(userVideoElement,{
            onFrame: async () => {
                await pose.send({image : userVideoElement});
            },
            width : 640,
            height : 480
        });*/
        
        // camera.start();
       
    },[])

    return (
        <>
        <div className ="studio_container">
            
            <div id="topbar"></div>
            <div id="left-content">
                {/* <video autoPlay id="user-video" ref={webcamRef}></video> */}
                <TestPose result={result}/>
                <button onClick={()=>{console.log(result)}}>버튼</button>
                <canvas ref={canvasRef} id="drawing-canvas" width="640px" height="480px"></canvas>
            </div>
            <div id="right-content">
            
            
            </div>

















        </div>
        </>
    )

}