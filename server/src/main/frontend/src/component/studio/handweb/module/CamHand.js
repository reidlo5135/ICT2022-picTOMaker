import {Hands} from '@mediapipe/hands'
import * as cam from '@mediapipe/camera_utils'
import { useEffect, useRef, forwardRef, useImperativeHandle, useState } from 'react'
import {drawLine, drawHead, drawRect} from '../../poseweb/util/DrawingUtils'
import Spin from '../resource/loading.gif'
import Modal from '../../../LoadingModal'

// Static Image를 통해 인체 모델을 테스트합니다.
let result = null;
const CamHand = forwardRef((props,ref)=> {

    const [loadingModal,setLoadingModal] = useState(true);

    useImperativeHandle(ref,()=> ({
        capture() {
            window.localStorage.setItem("pictogram_result",JSON.stringify(result));
            window.localStorage.setItem("picto_type","hand");
            document.location.href = "/edit"
        }
    }));

 
    const webcamRef = useRef(null);
    const canvasRef = useRef(null);

    function onResults(results) {
        if (loadingModal === true) {
            setLoadingModal(false);
        }
        result = results.multiHandLandmarks[0];
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
            drawLine(result[8].x,result[8].y,result[5].x,result[5].y,canvasCtx,640,480,thick,lineColor)
            drawLine(result[12].x,result[12].y,result[9].x,result[9].y,canvasCtx,640,480,thick,lineColor)
            drawLine(result[16].x,result[16].y,result[13].x,result[13].y,canvasCtx,640,480,thick,lineColor)
            drawLine(result[20].x,result[20].y,result[17].x,result[17].y,canvasCtx,640,480,thick,lineColor)
            drawLine(result[2].x,result[2].y,result[4].x,result[4].y,canvasCtx,640,480,thick,lineColor)
            drawLine(result[1].x,result[1].y,result[2].x,result[2].y,canvasCtx,640,480,thick,lineColor)
            drawLine(result[1].x,result[1].y,result[0].x,result[0].y,canvasCtx,640,480,thick,lineColor)
            drawLine(result[17].x,result[17].y,result[0].x,result[0].y,canvasCtx,640,480,thick,lineColor)
            drawLine(result[2].x,result[2].y,result[5].x,result[5].y,canvasCtx,640,480,thick,lineColor)
            drawLine(result[5].x,result[5].y,result[9].x,result[9].y,canvasCtx,640,480,thick,lineColor)
            drawLine(result[9].x,result[9].y,result[13].x,result[13].y,canvasCtx,640,480,thick,lineColor)
            drawLine(result[13].x,result[13].y,result[17].x,result[17].y,canvasCtx,640,480,thick,lineColor)
            drawRect(result, lineColor, canvasCtx);
            
        }
    }

    useEffect(()=> {
        console.log("CamPose Mounting Start")

        const userVideoElement = document.getElementById("user-video");

        const hands = new Hands({locateFile : (file) => {
            return `https://cdn.jsdelivr.net/npm/@mediapipe/hands/${file}`
          }})

        // 포즈 설정값
        hands.setOptions({
            maxNumHands: 1,
            modelComplexity: 1,
            minDetectionConfidence: 0.5,
            minTrackingConfidence: 0.5
          });

        hands.onResults(onResults);

        const camera = new cam.Camera(userVideoElement,{
            onFrame: async () => {
                await hands.send({image : userVideoElement});
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