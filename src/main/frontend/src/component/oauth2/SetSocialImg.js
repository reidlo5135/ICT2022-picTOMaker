import React from "react";
import kakaotalk from '../../image/kakaotalk.png';
import naver from '../../image/naver.png';
import google from '../../image/google.png';
import Logo from '../../image/Logo.png';

export default function SetSocialImg () {

    const provider = localStorage.getItem('provider');

    if(provider === 'kakao') {
        return (
            <img className='s-img' src={kakaotalk} alt='s-image'/>
        );
    } else if(provider === 'naver') {
        return (
            <img className='s-img' src={naver} alt='s-image'/>
        );
    } else if (provider === 'google') {
        return (
            <img className='s-img' src={google} alt='s-image'/>
        );
    } else {
        return (
            <div></div>
        );
    }
}