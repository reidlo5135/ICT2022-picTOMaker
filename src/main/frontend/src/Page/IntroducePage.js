import React from "react";
import "../css/Introduce.css";
import Top from "../component/contents/Top"
import Introduce from "../component/contents/Introduce"
import Footer from "../component/contents/Footer"

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