import React from "react";
import logo from './PicTOmaker-LOGO.png';
import {KAKAO_AUTH_URL, GOOGLE_AUTH_URL, NAVER_AUTH_URL} from "./component/oauth2/env";
import './App.css';

function App() {

  return (
    <div className="App">
        <h1>
            <img src={logo}/>
            <a href={KAKAO_AUTH_URL}>KAKAO LOGIN</a><br></br>
            <a href={GOOGLE_AUTH_URL}>GOOGLE LOGIN</a><br></br>
            <a href={NAVER_AUTH_URL}>NAVER LOGIN</a>
        </h1>
    </div>
  );
}

export default App;
