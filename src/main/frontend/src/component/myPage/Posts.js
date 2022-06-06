import React from "react";
import Pic from "../../image/Human.png";
import '../../css/Callback.css';

const Posts = ({ posts, loading }) => {
  return (
    <>
      {
          loading && <div className='loading'>
            <h1>잠시만 기다려주세요...</h1>
            <div></div>
            <div></div>
            <div></div>
            <div></div>
          </div>
      }
      <div className='mypics'>
        {posts.map((post) => (
          <div className='pic-div' key={post.id}>
                <div className='pic-cont'>
                    <div className='pic-backimg'></div>
                    <div className='pic-img'>
                        <img src={post} alt={"픽토이미지"} />
                    </div>
                    <p className='pic-name'>{post.id}Pickname</p>
                    <div className='pic-btns'>
                        <button className='pic-download pic-btn'>
                        </button>
                        <button className='pic-edit pic-btn'>
                        </button>
                    </div>
                    <div className='pic-txt'>
                        <span className='pic-dtxt'>다운로드</span>
                        <span className='pic-etxt'>편집하기</span>
                    </div>
                </div>
            </div>
        ))}
      </div>
    </>
  );
};
export default Posts;