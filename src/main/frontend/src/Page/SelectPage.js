import React from 'react';
import { Component } from 'react';
import '../App.css';
import Top from "../component/Top"
import Footer from "../component/Footer"
import SelectContent from "../component/Select-Content"




class SelectPage extends Component{
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
                <SelectContent/>
                <Footer />
            </div>
        );
    }
}
/*커밋*/

export default SelectPage;
  