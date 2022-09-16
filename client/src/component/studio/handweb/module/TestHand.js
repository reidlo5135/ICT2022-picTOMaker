import testImage from '../resource/test_hand2.png'
import {Hands} from '@mediapipe/hands'
import {drawLine, drawHead} from '../../poseweb/util/DrawingUtils'
import "../../../../css/stuido/posewebstudio.css"
import React, {forwardRef, useImperativeHandle, useEffect, useRef, useState} from 'react';

// Static Image를 통해 인체 모델을 테스트합니다.

const TestHand = forwardRef((props,ref) => {
    useImperativeHandle(ref,()=> ({
        capture() {
            console.log(result)
            window.localStorage.setItem("pictogram_result",JSON.stringify(result));

            // const item = window.localStorage.getItem("pictogram_result")
            // console.log(JSON.parse(item));
            document.location.href = "/edit"
        }
    }));

    let [result, setResult] = useState(null);

    const canvasRef = useRef(null);

    function onResults(results) {
        console.log(results.multiHandLandmarks[0])
        result = results.multiHandLandmarks[0];
        draw();
    }

    function draw() {
        canvasRef.current.width = 640;
        canvasRef.current.height = 480;

        const canvasElement = canvasRef.current;
        const canvasCtx = canvasElement.getContext('2d');

        canvasCtx.save();
        canvasCtx.clearRect(0,0,640,480);
        
        if(result !== undefined) {
            console.log(result[8].x);
            drawLine(result[8].x,result[8].y,result[5].x,result[5].y,canvasCtx,640,480,"15","000000")
            drawLine(result[12].x,result[12].y,result[9].x,result[9].y,canvasCtx,640,480,"15","000000")
            drawLine(result[16].x,result[16].y,result[13].x,result[13].y,canvasCtx,640,480,"15","000000")
            drawLine(result[20].x,result[20].y,result[17].x,result[17].y,canvasCtx,640,480,"15","000000")
            drawLine(result[2].x,result[2].y,result[4].x,result[4].y,canvasCtx,640,480,"15","000000")
            drawLine(result[1].x,result[1].y,result[2].x,result[2].y,canvasCtx,640,480,"15","000000")
            drawLine(result[1].x,result[1].y,result[0].x,result[0].y,canvasCtx,640,480,"15","000000")
            drawLine(result[17].x,result[17].y,result[0].x,result[0].y,canvasCtx,640,480,"15","000000")
            drawLine(result[2].x,result[2].y,result[5].x,result[5].y,canvasCtx,640,480,"15","000000")
            drawLine(result[5].x,result[5].y,result[9].x,result[9].y,canvasCtx,640,480,"15","000000")
            drawLine(result[9].x,result[9].y,result[13].x,result[13].y,canvasCtx,640,480,"15","000000")
            drawLine(result[13].x,result[13].y,result[17].x,result[17].y,canvasCtx,640,480,"15","000000")
        }
        console.log(result)
    }

    useEffect(()=> {

        const imageElement = document.getElementById('test-image')

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

        // 데이터 추출 후 실행할 콜백함수 설정
        hands.onResults(onResults);

        hands.send({image : imageElement});
    },[]);

    return (
        <>
            <div className="test-pose">
                <div className='left-content'>
                    <img src={testImage} id="test-image"></img>
                </div>
                <div className='right-content'>
                    <canvas ref={canvasRef} id="draw-canvas" width="640px" height="480px"></canvas>
                </div>
            </div>
        </>
    )
})

export default TestHand;