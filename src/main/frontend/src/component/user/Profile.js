import React, {useEffect, useState} from "react";
import axios from "axios";
import '../../css/Top.css'
import {Link} from "react-router-dom";
import Human from '../../image/Human.png';

const Profile = () => {
    const access_token = localStorage.getItem('access_token');
    const provider = localStorage.getItem('provider');
    const [isLogged, setIsLogged] = useState(false);

    const [email, setEmail] = useState();
    const [nickName, setNickName] = useState();
    const [profileImage, setProfileImage] = useState();
    let profile = null;

    const getOAuthProf = async () => {
        try {
            await axios.post(`/v1/api/oauth2/info/${provider}`, {
                access_token
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

    const getLocalProf = async () => {
        try {
            await axios.post('/v1/api/user/info', {
                access_token
            }).then((response) => {
                console.log('Local profile res data.data : ', response.data.data);

                profile = JSON.parse(JSON.stringify(response.data.data));
                setEmail(profile.email);
                setNickName(profile.nickName);

                console.log('Local get profile expires_in : ', profile.expires_in);

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

    useEffect(() => {
        if(provider !== 'LOCAL') {
            getOAuthProf();
        } else if(provider === 'LOCAL') {
            getLocalProf();
        } else if(provider === null) {
            setIsLogged(false);
        }
        if(access_token != null) {
            setIsLogged(true);
            localStorage.setItem("isLogged", isLogged.toString());
        }
    }, []);

    if(isLogged) {
        return (
            <Link to='/myPage' className="profile-link">
                <div className="profile-content">
                    {profileImage === null ?
                        <img src={Human} className='img_profile' alt={'p-image'} /> :
                        <img src={profileImage} className='img_profile' alt={'p-image'} />
                    }
                    {nickName}님 환영합니다!
                </div>
            </Link>
        );
    } else {
        return(
            <div></div>
        );
    }
}

export default Profile;