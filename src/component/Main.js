import React, { Component } from 'react';
import "../css/Main-Contents.css"
import { Link } from "react-router-dom";
import "../css/font.css"


class Main extends Component{
    render(){
      return(
        <div className='explanation'>
          <div className='Title'>
            <div className='MainTitle'>
              쉽고 재밌는 픽토그램 제작 플랫폼
            </div>
            <div className='SubTitle'>
              PC와 Mobile을 통해 간단하고 재밌게
              <div>
              픽토그램을 제작해 보세요.
              </div>
            </div>
            <Link to="/Login">
            <button className='MainButton'>
              무료로 시작
            </button>
            </Link>
          </div>
        </div>
      );
    }
  }

export default Main;