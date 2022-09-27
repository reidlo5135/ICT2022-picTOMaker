import React from 'react';
import {BrowserView, MobileView} from "react-device-detect";
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
import M_main from "./page/MainPage/Mobile_main";
import M_intro from "./page/IntroducePage/components/Mobile-intro";
import M_mypage from "./page/UserInfoPage/Mobile-mypage";
import M_community from "./page/CommunityPage/Mobile-Community";

export default function App(){

    return (
        <div className='App'>
            <MobileView>
                <AnimatePresence>
                    <Switch>
                        <Route exact path = '/' component={M_main}/>
                        <Route path = '/select' component={MenuSelect}/>
                        <Route path = '/signUp' component={LocalUserSignUp}/>
                        <Route path = '/myPage' component={M_mypage}/>
                        <Route path = '/introduce' component={M_intro}/>
                        <Route path = '/qna' component={QnA}/>
                        <Route path = '/community' component={M_community}/>
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
            </MobileView>
            <BrowserView>
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
            </BrowserView>
       </div>
    );
}