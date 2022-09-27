import React, {useEffect, useState} from 'react';
import {useCookies} from "react-cookie";
import {useHistory} from "react-router";
import "../page/SocialLoginPage/socialUserCallback.css";
import {post} from "../services/AxiosService";
import {wsCommunication, wsConnect} from "../services/StompService";

export default function MobileUserCallback() {
    const [cookies, setCookie] = useCookies(["accessToken"]);
    const history = useHistory();
    const [email, setEmail] = useState();
    const [nickName, setNickName] = useState();
    const [profileImage, setProfileImage] = useState();
    const [isLogged, setIsLogged] = useState(false);
    const provider = localStorage.getItem("provider");
    let profile = null;

    const getOAuthProf = () => {
        post(`/v1/api/oauth2/info/${provider}`, {}, {
            headers: {
                "X-AUTH-TOKEN": cookies.accessToken
            }
        }).then((response) => {
            console.log('OAuth profile res data.data : ', response.data.data);

            profile = JSON.parse(JSON.stringify(response.data.data));
            setEmail(profile.email);
            setNickName(profile.name);

            if(profile.profile_image_url === null){
                setProfileImage(null);
            } else {
                setProfileImage(profile.profile_image_url);
            }

            localStorage.setItem("profile", JSON.stringify(response.data.data));
        }).catch((err) => {
            console.error('err : ', JSON.stringify(err));
            alert(err.response.data.msg);
        });
    };

    const getLocalProf = () => {
        const headers = {
            "X-AUTH-TOKEN": cookies.accessToken
        }
        const action = (response) => {
            console.log('Local profile res data.data : ', response.body.data);

            profile = JSON.parse(JSON.stringify(response.body.data));
            setEmail(profile.email);
            setNickName(profile.nickName);

            if(profile.profile_image_url === null){
                setProfileImage(null);
            } else {
                setProfileImage(profile.profile_image_url);
            }

            localStorage.setItem("profile", JSON.stringify(profile));
            history.push("/");
        }
        wsCommunication('/pub/user/info', headers, {}, '/sub/info', action);
    };

    useEffect(() => {
        console.log("MobileUserCallback.js token : ", cookies.accessToken);
        if(provider === null) {
            setIsLogged(false);
        } else if(provider !== 'LOCAL') {
            getOAuthProf();
        } else if(provider === 'LOCAL') {
            getLocalProf();
        }
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