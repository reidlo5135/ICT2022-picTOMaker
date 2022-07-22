import React, { useEffect, useState } from 'react';
import {useHistory, useLocation} from "react-router-dom";
import axios from "axios";
import {fabric} from 'fabric';
import DetailComponent from './detail/DetailComponent';
import { color } from '@mui/system';
import Top from "../../contents/Top";
import '../../../css/stuido/topbar.css';
import '../../../css/stuido/edittool.css';

let canvas = null;

export default function EditImageTool() {
    const history = useHistory();
    const location = useLocation();

    const [selectMode, setSelectMode] = useState("none");
    const [changedUrl, setChangedUrl] = useState();

    const profile = localStorage.getItem("profile");
    const provider = localStorage.getItem("provider");
    console.log('EditTool state : ', location.state);

    const post = location.state.post;
    console.log('EditImageTool drawImage post : ', post);
    const image = new Image();

    function getImage() {
        console.log('EditTool drawImage image : ', image);
        image.src = post.fileUrl;
        image.onload = () => {
            const imageInstance = new fabric.Image.fromURL(image.src,function(oImg) {
                canvas.add(oImg);
            });
            console.log('EditImageTool imageInstance : ', imageInstance);
        }
        canvas.discardActiveObject();
        let sel = new fabric.ActiveSelection(canvas.getObjects(), {
            canvas: canvas,
        });
        canvas.setActiveObject(sel);
        canvas.getActiveObject().toGroup();
        canvas.requestRenderAll();

        const url = canvas.toDataURL("image/png").replace("image/png", "image/octet-stream");
        setChangedUrl(url);
        console.log('EditTool getImage url : ', url);
    }

    function update() {
        getImage();
        console.log('EditTool update image : ', image);
        console.log('EditTool getImage changedUrl : ', changedUrl);

        const jsonProf = JSON.parse(profile);
        const email = jsonProf.email;
        try {
            axios.put(`/v1/api/picTO/update/${email}/${provider}`, {
                octet: changedUrl
            }).then((response) => {
                console.log('EditImageTool update response data : ', response.data);
                console.log('EditImageTool update response data.data : ', response.data.data);

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
                        <button id="pencil-btn" onClick = {()=> {pencilMode()}}></button>
                        <button id="figure-btn" onClick = {()=> {figureMode()}}></button>
                        <button id="image-btn" onClick = {()=> {imageMode()}}></button>
                        <button id="text-btn" onClick = {()=> {textMode()}}></button>
                        <button id="download-btn" onClick={()=> {update()}} ></button>
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