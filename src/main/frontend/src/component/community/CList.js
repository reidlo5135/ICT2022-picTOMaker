import {useState,useEffect,useRef} from "react";
import {Link} from "react-router-dom";
import axios from 'axios';
import '../../css/Callback.css';
import getOAuthProf from "../user/Profile";
import getLocalProf from "../user/Profile";
import img from "../../image/naver.png";

const CList = ({diaryList,match}) => {
    const access_token = localStorage.getItem('access_token');
    const provider = localStorage.getItem('provider');
    const [isLogged, setIsLogged] = useState(false);

    /* useEffect(() => {
        if(provider != 'LOCAL') {
            getOAuthProf();
        } else if(provider === 'LOCAL') {
            getLocalProf();
        } else if(provider == null) {
            setIsLogged(false);
        }
        if(access_token != null) {
            setIsLogged(true);
            localStorage.setItem("isLogged", isLogged.toString());
        }
    }, []); */
  return (
    <div className="notice">
        <div className="title">
            커뮤니티
           {/*  {
            access_token === null ? <></> : 
                <Link to="/cposting">
                    <div className="moreBtn">
                        글쓰기
                    </div>
                </Link>
            } */}
            <Link to="/cposting">
                    <div className="moreBtn">
                        글쓰기
                    </div>
                </Link>
        </div>
        <table className="noticeList">
            <tr>
                <th className="cnickname"><div>닉네임</div></th>
                <th className="ctitle"><div>타이틀</div></th>
                <th className="cdate"><div>작성일</div></th>
            </tr>
                {diaryList&&diaryList.map((it)=> (
                    <Link to={`/cdetail/${it.id}`} key={it.id}>
                    <div className="listItem">
                        <span class="left_top"></span>
                        <span class="right_top"></span>
                        <span class="right_bottom"></span>
                        <span class="left_bottom"></span>
                        <span className="temimg"><img className="itemImg" src={img} alt="게시글 첨부 이미지"/></span>
                        <div className="Ntitle">{it.id}</div>
                        <div className="Ndate">{it.create_date}</div>
                    </div>  
                    </Link>    
                ))}
        </table>
    </div>
  );
};
export default CList;