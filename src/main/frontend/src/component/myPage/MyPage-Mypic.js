
import React,{useEffect, useState} from 'react';
import Logo from "../../image/Logo.png";
import "../../css/MyPage.css";
import "../../css/font.css";
import ReactFancyBox from "react-fancybox";
import 'react-fancybox/lib/fancybox.css';

const Mypic = () => {
    return(
                <div className='mypics'>
                   <div className='pic-div'>
                       <div className='pic-cont'>
                           <div className='pic-colorbox'/>
                           <div className='pic-img'>
                           <ReactFancyBox
                                thumbnail={Logo}
                                image={Logo}
                            />
                           </div>
                           <p className='pic-name'>picname</p>
                           <div className='pic-btns'>
                               <button className='pic-download pic-btn'>
                                   다운로드
                               </button>
                               <button className='pic-edit pic-btn'>
                                    편집하기
                               </button>
                           </div>
                       </div>
                   </div>
                </div>
    )
}
export default function MyPageMyPic(){
    return (
            <div className='MyPage-Right'>
                <div className='right-flex'>
                    <Mypic/>

               </div>
            </div>
    )
}