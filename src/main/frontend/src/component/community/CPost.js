import {useEffect,useState,useRef} from "react";
import CItem from "./CItem";
import {Link} from "react-router-dom";
const CPost = ({diaryList}) =>{

    const [data,setDate] = useState([]);

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
              created_date: new Date().getTime() + 1,
              id: dataId.current++
            };
          });
          setDate(initData);
    }
    const onPost = (targetId) => {
      const newPost = data.filter((it) => it.id !== targetId);
      setDate(newPost);
    }

    return(
            <div className="notice">
				<div className="title">
				커뮤니티
				<div className="moreBtn">
					더보기
					<img src="${path}/static/img/ic_more@2x.png"/>
					</div>
				</div>
				<div className="noticeList">
					
					<div>
                        {diaryList.map((it)=> (
                            <div className="listItem">
                            <Link to={`/CPost/${it.id}`} key={it.id} ><div className="Ntitle">게시글 이름</div></Link>
                            <div className="Ndate">2022-01-15</div>
                        </div>      
                        ))}
                    </div>
				</div>
			</div>
    )
}

export default CPost;