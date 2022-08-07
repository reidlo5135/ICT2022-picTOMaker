import React, { useEffect, useState,useRef } from 'react';
import {getData} from "./Community"

const PostView = ({ history, diaryList }) => {
  /* const [ data, setData ] = useState({});

  const { no } = match.params;

  useEffect(() => {
    setData(getPostByNo(no));
  }, [ ]); */

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
    setDate(initData);
  };

  useEffect(() => {
      getData();
  }, []);
  
  return (
    <>
      <h2 align="center">게시글 상세정보</h2>

      <div className="post-view-wrapper">
        {
          data ? (
            <>
              <div className="post-view-row">
                <label>게시글 번호</label>
                <label>{ data.no }</label>
              </div>
              <div className="post-view-row">
                <label>제목</label>
                <label>{ data.title }</label>
              </div>
              <div className="post-view-row">
                <label>작성일</label>
                <label>{ data.createDate }</label>
              </div>
              <div className="post-view-row">
                <label>조회수</label>
                <label>{ data.readCount }</label>
              </div>
              <div className="post-view-row">
                <label>내용</label>
                <div>
                  {
                    data.content
                  }
                </div>
              </div>
            </>
          ) : '해당 게시글을 찾을 수 없습니다.'
        }
        <button className="post-view-go-list-btn" onClick={() => history.goBack()}>목록으로 돌아가기</button>
      </div>
    </>
  )
}

export default PostView;