import React from "react";
import logo from './PicTOmaker-LOGO.png';
import {KAKAO_AUTH_URL} from "./env";
import './App.css';

function App() {

  return (
    <div className="App">
      <header className="App-header">
        <h1>
            <img src={logo}/>
            <a href={KAKAO_AUTH_URL}>KAKAO LOGIN</a>
        </h1>
      </header>
    </div>
  );
}

export default App;
