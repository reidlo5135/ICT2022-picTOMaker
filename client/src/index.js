import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './component/App';
import {BrowserRouter} from "react-router-dom";
import {CookiesProvider} from "react-cookie";
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <CookiesProvider>
        <BrowserRouter>
            <App />
        </BrowserRouter>
    </CookiesProvider>
);

reportWebVitals();