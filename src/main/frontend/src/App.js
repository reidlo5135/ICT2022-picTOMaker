import React, {Component} from 'react';
import './App.css';
import {Route, Routes} from 'react-router-dom';
import MainPage from './Page/MainPage';
import SelectPage from './Page/SelectPage';
import SignUpPage from './Page/SignUpPage';
import MyPage from './component/MyPage-Content';
import CallbackPage from './Page/CallBackPage';

class App extends Component{

    render(){
        const access_token =  localStorage.getItem("access_token");
        const refresh_token =  localStorage.getItem("refresh_token");
        const profile = localStorage.getItem("profile");
        console.log(access_token, refresh_token, profile);

        return (
            <div className='App'>
                <Routes>
                    <Route path = '/' element={<MainPage />}/>
                    <Route path = '/Select' element={<SelectPage />}/>
                    <Route path = '/SignUp' element={<SignUpPage />}/>
                    <Route path = '/MyPage' element={<MyPage />}/>
                    <Route path = '/oauth2/redirect/kakao' element={<CallbackPage />} />
                    <Route path='/oauth2/redirect/naver' element={<CallbackPage />} />
                    <Route path='/oauth2/redirect/google' element={<CallbackPage />} />
                </Routes>
            </div>
        );
    }
}

export default App;