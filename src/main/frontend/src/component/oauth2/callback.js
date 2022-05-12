import React, { Component} from 'react';

class Callback extends Component{

    render() {
        alert('CallBack Page');
        const code = new URL(window.location.href).searchParams.get("code");
        return(
            <div>
                {code}
            </div>
        );
    }

}

export default Callback;