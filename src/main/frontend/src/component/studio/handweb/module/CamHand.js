import {Pose} from '@mediapipe/pose'
import * as cam from '@mediapipe/camera_utils'
import { useEffect, useRef, forwardRef, useImperativeHandle, useState } from 'react'
// import {drawHead, drawLine} from '../util/DrawingUtils'
import Spin from '../resource/loading.gif'
import Modal from '../../../LoadingModal'

// Static Image를 통해 인체 모델을 테스트합니다.
const CamHand = forwardRef((props,ref)=> {

    const [loadingModal,setLoadingModal] = useState(true);

    useImperativeHandle(ref,()=> ({
        capture() {
            // const item = window.localStorage.getItem("pictogram_result")
            // console.log(JSON.parse(item));
            document.location.href = "/edit"
        }
    }));

    let result = null;
    const webcamRef = useRef(null);
    const canvasRef = useRef(null);

    function onResults(results) {
        if (loadingModal === true) {
            setLoadingModal(false);
        }
        result = results.poseLandmarks;
        window.localStorage.setItem("pictogram_result",JSON.stringify(result));
        draw();
    }

    function draw() {
        
        canvasRef.current.width = 640;
        canvasRef.current.height = 480;

        const canvasElement = canvasRef.current;
        const canvasCtx = canvasElement.getContext('2d');

        const thick = window.localStorage.getItem('lineThick');
        const lineColor = window.localStorage.getItem('lineColor');

        canvasCtx.save();
        canvasCtx.clearRect(0,0,640,480);

        if(result !== undefined) {
            // 어깨선 
            
        }
    }

    useEffect(()=> {
        console.log("CamPose Mounting Start")

        const userVideoElement = document.getElementById("user-video");

        const pose = new Pose({locateFile : (file) => {
            return `https://cdn.jsdelivr.net/npm/@mediapipe/pose/${file}`;
        }});

        // 포즈 설정값
        pose.setOptions({
            modelComplexity: 1,
            smoothLandmarks: true,
            enableSegmentation: false,
            smoothSegmentation: false,
            minDetectionConfidence: 0.5,
            minTrackingConfidence: 0.5
          });

          pose.onResults(onResults);

          const camera = new cam.Camera(userVideoElement,{
            onFrame: async () => {
                await pose.send({image : userVideoElement});
            },
            width : 640,
            height : 480
        });
        camera.start();
    },[]);

    return (
        <>
            <Modal open={loadingModal}>
                <img style={{display : "block" , margin : "auto"}} src={Spin} alt="불러오고 있습니다."/>
                <h2 style={{textAlign : "center"}}>스튜디오를 불러오고 있습니다.</h2>
            </Modal>
            <div className="cam-pose">
                <video  style = {{borderLeft : "1px solid #aeaeae"}} autoPlay id="user-video" ref={webcamRef}></video>
                <canvas style={{borderRight : "1px solid #aeaeae"}} ref={canvasRef} id="draw-canvas" width="640px" height="480px"></canvas>
            </div>
        </>
    )
})

export default CamHand;