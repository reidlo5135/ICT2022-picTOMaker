import React from "react";
import "../css/Introduce.css";
import Introduce from "../component/contents/Introduce"
import Top from "../component/contents/Top"
import Footer from "../component/contents/Footer"


function SelectPage() {
  return (
    <div className="introducepage">
      <Top/>
      <Introduce/>
      <Footer/>
    </div>
  );
}

export default SelectPage;