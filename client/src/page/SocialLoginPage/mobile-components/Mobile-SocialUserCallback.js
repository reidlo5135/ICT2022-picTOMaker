import React, {useState} from "react";
import {useCookies} from "react-cookie";
import {useHistory} from "react-router";
import "../socialUserCallback.css";
import {wsCommunicationWithConnection} from "../../../services/StompService";

export default function M_SocialUserCallback() {
    const [cookies, setCookie] = useCookies(["accessToken"]);
    const history = useHistory();
    const [isLogged, setIsLogged] = useState(true);
    const url = new URL(window.location.href);
    let provider;
    const token = new URL(window.location.href).searchParams.get("code");
    console.log('code : ', token);

    if(token.toString() == null) {
        alert('Token is Null');
    }

    if(url.toString().includes('kakao')) {
        provider = 'kakao';
    } else if(url.toString().includes('naver')) {
        provider = 'naver';
    } else if(url.toString().includes('google')) {
        provider = 'google';
    } else {
        provider = null;
    }

    console.log('provider : ', provider);

    const headers = {
        "X-AUTH-TOKEN": token
    }

    const action = (response) => {
        if(response.body.code === 0) {
            setIsLogged(true);
            setCookie("accessToken", response.body.data.access_token, {path: "/"});
            localStorage.setItem("refresh_token", response.body.data.refresh_token);
            localStorage.setItem("provider", provider);
            history.push("/user/redirect");
        }
    }

    wsCommunicationWithConnection(`/pub/user/social/login/${provider}`, headers, {}, '/sub/social/login', action);

    return (
        <center>
            <div className='loading'>
                <h1>잠시만 기다려주세요...</h1>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
            </div>
        </center>
    );
}
