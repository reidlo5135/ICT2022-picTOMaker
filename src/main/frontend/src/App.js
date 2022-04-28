import React, { Component } from 'react';
import './App.css';
import {BrowserRouter, Routes, Route} from 'react-router-dom';
import MainPage from './Page/MainPage';
import SelectPage from './Page/SelectPage';
import {KAKAO_AUTH_URL, NAVER_AUTH_URL, GOOGLE_AUTH_URL} from "./component/oauth2/env";

class App extends Component{

    render(){
        return (
            <div className='App'>
                <BrowserRouter>
                    <Routes >
                        <Route path = '/' element={<MainPage />}/>
                        <Route path = '/Select' element={<SelectPage />}/>
                    </Routes>
                </BrowserRouter>
                <div>
                    <a href={KAKAO_AUTH_URL}>KAKAO</a>
                    <a href={NAVER_AUTH_URL}>NAVER</a>
                    <a href={GOOGLE_AUTH_URL}>Google</a>
                </div>
            </div>
        );
    }
}

export default App;
