import React, { useEffect, useState } from 'react';
import {useHistory} from "react-router-dom";
import axios from "axios";
import {fabric} from 'fabric';
import DetailComponent from './detail/DetailComponent';
import { color } from '@mui/system';
import Top from "../../contents/Top";
import '../../../css/stuido/topbar.css';
import '../../../css/stuido/edittool.css';

let canvas = null;

export default function EditTool(props) {
    const history = useHistory();
    const [selectMode, setSelectMode] = useState("none");
    const profile = localStorage.getItem("profile");
    const provider = localStorage.getItem("provider");

    const pictogramImage = props.pictogramImage;

    function drawingPictogram() {
        const nonResult = window.localStorage.getItem('pictogram_result');
        if (nonResult !== "null") {
            const result = JSON.parse(nonResult);
            const thick = window.localStorage.getItem("lineThick");
            const color = '#' + window.localStorage.getItem("lineColor");

            for (let i=0; i<33; i++) {
                result[i].x = result[i].x * 640;
                result[i].y = result[i].y * 480;
            }
            console.log(result);

            // 어깨
            const shoulder = new fabric.Line([result[12].x,result[12].y,result[11].x,result[11].y], {
                strokeLineCap : 'round',
                strokeWidth : thick,
                stroke : color
            });
            // 좌측 팔 (Upper, Lower)
            const leftUpperArm = new fabric.Line([result[12].x,result[12].y,result[14].x,result[14].y], {
                strokeLineCap : 'round',
                strokeWidth : thick,
                stroke : color
            });
            const leftLowerArm = new fabric.Line([result[14].x,result[14].y,result[16].x,result[16].y], {
                strokeLineCap : 'round',
                strokeWidth : thick,
                stroke : color
            });

            // 우측 팔 (Upper,Lower)
            const rightUpperArm = new fabric.Line([result[11].x,result[11].y,result[13].x,result[13].y], {
                strokeLineCap : 'round',
                strokeWidth : thick,
                stroke : color
            });
            const rightLowerArm = new fabric.Line([result[13].x,result[13].y,result[15].x,result[15].y], {
                strokeLineCap : 'round',
                strokeWidth : thick,
                stroke : color
            });

            // 좌측 상체
            const leftUpperBody = new fabric.Line([result[12].x,result[12].y,result[24].x,result[24].y], {
                strokeLineCap : 'round',
                strokeWidth : thick,
                stroke : color
            });
            // 우측 상체
            const rightUpperBody = new fabric.Line([result[11].x,result[11].y,result[23].x,result[23].y], {
                strokeLineCap : 'round',
                strokeWidth : thick,
                stroke : color
            });

            // 허리
            const waist = new fabric.Line([result[24].x,result[24].y,result[23].x,result[23].y], {
                strokeLineCap : 'round',
                strokeWidth : thick,
                stroke : color
            });
            
            // 좌측 다리 (Upper, Lower)
            const leftUpperLeg = new fabric.Line([result[24].x,result[24].y,result[26].x,result[26].y], {
                strokeLineCap : 'round',
                strokeWidth : thick,
                stroke : color
            });

            const leftLowerLeg = new fabric.Line([result[26].x,result[26].y,result[28].x,result[28].y], {
                strokeLineCap : 'round',
                strokeWidth : thick,
                stroke : color
            });

            // 우측 다리 (Upper, Lower)
            const rightUpperLeg = new fabric.Line([result[23].x,result[23].y,result[25].x,result[25].y], {
                strokeLineCap : 'round',
                strokeWidth : thick,
                stroke : color
            });

            const rightLowerLeg = new fabric.Line([result[25].x,result[25].y,result[27].x,result[27].y], {
                strokeLineCap : 'round',
                strokeWidth : thick,
                stroke : color
            });

            // 머리
            const head = new fabric.Circle({
                top : result[0].y - 20 + 10 ,
                left : result[0].x - 20,
                radius : 25,
                stroke: color
            });

            canvas.add(shoulder,leftUpperArm,leftLowerArm,rightUpperArm,rightLowerArm,leftUpperBody,rightUpperBody,waist,leftUpperLeg,leftLowerLeg, rightUpperLeg,rightLowerLeg,head);

            canvas.discardActiveObject();
            let sel = new fabric.ActiveSelection(canvas.getObjects(), {
                canvas : canvas,
            });
            canvas.setActiveObject(sel);
            canvas.getActiveObject().toGroup();
            canvas.requestRenderAll();

            window.localStorage.setItem('pictogram_result',null);
        }
    }

    function download() {
        const image = canvas.toDataURL("image/png").replace("image/png", "image/octet-stream");
        console.log('image : ', image);

        const jsonProf = JSON.parse(profile);
        const email = jsonProf.email;
        try {
            axios.post(`/v1/upload/register/${email}/${provider}`, {
                image
            }, {
                baseURL: 'http://localhost:8080',
                withCredentials: true
            }).then((response) => {
                console.log('response data : ', response.data);
                console.log('response data.data : ', response.data.data);

                if(response.data.code === 0) {
                    localStorage.setItem("picTOUrl", response.data.data);
                    alert('성공적으로 저장되었습니다!');
                    history.push("/");
                }
            });
        } catch (err) {
            console.error(err);
        }
    }

    function pencilMode() {
        setSelectMode("pencil");
        if (selectMode !== "pencil")  {
            canvas.isDrawingMode = true;
            canvas.freeDrawingBrush.width = 5;
            canvas.freeDrawingBrush.color = '#00aeff';
        }
    }

    function figureMode () {
        canvas.isDrawingMode = false;
        setSelectMode("figure");
    }

    function imageMode() {
        setSelectMode("image");
    }
    
    function textMode() {
        setSelectMode("text");
    }
    
    function downloadMode() {
        setSelectMode("download");
    }

    function openMode() {
        setSelectMode("open");
    }

    function shareMode() {
        setSelectMode("share");
    }
    

    useEffect(()=> {
        canvas = new fabric.Canvas('edit-canvas');
        drawingPictogram();
    },[]);


    return (
        <>
            <Top />
            <div id="edit-box">
                <div  id="topbar" ></div>
                <div id="middle-view">
                    <div id="edit-view">
                        <canvas id="edit-canvas" width ="640px" height = "480px"></canvas>
                    </div>
                    <div id="tool-view">
                        <button id="pencil-btn" onClick = {()=> {pencilMode()}}></button>
                        <button id="figure-btn" onClick = {()=> {figureMode()}}></button>
                        <button id="image-btn" onClick = {()=> {imageMode()}}></button>
                        <button id="text-btn" onClick = {()=> {textMode()}}></button>
                        <button id="download-btn" onClick={()=> {download()}} ></button>
                        <button id="open-btn" onClick = {()=> {openMode()}}> </button>
                        <button id="share-btn" onClick = {()=> {shareMode()}}></button>
                    </div>
                </div>
                <div id="tool-detail-view">
                   <DetailComponent mode={selectMode} canvas={canvas}/>
                </div>
            </div>
        </>
    );
}