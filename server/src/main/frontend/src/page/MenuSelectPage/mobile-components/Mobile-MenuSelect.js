import React from "react";
import "../menuSelect.css";
import Select from "./Mobile-MenuSelectDetails"
import Logo from "../../../assets/image/Logo.png";
import { Link } from "react-router-dom";

function MenuSelect() {
    return (
        <>
            <div className="stars">
                <div className="star"></div>
                <div className="star"></div>
                <div className="star"></div>
                <div className="star"></div>
                <div className="star"></div>
                <div className="star"></div>
            </div>
            <div className="selectpage">

                <Link to='/'>
                    <div className='qna-logo'>
                        <img src={Logo} alt="PictoMaker-Logo" style={{width:"150px",height:"70px"}}/>
                    </div>
                </Link>
                <Select/>
            </div>
        </>
    );
}

export default MenuSelect;