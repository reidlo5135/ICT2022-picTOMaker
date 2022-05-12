import React, { Component} from 'react';
import '../App.css';
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Callback from "../component/oauth2/callback";

class CallBackPage extends Component {
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
                <Callback />
            </div>
        );
    }
}

export default CallBackPage;