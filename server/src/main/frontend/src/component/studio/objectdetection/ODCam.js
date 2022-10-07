import React, { useRef, useEffect, useState } from 'react';
import Webcam from "react-webcam";
import * as cocossd from "@tensorflow-models/coco-ssd"
import * as tf from "@tensorflow/tfjs";
import { drawRect }  from './utilities'
import style from './ObjectDetection.module.css'


const ODCam = () => {
    const webcamRef = useRef(null);
    const canvasRef = useRef(null);

    const [value, setValue] = useState("");
    const [avaliable, setAvaliable] = useState(false);

    
    const onClickButton = () => {
        console.log(value);
        localStorage.setItem("object",value);
        localStorage.setItem("picto_type","object");
        localStorage.setItem("pictogram_result","{ dd : 'dd'}")
        window.location.href = "/edit"
    }

    const runCoco = async () => {
        const net = await cocossd.load();
        
        setInterval(() => {
          detect(net);
        }, 1000);
      };

    const detect = async (net) => {
        if (
            typeof webcamRef.current !== "undefined" &&
            webcamRef.current !== null &&
            webcamRef.current.video.readyState === 4
        ) {
 
            const video = webcamRef.current.video;
            const videoWidth = webcamRef.current.video.videoWidth;
            const videoHeight = webcamRef.current.video.videoHeight;

   
            webcamRef.current.video.width = videoWidth;
            webcamRef.current.video.height = videoHeight;

            canvasRef.current.width = videoWidth;
            canvasRef.current.height = videoHeight;
     

            const obj = await net.detect(video);
            const ctx = canvasRef.current.getContext("2d");
            drawRect(obj,ctx);

            

            if (obj.length === 0 || obj[0]['class'] === "person" || obj.length > 1) {
                setValue("인식할 수 없음");
                setAvaliable(false);
                return;
            }

            if (obj[0]['class'] !== undefined) {
                setValue(obj[0]['class'])
                setAvaliable(true);
                console.log(obj[0]['class'])
                return;
            }

          
            

        
        }
    };

    useEffect(()=>{runCoco()},[]);

    return (
      <div>
        <div id="topbar"></div>
        <div className={style.studioContainer}>



        <Webcam
          ref={webcamRef}
          muted={true} 
          style={{
            
            left: 0,
            right: 0,
            textAlign: "center",
            zindex: 9,
            width: 640,
            height: 480,
          }}
        />

<canvas
          ref={canvasRef}
          style={{
            
            left: 0,
            right: 0,
            textAlign: "center",
            zindex: 8,
            width: 640,
            height: 480,
          }}
        />

        <div style={{textAlign : "center"}}>
            <button onClick={onClickButton} disabled={avaliable ? false : true} className={avaliable ? style.button : style.buttonDis}>확인</button>
        </div>
        </div>
        </div>
    );
};

export default ODCam;