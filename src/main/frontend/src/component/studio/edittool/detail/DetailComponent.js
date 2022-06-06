import FigureComponent from "./FigureComponent";
import ImageComponent from "./ImageComponent";
import PencilComponent from "./PencilComponent";
import TextComponent from "./TextComponent";

export default function DetailComponent (props) {
    const mode = props.mode;
    const canvas = props.canvas;
    
    if (mode === "pencil") 
        return (<PencilComponent canvas={canvas}/>);
    else if (mode === "figure")
        return (<FigureComponent canvas={canvas}/>);
    else if (mode === "image") 
        return (<ImageComponent canvas={canvas}/>);
    else if (mode === "text" )
        return (<TextComponent/>);
    else 
        return (<></>)
}