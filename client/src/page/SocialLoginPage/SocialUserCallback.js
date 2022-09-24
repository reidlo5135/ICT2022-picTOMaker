import React, {useState} from 'react';
import {post} from "../../services/AxiosService";
import {useCookies} from "react-cookie";
import {useHistory} from "react-router";
import './socialUserCallback.css';

export default function SocialUserCallback(){
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

    post(`/v1/api/oauth2/login/${provider}`, {}, {
        headers: {
            "X-AUTH-TOKEN": token
        }
    }).then((response) => {
        console.log('res data : ', response.data);
        console.log('res data.data : ', response.data.data);

        if(response.data.code === 0) {
            setIsLogged(true);
            setCookie("accessToken", response.data.data.access_token, {path: "/"});
            localStorage.setItem("refresh_token", response.data.data.refresh_token);
            localStorage.setItem("provider", provider);
            history.push("/");
        }
    }).catch((err) => {
        console.error('err : ', JSON.stringify(err));
        alert(err.response.data.msg);
    });

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