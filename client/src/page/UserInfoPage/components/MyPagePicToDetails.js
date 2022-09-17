import React, {useState,useEffect} from 'react';
import {useCookies} from "react-cookie";
import axios from "axios";
import MyPagePicToPosting from "./MyPagePicToPosting";
import MyPagePicToPagination from "./MyPagePicToPagination";
import "../myPage.css";
import "../../../styles/font.css";
import 'react-fancybox/lib/fancybox.css';

export default function MyPagePicToDetails(){
    const [cookies, setCookie] = useCookies(["accessToken"]);
    const [posts, setPosts] = useState([]);
    const [loading, setLoading] = useState(false);
    const [currentPage, setCurrentPage] = useState(1);
    const [postsPerPage, setPostsPerPage] = useState(4);

    const getProfile = localStorage.getItem('profile');
    const prof = JSON.parse(getProfile);
    const email = prof.email;
    const provider = localStorage.getItem("provider");

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            await axios.get(`/v1/api/picto/${provider}`, {
                headers: {
                    "X-AUTH-TOKEN": cookies.accessToken
                }
            }).then((response) => {
                    console.log('response data : ' + response.data);
                    console.log('response data.list : ' + response.data.list);
                    console.log('response data.list JSON : ', JSON.stringify(response.data.list));

                    if(response.data.code === 0) {
                        setPosts(response.data.list);
                        setLoading(false);
                    }
                }).catch((err) => {
                    console.error('err : ', JSON.stringify(err));
                    setPosts(null);
                    alert(err.response.data.msg);
                    window.location.replace('/myPage');
                });
        };
        fetchData();
    }, []);
  

    const indexOfLast = currentPage * postsPerPage;
    const indexOfFirst = indexOfLast - postsPerPage;
    const currentPosts = (posts) => {
      let currentPosts = 0;
      currentPosts = posts.slice(indexOfFirst, indexOfLast);
      return currentPosts;
    };

    return (
        <div className='MyPage-Right'>
            <div className='right-flex'>
                <MyPagePicToPosting posts={currentPosts(posts)} loading={loading}></MyPagePicToPosting>
                <MyPagePicToPagination
                    postsPerPage={postsPerPage}
                    totalPosts={posts.length}
                    paginate={setCurrentPage}
                />
            </div>
        </div>
    );
}