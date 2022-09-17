import React, {useEffect} from 'react';
import {useCookies} from "react-cookie";
import {useHistory} from "react-router";
import axios from "axios";
import '../App.css';
import {Route, Switch} from 'react-router-dom';
import Main from "./Main";
import SelectContent from "../Page/SelectPage";
import SignUp from "./user/SignUp";
import Introduce from "../Page/IntroducePage";
import QnA from "./contents/QnA";
import Community from "./community/Community";
import Terms from "./contents/terms";
import Callback from "./oauth2/callback";
import MyPageContent from "./myPage/MyPage";
import PoseWebStudio from "./studio/poseweb/PoseWebStudio";
import EditTool from './studio/edittool/EditTool';
import EditImageTool from "./studio/edittool/EditImageTool";
import {AnimatePresence} from "framer-motion";
import HandWebStudio from './studio/handweb/HandWebStudio';
import CDetail from './community/CDetail';
import CPosting from './community/CPosting';

export default function App(){
    const [cookies, setCookie] = useCookies(["accessToken"]);
    const history = useHistory();
    const JWT_EXPIRE_TIME = 60 * 60 * 1000;

    useEffect(() => {
        console.log("app.js token : ", cookies.accessToken);
        axios.post('/v1/api/user/valid', {}, {
            headers: {
                "X-AUTH-TOKEN": cookies.accessToken
            }
        }).then((response) => {
            if(response.data.data === false) {
                axios.post('/v1/api/user/reissue', {
                    accessToken: cookies.accessToken,
                    refreshToken: localStorage.getItem("refresh_token")
                }).then((response) => {
                    setCookie("accessToken", response.data.accessToken);
                    localStorage.setItem("refresh_token", response.data.refreshToken);
                    history.push("/");
                }).catch((err) => {
                    alert(err.response.data.msg);
                });
            }
        }).catch((err) => {
            alert(err.response.data.msg);
        })
    }, []);

    return (
        <div className='App'>
            <AnimatePresence>
                <Switch>
                    <Route exact path = '/' component={Main}/>
                    <Route path = '/select' component={SelectContent}/>
                    <Route path = '/signUp' component={SignUp}/>
                    <Route path = '/myPage' component={MyPageContent}/>
                    <Route path = '/introduce' component={Introduce}/>
                    <Route path = '/qna' component={QnA}/>
                    <Route path = '/community' component={Community}/>
                    <Route path = '/cdetail/:id' component={CDetail}/>
                    <Route path = '/cposting' component={CPosting}/>
                    <Route path = '/terms' component={Terms}/>
                    <Route path = '/oauth2/redirect/kakao' component={Callback} />
                    <Route path='/oauth2/redirect/naver' component={Callback} />
                    <Route path='/oauth2/redirect/google' component={Callback} />
                    <Route path='/studio/pose/web' component={PoseWebStudio}/>
                    <Route path='/studio/hand/web' component={HandWebStudio}/>
                    <Route path='/edit' component={EditTool} />
                    <Route path='/tool/image' component={EditImageTool} />
                </Switch>
            </AnimatePresence>
       </div>
    );
}