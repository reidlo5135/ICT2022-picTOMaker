import React, {Component} from 'react';
import './App.css';
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import MainPage from './Page/MainPage';
import SelectPage from './Page/SelectPage';
import LoginPage from './Page/LoginPage';
import SignUpPage from './Page/SignUpPage';
import MyPage from './component/MyPage-Content';
import CallBackPage from './Page/CallBackPage';

class App extends Component{

    render(){
        return (
            <div className='App'>
                <BrowserRouter>
                    <Routes>
                        <Route path = '/' element={<MainPage />}/>
                        <Route path = '/Login' element={<LoginPage />}/>
                        <Route path = '/Select' element={<SelectPage />}/>
                        <Route path = '/SignUp' element={<SignUpPage />}/>
                        <Route path = '/MyPage' element={<MyPage />}/>
                        <Route path = '/oauth2/redirect/kakao' element={<CallBackPage />} />
                    </Routes>
                </BrowserRouter>
            </div>
        );
    }
}

export default App;