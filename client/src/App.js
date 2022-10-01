import React, {useEffect} from 'react';
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
import CommunityDetails from "./page/CommunityPage/components/CommunityDetails";
import CommunityPosting from './page/CommunityPage/components/CommunityPosting';
import M_Main from "./page/MainPage/mobile-components/Mobile-Main";
import M_UserCallback from "./page/MainPage/mobile-components/Mobile-UserCallback";
import M_SocialUserCallback from "./page/SocialLoginPage/mobile-components/Mobile-SocialUserCallback";
import M_Intro from "./page/IntroducePage/components/Mobile-intro";
import M_MyPage from "./page/UserInfoPage/mobile-components/Mobile-MyPage";
import M_Community from "./page/CommunityPage/mobile-components/Mobile-Community";
import M_Select from "./page/MenuSelectPage/mobile-select/Mobile-MenuSelect";
import MobileWebStudio from "./component/studio/poseweb/Mobile-WebStudio";
import SelectMobileOrBrowser from "./page/MenuSelectPage/components/SelectMobileOrBrowser";
import M_EditTool from "./component/studio/edittool/mobile-edittool/Mobile-EditTool";

export default function App(){

    return (
        <div className='App'>
            <MobileView>
                <AnimatePresence>
                    <Switch>
                        <Route exact path = '/' component={M_Main}/>
                        <Route path = '/select' component={M_Select}/>
                        <Route path = '/signUp' component={LocalUserSignUp}/>
                        <Route path = '/myPage' component={M_MyPage}/>
                        <Route path = '/introduce' component={M_Intro}/>
                        <Route path = '/qna' component={QnA}/>
                        <Route path = '/community' component={M_Community}/>
                        <Route path = '/cdetail/:id' component={CommunityDetails}/>
                        <Route path = '/cposting' component={CommunityPosting}/>
                        <Route path = '/terms' component={UserAgreement}/>
                        <Route path= '/user/redirect' component={M_UserCallback} />'
                        <Route path = '/oauth2/redirect/kakao' component={M_SocialUserCallback} />
                        <Route path= '/oauth2/redirect/naver' component={M_SocialUserCallback} />
                        <Route path= '/oauth2/redirect/google' component={M_SocialUserCallback} />
                        <Route path= '/studio/pose/mobile' component={MobileWebStudio} />
                        <Route path= '/edit' component={M_EditTool} />
                        <Route path= '/tool/image' component={EditImageTool} />
                    </Switch>
                </AnimatePresence>
            </MobileView>
            <BrowserView>
                <AnimatePresence>
                    <Switch>
                        <Route exact path = '/' component={Main}/>
                        <Route path = '/select' component={MenuSelect}/>
                        <Route exact path= '/edit/select' component={SelectMobileOrBrowser} />
                        <Route path = '/signUp' component={LocalUserSignUp}/>
                        <Route path = '/myPage' component={MyPageContent}/>
                        <Route path = '/introduce' component={Introduce}/>
                        <Route path = '/qna' component={QnA}/>
                        <Route path = '/community' component={Community}/>
                        <Route path = '/cdetail/:id' component={CommunityDetails}/>
                        <Route path = '/cposting' component={CommunityPosting}/>
                        <Route path = '/terms' component={UserAgreement}/>
                        <Route path = '/oauth2/redirect/kakao' component={SocialUserCallback} />
                        <Route path= '/oauth2/redirect/naver' component={SocialUserCallback} />
                        <Route path= '/oauth2/redirect/google' component={SocialUserCallback} />
                        <Route path= '/studio/pose/web' component={PoseWebStudio}/>
                        <Route path= '/studio/hand/web' component={HandWebStudio}/>
                        <Route path='/edit' component={EditTool} />
                        <Route path='/tool/image' component={EditImageTool} />
                    </Switch>
                </AnimatePresence>
            </BrowserView>
       </div>
    );
}