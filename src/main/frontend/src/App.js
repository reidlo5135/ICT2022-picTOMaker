import React, {Component, useEffect, useState} from 'react';
import './App.css';
import {BrowserRouter, Routes, Route, Link} from 'react-router-dom';
import MainPage from './Page/MainPage';
import SelectPage from './Page/SelectPage';
import LoginPage from './Page/LoginPage';
import SignUpPage from './Page/SignUpPage';
import axios from "axios";

class App extends Component{

    render(){
        const [profile, setProfile] = useState();
        useEffect(() => {
           axios.post("http://localhost:8080/oauth2/login").then((response) => {
               if(response.data) {
                   console.log(response.data);
                   setProfile(response.data);
               } else {
                   alert("failed to");
               }
           });
        }, []);
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
                <h1>{profile.email}</h1>
                <h1>{profile.nickname}</h1>
            </div>
        );
    }
}

export default App;