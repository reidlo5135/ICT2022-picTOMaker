import React, { Component } from 'react';
import Login from "../component/Login"
import '../App.css';
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";


class LoginPage extends Component{
    constructor(props){
        super(props);
        this.state = {
            Subject: {title:"Web", sub:"world wide web!"},
            Contents: [
                {id:1, title:"HTML", desc:"HTML is HyperText Markup Language."},
                {id:2, title:"CSS", desc:"CSs is for design"},
                {id:3, title:"JavaScript", desc:"JavaScript is for interactive"}
            ]
        }
    }
    render(){
        return (
            <div className="App">
                <Login />
            </div>
        );
    }
}

export default LoginPage;