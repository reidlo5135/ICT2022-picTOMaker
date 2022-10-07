import React from "react";
import "./introduce.css";
import IntroducePage from "./components/IntroducePage"
import Top from "../../component/Top"
import Footer from "../../component/Footer"


function SelectPage() {
  return (
    <div className="introducepage">
      <Top/>
      <IntroducePage/>
      <Footer/>
    </div>
  );
}

export default SelectPage;