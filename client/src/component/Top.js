import React from 'react';
import {useHistory} from "react-router";
import {useCookies} from "react-cookie";
import { Link } from "react-router-dom";
import TopProfile from "./TopProfile";
import Logo from "../assets/image/Logo.png";
import Sidebar from "./Sidebar";
import "../styles/top.css";
import "../styles/font.css";


export default function Top(){
    const history = useHistory();
    const [cookies] = useCookies(["accessToken"])
    const access_token = cookies.accessToken;

    function start() {
        try {
            if(access_token !== null) {
                history.push("/select");
            } else {
                alert('로그인 후 사용 가능합니다.');
            }
        } catch (err) {
            console.error(err);
        }
    }

    function moveToComm() {
        try {
            if(access_token !== null) {
                history.push("/community");
            } else {
                alert('로그인 후 사용 가능합니다.');
            }
        } catch (err) {
            console.error(err);
        }
    }

    function moveToQna() {
        try {
            if(access_token !== null) {
                history.push("/qna");
            } else {
                alert('로그인 후 사용 가능합니다.');
            }
        } catch (err) {
            console.error(err);
        }
    }

    return (
        <div className='top' id="1">
            <div className='topMenu'>
                <Link to='/'>
                    <img src={Logo} className='img_logo' alt="PictoMaker-Logo" style={{width:"100px",height:"50px"}}/>
                </Link>
                <div className='top_sidebar'>
                    {(access_token === null || access_token === undefined) ? <div></div> : <TopProfile />}
                    {(access_token === null || access_token === undefined) ? <div></div> : <Sidebar/>}
                </div>
            </div>
        </div>
    );
}