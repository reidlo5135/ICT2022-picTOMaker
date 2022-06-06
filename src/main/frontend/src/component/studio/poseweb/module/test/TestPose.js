import testImage from '../..//resource/human_pose.png'
import {Pose} from '@mediapipe/pose'
import {drawLine, drawHead} from '../../util/DrawingUtils'
import "../../../../../css/stuido/posewebstudio.css"
import React, {forwardRef, useImperativeHandle, useEffect, useRef, useState} from 'react';

// Static Image를 통해 인체 모델을 테스트합니다.

const TestPose = forwardRef((props,ref) => {
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
        result = results.poseLandmarks;
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
            // 어깨선 
            drawLine(result[12].x,result[12].y,result[11].x,result[11].y,canvasCtx,640,480,"15","000000")

            // 좌측 팔
            drawLine(result[12].x,result[12].y,result[14].x,result[14].y,canvasCtx,640,480,"15","000000") // 어깨 -> 팔꿈치
            drawLine(result[14].x,result[14].y,result[16].x,result[16].y,canvasCtx,640,480,"15","000000") // 팔꿈치 -> 손목

            // 우측 팔
            drawLine(result[11].x,result[11].y,result[13].x,result[13].y,canvasCtx,640,480,"15","000000") // 어깨 -> 팔꿈치
            drawLine(result[13].x,result[13].y,result[15].x,result[15].y,canvasCtx,640,480,"15","000000") // 팔꿈치 -> 손목
            

            // 좌측 상체
            drawLine(result[12].x,result[12].y,result[24].x,result[24].y,canvasCtx,640,480,"15","000000")
            // 우측 상체
            drawLine(result[11].x,result[11].y,result[23].x,result[23].y,canvasCtx,640,480,"15","000000")
            // 허리
            drawLine(result[24].x,result[24].y,result[23].x,result[23].y,canvasCtx,640,480,"15","000000")

            // 좌측 다리
            drawLine(result[24].x,result[24].y,result[26].x,result[26].y,canvasCtx,640,480,"15","000000") // 허벅지
            drawLine(result[26].x,result[26].y,result[28].x,result[28].y,canvasCtx,640,480,"15","000000") // 종아리

            // 우측 다리
            drawLine(result[23].x,result[23].y,result[25].x,result[25].y,canvasCtx,640,480,"15","000000") // 허벅지
            drawLine(result[25].x,result[25].y,result[27].x,result[27].y,canvasCtx,640,480,"15","000000") // 종아리

            // 머리
            drawHead(result[0].x,result[0].y,canvasCtx,640,480,"15","000000");
        }
        console.log(result)
    }

    useEffect(()=> {

        const imageElement = document.getElementById('test-image')

        const pose = new Pose({locateFile : (file) => {
            return `https://cdn.jsdelivr.net/npm/@mediapipe/pose/${file}`
          }})

        // 포즈 설정값
        pose.setOptions({
            modelComplexity: 1,
            smoothLandmarks: true,
            enableSegmentation: false,
            smoothSegmentation: false,
            minDetectionConfidence: 0.5,
            minTrackingConfidence: 0.5
          });

        // 데이터 추출 후 실행할 콜백함수 설정
        pose.onResults(onResults);

        pose.send({image : imageElement});
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

export default TestPose;