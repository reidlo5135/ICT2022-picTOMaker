import React,{useRef, useState,useEffect} from "react";
import CEditor from "./CEditor";
import Ccomment from "./Ccomment";
import Top from "../contents/Top";
import CDetail from "./CDetail";
import CList from "./CList";
import Pagination from "./Pagination";

function Community() {
  const [data,setDate] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [postsPerPage, setPostsPerPage] = useState(4);

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
        create_date: new Date().getTime() + 1,
        id: dataId.current++
      };
    });
    setDate(initData);
  };

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
    setDate([ newItem,...data]);
  };

  const onRemove = (targetId) => {
    const newDiaryList = data.filter((it) => it.id !== targetId);
    setDate(newDiaryList);
  }

  const onEdit = (targetId,newContent) => {
    setDate(
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
