import React, { Component } from 'react';
import MyPageContent from "../component/MyPage-Content"
import '../css/MyPage.css';


class MyPage extends Component{
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

                <MyPageContent />

            </div>
        );
    }
}

export default MyPage;