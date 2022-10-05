import React ,{useEffect}from "react";
import {del} from "../../../services/AxiosService";
import {useCookies} from "react-cookie";
import {Link} from "react-router-dom";
import '../../SocialLoginPage/socialUserCallback.css';
import axios from 'axios';

const MyPagePicToPosting = ({ posts, loading }) => {
    const [cookies, setCookie] = useCookies(["accessToken"]);
    console.log('POST posts : ', posts);
    posts.map((post) => {
        console.log('post : ', post);
    });

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
            <Link to={`/edit/${post.fileUrl}`}>
                <div className='pic-div' key={post.id}>
                    <div className='pic-cont'>
                        <div className='pic-backimg'></div>
                        <div className='pic-img'>
                            <img src={post.fileUrl} alt={"픽토이미지"} />
                        </div>
                    </div>
                </div>
            </Link>
        ))}
      </div>
    </>
  );
};
export default MyPagePicToPosting;