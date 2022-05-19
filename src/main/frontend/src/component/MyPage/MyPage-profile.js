
import React from 'react';
import Logo from "../../image/Logo.png";
import "../../css/MyPage.css"
import "../../css/font.css"
import { Link } from "react-router-dom";

export default function MyPageProfile(){
    
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
                                홍길동
                            </div>
                        </div>
                        <div className='MenuBox'>
                            <div className='Name'>
                                이름
                            </div>
                            <div className='MenuBox-props'>
                                홍길동
                            </div>
                        </div>
                        <div className='MenuBox'>
                            <div className='Name'>
                                이름
                            </div>
                            <div className='MenuBox-props'>
                                홍길동
                            </div>
                        </div>
                        <div className='MenuBox'>
                            <div className='Name'>
                                이름
                            </div>
                            <div className='MenuBox-props'>
                                홍길동
                            </div>
                        </div>
                    </div>
                </div>
    )
}