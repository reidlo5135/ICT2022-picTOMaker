import React, { Component } from 'react';
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import "../../css/font.css"


export default function Best(){
    const settings = { dots: true,
        infinite: true,
        speed: 500,
        slidesToShow: 3,
        slidesToScroll: 1
    };

    return (
        <div className='Best'>
            <div className='Best-title'>
                금주의 인기 픽토그램
            </div>

            <Slider {...settings}>
                <div className='Best-list'>
                    <h3>1</h3>
                </div>
                <div className='Best-list'>
                    <h3>2</h3>
                </div>
                <div className='Best-list'>
                    <h3>3</h3>
                </div>
                <div className='Best-list'>
                    <h3>4</h3>
                </div>
                <div className='Best-list'>
                    <h3>5</h3>
                </div>
                <div className='Best-list'>
                    <h3>6</h3>
                </div>
            </Slider>


        </div>
    );
}