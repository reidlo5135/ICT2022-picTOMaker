import React, {useEffect, useRef} from 'react';
import {useLocation} from "react-router-dom";
import ImagePose from "./edittool/ImagePose";

export default function EditTransfer(){
    const location = useLocation();
    const childRef = useRef();

    const post = location.state.post;
    console.log('EditTransfer post : ', post);

    localStorage.setItem('userPicTo', JSON.stringify(post));

    useEffect(() => {
        console.log('EditTransfer useEffect invoked');
        childRef.current.capture();
    }, []);

    return (
      <>
          <ImagePose ref={childRef} />
      </>
    );
}
