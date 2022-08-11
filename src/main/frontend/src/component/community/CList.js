import {useState,useEffect,useRef} from "react";
import {Link} from "react-router-dom";
import axios from 'axios';
import '../../css/Callback.css';
import getOAuthProf from "../user/Profile";
import getLocalProf from "../user/Profile";

const CList = ({diaryList,match}) => {
   
  return (
    <div className="notice">
        <div className="title">
            커뮤니티
            
        </div>
        <div className="noticeList">
            <div>
                {diaryList&&diaryList.map((it)=> (
                    <div className="listItem">
                    <Link to={`/cdetail/${it.id}`} key={it.id}><div className="Ntitle">{it.id}</div></Link>
                    <div className="Ndate">{it.create_date}</div>
                </div>      
                ))}
            </div>
        </div>
    </div>
  );
};
export default CList;