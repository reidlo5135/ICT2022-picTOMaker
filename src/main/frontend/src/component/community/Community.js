import React,{useRef, useState,useEffect} from "react";
import axios from "axios";
import CEditor from "./CEditor";
import Ccomment from "./Ccomment";
import Top from "../contents/Top";
import CDetail from "./CDetail";
import CList from "./CList";
import Pagination from "./Pagination";

function Community() {
  const [data, setData] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [postsPerPage, setPostsPerPage] = useState(4);

  const dataId = useRef(0);

  const getData = async () => {
    try {
      await axios.get('/v1/api/community/find')
          .then((response) => {
            console.log('response : ', response.data);
            console.log('response : ', response.data.list);

            if(response.data.code === 0) {
                const res = response.data.list;
                console.log('community res : ' + res);
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
    } catch (err) {
      console.error(err);
    }
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
      console.log('community currentPosts data : ' + data);
      currentPosts = data.slice(indexOfFirst, indexOfLast);
      return currentPosts;
  };

  return (
      <div className="commu">
      <Top/>
      {/* <CList diaryList={data}/> */}
      <CList diaryList={currentPosts(data)}/>
      <Pagination
          postsPerPage={postsPerPage}
          totalPosts={data.length}
          paginate={setCurrentPage}
      />
      {/* <Top/>
      <CEditor onCreate={onCreate}/>
      <CList onEdit={onEdit} onRemove={onRemove} diaryList={data}/> */}
      
      </div>
  );
}

export default Community;
