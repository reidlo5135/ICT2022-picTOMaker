import React from 'react';
import { Component } from 'react';
import Info from '../component/Info';

class InfoPage extends Component{
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
                <Info />
            </div>
        );
    }
}

export default InfoPage;