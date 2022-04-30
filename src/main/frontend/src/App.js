import React, { Component } from 'react';
import './App.css';
import {BrowserRouter, Routes, Route, Link} from 'react-router-dom';
import MainPage from './Page/MainPage';
import SelectPage from './Page/SelectPage';
import LoginPage from './Page/LoginPage';
import SignUpPage from './Page/SignUpPage';

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
                    </Routes>
                </BrowserRouter>
            </div>
        );
    }
}

export default App;