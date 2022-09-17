import React, {useEffect} from 'react';
import {useCookies} from "react-cookie";
import {useHistory} from "react-router";
import axios from "axios";
import './App.css';
import {Route, Switch} from 'react-router-dom';
import Main from "./page/MainPage/Main";
import MenuSelect from "./page/MenuSelectPage/MenuSelect";
import LocalUserSignUp from "./page/UserSignUpPage/LocalUserSignUp";
import Introduce from "./page/IntroducePage/Introduce";
import QnA from "./page/QnAPage/QnA";
import Community from "./page/CommunityPage/Community";
import UserAgreement from "./page/UserAgreementsPage/UserAgreement";
import SocialUserCallback from "./page/SocialLoginPage/SocialUserCallback";
import MyPageContent from "./page/UserInfoPage/MyPage";
import PoseWebStudio from "./component/studio/poseweb/PoseWebStudio";
import EditTool from './component/studio/edittool/EditTool';
import EditImageTool from "./component/studio/edittool/EditImageTool";
import {AnimatePresence} from "framer-motion";
import HandWebStudio from './component/studio/handweb/HandWebStudio';
import CommunitytDetails from './page/CommunityPage/components/CommunitytDetails';
import CommunityPosting from './page/CommunityPage/components/CommunityPosting';

export default function App(){
    const [cookies, setCookie] = useCookies(["accessToken"]);
    const history = useHistory();

    useEffect(() => {
        console.log("app.js token : ", cookies.accessToken);
        axios.post('/v1/api/user/valid', {}, {
            headers: {
                "X-AUTH-TOKEN": cookies.accessToken
            }
        }).then((response) => {
            console.log("App.js response : ", response.data);
            if(response.data.data === false) {
                axios.post('/v1/api/user/reissue', {
                    accessToken: cookies.accessToken,
                    refreshToken: localStorage.getItem("refresh_token")
                }).then((response) => {
                    setCookie("accessToken", response.data.data.accessToken);
                    localStorage.setItem("refresh_token", response.data.data.refreshToken);
                    history.push("/");
                }).catch((err) => {
                    alert(err.response.data.msg);
                });
            }
        }).catch((err) => {
            console.log("err : ", err.response.data);
        })
    }, []);

    return (
        <div className='App'>
            <AnimatePresence>
                <Switch>
                    <Route exact path = '/' component={Main}/>
                    <Route path = '/select' component={MenuSelect}/>
                    <Route path = '/signUp' component={LocalUserSignUp}/>
                    <Route path = '/myPage' component={MyPageContent}/>
                    <Route path = '/introduce' component={Introduce}/>
                    <Route path = '/qna' component={QnA}/>
                    <Route path = '/community' component={Community}/>
                    <Route path = '/cdetail/:id' component={CommunitytDetails}/>
                    <Route path = '/cposting' component={CommunityPosting}/>
                    <Route path = '/terms' component={UserAgreement}/>
                    <Route path = '/oauth2/redirect/kakao' component={SocialUserCallback} />
                    <Route path='/oauth2/redirect/naver' component={SocialUserCallback} />
                    <Route path='/oauth2/redirect/google' component={SocialUserCallback} />
                    <Route path='/studio/pose/web' component={PoseWebStudio}/>
                    <Route path='/studio/hand/web' component={HandWebStudio}/>
                    <Route path='/edit' component={EditTool} />
                    <Route path='/tool/image' component={EditImageTool} />
                </Switch>
            </AnimatePresence>
       </div>
    );
}