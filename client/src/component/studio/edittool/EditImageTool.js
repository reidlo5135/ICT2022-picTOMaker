import React, { useEffect, useState } from 'react';
import {useHistory, useLocation} from "react-router-dom";
import axios from "axios";
import {fabric} from 'fabric';
import DetailComponent from './detail/DetailComponent';
import Top from "../../Top";
import '../../../styles/stuido/topbar.css';
import '../../../styles/stuido/edittool.css';

let canvas = null;

export default function EditImageTool() {
    const history = useHistory();
    const location = useLocation();

    const [selectMode, setSelectMode] = useState("none");
    const [changedUrl, setChangedUrl] = useState();

    const profile = localStorage.getItem("profile");
    const post = location.state.post;
    const image = new Image();

    function getImage() {
        image.src = post.fileUrl;
        image.onload = () => {
            const imageInstance = new fabric.Image.fromURL(image.src,function(oImg) {
                canvas.add(oImg);
            });
        };
        canvas.discardActiveObject();
        let sel = new fabric.ActiveSelection(canvas.getObjects(), {
            canvas: canvas,
        });
        canvas.setActiveObject(sel);
        canvas.getActiveObject().toGroup();
        canvas.requestRenderAll();

        const url = canvas.toDataURL("image/png").replace("image/png", "image/octet-stream");
        setChangedUrl(url);
    }

    function update() {
        const jsonProf = JSON.parse(profile);
        const email = jsonProf.email;
        try {
            axios.put(`/v1/api/picto/email/${email}/id/${post.id}`, {
                octet: changedUrl
            }).then((response) => {
                if(response.data.code === 0) {
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
        getImage();
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
                            <button id="download-btn" onClick={()=> {update()}} ></button>
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