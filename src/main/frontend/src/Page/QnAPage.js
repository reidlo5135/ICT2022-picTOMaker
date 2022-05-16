import React, { Component } from 'react';
import QnA from "../component/QnA"
import Top from "../component/Top"
import '../css/QnA.css';



class MyPage extends Component{
    render(){
        return (
            <div className="App">
                <div className='qnapage'>
                <QnA />
            </div>
            </div>
        );
    }
}

export default MyPage;