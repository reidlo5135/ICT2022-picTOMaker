import React, {forwardRef, useImperativeHandle, useEffect, useRef,useState} from 'react';
import testImage from '../../resource/test_hand2.png';
import {Hands} from '@mediapipe/hands';
import {drawLine, drawHead, drawRect} from '../../../poseweb/util/DrawingUtils'
import "../../../../../styles/stuido/posewebstudio.css";
import Modal from '../../../../LoadingModal'
import * as cam from '@mediapipe/camera_utils'
import Spin from '../../resource/loading.gif'
// Static Image를 통해 인체 모델을 테스트합니다.
let result = null;

const MobileCamHand = forwardRef((props, ref) => {
    const [loadingModal,setLoadingModal] = useState(true);

    useImperativeHandle(ref,()=> ({
        capture() {
            console.log("MobileCamHand result : ", result);
            const skeleton = JSON.stringify(result);
            const ws = new WebSocket("ws://ec2-52-79-56-189.ap-northeast-2.compute.amazonaws.com/picto");
            const json = {
                "skeleton": skeleton,
                "thick": 50,
                "lineColor": "FF03030",
                "backgroundColor": "FFFFFF",
                "type": "hand"
            }
            ws.onopen = () => {
                ws.send(JSON.stringify(json));
            };
        }
    }));

    const canvasRef = useRef(null);
    const webcamRef = useRef(null);

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
        const userVideoElement = document.getElementById("user-hand-video");
        const imageElement = document.getElementById('test-image')

        const hands = new Hands({locateFile : (file) => {
                return `https://cdn.jsdelivr.net/npm/@mediapipe/hands/${file}`
        }});

        // 포즈 설정값
        hands.setOptions({
            maxNumHands: 1,
            modelComplexity: 1,
            minDetectionConfidence: 0.5,
            minTrackingConfidence: 0.5
        });

        // 데이터 추출 후 실행할 콜백함수 설정
        hands.onResults(onResults);

        hands.send({image : imageElement});
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
            <div className="test-pose">
                <div className='left-content'>
                    <video  style = {{borderLeft : "1px solid #aeaeae"}} autoPlay id="user-hand-video" ref={webcamRef}></video>
                </div>
                <div className='right-content'>
                    <canvas ref={canvasRef} id="draw-canvas" width="640px" height="480px"></canvas>
                </div>
            </div>
        </>
    )
})

export default MobileCamHand;