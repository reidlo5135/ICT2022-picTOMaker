import React, {useEffect, useRef, useState} from 'react';
import {useCookies} from "react-cookie";
import {useHistory} from "react-router-dom";
import axios from "axios";
import {fabric} from 'fabric';
import DetailComponent from './detail/DetailComponent';
import Top from "../../Top";
import '../../../styles/stuido/topbar.css';
import '../../../styles/stuido/edittool.css';
import { models } from '../objectdetection/ModelList';

let canvas = null;

export default function EditTool(props,match) {
    const [cookies, setCookie] = useCookies(["accessToken"]);
    const history = useHistory();
    const [selectMode, setSelectMode] = useState("none");
    const provider = localStorage.getItem("provider");
    let type = null;

    const pictogramImage = props.pictogramImage;

    function drawingPictogram() {
        const nonResult = window.localStorage.getItem('pictogram_result');
        type = localStorage.getItem("picto_type");
        if (type === "object") {
            drawCanvas(null,null,null,type);
            return;
        }

        if (nonResult !== "null") {
            const result = JSON.parse(nonResult);
            const thick = localStorage.getItem("lineThick");
            const color = localStorage.getItem("lineColor");

            drawCanvas(result, thick, color, type);
            window.localStorage.setItem('pictogram_result', null);
        }
    }

    function drawCanvas(result, thick, color, type) {
        if (type === "hand") {
            for (let i = 0; i < 21; i++) {
                result[i].x = result[i].x * 640;
                result[i].y = result[i].y * 480;
            }

            const zeroToOne = new fabric.Line([result[0].x, result[0].y, result[1].x, result[1].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            const oneToTwo = new fabric.Line([result[1].x, result[1].y, result[2].x, result[2].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            const twoToThree = new fabric.Line([result[2].x, result[2].y, result[3].x, result[3].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            const threeToFour = new fabric.Line([result[3].x, result[3].y, result[4].x, result[4].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            const fiveToSix = new fabric.Line([result[5].x, result[5].y, result[6].x, result[6].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            const sixToSeven = new fabric.Line([result[6].x, result[6].y, result[7].x, result[7].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            const sevenToEight = new fabric.Line([result[7].x, result[7].y, result[8].x, result[8].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            const nineToTen = new fabric.Line([result[9].x, result[9].y, result[10].x, result[10].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            const tenToEleven = new fabric.Line([result[10].x, result[10].y, result[11].x, result[11].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            const elevenToTwelve = new fabric.Line([result[11].x, result[11].y, result[12].x, result[12].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            const thirdteenToFourteen = new fabric.Line([result[13].x, result[13].y, result[14].x, result[14].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            const fourteenTofifteen = new fabric.Line([result[14].x, result[14].y, result[15].x, result[15].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            const fifteenToSixteen = new fabric.Line([result[15].x, result[15].y, result[16].x, result[16].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            const sevenTeenToEightTeen = new fabric.Line([result[17].x, result[17].y, result[18].x, result[18].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            const eightTeenToNineteen = new fabric.Line([result[18].x, result[18].y, result[19].x, result[19].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            const nineTeenToTwenty = new fabric.Line([result[19].x, result[19].y, result[20].x, result[20].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            //
            const twoToFive = new fabric.Line([result[2].x, result[2].y, result[5].x, result[5].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            const fiveToNine = new fabric.Line([result[5].x, result[5].y, result[9].x, result[9].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            const nineToThirdteen = new fabric.Line([result[9].x, result[9].y, result[13].x, result[13].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            const thirdTeenToSeventeen = new fabric.Line([result[13].x, result[13].y, result[17].x, result[17].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            const sevenTeenToZero = new fabric.Line([result[17].x, result[17].y, result[0].x, result[0].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });


            var palm = new fabric.Polygon([
                {x:result[1].x , y:result[1].y},
                {x:result[2].x , y:result[2].y},
                {x:result[5].x , y:result[5].y},
                {x:result[9].x , y:result[9].y},
                {x:result[13].x , y:result[13].y},
                {x:result[17].x , y:result[17].y},
                {x:result[0].x , y:result[0].y}
            ], {
                fill : color
            });

            var group = new fabric.Group([zeroToOne,oneToTwo,twoToThree,threeToFour,fiveToSix,sixToSeven,sevenToEight,nineToTen,tenToEleven,elevenToTwelve,thirdteenToFourteen,fourteenTofifteen,fifteenToSixteen,sevenTeenToEightTeen,eightTeenToNineteen,nineTeenToTwenty
                ,twoToFive,fiveToNine,nineToThirdteen, thirdTeenToSeventeen, sevenTeenToZero,palm ], {
                originX : 'cetner',
                originY : 'cetner',
                angle: -10
            });

            canvas.add(group);
        }

        if (type === "pose") {
            for (let i = 0; i < 33; i++) {
                result[i].x = result[i].x * 640;
                result[i].y = result[i].y * 480;
            }
            // 어깨
            const shoulder = new fabric.Line([result[12].x, result[12].y, result[11].x, result[11].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });
            // 좌측 팔 (Upper, Lower)
            const leftUpperArm = new fabric.Line([result[12].x, result[12].y, result[14].x, result[14].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });
            const leftLowerArm = new fabric.Line([result[14].x, result[14].y, result[16].x, result[16].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            // 우측 팔 (Upper,Lower)
            const rightUpperArm = new fabric.Line([result[11].x, result[11].y, result[13].x, result[13].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });
            const rightLowerArm = new fabric.Line([result[13].x, result[13].y, result[15].x, result[15].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            // 좌측 상체
            const leftUpperBody = new fabric.Line([result[12].x, result[12].y, result[24].x, result[24].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });
            // 우측 상체
            const rightUpperBody = new fabric.Line([result[11].x, result[11].y, result[23].x, result[23].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            // 허리
            const waist = new fabric.Line([result[24].x, result[24].y, result[23].x, result[23].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            // 좌측 다리 (Upper, Lower)
            const leftUpperLeg = new fabric.Line([result[24].x, result[24].y, result[26].x, result[26].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            const leftLowerLeg = new fabric.Line([result[26].x, result[26].y, result[28].x, result[28].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            // 우측 다리 (Upper, Lower)
            const rightUpperLeg = new fabric.Line([result[23].x, result[23].y, result[25].x, result[25].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            const rightLowerLeg = new fabric.Line([result[25].x, result[25].y, result[27].x, result[27].y], {
                strokeLineCap: 'round',
                strokeWidth: thick,
                stroke: color
            });

            // 머리
            const head = new fabric.Circle({
                top: result[0].y - thick,
                left: result[0].x - thick,
                radius: thick,
                stroke: color
            });

            // 몸통
            const body = new fabric.Polygon([
                {x:result[12].x , y:result[12].y},
                {x:result[11].x , y:result[11].y},
                {x:result[23].x , y:result[23].y},
                {x:result[24].x , y:result[24].y}
            ],{
                fill : color,
            })

            canvas.add(shoulder, leftUpperArm, leftLowerArm, rightUpperArm, rightLowerArm, leftUpperBody, rightUpperBody, waist, leftUpperLeg, leftLowerLeg, rightUpperLeg, rightLowerLeg, head, body);
        }

        if (type === "object") {
            console.log("오브젝트 입니다!!")
            const objectValue = localStorage.getItem("object");

            console.log(models[objectValue].url)
            fabric.Image.fromURL(models[objectValue].url, function(oImg) {
                canvas.add(oImg);
            })
        }

        canvas.discardActiveObject();
        let sel = new fabric.ActiveSelection(canvas.getObjects(), {
            canvas: canvas,
        });
        canvas.setActiveObject(sel);
        canvas.getActiveObject().toGroup();
        canvas.requestRenderAll();
    }

    function download() {
        const image = canvas.toDataURL("image/png").replace("image/png", "image/octet-stream");
        try {
            axios.post(`/v1/api/picto/${provider}`, {
                image
            }, {
                headers: {
                    "X-AUTH-TOKEN": cookies.accessToken
                }
            }).then((response) => {
                if(response.data.code === 0) {
                    localStorage.setItem("picTOUrl", response.data.data);
                    alert('성공적으로 저장되었습니다!');
                    history.push("/");
                }
            }).catch((err) => {
                alert(err.response.data.msg);
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
                        <div className='pencil-tool tool'>
                            <button id="pencil-btn" onClick = {()=> {pencilMode()}}></button>
                            <p className='pencil-desc'>펜 그리기</p>
                        </div>
                        <div className='figure-tool tool'>
                            <button id="figure-btn" onClick = {()=> {figureMode()}}></button>
                            <p className='figure-desc'>도형 삽입</p>
                        </div>
                        <div className='image-tool tool'>
                            <button id="image-btn" onClick = {()=> {imageMode()}}></button>
                            <p className='image-desc'>이미지 삽입</p>
                        </div>
                        {/*                         <button id="text-btn" onClick = {()=> {textMode()}}></button> */}
                        {/* <button id="open-btn" onClick = {()=> {openMode()}}></button> */}
                        <div className='download-tool tool'>
                            <button id="download-btn" onClick={()=> {download()}} ></button>
                            <p className='download-desc'>다운로드</p>
                        </div>
                        {/*                         <button id="share-btn" onClick = {()=> {shareMode()}}></button> */}
                    </div>
                </div>
                <div id="tool-detail-view">
                    <DetailComponent mode={selectMode} canvas={canvas}/>
                </div>
            </div>
        </>
    );
}