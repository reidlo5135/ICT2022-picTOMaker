import axios from 'axios';
import React, { Component, useEffect, useState } from 'react';
import Slider from "react-slick";
import "../../css/community.css";
import "../../css/font.css"
import Top from "./Top";
import Footer from "./Footer";

export default function Community(){

    return (
            <>
                <Top/>
                <div className='Community'>
                    
                    <h2 className="commu-title">커뮤니티</h2>
                    <div className ="row">
                        <table className="commu-table">
                            <thead>
                                <tr>
                                    <th>글 번호</th>
                                    <th>타이틀 </th>
                                    <th>작성자 </th>
                                    <th>작성일 </th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td> 1 </td>
                                    <td> 2 </td>
                                    <td> 3</td>
                                    <td> 4 </td>
                                </tr>     
                            </tbody>
                        </table>
                    </div>
            </div>
            <Footer/>
        </>
    );
}