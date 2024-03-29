import React, {useState,useEffect} from "react";
import {Link} from "react-router-dom";
import axios from 'axios';
import '../../SocialLoginPage/socialUserCallback.css';

const CommunityList = ({diaryList}) => {
    const access_token = localStorage.getItem('access_token');
    const provider = localStorage.getItem('provider');
    const [isLogged, setIsLogged] = useState(false);

    const getOAuthProf = () => {
        try {
            axios.post(`/v1/api/oauth2/profile/${provider}`, {
                access_token
            }).then((response) => {
                localStorage.setItem("profile", JSON.stringify(response.data));
            });
        } catch (err) {
            console.error(err);
        }
    }

    const getLocalProf = async () => {
        try {
            await axios.post('/v1/api/user/profile', {
                access_token
            }).then((response) => {
                const result = response.data['result'];
                localStorage.setItem("profile", JSON.stringify(result));
            });
        } catch (err) {
            console.error(err);
        }
    }
    useEffect(() => {
        if(provider !== 'LOCAL') {
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
                {/* <Link to="/cposting">
                    <div className="moreBtn">
                        글쓰기
                    </div>
            </Link> */}
            </div>
            <table className="noticeList">
                <thead>
                <tr>
                    <th className="cnickname"><div>닉네임</div></th>
                    <th className="ctitle"><div>타이틀</div></th>
                    <th className="cdate"><div>작성일</div></th>
                </tr>
                </thead>
                <tbody>
                    {diaryList&&diaryList.map((it)=> (
                        <Link to={`/cdetail/${it.id}`} key={it.id}>
                            <div className="listItem">
                                <span className="left_top"></span>
                                <span className="right_top"></span>
                                <span className="right_bottom"></span>
                                <span className="left_bottom"></span>
                                {/*<span className="temimg"><img className="itemImg" src={img} alt="게시글 첨부 이미지"/></span>*/}
                                <div className="Nauthor">{it.author}</div>
                                <div className="Ntitle">{it.title}</div>
                                <div className="Ndate">{it.createdDate}</div>
                            </div>
                        </Link>
                    ))}
                </tbody>
            </table>
        </div>
    );
};
export default CommunityList;