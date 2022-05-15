import React, { Component } from 'react';
import Introduce from "../component/Introduce"
import '../App.css';
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Top from "../component/Top";
import Footer from "../component/Footer";

class LoginPage extends Component{
  
    render(){
        return (
            <div className="App">
                <Top/>
                <Introduce />
                <div className="Foot"></div>
                <Footer/>
            </div>
        );
    }
}

export default LoginPage;