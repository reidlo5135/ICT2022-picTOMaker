import React, {useRef, useState,useEffect} from "react";
import {get} from "../../services/AxiosService";
import Top from "../../component/Top";
import CommunityList from "./mobile-components/Mobile-CommunityList";
import CommunityPagination from "./components/CommunityPagination";

function Community() {
  const [data, setData] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [postsPerPage, setPostsPerPage] = useState(4);

  const dataId = useRef(0);

  const getData = async () => {
    get('/v1/api/community/')
        .then((response) => {
            if(response.data.code === 0) {
                const res = response.data.list;
                const initData = res.slice(0, 20).map((it) => {
                    return {
                        id: it.id,
                        author: it.nickname,
                        title: it.title,
                        content: it.content,
                        emotion: Math.floor(Math.random() * 5) + 1,
                        //Math.random()*5 = 0부터 4까지의 난수 생성(소수점까지 포함)
                        //Math.floor = 소수점을 없애줌 , +1 = 5까지
                        create_date: new Date().getTime() + 1,
                    };
                });
                setData(initData);
            }
        })
        .catch((err) => {
            console.error('err : ', JSON.stringify(err));
            alert(err.response.data.msg);
        });
  }

  useEffect(() => {
      getData();
  }, []);

  const onCreate = (author,content,emotion) => {
    const create_date = new Date().getTime();
    const newItem ={
      author,
      content,
      emotion,
      create_date,
      id : dataId.current
    }
    dataId.current += 1;
    setData([ newItem,...data]);
  };

  const onRemove = (targetId) => {
    const newDiaryList = data.filter((it) => it.id !== targetId);
    setData(newDiaryList);
  }

  const onEdit = (targetId,newContent) => {
    setData(
      data.map((it)=>
      it.id === targetId ? {...it,content:newContent}: it)
    )
  }

  const indexOfLast = currentPage * postsPerPage;
  const indexOfFirst = indexOfLast - postsPerPage;
  const currentPosts = (data) => {
      let currentPosts = 0;
      currentPosts = data.slice(indexOfFirst, indexOfLast);
      return currentPosts;
  };

  return (
      <div className="commu">
      <Top/>
      {/* <CommunityList diaryList={data}/> */}
      <CommunityList diaryList={currentPosts(data)}/>
      <CommunityPagination
          postsPerPage={postsPerPage}
          totalPosts={data.length}
          paginate={setCurrentPage}
      />
      {/* <Top/>
      <CommunityEditor onCreate={onCreate}/>
      <CommunityList onEdit={onEdit} onRemove={onRemove} diaryList={data}/> */}
      
      </div>
  );
}

export default Community;
