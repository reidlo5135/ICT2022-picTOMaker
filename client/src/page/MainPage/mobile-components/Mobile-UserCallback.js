import React, {useEffect, useState} from 'react';
import {useCookies} from "react-cookie";
import {useHistory} from "react-router";
import "../../SocialLoginPage/socialUserCallback.css";
import {wsCommunicationWithConnection, wsConnect} from "../../../services/StompService";

export default function MobileUserCallback() {
    const [cookies, setCookie] = useCookies(["accessToken"]);
    const history = useHistory();
    const [email, setEmail] = useState();
    const [nickName, setNickName] = useState();
    const [profileImage, setProfileImage] = useState();
    const [isLogged, setIsLogged] = useState(false);
    const provider = localStorage.getItem("provider");
    let profile = null;

    const headers = {
        "X-AUTH-TOKEN": cookies.accessToken
    };

    const getLocalProf = (requestUrl, responseUrl) => {
        const action = (response) => {
            profile = JSON.parse(JSON.stringify(response.body.data));
            setEmail(profile.email);
            setNickName(profile.nickName);

            if(profile.profile_image_url === null){
                setProfileImage(null);
            } else {
                setProfileImage(profile.profile_image_url);
            }

            localStorage.setItem("profile", JSON.stringify(profile));
        }
        wsCommunicationWithConnection(requestUrl, headers, {}, responseUrl, action);
        history.push("/");
    };

    useEffect(() => {
        console.log("Mobile-UserCallback.js token : ", cookies.accessToken);
        console.log("Mobile-UserCallback.js provider : ", provider);
        let requestUrl = null;
        let responseUrl = null;
        if(provider === null) {
            setIsLogged(false);
        }
        if(provider !== 'LOCAL') {
            requestUrl = `/pub/info/${provider}`;
            responseUrl = '/sub/social/info';
        } else if(provider === 'LOCAL') {
            requestUrl = '/pub/user/info';
            responseUrl = '/sub/info';
        }
        getLocalProf(requestUrl, responseUrl);
    }, []);

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