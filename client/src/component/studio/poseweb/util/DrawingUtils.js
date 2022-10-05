

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
    let convertStartX = (Math.round(width*x) / 10) * 10;
    let convertStartY = Math.round(height*y);

    context.fillStyle= "#" + color;
    context.beginPath();
    context.arc(convertStartX,convertStartY,parseInt(thick)/2,0,2*Math.PI);
    context.stroke();
    context.fill();
    
}

export function drawBody(position,color,ctx) {
    ctx.fillStyle = "#" + color;
    ctx.beginPath();
    ctx.moveTo(Math.round(640*position[12].x), Math.round(480*position[12].y));
    ctx.lineTo(Math.round(640*position[11].x), Math.round(480*position[11].y));
    ctx.lineTo(Math.round(640*position[23].x), Math.round(480*position[23].y));
    ctx.lineTo(Math.round(640*position[24].x), Math.round(480*position[24].y));
    ctx.closePath();
    ctx.fill();
}

export function drawRect(position, color, ctx) {
    ctx.fillStyle = "#" + color;
    ctx.beginPath();
    ctx.moveTo(Math.round(640*position[3].x), Math.round(480*position[3].y));
    ctx.lineTo(Math.round(640*position[5].x), Math.round(480*position[5].y));
    ctx.lineTo(Math.round(640*position[9].x), Math.round(480*position[9].y));
    ctx.lineTo(Math.round(640*position[13].x), Math.round(480*position[13].y));
    ctx.lineTo(Math.round(640*position[17].x), Math.round(480*position[17].y));
    ctx.lineTo(Math.round(640*position[0].x), Math.round(480*position[0].y));
    ctx.lineTo(Math.round(640*position[1].x), Math.round(480*position[1].y));
    ctx.lineTo(Math.round(640*position[2].x), Math.round(480*position[2].y));
    ctx.closePath();
    ctx.fill();
}