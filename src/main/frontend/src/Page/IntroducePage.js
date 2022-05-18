import React from "react";
import "../css/Introduce.css";
import Top from "../component/Top"
import Introduce from "../component/Introduce"
import Footer from "../component/Footer"

function SelectPage() {
  return (
    <div className="introducepage">
      <Top/>
      <Introduce/>
      <Footer />
    </div>
  );
}

export default SelectPage;