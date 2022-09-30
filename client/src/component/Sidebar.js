import React from 'react';
import {del} from "../services/AxiosService";
import "../styles/font.css"
import "../styles/top.css"
import { Link } from "react-router-dom";
import axios from "axios";
import {useHistory} from "react-router";
import {useCookies} from "react-cookie";

export default function Sidebar(){
    const history = useHistory();
    const [cookies, setCookie, removeCookie] = useCookies(["accessToken"])
    const provider = localStorage.getItem("provider");

    function Logout(e) {
        let url = null;
        e.preventDefault();
        if(provider === "LOCAL") {
            url = "/v1/api/user/logout";
        } else {
            url = "/v1/api/oauth2/logout";
        }
        del(url, {
            headers: {
                "X-AUTH-TOKEN": cookies.accessToken
            }
        }).then(() => {
            console.clear();
            localStorage.clear();
            removeCookie("accessToken", {path: "/"});
            history.push("/");
        }).catch((err) => {
            alert(err.response.data.msg);
        });
    }

    return (
        <div className={"hamburger-menu"}>
            <input id="menu__toggle" type="checkbox" />
            <label className="menu__btn" htmlFor="menu__toggle">
                <span></span>
            </label>

            <ul className="menu__box">
                <li><Link to='/mypage' className='menu__item'>마이페이지</Link></li>
                <li><a className="menu__item" href="client/src/component/Sidebar#" onClick={Logout}>로그아웃</a></li>
                <li><Link to='/qna' className='menu__item'>문의하기</Link></li>
                <li><Link to='/community' className='menu__item'>커뮤니티</Link></li>
            </ul>
        </div>
    );
}