import React from "react";
import "./introduce.css";
import IntroducePage from "./components/IntroducePage"
import MobileTop from "../../component/mobile-components/Mobile-Top"
import Footer from "../../component/Footer"
import Top from "../../component/Top"


function Introduce() {
  return (
    <div className="introducepage">
      <Top/>
      <IntroducePage/>
      <Footer/>
    </div>
  );
}

export default Introduce;