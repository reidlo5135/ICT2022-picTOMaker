import React, { Component } from 'react';
import './App.css';
import {BrowserRouter, Routes, Route} from 'react-router-dom';
import MainPage from './Page/MainPage';
import SelectPage from './Page/SelectPage';

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
            </div>
        );
    }
}

export default App;
