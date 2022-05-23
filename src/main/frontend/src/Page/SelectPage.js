import React from "react";
import "../css/Select-Content.css";
import Select from "../component/contents/Select-Content"
import Logo from "../image/Logo.png";
import { Link } from "react-router-dom";

function SelectPage() {
  return (
    <div className="selectpage">
      <div class="star"></div>
        <div class="star"></div>
        <div class="star"></div>
        <div class="star"></div>
        <div class="star"></div>
        <div class="star"></div>
        <div class="star"></div>
      <Link to='/'>
        <div className='qna-logo'>
          <img src={Logo} alt="PictoMaker-Logo" style={{width:"150px",height:"70px"}}/>
        </div>
      </Link>
      <Select/>
    </div>
  );
}

export default SelectPage;