import axios from 'axios';
import React, { useEffect, useState,useRef } from 'react';
import Top from "../contents/Top";
import img from "../../image/naver.png";

const CDetail = ({ history, diaryList,match}) => {
    const [data,setDate] = useState([]);
    const dataId = useRef(0)


    const getData = async () => {
        const res = await fetch(
            "https://jsonplaceholder.typicode.com/comments"
        ).then((res) => res.json());

        const initData = res.slice(0, 20).map((it) => {
            //slice = 0부터 20까지 데이터를 자를것임
            return {
                author: it.email,
                content: it.body,
                emotion: Math.floor(Math.random() * 5) + 1,
                //Math.random()*5 = 0부터 4까지의 난수 생성(소수점까지 포함)
                //Math.floor = 소수점을 없애줌 , +1 = 5까지
                created_date: new Date().getTime() + 1,
                id: dataId.current++
            };
        });
        setDate(initData[`${match.params.id}`]);
    };

    useEffect(() => {
        getData();

    }, []);
    console.log(data);

    return (
        <>
        <Top/>
        <div className='cdetail'>
            
            <h2 align="center"></h2>

            <div className="post-view-wrapper">
                {
                    data ? (
                        <>
                            <div className='infocard'>
                                <div className="dprofimg"><img className="profimg" src={img} alt="작성자 프로필 이미지"/></div>
                                <div className='postcont'>
                                    <div className="post-view-row row1">
                                        <label className='profname plabel'>작성자</label>
                                        <p>{ data.author }</p>
                                    </div>
                                    <div className="post-view-row row2">
                                        <label className='proftitle plabel'>제목</label>
                                        <p>{ data.id }</p>
                                    </div>
                                    <div className="post-view-row row3">
                                        <label className='profdate plabel'>작성일</label>
                                        <p>{ data.created_date }</p>
                                    </div>
                                </div>
                            </div>
                            <div className='contcard'>
                                <div className="post-view-row">
                                    <label>내용</label>
                                    <div>
                                        {
                                            data.content
                                        }
                                    </div>
                                </div>
                            </div>
                        </>
                    ) : '해당 게시글을 찾을 수 없습니다.'
                }
                <button className="post-view-go-list-btn" onClick={() => history.goBack()}>목록으로 돌아가기</button>
            </div>
        </div>
        </>
    )
}

export default CDetail;