import React, {useState} from 'react';
import {useHistory} from "react-router";
import axios from "axios";
import '../../css/Callback.css';

export default function Callback(){
    const history = useHistory();
    const [isLogged, setIsLogged] = useState(true);
    const url = new URL(window.location.href);
    let provider;
    const code = new URL(window.location.href).searchParams.get("code");
    console.log('code : ', code);

    if(code.toString() == null) {
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

    try {
        axios.post(`/v1/api/oauth2/signup/${provider}`, {
            code
        }).then((response) => {
            console.log('res data : ', response.data);
            console.log('res data.data : ', response.data.data);

            if(response.data.code === 0) {
                const access_token = response.data.data.access_token;

                setIsLogged(true);
                localStorage.setItem("access_token", access_token);
                localStorage.setItem("provider", provider);

                history.push("/");
            }
        }).catch((err) => {
            console.error('err : ', JSON.stringify(err));
            alert(err.response.data.msg);
        });
    } catch (err) {
        alert(err);
        console.error(err);
    }

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