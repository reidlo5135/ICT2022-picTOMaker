import React from 'react';
import '../App.css';
import {Route, Switch} from 'react-router-dom';
import Main from "./Main";
import SelectContent from "../Page/SelectPage";
import SignUp from "./user/SignUp";
import Introduce from "../Page/IntroducePage";
import QnA from "./contents/QnA";
import Callback from "./oauth2/callback";
import MyPageContent from "./myPage/MyPage";
import {AnimatePresence} from "framer-motion";


export default function App(){

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
                    <Route path = '/oauth2/redirect/kakao' component={Callback} />
                    <Route path='/oauth2/redirect/naver' component={Callback} />
                    <Route path='/oauth2/redirect/google' component={Callback} />
                </Switch>
            </AnimatePresence>
       </div>
    );
}