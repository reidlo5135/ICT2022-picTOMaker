import React,{useState,useEffect,useRef} from "react";
import {Link} from "react-router-dom";
import axios from 'axios';
import '../../css/Callback.css';
import img from "../../image/naver.png";

const CList = ({diaryList}) => {
    const access_token = localStorage.getItem('access_token');
    const provider = localStorage.getItem('provider');
    const [isLogged, setIsLogged] = useState(false);

    const getOAuthProf = () => {
        try {
            axios.post(`/v1/api/oauth2/profile/${provider}`, {
                access_token
            }).then((response) => {
                console.log('OAuth profile res data.data : ', response.data);
                console.log('OAuth get profile email : ', response.data.email);
                console.log('OAuth get profile nickname : ', response.data.nickname);
                console.log('OAuth get profile profile_image_url : ', response.data.profile_image_url);

                console.log('undefined : ', response.data.profile_image_url === undefined);

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
                console.log('Local profile res data : ', result);
                console.log('Local get profile email : ', result.email);
                console.log('Local get profile nickname : ', result.nick_name);
                console.log('Local get profile profile_image_url : ', result.profile_image_url);


                console.log('undefined : ', result.profile_image_url === undefined);

                localStorage.setItem("profile", JSON.stringify(result));
            });
        } catch (err) {
            console.error(err);
        }
    }
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
                                <div className="Ndate">{it.create_date}</div>
                            </div>
                        </Link>
                    ))}
                </tbody>
            </table>
        </div>
    );
};
export default CList;