import React from "react";
import Pic from "../../image/Human.png";
import '../../css/Callback.css';
import {Link} from "react-router-dom";

const Posts = ({ posts, loading }) => {
    console.log('POST posts : ', posts);
    console.log('POST posts Map : ', posts.map((post) => {
        console.log('post : ', post);
    }));
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
                        <img src={post.fileUrl} alt={"픽토이미지"} />
                    </div>
                    <p className='pic-name'>{post.fileName}</p>
                    <div className='pic-btns'>
                        <button className='pic-download pic-btn' />
                        <Link to={{
                            pathname: '/edit',
                            state: {
                                post: post
                            }
                        }}>
                            <button className='pic-edit pic-btn' />
                        </Link>
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