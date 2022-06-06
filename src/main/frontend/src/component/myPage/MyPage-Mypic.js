import React,{useState,useEffect} from 'react';
import "../../css/MyPage.css";
import "../../css/font.css";
import 'react-fancybox/lib/fancybox.css';
import Pic from "../../image/Human.png"
import Mockup from "../../image/mockup.png"
import axios from "axios";
import Posts from "./Posts";
import Pagination from "./Pagination";

export default function MyPageMyPic(){
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
        // const response = await axios.get(
        //   "https://jsonplaceholder.typicode.com/posts"
        // );

          const response = await axios.post(`/v1/upload/get/picTO/${email}/${provider}`, {}, {
              baseURL: 'http://localhost:8080',
              withCredentials: true
          }).then((response) => {
              console.log('response data : ' + response.data);
              console.log('response data.list : ' + response.data.list);

              if(response.data.code === 0) {
                  setPosts(response.data.list);
                  setLoading(false);
              }
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
                <Posts posts={currentPosts(posts)} loading={loading}></Posts>
                <Pagination
                    postsPerPage={postsPerPage}
                    totalPosts={posts.length}
                    paginate={setCurrentPage}
                />
            </div>
        </div>
    );
}