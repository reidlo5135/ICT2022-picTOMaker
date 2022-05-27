import testImage from '../..//resource/human_pose.png'
import {Pose} from '@mediapipe/pose'
import { useEffect, useRef, useState } from 'react'
import {drawLine} from '../../util/DrawingUtils'

// Static Image를 통해 인체 모델을 테스트합니다.
export default function TestPose(props) {
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
            drawLine(result[12].x,result[12].y,result[11].x,result[11].y,canvasCtx,640,480,"15","000000")
            drawLine(result[12].x,result[12].y,result[14].x,result[14].y,canvasCtx,640,480,"15","000000")
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
    }) 

    return (
        <>
            <div className="test-pose">
                <img src={testImage} id="test-image"></img>
                <canvas ref={canvasRef} id="draw-canvas" width="640px" height="480px"></canvas>
            </div>
        </>
    )
}