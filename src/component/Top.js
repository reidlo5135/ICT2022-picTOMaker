import React, { Component } from 'react';
import Logo from "../image/Logo.png";
import "../css/Top.css"

class Top extends Component{
    render(){
    return (
      <div className='topMenu'>
        <img src={Logo} alt="PictoMaker-Logo" style={{width:"100px",height:"50px"}}/>
        <div className='GnbMenu'>
          <div>소개</div>
	        <div>시작하기</div>
	        <div>도움말</div>
	        <div>커뮤니티</div>
          </div>
      </div>
      
    );
    }
  }

export default Top;