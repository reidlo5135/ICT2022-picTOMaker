import React, {useEffect, useState} from 'react';
import axios from "axios";
import "../../css/MyPage.css";
import "../../css/font.css";

export default function MyPageProfile(){
    const getProfile = localStorage.getItem('profile');
    const provider = localStorage.getItem('provider');

    const [email, setEmail] = useState();
    const [name, setName] = useState();
    const [nickName, setNickName] = useState();
    const [profileImage, setProfileImage] = useState();
    const [count, setCount] = useState(0);

    const getProf = () => {
        try {
            const jsonProf = JSON.parse(getProfile);
            console.log('MyPage-profile jProf : ', jsonProf);

            const email = jsonProf.email;
            setEmail(email);
            axios.get(`/v1/api/picto/count/email/${email}/provider/${provider}`)
                .then((response) => {
                    console.log('MyPage-profile getPicToCount : ', response.data);

                    if(response.data.code === 0) {
                        console.log('MyPage-profile getPicToCount count : ', response.data.data);
                        setCount(response.data.data);
                    }
                }).catch((err) => {
                    console.error('err : ', JSON.stringify(err));
                    alert(err.response.data.msg);
                });

            setNickName(jsonProf.nickName);
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
                    {nickName === null ? <div className='Name'></div> : <div className='Name'>닉네임</div>}
                    <div className='MenuBox-props'>
                        <b>{nickName}</b>
                    </div>
                </div>
                <div className='MenuBox'>
                    <div className='Name'>
                        이메일
                    </div>
                    <div className='MenuBox-props'>
                        <b>{email}</b>
                    </div>
                </div>
                <div className='MenuBox'>
                    <div className='Name'>
                        제작한 픽토
                    </div>
                    <div className='MenuBox-props'>
                        <b>{count}</b>
                    </div>
                </div>
            </div>
        </div>
    );
}