import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './component/App';
import {BrowserRouter} from "react-router-dom";
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
      <React.StrictMode>
          <App />
      </React.StrictMode>
  </BrowserRouter>
);

reportWebVitals();

// ReactDOM.render(
//     <Router>
//         <App />
//     </Router>,
//     document.getElementById('root')
// );