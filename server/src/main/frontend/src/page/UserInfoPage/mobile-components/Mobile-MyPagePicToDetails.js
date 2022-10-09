import React, {useState,useEffect} from 'react';
import {get} from "../../../services/AxiosService";
import {useCookies} from "react-cookie";
import MyPagePicToPosting from "../components/MyPagePicToPosting";
import MobileMyPagePicToPagination from "./Mobile-MyPagePicToPagination";
import "../myPage.css";
import "../../../styles/font.css";
import 'react-fancybox/lib/fancybox.css';

export default function MobileMyPagePicToDetails(){
    const [cookies, setCookie] = useCookies(["accessToken"]);
    const [posts, setPosts] = useState([]);
    const [loading, setLoading] = useState(false);
    const [currentPage, setCurrentPage] = useState(1);
    const [postsPerPage, setPostsPerPage] = useState(1);

    const getProfile = localStorage.getItem('profile');
    const prof = JSON.parse(getProfile);
    const email = prof.email;
    const provider = localStorage.getItem("provider");

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            get(`/v1/api/picto/${provider}`, {
                headers: {
                    "X-AUTH-TOKEN": cookies.accessToken
                }
            }).then((response) => {
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
                <MobileMyPagePicToPagination
                    postsPerPage={postsPerPage}
                    totalPosts={posts.length}
                    paginate={setCurrentPage}
                />
            </div>
        </div>
    );
}