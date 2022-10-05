import React, {useEffect, useState} from 'react';
import SetUpUserImage from "../../../component/SetUpUserImage";
import Human from '../../../assets/image/Human.png';
import "../myPage.css";
import "../../../styles/font.css";

const M_MyPageProfile = () => {
    const getProfile = localStorage.getItem('profile');
    const provider = localStorage.getItem('provider');

    const [email, setEmail] = useState();
    const [nickName, setNickName] = useState();
    const [profileImage, setProfileImage] = useState();

    const getProf = () => {
        try {
            const jsonProf = JSON.parse(getProfile);
            setEmail(jsonProf.email);
            if(provider === 'LOCAL') {
                setNickName(jsonProf.nickName);
            } else {
                setNickName(jsonProf.name);
            }
            if(jsonProf.profile_image_url === null) {
                setProfileImage(null);
            } else {
                setProfileImage(jsonProf.profile_image_url);
            }
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
                {profileImage === null ?
                    <img src={Human} className='p-img-local' alt={'p-image'} /> :
                    <img src={profileImage} className='p-img' alt={'p-image'} />
                }
                <SetUpUserImage />
            </div>
        </div>
    );
}

export default M_MyPageProfile;