import React from 'react';
import '../App.css';
import {Route, Switch} from 'react-router-dom';
import Main from "./Main";
import SelectContent from "./Select-Content";
import SignUp from "./SignUp";
import Introduce from "./Introduce";
import QnA from "./QnA";
import Callback from "./oauth2/callback";
import MyPageContent from "./MyPage-Content";


export default function App(){

    return (
        <div className='App'>
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
        </div>
    );
}