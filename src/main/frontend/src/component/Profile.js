import React, {useEffect, useState} from "react";
import axios from "axios";
import '../css/Top.css'

const Profile = () => {
    const access_token = localStorage.getItem('access_token');
    const provider = localStorage.getItem('provider');
    const [isLogged, setIsLogged] = useState(false);

    const [email, setEmail] = useState();
    const [nickName, setNickName] = useState();
    const [profileImage, setProfileImage] = useState();

    const getProf = async () => {
        try {
            await axios.post(`/oauth2/profile/${provider}`, {
                access_token
            },{
                baseURL: 'http://localhost:8080',
                withCredentials: true
            }).then((response) => {
                console.log('profile res data.data : ', response.data.data);

                console.log('get profile : ', response.data.data);
                console.log('get profile : ', response.data.data.email);
                console.log('get profile : ', response.data.data.nickname);
                console.log('get profile : ', response.data.data.profile_image_url);

                setEmail(response.data.data.email);
                setNickName(response.data.data.nickname);
                setProfileImage(response.data.data.profile_image_url);
            });
        } catch (err) {
            console.error(err);
        }
    }

    useEffect(() => {
        getProf();
        setIsLogged(true);
    }, []);

    if(isLogged) {
        return (
            <div>
                <section>
                    <img src={profileImage} className='img_profile' alt={'p-image'} />
                    {nickName}님 환영합니다!
                </section>
            </div>
        );
    }
}

export default Profile;