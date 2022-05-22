import React, {useEffect, useState} from 'react';
import SetSocialImg from "../oauth2/SetSocialImg";
import "../../css/MyPage.css";
import "../../css/font.css";

const GetProfile = () => {

    const getProfile = localStorage.getItem('profile');

    const [email, setEmail] = useState();
    const [nickName, setNickName] = useState();
    const [profileImage, setProfileImage] = useState();

    const getProf = () => {
        try {
            const jsonProf = JSON.parse(getProfile);
            console.log('jProf : ', jsonProf);

            setEmail(jsonProf.email);
            setNickName(jsonProf.nickname);
            setProfileImage(jsonProf.profile_image_url);
        } catch (err) {
            console.error(err);
        }
    }

    useEffect(() => {
        getProf();
    }, []);

    return (
        <div className='Profile'>
            <div className='Pro-Img'>
                <img className={'p-img'} src={profileImage} alt='p-image' />
                <SetSocialImg />
            </div>

            <div className='Pro-Txt'>
                <div className='Pro-Title'>
                    {nickName}님, 반갑습니다!
                </div>
                <div className='Pro-Email'>
                    {email}
                </div>
            </div>
        </div>
    );
}

export default GetProfile;