import {useState,useEffect,useRef} from "react";
import {Link} from "react-router-dom";
import axios from 'axios';
import '../../css/Callback.css';

const CPost = ({diaryList,match}) => {
  return (
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
            {diaryList&&diaryList.map((it)=> (
                <div className="listItem">
                <Link to={`/cpostview/${it.id}`} key={it.id}><div className="Ntitle">{it.id}</div></Link>
                <div className="Ndate">2022-01-15</div>
            </div>      
            ))}
        </div>
    </div>
</div>
  );
};
export default CPost;