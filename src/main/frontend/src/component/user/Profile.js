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

    const getOAuthProf = async () => {
        try {
            await axios.post(`/oauth2/profile/${provider}`, {
                access_token
            },{
                baseURL: 'http://localhost:8080',
                withCredentials: true
            }).then((response) => {
                console.log('OAuth profile res data.data : ', response.data.data);
                console.log('OAuth get profile email : ', response.data.data.email);
                console.log('OAuth get profile nickname : ', response.data.data.nickname);
                console.log('OAuth get profile profile_image_url : ', response.data.data.profile_image_url);

                setEmail(response.data.data.email);
                setNickName(response.data.data.nickname);
                setProfileImage(response.data.data.profile_image_url);

                localStorage.setItem("profile", JSON.stringify(response.data.data));
            });
        } catch (err) {
            console.error(err);
        }
    }

    const getLocalProf = async () => {
        try {
            await axios.post('/v1/user/profile', {
                access_token
            },{
                baseURL: 'http://localhost:8080',
                withCredentials: true
            }).then((response) => {
                console.log('Local profile res data.data : ', response.data.data);
                console.log('Local get profile email : ', response.data.data.email);
                console.log('Local get profile nickname : ', response.data.data.nickname);
                console.log('Local get profile profile_image_url : ', response.data.data.profile_image_url);

                setEmail(response.data.data.email);
                setNickName(response.data.data.nickname);

                if(response.data.data.profile_image_url === null){
                    setProfileImage(null);
                }

                localStorage.setItem("profile", JSON.stringify(response.data.data));
            });
        } catch (err) {
            console.error(err);
        }
    }

    useEffect(() => {
        if(provider != 'LOCAL') {
            getOAuthProf();
        } else if(provider === 'LOCAL') {
            getLocalProf();
        } else if(provider == null) {
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