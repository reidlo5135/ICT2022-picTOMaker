import React, {useEffect, useState} from 'react';
import "../../css/MyPage.css"
import "../../css/font.css"

export default function MyPageProfile(){

    const getProfile = localStorage.getItem('profile');

    const [email, setEmail] = useState();
    const [nickName, setNickName] = useState();
    const [profileImage, setProfileImage] = useState();

    const getProf = () => {
        try {
            const jsonProf = JSON.parse(getProfile);
            console.log('MyPage-profile jProf : ', jsonProf);

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
        <div className='MyPage-Right'>
            <div className='Right-Contents'>
                <div className='Menu-Name'>
                    내 프로필
                </div>
                <div className='MenuBox'>
                    <div className='Name'>
                        이름
                    </div>
                    <div className='MenuBox-props'>
                        {nickName}
                    </div>
                </div>
                <div className='MenuBox'>
                    <div className='Name'>
                        이메일
                    </div>
                    <div className='MenuBox-props'>
                        {email}
                    </div>
                </div>
                <div className='MenuBox'>
                    <div className='Name'>
                        제작한 픽토
                    </div>
                    <div className='MenuBox-props'>
                        0
                    </div>
                </div>
            </div>
        </div>
    );
}