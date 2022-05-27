export function drawLine (startX,startY,endX,endY,context,width,height,thick,color) {
    let convertStartX = (Math.round(width*startX) / 10) * 10;
    let convertStartY = Math.round(height*startY);
    let convertEndX = Math.round(width*endX);
    let convertEndY = Math.round(height*endY);

    context.strokeStyle= "#" + color;
    context.lineCap = "round";
    context.lineJoin = "round";
    context.lineWidth = thick;

    context.beginPath();
    context.moveTo(convertStartX,convertStartY);
    context.lineTo(convertEndX,convertEndY);
    context.closePath();
    context.stroke();
}

export function drawHead(x,y,context,width,height,thick,color) {
    const weight = 25;

    let convertStartX = (Math.round(width*x) / 10) * 10;
    let convertStartY = Math.round(height*y);

    context.strokeStyle= "#" + color;
    context.lineCap = "round";
    context.lineJoin = "round";
    context.lineWidth = parseInt(thick) + weight;
    context.beginPath();
    context.moveTo(convertStartX,convertStartY);
    context.lineTo(convertStartX,convertStartY);
    context.closePath();
    context.stroke();
}