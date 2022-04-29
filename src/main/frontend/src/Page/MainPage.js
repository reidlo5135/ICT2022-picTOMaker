import React, { Component } from 'react';
import Best from "../component/Main-best"
import Main from "../component/Main"
import Top from "../component/Top"
import '../App.css';
import Footer from "../component/Footer"
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";


class MainPage extends Component{
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

                <Top/>
                <Main />
                <Best />
                <Footer />
            </div>
        );
    }
}
/*커밋*/

export default MainPage;