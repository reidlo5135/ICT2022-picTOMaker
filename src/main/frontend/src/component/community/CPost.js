import {useEffect,useState,useRef} from "react";
import CItem from "./CItem";

const CPost = ({onEdit,onRemove,diaryList}) =>{

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
              emotion: Math.floor(Math.random() * 5) + 1,
              //Math.random()*5 = 0부터 4까지의 난수 생성(소수점까지 포함)
              //Math.floor = 소수점을 없애줌 , +1 = 5까지   
              created_date: new Date().getTime() + 1,
              id: dataId.current++
            };
          });
          setDate(initData);
          
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
                            <div className="Ntitle">게시글 이름</div>
                            <div className="Ndate">2022-01-15</div>
                        </div>      
                        ))}
                    </div>
				</div>
			</div>
    )
}

export default CPost;