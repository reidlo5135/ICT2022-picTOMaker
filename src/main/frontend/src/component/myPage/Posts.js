import React from "react";
import {Link} from "react-router-dom";
import axios from 'axios';
import '../../css/Callback.css';

const Posts = ({ posts, loading }) => {
    console.log('POST posts : ', posts);
    posts.map((post) => {
        console.log('post : ', post);
    });

    const deletePicTo = (id) => {
        console.log('Posts deletePicTo id : ', id);
        try {
            axios.delete(`/v1/api/picto/id/${id}`)
                .then((response) => {
                    console.log('Posts deletePicTo response data : ' + response.data);
                    console.log('Posts deletePicTo response data.data : ' + response.data.data);

                    if(response.data.code === 0) {
                        alert('성공적으로 삭제되었습니다.');
                    }
                }).catch((err) => {
                    console.error('err : ', JSON.stringify(err));
                    alert(err.response.data.msg);
                });
        } catch (e) {
            console.error(e);
        }
    }

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
                            pathname: '/tool/image',
                            state: {
                                post: post
                            }
                        }}>
                            <button className='pic-edit pic-btn' />
                        </Link>
                        <button className='pic-delete pic-btn' onClick={() => {
                            deletePicTo(post.id);
                        }} />
                    </div>
                    <div className='pic-txt'>
                        <span className='pic-dtxt'>다운로드</span>
                        <span className='pic-etxt'>편집하기</span>
                        <span className='pic-etxt'>삭제하기</span>
                    </div>
                </div>
            </div>
        ))}
      </div>
    </>
  );
};
export default Posts;