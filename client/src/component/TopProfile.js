import React, {useEffect, useState} from "react";
import {useHistory} from "react-router";
import {useCookies} from "react-cookie";
import axios from "axios";
import '../styles/top.css'
import {Link} from "react-router-dom";
import Human from '../assets/image/Human.png';

const TopProfile = () => {
    const history = useHistory();
    const [cookies, setCookie, removeCookie] = useCookies(["accessToken"]);
    const provider = localStorage.getItem('provider');
    const [isLogged, setIsLogged] = useState(false);

    const [email, setEmail] = useState();
    const [nickName, setNickName] = useState();
    const [profileImage, setProfileImage] = useState();
    let profile = null;

    const getOAuthProf = async () => {
        try {
            await axios.post(`/v1/api/oauth2/info/${provider}`, {}, {
                headers: {
                    "Authorization": cookies.accessToken
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
        } catch (err) {
            console.error(err);
        }
    };

    const getLocalProf = () => {
        try {
            axios.post('/v1/api/user/info', {}, {
                headers: {
                    "X-AUTH-TOKEN": cookies.accessToken
                }
            }).then((response) => {
                console.log('Local profile res data.data : ', response.data.data);

                profile = JSON.parse(JSON.stringify(response.data.data));
                setEmail(profile.email);
                setNickName(profile.nickName);

                if(profile.profile_image_url === null){
                    setProfileImage(null);
                } else {
                    setProfileImage(profile.profile_image_url);
                }

                localStorage.setItem("profile", JSON.stringify(profile));
            }).catch((err) => {
                console.error('err : ', JSON.stringify(err));
                console.error('code : ', err.response.data.code);
                axios.post('/v1/api/user/reissue', {
                    accessToken: cookies.accessToken,
                    refreshToken: localStorage.getItem("refresh_token")
                }).then((response) => {
                    setCookie("accessToken", response.data.data.accessToken);
                    localStorage.setItem("refresh_token", response.data.data.refreshToken);
                    window.location.reload();
                }).catch((err) => {
                    alert(err.response.data.msg);
                });
            });
        } catch (err) {
            console.error(err);
        }
    };

    useEffect(() => {
        console.log("TopProfile.js token : ", cookies.accessToken);
        if(provider === null) {
            setIsLogged(false);
        } else if(provider !== 'LOCAL') {
            getOAuthProf();
        } else if(provider === 'LOCAL') {
            getLocalProf();
        }
        if(cookies.accessToken != null) {
            setIsLogged(true);
            localStorage.setItem("isLogged", isLogged.toString());
        }
    }, []);

    if(isLogged) {
        return (
            <Link to='/myPage' className="profile-link">
                {
                    localStorage.getItem("profile") === null ?
                        <div></div> :
                        <div className="profile-content">
                            {profileImage === null ?
                                <img src={Human} className='img_profile' alt={'p-image'} /> :
                                <img src={profileImage} className='img_profile' alt={'p-image'} />
                            }
                            {nickName}님 환영합니다!
                        </div>
                }
            </Link>
        );
    } else {
        return(
            <div></div>
        );
    }
}

export default TopProfile;