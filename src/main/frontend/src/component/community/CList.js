import {useState,useEffect,useRef} from "react";
import {Link} from "react-router-dom";
import axios from 'axios';
import '../../css/Callback.css';
import getOAuthProf from "../user/Profile";
import getLocalProf from "../user/Profile";

const CList = ({diaryList,match}) => {
    const access_token = localStorage.getItem('access_token');
    const provider = localStorage.getItem('provider');
    const [isLogged, setIsLogged] = useState(false);

    useEffect(() => {
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
    }, []);
  return (
    <div className="notice">
        <div className="title">
            커뮤니티
            {
            access_token === null ? <></> : 
                <Link to="/cposting">
                    <div className="moreBtn">
                        글쓰기
                    </div>
                </Link>
            }
            
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